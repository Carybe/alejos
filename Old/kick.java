/*
*  MAC0318 - Projeto 1
*
* 	Nomes			Nºs USP
* Carybé Gonçalves Silva	8033961
* Gabriel Baptista              8941300
*
*/
import lejos.nxt.*;
import lejos.util.Delay;

public class kick {
    public static void main(String[] args) {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        LightSensor light = new LightSensor(SensorPort.S3);
        Motor.A.setSpeed(200);
        Motor.B.setSpeed(200);
        while (true) {
            int value = light.readValue();
            if (sonic.getDistance() < 23.5) {
                if (value > 34 && value < 55){
                    Sound.playTone(500,500);
                    Motor.C.rotateTo(90);
                    Motor.C.rotateTo(0);
                    Motor.B.stop(true);
                    Motor.A.stop();
                    Delay.msDelay(10000);
                    break;
                } else {
                    Motor.B.backward();
                    Motor.A.backward();
                    LCD.clear();
                    LCD.drawInt(sonic.getDistance(), 0, 0);
                    LCD.drawInt(value, 0, 1);
                    Delay.msDelay(1000);
                    Motor.B.stop(true);
                    Motor.A.stop();
                    Motor.B.rotate(369,true);
                    Motor.A.rotate(-369);
                }
            } else {
                Motor.A.forward();
                Motor.B.forward();

            }
        }
    }
}
