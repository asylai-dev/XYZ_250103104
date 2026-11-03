package tests;

import task1.*;
import task2.*;
import task3.*;
import task4.*;
import task5.*;

public class VerificationTests {
    private static int passed = 0;
    private static void check(boolean condition, String name) {
        if (!condition) throw new AssertionError("FAILED: " + name);
        System.out.println("[PASS] " + name);
        passed++;
    }
    public static void main(String[] args) throws Exception {
        String json = new JsonFormatter().format("t", new String[]{"a"}, new String[][]{{"value"}});
        check(json.contains("\"a\": \"value\""), "Task 1 JSON formatter");
        String csv = new CsvFormatter().format("t", new String[]{"a,b"}, new String[][]{{"x\"y"}});
        check(csv.contains("\"a,b\"") && csv.contains("\"x\"\"y\""), "Task 1 valid CSV quoting");
        StockProvider adapter = new StockFeedAdapter(new LegacyStockFeed());
        check(Math.abs(adapter.getMarketPrice("AAPL") - 183) < 0.00001, "Task 2 BID/ASK midpoint");
        check("OPEN".equals(adapter.getStatus("GOOGL")), "Task 2 status extraction");
        check(new BusinessHoursStrategy().shouldDispatch(5, 2), "Task 3 emergency bypass");
        check(!new BusinessHoursStrategy().shouldDispatch(1, 23), "Task 3 suppress at night");
        check(new BusinessHoursStrategy().shouldDispatch(1, 9), "Task 3 workday start");
        PricingService base = new CurrencyOrderAdapter(new LegacyForeignOrder("T", 100), new LegacyCurrencyConverter(), "USD");
        PricingService finalPrice = new CustomsDutyDecorator(new ExpressCourierDecorator(new GiftWrapDecorator(base)));
        check(Math.abs(finalPrice.calculateTotal() - 157.33) < 0.00001, "Task 4 final USD price");
        check(new StorageDriverProxy(new NfsStorageDriver(), new UserSession("guest", "GUEST"))
                .loadRawBlob("file").length == 0, "Task 5 guest access denied");
        check(new StorageDriverProxy(new S3StorageDriver(), new UserSession("admin", "ADMIN"))
                .loadRawBlob("file").length == 4, "Task 5 authorized access");
        System.out.println("ALL " + passed + " TESTS PASSED");
    }
}
