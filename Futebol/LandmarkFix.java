import lejos.robotics.subsumption.Behavior;

class LandmarkFix implements Behavior{
	private Player player;

	public LandmarkFix(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return false;
	}

	public void suppress(){

	}

	public void action(){
		
	}
}