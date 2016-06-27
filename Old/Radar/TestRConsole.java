import lejos.nxt.comm.RConsole;
public class TestRConsole {
		public static void main(String[] args) {
		RConsole.open();
		int x = 17;
		int y = 3;
		int sum = x + y;
		RConsole.println("Sum: " + sum);
		RConsole.close();
	}
}