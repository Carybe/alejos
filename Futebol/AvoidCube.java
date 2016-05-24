import lejos.robotics.subsumption.Behavior;

class AvoidCube implements Behavior{
	private Player player;

	public AvoidCube(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return player.getColor().getColorID() == player.notTarget;
	}

	public void suppress(){
	}

	public void action(){
		System.out.println("Wrong cube");
		player.getPilot().stop();
		player.clear();

		player.getPilot().travel(-80);
        player.getPilot().rotate(35);

        player.getPilot().travel(275);
        player.getPilot().rotate(-70);

        player.getPilot().travel(275);
        player.getPilot().rotate(35);
	}
}