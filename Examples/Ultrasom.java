/*
 *  MAC0318 - Programa 6
*/
import lejos.nxt.*;

public class Ultrasom {
  public static void main(String[] args) {
    UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
    int dist;
    Motor.A.setSpeed(200);
    Motor.B.setSpeed(200);
    while (true) {
      dist = sonic.getDistance();
      System.out.println(dist);
      Motor.A.forward();
      Motor.B.forward();
      if (dist < 26) break;
      }
    Motor.A.stop(true);
    Motor.B.stop();
    }
  }


