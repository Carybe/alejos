import lejos.nxt.*;
import lejos.nxt.addon.CompassHTSensor;

public class Compass {
	public static void main(String[] args) {
		CompassHTSensor compass = new CompassHTSensor(SensorPort.S4);
		while (true) {
			/* Quando estiver calibrando rode bem *devagar*, uma volta
			 * a cada 20 segundos! 
			 */
			if (Button.ENTER.isDown()){
				Button.ENTER.waitForPressAndRelease();
				System.out.println("Rode 2 vezes. 12 g/s");
				compass.startCalibration();
				Button.ENTER.waitForPressAndRelease();
				compass.stopCalibration();
			}
			System.out.println(compass.getDegrees());
		}
	}
}