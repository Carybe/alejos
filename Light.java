import lejos.nxt.*;
import lejos.util.Delay;

public class Light {
  public static void main(String[] args) {
    LightSensor light = new LightSensor(SensorPort.S4);
    while (true) {
      int value = light.readValue();
      System.out.println(value);
      Delay.msDelay(1000);
    }
  }
}
