import lejos.geom.Point;

import lejos.robotics.localization.OdometryPoseProvider;

import lejos.robotics.navigation.Navigator;
import lejos.nxt.SensorPort;
import lejos.nxt.MotorPort;
import lejos.robotics.Color;

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

		origin = new Point(0f,0f);
		position = alejadinho.getPosition();
		navigator = alejadinho.getNavigator();
		navigator.goTo(0f, -405f ,1f);
		//navigator.waitForStop();
		navigator.goTo(834f, -405f ,0f);
		navigator.waitForStop();
	}	
}