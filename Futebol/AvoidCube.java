import lejos.robotics.subsumption.Behavior;

class AvoidCube implements Behavior{
	private Player player;

	public AvoidCube(Player player){
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