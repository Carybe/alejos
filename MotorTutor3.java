import lejos.nxt.*;
import lejos.util.Delay;

/**
* Use rotate() methods 
* @author owner.GLASSEY
*
*/
public class MotorTutor3 {
      /**
       * @param args
       */
      public static void main(String[] args) 
      {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);        
        UltrasonicSensor sonic2 = new UltrasonicSensor(SensorPort.S1);
        Motor.A.setSpeed(200);
        Motor.B.setSpeed(250);
            Motor.A.forward();
            Motor.B.forward();
            Delay.msDelay(10000);
           //LCD.drawString("Program 3", 0, 0);
           //Button.waitForAnyPress();
           /*LCD.clear();
           Motor.A.rotate(720);
           LCD.drawInt(Motor.A.getTachoCount(),0,0);
           //Motor.A.rotateTo(0);
           //LCD.drawInt(Motor.A.getTachoCount(),0,1);
           Button.waitForAnyPress();*/ 
        }
}