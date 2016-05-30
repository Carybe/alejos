import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TachoMotorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.nxt.UltrasonicSensor;

import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

class Player {
	public int target, notTarget, half;
	private double  wheelDiam, width;
	
	private DifferentialPilot pilot;
	private NXTRegulatedMotor claw;
	
	private OdometryPoseProvider position;
	private Navigator navigator;

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

		claw.setSpeed(1000);

		navigator = new Navigator(pilot);

		position = new OdometryPoseProvider(pilot);

		navigator.setPoseProvider(position);

		pilot.setTravelSpeed(160);  	// cm per second
        pilot.setRotateSpeed(60);	    // degrees per second

		compass = new CompassHTSensor(compassPort);
		compass.resetCartesianZero();
		color = new ColorSensor(colorPort);
		head = new UltrasonicSensor(headPort);

		half = -1;
	}

	public DifferentialPilot getPilot(){
		return pilot;
	}

	public NXTRegulatedMotor getClaw(){
		return claw;
	}
	
	public OdometryPoseProvider getPosition(){
		return position;
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

	public Navigator getNavigator(){
		return navigator;
	}

	public float getX(){
		return position.getPose().getX();
	}


	public float getY(){
		return position.getPose().getY();
	}

	public void clear(){
		navigator.stop();
		navigator.clearPath();
	}
}