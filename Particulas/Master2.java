import lejos.pc.comm.*;
import java.util.Scanner;
import java.io.*;

import lejos.robotics.mapping.LineMap;
import lejos.geom.Rectangle;
import lejos.geom.Line;
import lejos.robotics.navigation.Pose;

// MAC RADAR: 00:16:53:1B:69:93


public class Master2 {
	private static final byte ROTATE = 0;
	private static final byte ROTATETO = 1;
	private static final byte RANGE = 2;
	private static final byte STOP = 3;
	private static final int NUM_OF_MEASURES = 36;
	
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
	
	/**
	 * Connect to the NXT
	 *
	 */
	private void connect() {
		try {
			//NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			/* Uncomment next line for Blluetooth communication */
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);			
			NXTInfo[] nxtInfo = nxtComm.search(Master2.NXT_ID);
			
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

	public static void main(String[] args) {
		int n;
		byte cmd = 0; float param = 0f; float ret=0f;
		Master2 master = new Master2();
		float angle_interval =  360/NUM_OF_MEASURES;
		Line[] lines = new Line[9];
		LineMap map = makeMap(lines);

		master.connect();
	    Scanner scan = new Scanner( System.in );
	    for (int i = 0; i < NUM_OF_MEASURES ; i++) {
	    	master.sendCommand((byte)1, 5 * i * angle_interval);
	    	System.out.println(master.sendCommand((byte)2,0f));
	    }
	}
}