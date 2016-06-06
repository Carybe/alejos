import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.nxt.Motor;
import java.io.*;

/**
 * Slave: Executes commands sent by PC Master application
 * @author Denis Maua'
 * @since 2016-06-05
 * Inspired on code by Lawrie Griffiths
 *
 */

public class Slave {
	private static final byte ROTATE = 0;
	private static final byte ROTATETO = 1;
	private static final byte RANGE = 2;
	private static final byte STOP = 3;
	
	public static void main(String[] args) throws Exception {
		//USBConnection btc = USB.waitForConnection(); /* USB communication */
		/* Uncomment next line for Bluetooth */
		BTConnection btc = Bluetooth.waitForConnection();
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S1);
		Motor.C.setSpeed(50);
		LCD.drawString("READY", 0, 10);
		while (true) {
			try {
				byte cmd = dis.readByte();
				
				LCD.drawInt(cmd,1,0,0);
				float param = dis.readFloat();
				LCD.drawInt((int) (param + 0.5f),4,0,1);
				
				switch (cmd) {
				case ROTATE: 
					Motor.C.rotate((int) (param + 0.5f));
					dos.writeByte(0);
					break;
				case ROTATETO: 
					Motor.C.rotateTo((int) (param + 0.5f));
					dos.writeByte(0);
					break;					
				case RANGE:
					dos.writeByte(sonar.getDistance());
					break;
				case STOP:
					System.exit(1);
				default:
					dos.writeByte(-1);
				}
				dos.flush();
				
			} catch (IOException ioe) {
				System.err.println("IO Exception");
				Thread.sleep(2000);
				System.exit(1);
			}
		}
	}

}
