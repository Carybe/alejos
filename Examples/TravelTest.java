import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;


/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class TravelTest {
  DifferentialPilot pilot;
  UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);

  public void go() {
    pilot.travel(20, true);
    while (pilot.isMoving()) {
      if (trueDistance()<40) pilot.stop();
    }
  }

  public int trueDistance(){
        int a,b,c,d,e;

        a=sonic.getDistance();
        Delay.msDelay(100);
        b=sonic.getDistance();
        Delay.msDelay(100);
        c=sonic.getDistance();
        Delay.msDelay(100);
        d=sonic.getDistance();
        Delay.msDelay(100);
        e=sonic.getDistance();
        Delay.msDelay(100);
        return (a+b+c+d+e)/5;
  }

  public static void main(String[] args) {
    TravelTest traveler = new TravelTest();
    traveler.pilot = new DifferentialPilot(11.2f, 5.6f, Motor.B, Motor.A);
    traveler.go();
  }
}