public class SmartLight implements Device {
    public void turnOn() { System.out.println("Smart Light glows bright white."); }
    public void turnOff() { System.out.println("Smart Light shuts off."); }
    public void setVolume(int level) { System.out.println("Smart Light brightness set to: " + level + "%"); }
}
