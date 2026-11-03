package task2;
import java.util.HashMap;
import java.util.Map;
public class StockFeedAdapter implements StockProvider {
    private final LegacyStockFeed legacyFeed;
    public StockFeedAdapter(LegacyStockFeed legacyFeed) {
        if (legacyFeed == null) throw new IllegalArgumentException("Legacy feed cannot be null");
        this.legacyFeed = legacyFeed;
    }
    private Map<String, String> parsePacket(String raw) {
        Map<String,String> map = new HashMap<>();
        for (String token : raw.split(";")) {
            String[] pair = token.split("=", 2);
            if (pair.length == 2) map.put(pair[0].trim(), pair[1].trim());
        }
        return map;
    }
    @Override public double getMarketPrice(String ticker) {
        Map<String,String> data = parsePacket(legacyFeed.fetchRawTickerPacket(ticker));
        double bid = Double.parseDouble(data.getOrDefault("BID", "0.0"));
        double ask = Double.parseDouble(data.getOrDefault("ASK", "0.0"));
        return (bid + ask) / 2.0;
    }
    @Override public String getStatus(String ticker) {
        return parsePacket(legacyFeed.fetchRawTickerPacket(ticker)).getOrDefault("STATUS", "UNAVAILABLE");
    }
}
