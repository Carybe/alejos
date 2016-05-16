import lejos.nxt.*;
import lejos.util.Delay;

public class distance {

    public static int trueDistance(UltrasonicSensor sonic){
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
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        int speed = 100;
        int side = 1;
        int boxFront = 570;
        int angle = 182;
        boolean wall = false;
        
        while (true) {
        
        Motor.A.setSpeed(speed);
        Motor.B.setSpeed((int)((double)trueDistance(sonic)/128f)*speed);
        
            LCD.refresh();
            LCD.drawInt(sonic.getDistance(), 6,0,1);
            Delay.msDelay(300);
            Motor.A.forward();
            Motor.B.forward();
        }

        //Motor.A.stop(true);
        //Motor.B.stop();

            /*if (sonic.getDistance() < 23.5) { // se encontrou um obstáculo
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
        }*/
    }
}
