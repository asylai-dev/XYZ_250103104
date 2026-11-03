package task2;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class CachedStockProxy implements StockProvider {
    private static class CacheEntry {
        final double price; final long timestamp;
        CacheEntry(double price, long timestamp) { this.price = price; this.timestamp = timestamp; }
    }
    private final StockProvider realProvider;
    private final long ttlMillis;
    private final Map<String,CacheEntry> priceCache = new HashMap<>();
    public CachedStockProxy(StockProvider realProvider, long ttlMillis) {
        if (realProvider == null) throw new IllegalArgumentException("Real provider cannot be null");
        if (ttlMillis < 0) throw new IllegalArgumentException("TTL cannot be negative");
        this.realProvider = realProvider; this.ttlMillis = ttlMillis;
    }
    @Override public double getMarketPrice(String ticker) {
        String key = ticker.toUpperCase(Locale.ROOT);
        long now = System.currentTimeMillis();
        CacheEntry entry = priceCache.get(key);
        if (entry != null && now - entry.timestamp < ttlMillis) {
            System.out.printf("[CACHE HIT] Returning cached price for %s: $%.2f (Age: %d ms)%n", key, entry.price, now - entry.timestamp);
            return entry.price;
        }
        System.out.printf("[NETWORK FETCH] Cache miss or expired for %s. Calling upstream adapter...%n", key);
        double freshPrice = realProvider.getMarketPrice(key);
        priceCache.put(key, new CacheEntry(freshPrice, System.currentTimeMillis()));
        return freshPrice;
    }
    @Override public String getStatus(String ticker) { return realProvider.getStatus(ticker); }
}
