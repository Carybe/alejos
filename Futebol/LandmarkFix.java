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
    private double distance;
	
	public LandmarkFix(Player player) {
		this.player = player;
		head = player.getHead();
		position = player.getPosition();
		compass = player.getCompass();
	}

	public boolean takeControl() {
				//((90f + player.half*90f) - compass.getDegreesCartesian() < 2) &&
		return head.getDistance() < 50 &&
				(Math.abs(25 - head.getDistance()) > 2) &&
				Math.abs((player.half * 400f + (25 - head.getDistance())) - position.getPose().getLocation().y) > 2;
	}

	public void suppress() {

	}

	public void action() {
		player.clear();
		Point location = position.getPose().getLocation();
		position.setPose( new Pose(location.x , player.half * 400f + (25 - head.getDistance()), (float) compass.getDegreesCartesian()));
		System.out.println("LandmarkFix:"+  (player.half * 400f + (25 - head.getDistance())));
	}

}