import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;

import lejos.robotics.mapping.LineMap;
import lejos.geom.Rectangle;
import lejos.geom.Line;
import lejos.robotics.navigation.Pose;
import lejos.geom.Point;

// MAC RADAR: 00:16:53:1B:69:93

public class Master3 {
	private static final byte ROTATE = 0;
	private static final byte ROTATETO = 1;
	private static final byte RANGE = 2;
	private static final byte STOP = 3;
	private static final int NUM_OF_MEASURES = 36;
	private static final int NUM_OF_POINTS = 3;
	private NXTComm nxtComm;
	private DataOutputStream dos;
	private DataInputStream dis;	
	// NXT BRICK ID
	private static final String NXT_ID = "NXT2";	
	
	private float sendCommand(byte command, float param) {
		try {
			dos.writeByte(command);
			dos.writeFloat(param);
			dos.flush();
			return dis.readFloat();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return -1;
		}
	}

	private static FileInputStream getFile(String path) {
		try { 
			return  new FileInputStream(path);
		} catch (Exception e) {
			System.err.println("Find file Exception");
			return null;
		}

	}

	private static BufferedReader openFile(InputStreamReader fin) {
		try {
			return new BufferedReader(fin); 
		} catch (Exception e) {
			System.err.println("Open file Exception");
			return null;
		}
	}
	
	/**
	 * Connect to the NXT
	 *
	 */
	private void connect() {
		try {
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Blluetooth communication */
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);			
			NXTInfo[] nxtInfo = nxtComm.search(Master3.NXT_ID);
			if (nxtInfo.length == 0) {
				System.err.println("NO NXT found");
				System.exit(1);
			}
			
			if (!nxtComm.open(nxtInfo[0])) {
				System.err.println("Failed to open NXT");
				System.exit(1);
			}
			
			dis = new DataInputStream(nxtComm.getInputStream());
			dos = new DataOutputStream(nxtComm.getOutputStream());
			
		} catch (NXTCommException e) {
			System.err.println("NXTComm Exception: "  + e.getMessage());
			System.exit(1);
		}
	}
	/**
	 * Terminate the program and send stop command to the robot
	 *
	 */
	private void close() {
		try {
			dos.writeByte(STOP);
			dos.writeFloat(0f);
			dos.flush();
			Thread.sleep(200);
			System.exit(0);
		} catch (Exception ioe) {
			System.err.println("IO Exception");
		}
	}	
	
	static LineMap makeMap(Line [] lines){
		float limit;
		limit=220;

		lines[0] = new Line(0,0,160,0);
		lines[1] = new Line(160,0,78,106);
		lines[2] = new Line(78,106,151,162);
		lines[3] = new Line(151,162,0,217);
		lines[4] = new Line(0,217,0,0);
		lines[5] = new Line(34,39,44,39);
		lines[6] = new Line(44,39,44,58);
		lines[7] = new Line(44,58,34,58);
		lines[8] = new Line(34,58,34,39);

		return new LineMap(lines,new Rectangle(0,0,limit,limit));
	}

	static float norm(float x, float y){
		float norm = (float) Math.exp(-(x-y)*(x-y)/30);
		if(norm<0.00001) return 0.00001f;
		return norm;
	}

	static void printa(float[][] a){
		for (int j = 0;j<NUM_OF_MEASURES ;j++ ) {
			System.out.print(j +"°  ");
		}
		for (int i = 0;i<NUM_OF_POINTS ;i++ ) {
			System.out.println("");
			for (int j = 0;j<NUM_OF_MEASURES ;j++ ) {
				System.out.printf("%.2f ",a[i][j]);
			}
		}
		System.out.println("");
	}

	static void p(String s){
		System.out.println(s);
	}

	public static void main(String[] args) {
		int n;
		byte cmd = 0; float param = 0f; float ret=0f; float z=0f;

		Scanner scan = new Scanner( System.in );		
		Master3 master = new Master3();
		float angle_interval =  360/NUM_OF_MEASURES;
		Line[] lines = new Line[9];
		LineMap map = makeMap(lines);
		FileInputStream fin = getFile("/home/ccsl/radar/alejos/Radar/P" + args[0]);
		InputStreamReader isr = new InputStreamReader(fin);
		BufferedReader din = openFile(isr);
//		System.out.print("Enter the number of points:");
//		n = (int) scan.nextFloat();

		float ranges[][] = new float[NUM_OF_POINTS][NUM_OF_MEASURES];
		float belief[][] = new float[NUM_OF_POINTS][NUM_OF_MEASURES];
		float move[][] = new float[NUM_OF_POINTS][NUM_OF_MEASURES];
		Point points[] = new Point[NUM_OF_POINTS];		
		points[0] = new Point(100f, 27f); 
		points[1] = new Point(31f, 156f);
		points[2] = new Point(102f, 156f); 
		float alpha;

		for (int i = 0; i < NUM_OF_POINTS; i++ ) {
			//float x = scan.nextFloat();
			//float y = scan.nextFloat();
			for (int j=0; j < NUM_OF_MEASURES; j++){
				ranges[i][j] = map.range(new Pose(points[i].x, points[i].y, j*angle_interval));
				belief[i][j] = (1f/(NUM_OF_POINTS*NUM_OF_MEASURES));
				}
		}
		// uncomment to connect with NXT2 and read measures
		// master.connect();
		for (int i = 0; i < NUM_OF_MEASURES ; i++) {
			//master.sendCommand((byte)1, 5 * i * angle_interval);
			//z = master.sendCommand((byte)2,0f);

			try { z = Float.parseFloat(din.readLine()); }
			 catch (Exception e ) {
				System.err.println("erro");
			}

			double sum = 0 ;

			for(int j = 0; j < NUM_OF_POINTS; j++){
				for (int k = 0 ; k < NUM_OF_MEASURES ; k++ ) {
					alpha = norm(z,ranges[j][k]);
					//System.out.println(alpha);
					belief[j][k] *= alpha;
					sum += belief[j][k];
				}
				System.out.println("");
			}

			/*
			if (sum < 0.001){
				for(int j = 0; j < NUM_OF_POINTS; j++){
					for (int k = 0 ; k < NUM_OF_MEASURES ; k++ ) {
						alpha = norm(z,ranges[j][k]);
						belief[j][k] /= alpha;
					}
				}
				continue;
			}*/

			// Normalização
			for(int j = 0; j < NUM_OF_POINTS; j++)
				for (int k = 0 ; k < NUM_OF_MEASURES ; k++ )
					belief[j][k] /= sum;

			System.out.println(sum);


			// MOVIMENTO
			System.arraycopy(belief,0,move,0,belief.length);
			
			System.out.print("move");
			printa(move);

			System.out.print("beli");
			printa(belief);

			for(int j = 0; j < NUM_OF_POINTS; j++)
				for (int k = 0 ; k < NUM_OF_MEASURES ; k++ ){
					//System.out.println((k+1)%NUM_OF_MEASURES + "->" + k);
					belief[j][k] = move[j][(k+1)%NUM_OF_MEASURES];
				}
		}

		float max = 0f; float maxi = 0f;
		for (int q = 0; q < NUM_OF_POINTS ; q++ ) {
			float sum = 0;
			for (int j =0 ;j < NUM_OF_MEASURES ;j++ ) {
				sum += belief[q][j];
			}
			//belief[i][0] = sum;
			System.out.println("P" + (q+1) +": "+ sum);
			if(max < sum){
				max = sum;
				maxi = q;
			}
		}



		//System.out.println("\nÉ mais provável que eu esteja no ponto P" + (maxi+1));
	}
}