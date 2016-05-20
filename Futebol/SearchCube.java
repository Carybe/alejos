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
		return (Math.abs(position.getPose().getY() - 405) < 10 );
	}

	public void suppress(){
		navigator.clearPath();
	}

	public void action(){
		navigator.goTo(417f - player.half * 417f, player.half * 405f ,0f);
	}
}