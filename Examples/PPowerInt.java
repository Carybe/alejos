import lejos.nxt.*;

public class PPowerInt{
	public static void main(String[] args){
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
		NXTMotor mA = new NXTMotor(MotorPort.A);
		NXTMotor mB = new NXTMotor(MotorPort.B);
		double r = 40;
		double y = 0;
		double u;
		double kp = 7;
		double e = 0;
		double eant = 0;
		double kd = 10;
		double edif = 0;
		double E = 0;
		double ki = 0.01;

		LCD.drawString("Controle Proporcional 2",0,0);
		while (true){
			y = sonic.getDistance();
			e = (y - r);
			E = E + e;
			edif = e - eant;
			eant = e;
			u = kp*e + ki*E + kd*edif;
			if (u > 100) u=100;
			if (u < -100) u=-100;
			mA.setPower((int)u);
			mB.setPower((int)u);
		}
	}
}