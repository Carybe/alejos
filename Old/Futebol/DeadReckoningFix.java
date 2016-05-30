import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;

class DeadReckoningFix implements Behavior{
	private Player player;

	public DeadReckoningFix(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return ( 180 - Math.abs(Math.abs(player.getCompass().getDegreesCartesian() - player.getPosition().getPose().getHeading()) - 180) > 5);
	}

	public void suppress(){
		//Since  this is highest priority behavior, suppress will never be called.
	}

	public void action(){
		System.out.println("DeadReckoning:");
		player.getPosition().setPose( new Pose(player.getX(), player.getY() , player.getCompass().getDegreesCartesian()));
	}
}