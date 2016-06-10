import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;

import lejos.robotics.mapping.LineMap;
import lejos.geom.Rectangle;
import lejos.geom.Line;
import lejos.robotics.navigation.Pose;

public class Master {
	private static final byte ROTATE = 0;
	private static final byte ROTATETO = 1;
	private static final byte RANGE = 2;
	private static final byte STOP = 3;
	private static final int NUM_OF_MEASURES = 5;
	
	private NXTComm nxtComm;
	private DataOutputStream dos;
	private DataInputStream dis;	
	// NXT BRICK ID
	private static final String NXT_ID = "NXT2";	
	
	private byte sendCommand(byte command, float param) {
		try {
			dos.writeByte(command);
			dos.writeFloat(param);
			dos.flush();
			return dis.readByte();
		} catch (IOException ioe) {
			System.err.println("IO Exception");
			System.exit(1);
			return -1;
		}
	}
	
	/**
	 * Connect to the NXT
	 *
	 */
	private void connect() {
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Blluetooth communication */
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);			
			NXTInfo[] nxtInfo = nxtComm.search(Master.NXT_ID);
			
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
	
	static LineMap makeMap(lines){
		float limit,x0,x1,x2,y0,y1,y2;
		limit=160;
		x0 = 0;
		x1 = 110;
		x2 = 155;
		y0 = 0;
		y1 = 132;
		y2 = y0;

		lines.add(new Line(x0,y0,x0,y1));
		lines.add(new Line(x0,y1,x1,y1));
		lines.add(new Line(x1,y1,x2,y2));
		lines.add(new Line(x2,y2,x0,y0));

		return new LineMap(lines,new Rectangle(0,0,limit,limit))
	}


	public static void main(String[] args) {
		int n;
		byte cmd = 0; float param = 0f; float ret=0f; 
		Master master = new Master();
		float angle_interval =  360/NUM_OF_MEASURES;
		lines[] = new Line[];
		map = makeMap(lines);

	    System.out.print("Enter the number of points:");
	    n = (int) scan.nextFloat();
	    Pose poses[][] = new Pose[n][NUM_OF_MEASURES];
		//int range[] = new int[n];
	    System.out.print("Now enter the points: \"x y\"");

	    for (int i = 0; i < n ; i++ ) {
		    float x = scan.nextFloat();
		    float y = scan.nextFloat();
		    for (int j=0; j < NUM_OF_MEASURES; j++) 
		    	poses[i][j] = new Pose(x,y,j*angle_interval);
		    //poses.add(new Pose(x,y,j*angle_interval);
		    //range[i] = map.range(poses[i]);
	    }

		master.connect();
	    Scanner scan = new Scanner( System.in );	    
	    /*while(true) {
	    	
	    	cmd = (byte) scan.nextFloat();

	    	if (cmd < 2) {
	    	 System.out.print("Enter param [float]: ");
	    	 param = scan.nextFloat();
	    	} else {
	    		param = 0;	    		
	    	}
	    	ret = master.sendCommand(cmd, param);
	    	System.out.println("cmd: " + cmd + " param: " + param + " return: " + ret);
	    	*/
	    for (int i = 0; i < 36 ; i++) {
	    	master.sendCommand((byte)1,5*i*10f);
	    	System.out.println(master.sendCommand((byte)2,0f));
	    }
	}

}
