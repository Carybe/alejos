/*
*  MAC0318 - Projeto 2
*
* 	Nomes			Nºs USP
* Carybé Gonçalves Silva	8033961
* Gabriel Baptista              8941300
*
*/
import lejos.nxt.*;
import lejos.util.Delay;

public class square {
    public static void main(String[] args) {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        int distanceInAngles = 1000;
        int rotateAngle = 180;
        int speed = 200;

        Motor.A.setSpeed(speed);
        Motor.B.setSpeed(speed);

        Motor.B.rotate(distanceInAngles,true);
        Motor.A.rotate(distanceInAngles);

     	Motor.B.rotate(rotateAngle,true);
        Motor.A.rotate(-rotateAngle);

		Motor.B.rotate(distanceInAngles,true);
        Motor.A.rotate(distanceInAngles);

     	Motor.B.rotate(rotateAngle,true);
        Motor.A.rotate(-rotateAngle);

		Motor.B.rotate(distanceInAngles,true);
        Motor.A.rotate(distanceInAngles);

     	Motor.B.rotate(rotateAngle,true);
        Motor.A.rotate(-rotateAngle);

		Motor.B.rotate(distanceInAngles,true);
        Motor.A.rotate(distanceInAngles);

		Motor.B.rotate(rotateAngle,true);
        Motor.A.rotate(-rotateAngle);
    }
}
