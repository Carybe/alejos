import lejos.robotics.subsumption.Behavior;

class SearchCube implements Behavior{
	private Player player;

	public SearchCube(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return (Math.abs(player.getY() - player.half*400) < 15 );
	}

	public void suppress(){
		player.clear();
	}

	public void action(){
		System.out.println("Searching cube");
		player.getNavigator().goTo(417f - (player.half * 417f), player.half * 405f ,0f);
	}
}