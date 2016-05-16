/*
*  MAC0318 - Robô Seguidor de Linha
*
* 	Nomes					Nºs USP
* 	Carybé Gonçalves Silva	8033961
* 	Gabriel Baptista        8941300
*
*/

import lejos.nxt.*;

public class LineFollower {
	LightSensor light;
	int limiar;
	double maxPow;
	NXTMotor m1;
	NXTMotor m2;

	public void run(){
		double y, kp, kd, ki, E, e, eant, ediff, u1, u2;
		double r = limiar;
		y = E = eant = 0;
		kp = maxPow/31;
		ki = maxPow/950000;
		kd = maxPow/60;

		while (true){

			/* Ameniza o efeito de erros sensoriais ponderando o
			 peso da medida atual como contribuidora em apenas 1/3
			 na medida de erro.*/
			y = (y + 2*light.readValue())/3;
			e = (y - r);

			E += e;
			ediff = (e - eant);
			eant = e;
			
			// PID
			u1 = maxPow + (kp*e) + (ki*E) + (kd*ediff) ;
			u2 = maxPow - (kp*e) - (ki*E) - (kd*ediff);

			if(u1 > maxPow) u1 = maxPow;
			if(u2 > maxPow) u2 = maxPow;

			m1.setPower((int)u1);
			m2.setPower((int)u2);
		}
	}

	public static void main(String[] args) {
		LineFollower robo = new LineFollower();
		robo.light = new LightSensor(SensorPort.S4);
		robo.m1 = new NXTMotor(MotorPort.A);
		robo.m2 = new NXTMotor(MotorPort.B);

		robo.limiar = (32+60)/2; // valor médio entre leituras de preto e branco
		robo.maxPow = 100; // potência máxima dos motores

		robo.run();
	}
}