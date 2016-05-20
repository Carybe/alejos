import lejos.robotics.subsumption.Behavior;

class MakeGoal implements Behavior{
	private Player player;

	public MakeGoal(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return player.getColor().getColorID() == player.target;
	}

	public void suppress(){
		player.half *= -1;
	}

	public void action(){
		player.getClaw().rotateTo(45);
		player.getNavigator().goTo(834f+411f,0f,0f);
		player.getNavigator().waitForStop();
		player.getClaw().rotateTo(-105);
		player.getNavigator().goTo(834f,0f,-180f);
		player.getNavigator().waitForStop();
	}
}