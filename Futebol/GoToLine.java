import lejos.robotics.navigation.Navigator;

import lejos.robotics.subsumption.Behavior;

class GoToLine implements Behavior{
	private Player player;
	private Navigator navigator;

	public GoToLine(Player player){
		this.player = player;
		navigator = player.getNavigator();
        navigator.singleStep(true);
	}

	public boolean takeControl(){
		return true;  // this behavior always wants control.
	}

	public void suppress(){
		System.out.println("I'm going on an adventure!");
	}

	public void action(){
		navigator.goTo(player.getPosition().getPose().getX(), player.half * 405f ,90f + player.half*90f);
	}
}