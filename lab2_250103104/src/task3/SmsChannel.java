package task3;
public class SmsChannel implements MessageChannel {
    @Override public void transmit(String recipient, String message) {
        System.out.printf("[SMS PROTOCOL -> %s] %s%n", recipient, message.replace("\n", " -- "));
    }
}
