import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("          OMNIHOME SMART CONTROLLER: SYSTEM STARTUP");
        System.out.println("============================================================");

        LegacyBulb rawBulb = new LegacyBulb();
        LegacyThermostat rawThermostat = new LegacyThermostat();
        SmartDevice bulbAdapter = new BulbAdapter(rawBulb);
        SmartDevice thermostatAdapter = new ThermostatAdapter(rawThermostat);
        System.out.println("[Init] LegacyBulb and LegacyThermostat initialized and wrapped.");

        List<SmartDevice> deviceList = List.of(bulbAdapter, thermostatAdapter);
        ModernHub hub = new ModernHub(deviceList);
        System.out.println("[Hub] Registering 2 adapted devices into ModernHub...");

        // ModernHub badHub = new ModernHub(List.of(rawBulb)); // COMPILE ERROR
        /* LegacyBulb does not implement SmartDevice, so a List containing it cannot
           satisfy ModernHub's required List<SmartDevice> constructor argument.
           The Object Adapter implements SmartDevice and delegates to LegacyBulb
           without modifying the vendor's original class. */

        System.out.println("\n--- OPERATION: ACTIVATE ALL DEVICES ---");
        hub.activateAll();
        System.out.println("[Action] ModernHub.activateAll() invoked.");
        System.out.println("  -> BulbAdapter: Brightness set to " + rawBulb.readBrightness() + ".");
        System.out.println("  -> ThermostatAdapter: Dial set to '" + rawThermostat.checkDial() + "'.");
        System.out.println("[Status] All devices reported active: " + (bulbAdapter.isOn() && thermostatAdapter.isOn()));
        System.out.printf("[Power] Fleet Average Power Usage: %.2f%% (Bulb: %d%%, Thermostat: %d%%)%n",
                hub.calculateAveragePowerUsage(), bulbAdapter.getPowerPercent(), thermostatAdapter.getPowerPercent());

        System.out.println("\n--- AUDIT: HARDWARE FAULT INJECTION (STAGE 4) ---");
        rawBulb.breakFilament();
        System.out.println("[Fault 1] Filament physically severed on LegacyBulb...");
        System.out.println("  -> BulbAdapter.isOn(): " + bulbAdapter.isOn());
        System.out.println("  -> BulbAdapter.getPowerPercent(): " + bulbAdapter.getPowerPercent() + "%");

        rawThermostat.rotateDial("STUCK");
        System.out.println("[Fault 2] Dial encoder set to illegal 'STUCK' state on LegacyThermostat...");
        System.out.println("  -> ThermostatAdapter.isOn(): " + thermostatAdapter.isOn());
        System.out.println("  -> ThermostatAdapter.getPowerPercent(): " + thermostatAdapter.getPowerPercent());

        rawThermostat.rotateDial(null);
        System.out.println("[Fault 3] Dial encoder set to null...");
        System.out.println("  -> ThermostatAdapter.isOn(): " + thermostatAdapter.isOn());
        System.out.println("  -> ThermostatAdapter.getPowerPercent(): " + thermostatAdapter.getPowerPercent());

        System.out.println("\n--- OPERATION: EMERGENCY SHUTDOWN ---");
        hub.emergencyShutdown();
        System.out.println("[Action] ModernHub.emergencyShutdown() invoked.");
        System.out.println("  -> BulbAdapter: Brightness set to " + rawBulb.readBrightness() + ".");
        System.out.println("  -> ThermostatAdapter: Dial rotated to '" + rawThermostat.checkDial() + "'.");
        System.out.printf("[Power] Fleet Average Power Usage: %.2f%%%n", hub.calculateAveragePowerUsage());

        require(rawBulb.readBrightness() == 0 && !bulbAdapter.isOn(), "bulb shutdown");
        require("IDLE".equals(rawThermostat.checkDial()) && !thermostatAdapter.isOn(), "thermostat shutdown");
        require(hub.calculateAveragePowerUsage() == 0.0, "zero shutdown power");
        System.out.println("\nALL INTEGRATION TESTS PASSED");
    }

    private static void require(boolean condition, String test) {
        if (!condition) throw new AssertionError("Failed: " + test);
    }
}
