/*
*  MAC0318 - Projeto 3
*
* 	Nomes			Nºs USP
* Carybé Gonçalves Silva	8033961
* Gabriel Baptista              8941300
*
*/
import lejos.nxt.*;
import lejos.util.Delay;

public class obstacle {
    public static void main(String[] args) {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        int speed = 300;
        int side = 1;
        int boxFront = 570;
        int angle = 182;
        boolean wall = false;
        
        Motor.A.setSpeed(speed);
        Motor.B.setSpeed(speed);

        while (true) {
            if (sonic.getDistance() < 23.5) { // se encontrou um obstáculo
                Motor.A.stop(true);
                Motor.B.stop();
                
                Motor.B.rotate(side * angle,true);
                Motor.A.rotate(-side * angle);
                
                Motor.A.resetTachoCount();
                Motor.B.resetTachoCount();
                
                while((Motor.A.getTachoCount() + Motor.B.getTachoCount()) < 2 * boxFront){
                    if (sonic.getDistance() < 23.5){

                        Motor.A.stop(true);
                        Motor.B.stop();

                        wall = true; // encontrou uma parede

                        Motor.A.rotateTo(0, true); // da ré na distância percorrida até encontrar a parede
                        Motor.B.rotateTo(0);

                        break;
                        }
                        Motor.B.forward();
                        Motor.A.forward();
                    }

                Motor.A.stop(true);
                Motor.B.stop();
                    
                Motor.B.rotate(-side * angle,true);
                Motor.A.rotate(side * angle);

                if (wall){ // se encontrou uma parede, vira para o outro lado, no próximo bloqueio
                    side = -side;
                    wall = false;
                }

            } else { // se não há obstáculo
                side = -side; // aleatoriza o próximo lado que será virado
                Motor.A.forward();
                Motor.B.forward();
            }
        }
    }
}
