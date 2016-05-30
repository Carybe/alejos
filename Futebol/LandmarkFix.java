import lejos.robotics.subsumption.Behavior;
import lejos.robotics.navigation.Pose;

class LandmarkFix implements Behavior {
	private Player player;
    private float current_distance;
	
	public LandmarkFix(Player player) {
		this.player = player;
	 	current_distance = player.getHead().getDistance();
	}

	public boolean takeControl() {	
		return current_distance < 50 &&
				(Math.abs(25 -  current_distance ) > 5) &&
				Math.abs((player.half * 400f + (25 - current_distance )) - player.getY()) > 5;
	}

	public void suppress() {

	}

	public void action() {
		player.clear();
		player.getPosition().setPose( new Pose(player.getX() , player.half * 400f + (25 - current_distance), (float) player.getCompass().getDegreesCartesian()));
		System.out.println("LandmarkFix:"+  (player.half * 400f + (25 - current_distance)));
	}

}