import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TachoMotorPort;
import lejos.nxt.UltrasonicSensor;

import lejos.robotics.Color;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.RegulatedMotor;
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
		/*
		Behavior b3 = new AvoidCube(alejadinho);
		Behavior b4 = new MakeGoal(alejadinho);
		Behavior b5 = new LandmarkFix(alejadinho);
		Behavior b6 = new DeadReckoningFix(alejadinho);
		*/

		//Behavior[] behaviorList = { b1,b2,b3,b4,b5,b6 };
		Behavior[] behaviorList = { b1,b2 };
		Arbitrator arby = new Arbitrator(behaviorList);
		arby.start();
	}	
}

class Player {
	int target, notTarget;
	double  wheelDiam, width;

	DifferentialPilot pilot;
	NXTRegulatedMotor claw;

	CompassHTSensor compass;
	ColorSensor color;
	UltrasonicSensor head;

	public Player(int target ,int notTarget ,double wheelDiam ,double width
		,TachoMotorPort lMotorPort ,TachoMotorPort rMotorPort ,TachoMotorPort clawPort
		,SensorPort compassPort ,SensorPort colorPort ,SensorPort headPort){
		this.target = target;
		this.notTarget = notTarget;
		this.wheelDiam = wheelDiam;
		this.width = width;
		
		pilot = new DifferentialPilot(wheelDiam, width,
						new NXTRegulatedMotor(lMotorPort),
						new NXTRegulatedMotor(rMotorPort));
		claw = new NXTRegulatedMotor(clawPort);

		compass = new CompassHTSensor(compassPort);
		color = new ColorSensor(colorPort);
		head = new UltrasonicSensor(headPort);
	}
}

class GoToLine implements Behavior{
	Player player;
	Navigator navigator;
	private boolean suppressed;

	public GoToLine(Player player){
		this.player = player;
		navigator = new Navigator(player.pilot);
		player.pilot.setTravelSpeed(20);  // cm por segundo
        player.pilot.setRotateSpeed(45);  // graus por segundo
        navigator.singleStep(true);
        suppressed = false;
	}

	public boolean takeControl(){
		return true;  // this behavior always wants control.
	}

	public void suppress(){
		suppressed = true;// standard practice for suppress methods
		System.out.println("BATATA");
	}

	public void action(){
		suppressed = false;
		while (!suppressed) {
			Thread.yield(); //don't exit till suppressed
		}
		navigator.addWaypoint(0.0f, 0.0f);
		navigator.addWaypoint(100.0f, 100.0f);
		navigator.followPath();
		navigator.waitForStop();
	}
}
class SearchCube implements Behavior{
	Player player;

	public SearchCube(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return Button.ENTER.isPressed();
	}

	public void suppress(){

	}

	public void action(){
//		System.out.println("ASASDSADASDSA");
	}
}

/*
class AvoidCube implements Behavior{
	Player player;

	public AvoidCube(Player player){
		this.player = player;
	}

	public boolean takeControl(){

	}

	public void suppress(){

	}

	public void action(){
		
	}
}

class MakeGoal implements Behavior{
	Player player;

	public MakeGoal(Player player){
		this.player = player;
	}

	public boolean takeControl(){

	}

	public void suppress(){

	}

	public void action(){
		
	}
}

class LandmarkFix implements Behavior{
	Player player;

	public LandmarkFix(Player player){
		this.player = player;
	}
	public boolean takeControl(){

	}

	public void suppress(){

	}

	public void action(){
		
	}
}

class DeadReckoningFix implements Behavior{
	Player player;

	public DeadReckoningFix(Player player){
		this.player = player;
	}

	public boolean takeControl(){

	}

	public void suppress(){

	}

	public void action(){
		
	}
}
*/