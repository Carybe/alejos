import lejos.nxt.Button;

import lejos.robotics.subsumption.Behavior;

class SearchCube implements Behavior{
	private Player player;

	public SearchCube(Player player){
		this.player = player;
	}

	public boolean takeControl(){
		return Button.ENTER.isPressed();
	}

	public void suppress(){

	}

	public void action(){
//		System.out.println("ASASDSADASDSA");
	}
}