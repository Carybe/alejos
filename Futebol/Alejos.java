/*
import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TachoMotorPort;
import lejos.nxt.UltrasonicSensor;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.RegulatedMotor;
*/

import lejos.nxt.SensorPort;
import lejos.nxt.MotorPort;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Alejos{
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

		Behavior b1 = new GoToLine(alejadinho);
		Behavior b2 = new SearchCube(alejadinho);
		Behavior b3 = new AvoidCube(alejadinho);
		Behavior b4 = new MakeGoal(alejadinho);
		Behavior b5 = new LandmarkFix(alejadinho);
		Behavior b6 = new DeadReckoningFix(alejadinho);

		Behavior[] behaviorList = { b1,b2,b3,b4,b5,b6 };
		Arbitrator arby = new Arbitrator(behaviorList);
		arby.start();
	}	
}