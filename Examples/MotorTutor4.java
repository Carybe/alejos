import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.util.Delay;

 
/**
* Interrupt motor rotation
* @author owner.GLASSEY
*
*/

public class MotorTutor4 {
 
 
      public static void main(String[] args) {
           LCD.drawString("Tutor 4", 0, 0);
           Button.waitForAnyPress();
          while(Button.readButtons()>0);
           LCD.clear();
           Motor.A.rotateTo(1440,true);
           while(Motor.A.isMoving())
           {
             Delay.msDelay(200);
             LCD.drawInt(Motor.A.getTachoCount(),0,0);
              if(Button.readButtons()>0) Motor.A.stop();
           }
           LCD.drawInt(Motor.A.getTachoCount(),0,1);
 
           Button.waitForAnyPress();     
      }
 
 }