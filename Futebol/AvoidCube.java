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
		player.getPilot().travel(-80);
        player.getPilot().rotate(-65);
	    
	    player.getPilot().travel(450);
        player.getPilot().rotate(65);
        player.getPilot().travel(400);
        
        player.getPilot().rotate(65);
	    player.getPilot().travel(450);
	}
}