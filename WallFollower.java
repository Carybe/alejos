/*
*  MAC0318 - Caminhar a 20 cm de uma parede
*
* 	Nomes					Nºs USP
* 	Carybé Gonçalves Silva	8033961
* 	Gabriel Baptista        8941300
*
*/

import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;

public class WallFollower {
	double  wheelDiam;
	double  robotWidth;
	DifferentialPilot pilot;
	UltrasonicSensor sonic;
	int dist;
	double speed;
	double maxAngle;

	public void run(){
		double kp, ki, kd, y, e, eant, ediff, E, u, r = dist;
		double angle = 0;
		ediff = e = eant = E = y = 0;
		kp = speed*2.3;
		ki = speed/10000;
		kd = speed/5;

		while (true) {
			y = sonic.getDistance();
			e = (y - r);
			E += e;
			ediff = e - eant;
			eant = e;
			
			u = (ki * E) + (kp * e) + (kd * ediff);
			
			if (u > maxAngle){
				u = maxAngle;
			}else if(u < -maxAngle){
				u = -maxAngle;
			}

			angle = u;

			pilot.steer(-angle);
		}
	}

	public static void main(String[] args) {
		WallFollower robo = new WallFollower();
		
		robo.wheelDiam = 56f;
		robo.robotWidth = 112f;
		robo.dist = 28; // Hipotenusa de triâgulo com cateto oposto = 20cm
		robo.speed = 150;
		robo.maxAngle = 55;

		robo.pilot = new DifferentialPilot(robo.wheelDiam, robo.robotWidth, Motor.B, Motor.A);
		robo.pilot.setTravelSpeed(robo.speed);
		robo.sonic = new UltrasonicSensor(SensorPort.S3);
		robo.run();
	}
}