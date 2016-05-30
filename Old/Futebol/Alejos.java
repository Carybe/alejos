import lejos.nxt.SensorPort;
import lejos.nxt.MotorPort;
import lejos.robotics.Color;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Alejos{
	public static void main(String[] args) {
		Player alejadinho = new Player(Color.GREEN,
										Color.RED,
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

		alejadinho.getCompass().resetCartesianZero();
		alejadinho.getCompass().resetCartesianZero();
		alejadinho.getCompass().resetCartesianZero();

		Behavior[] behaviorList = { b1,b2,b3,b4,b5,b6 };
		Arbitrator arby = new Arbitrator(behaviorList);
		arby.start();
	}	
}