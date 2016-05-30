import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;

public class SonarAngleTest {

	public static void main(String[] args) throws InterruptedException {
		RConsole.open();
		UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S4);
		int i = 0, j = 0;
		int dist = 0;
		int sum = 0;
		double mean = 0;
		Motor.C.setSpeed(25); 
		dist = sonar.getDistance();
		RConsole.println("Distance: " + dist);
		System.out.println("D="+dist);
		RConsole.println("Press any button on BRICK to start measuring...");
		System.out.println("Press any button to start");
		Button.waitForAnyPress();
		System.out.println("measuring now...");
		
		for (i = 50; i >= -50; i--) {
			Motor.C.rotateTo(i);
			
			while (Motor.C.isMoving());
			
		    mean = 0;
		    sum = 0;
		    j = 25; // try 25 times
		    Thread.sleep(100);
		    for (int k = 0; k < j; k++){
		  	  dist = sonar.getDistance();
		  	  if(dist == 255){
		  	  	j--;
		  	  	continue;
		  	  }
		  	  sum += dist;
		    }

		    Thread.sleep(100);
		    mean = (1.0*sum)/(1.0*j);
		    
		    RConsole.println(i + " " + mean);
		}
		System.out.println("done!");
		Motor.C.rotateTo(0);
	}
}