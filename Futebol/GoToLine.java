import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

class GoToLine implements Behavior{
	private Player player;
	private Navigator navigator;
	private boolean suppressed;

	public GoToLine(Player player){
		this.player = player;
		navigator = new Navigator(player.getPilot());
        navigator.singleStep(true);
        suppressed = false;
	}

	public boolean takeControl(){
		return true;  // this behavior always wants control.
	}

	public void suppress(){
		suppressed = true;// standard practice for suppress methods
		System.out.println("BATATA");
	}

	public void action(){
		suppressed = false;
		while (!suppressed) {
			Thread.yield(); //don't exit till suppressed
		}
		navigator.addWaypoint(0.0f, 0.0f);
		navigator.addWaypoint(100.0f, 100.0f);
		navigator.followPath();
		navigator.waitForStop();
	}
}