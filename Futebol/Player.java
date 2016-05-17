import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.ColorSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TachoMotorPort;
import lejos.nxt.UltrasonicSensor;

import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

class Player {
	public int target, notTarget;
	private double  wheelDiam, width;
	
	private DifferentialPilot pilot;
	private NXTRegulatedMotor claw;
	
	private CompassHTSensor compass;
	private ColorSensor color;
	private UltrasonicSensor head;

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

		pilot.setTravelSpeed(30);  	// cm per second
        //pilot.setRotateSpeed(45);	// degrees per second

		compass = new CompassHTSensor(compassPort);
		color = new ColorSensor(colorPort);
		head = new UltrasonicSensor(headPort);
	}

	public DifferentialPilot getPilot(){
		return pilot;
	}

	public NXTRegulatedMotor getClaw(){
		return claw;
	}

	public CompassHTSensor getCompass(){
		return compass;
	}

	public ColorSensor getColor(){
		return color;
	}

	public UltrasonicSensor getHead(){
		return head;
	}

}