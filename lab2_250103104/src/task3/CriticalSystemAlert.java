package task3;
public class CriticalSystemAlert extends Alert {
    public CriticalSystemAlert(MessageChannel channel, DispatchStrategy strategy) { super(channel, strategy); }
    @Override public int getSeverityLevel() { return 5; }
    @Override public String formatAlertBody(String raw) {
        return "!!! CRITICAL PRODUCTION ALERT !!!\nIncident Report: " + raw
                + "\nACTION REQUIRED: SRE on-call immediate intervention.";
    }
}
