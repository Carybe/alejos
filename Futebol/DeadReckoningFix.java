import lejos.geom.Point;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.CompassHTSensor;

import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;

import lejos.robotics.subsumption.Behavior;

class DeadReckoningFix implements Behavior{
	private CompassHTSensor compass; 
	private OdometryPoseProvider position;
	private double current_compass;
	private Player player;

	public DeadReckoningFix(Player player){
		this.player = player;
		compass = player.getCompass();
		position = player.getPosition();
	}

	public boolean takeControl(){
		current_compass = compass.getDegreesCartesian();
		return ( 180 - Math.abs(Math.abs(current_compass - position.getPose().getHeading())%360-180) > 10);
	}

	public void suppress(){
		//Since  this is highest priority behavior, suppress will never be called.
	}

	public void action(){
		System.out.println(current_compass +"\t"+ position.getPose().getHeading());
		Point location = position.getPose().getLocation();
		position.setPose( new Pose(location.x, location.y , (float) current_compass));
	}
}