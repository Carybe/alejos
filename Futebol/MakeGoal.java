import lejos.robotics.subsumption.Behavior;

class MakeGoal implements Behavior{
	private Player player;

	public MakeGoal(Player player){
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