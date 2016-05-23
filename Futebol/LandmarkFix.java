import lejos.geom.Point;
import lejos.robotics.subsumption.Behavior;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.CompassHTSensor;
import lejos.robotics.navigation.Pose;

import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;

class LandmarkFix implements Behavior {
	private Player player;
	private OdometryPoseProvider position;
	private UltrasonicSensor head;
	private CompassHTSensor compass;
	private double current_compass;
    private double distance;
	
	public LandmarkFix(Player player) {
		this.player = player;
		head = player.getHead();
		position = player.getPosition();
		distance = setDistance();
	}

	public boolean takeControl() {
		return (0 - compass.getDegreesCartesian() < 15)
				&& (Math.abs(position.getPose().getX()) -(1251f - distance) < 3) && (distance != 255*11.125);
	}

	public void suppress() {

	}

	public void action() {
		current_compass = 360 - compass.getDegreesCartesian();
		Point location = position.getPose().getLocation();
		position.setPose( new Pose( (float) (1251f - distance), location.y , (float) current_compass));
	}
	
	public double setDistance() {
		return (head.getDistance())*11.125 - 417f;
	}
}