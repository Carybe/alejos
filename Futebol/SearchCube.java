import lejos.geom.Point;

import lejos.robotics.localization.OdometryPoseProvider;

import lejos.nxt.Button;

import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

class SearchCube implements Behavior{
	private Player player;
	private Navigator navigator;
	private OdometryPoseProvider position;

	public SearchCube(Player player){
		this.player = player;
		navigator = player.getNavigator();
		position = player.getPosition();
	}

	public boolean takeControl(){
		return (Math.abs(position.getPose().getY() - player.half*400) < 15 );
	}

	public void suppress(){
		//navigator.clearPath();
		player.clear();
	}

	public void action(){
		//navigator.stop();
		//System.out.println(player.half*position.getPose().getY() - 405);
		//player.getPilot().steer(0);
		//navigator.addWaypoint(834f, -405f, 0);
		//navigator.followPath();
		//System.out.println("Hey! im searching!!!");
		System.out.println("Searching cube");
		navigator.goTo(417f - (player.half * 417f), player.half * 405f ,0f);
		//navigator.goTo(834f, -405f ,0f);
	}
}