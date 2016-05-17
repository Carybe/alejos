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

		// Maybe the position should be a player attribute
		position = new OdometryPoseProvider(player.getPilot());
	}

	public boolean takeControl(){
		current_compass = 360 - compass.getDegreesCartesian();
		return ( Math.abs(current_compass - position.getPose().getHeading()) > 10);
	}

	public void suppress(){
		//Since  this is highest priority behavior, suppress will never be called.
	}

	public void action(){
		Point location = position.getPose().getLocation();
		position.setPose( new Pose(location.x, location.y , (float) current_compass));
	}
}