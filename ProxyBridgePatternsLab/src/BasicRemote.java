public class BasicRemote {
    protected final Device device;
    public BasicRemote(Device device) { this.device = device; }
    public void powerOn() { device.turnOn(); }
    public void powerOff() { device.turnOff(); }
}
