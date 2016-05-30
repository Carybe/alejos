import lejos.robotics.subsumption.Behavior;

class GoToLine implements Behavior{
	private Player player;

	public GoToLine(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return true;  // this behavior always wants control.
	}

	public void suppress(){
	}

	public void action(){
		System.out.println("Going to line");
		player.getClaw().rotateTo(0);
		player.getNavigator().goTo(player.getX(), player.half * 400f ,90f + player.half*90f);			
		player.getNavigator().waitForStop();
	}
}