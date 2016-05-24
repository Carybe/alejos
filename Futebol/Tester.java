import lejos.nxt.*;
import lejos.nxt.addon.*;
import lejos.robotics.navigation.*;
import lejos.robotics.localization.*;
import lejos.robotics.*;
import lejos.geom.*;

public class Tester{
	public static void main(String[] args) {
		Player alejadinho = new Player(Color.RED,
										Color.GREEN,
										56f,
										112f,
										MotorPort.B,
										MotorPort.A,
										MotorPort.C,
										SensorPort.S4,
										SensorPort.S3,
										SensorPort.S2
										);

		Navigator navigator;
		OdometryPoseProvider position;
		Point origin;

		DifferentialPilot pilot;
		NXTRegulatedMotor claw;
		CompassHTSensor compass;
		ColorSensor color;
		UltrasonicSensor head;

		origin = new Point(0f,0f);
		position = alejadinho.getPosition();
		navigator = alejadinho.getNavigator();

		pilot = alejadinho.getPilot();
		claw = alejadinho.getClaw();
		compass = alejadinho.getCompass();
		color = alejadinho.getColor();
		head = alejadinho.getHead();

		while(true){
			System.out.println(head.getDistance());
		}

		/*
		navigator.goTo(0f, -405f ,0f);
		navigator.waitForStop();
		navigator.goTo(834f,-405f ,90f);
		navigator.waitForStop();
		navigator.goTo(834f,405f ,180f);
		navigator.waitForStop();
		navigator.goTo(0f,405f ,270f);
		navigator.waitForStop();
		navigator.goTo(0f,0f ,0f);
		navigator.waitForStop();
		//navigator.waitForStop();
		*/
	}	
}