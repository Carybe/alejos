import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Demonstration of the Behavior subsumption classes.
 * 
 * Requires a wheeled vehicle with two independently controlled
 * motors connected to motor ports A and C, and 
 * a touch sensor connected to sensor  port 1 and
 * an ultrasonic sensor connected to port 3;
 * 
 * @author Brian Bagnall and Lawrie Griffiths
 * modificado para a disciplina MAC0318
 *
 */
public class BumperCar {
  static RegulatedMotor leftMotor = Motor.A;
  static RegulatedMotor rightMotor = Motor.B;
  
  // Use these definitions instead if your motors are inverted
  // static RegulatedMotor leftMotor = MirrorMotor.invertMotor(Motor.A);
  // static RegulatedMotor rightMotor = MirrorMotor.invertMotor(Motor.C);
  
  public static void main(String[] args) {
    leftMotor.setSpeed(300);
    rightMotor.setSpeed(300);
    Behavior b1 = new DriveForward();
    Behavior b2 = new DetectWall();
    Behavior[] behaviorList = { b1, b2 };
    Arbitrator arby = new Arbitrator(behaviorList);
    arby.start();
  }
}

class DriveForward implements Behavior {

  private boolean suppressed = false;

  public boolean takeControl() {
    return true;  // this behavior always wants control.
  }

  public void suppress() {
    suppressed = true;// standard practice for suppress methods
  }

  public void action() {
    LCD.clear();
    LCD.drawString("DriveForward",0,1);
    suppressed = false;
    BumperCar.leftMotor.forward();
    BumperCar.rightMotor.forward();
    while (!suppressed) {
      Thread.yield(); //don't exit till suppressed
    }
    BumperCar.leftMotor.stop(); 
    BumperCar.rightMotor.stop();
  }
}


class DetectWall implements Behavior {

  public DetectWall() {
    sonar = new UltrasonicSensor(SensorPort.S3);
  }

  public boolean takeControl() {
    sonar.ping();
    
    return sonar.getDistance() < 25 || Button.LEFT.isDown();
  }

  public void suppress() {
    //Since  this is highest priority behavior, suppress will never be called.
  }

  public void action() {
    LCD.clear();
    LCD.drawString("DetectWall",0,1);
    BumperCar.leftMotor.rotate(-180, true);// start Motor.A rotating backward
    BumperCar.rightMotor.rotate(-360);  // rotate C farther to make the turn
  }
  
  private UltrasonicSensor sonar;
}
