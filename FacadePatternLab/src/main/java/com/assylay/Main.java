package com.assylay;

public class Main {

    public static void main(String[] args) {
        FlightBookingFacade facade = new FlightBookingFacade();
        System.out.println("TEST 1: Successful Booking");
        String ticket1 = facade.bookFlight(
                "Adilbekkyzy Assylay",
                "KC101",
                "14A",
                320.00,
                2,
                23.0,
                "EUR",
                "4532015112830366"
        );
        System.out.println("Result 1: " + ticket1 + "\n");
        System.out.println("TEST 2: Blocked Seat");
        String ticket2 = facade.bookFlight(
                "Nurzhan Altynay",
                "KC101",
                "13C",
                320.00,
                1,
                15.0,
                "USD",
                "4532015112830366"
        );
        System.out.println("Result 2: " + ticket2 + "\n");
        System.out.println("TEST 3: Payment Failure");
        String ticket3 = facade.bookFlight(
                "Matkarimova Kurbanay",
                "KC101",
                "07D",
                320.00,
                1,
                18.0,
                "KZT",
                "9999"
        );
        System.out.println("Result 3: " + ticket3 + "\n");
        System.out.println("\n=== TEST 4: SILVER Loyalty Passenger ===");
        String ticket4 = facade.bookFlight(
                "Amina Suleimenova",
                "KC204",
                "15A",
                320.00,
                3,
                30.0,
                "EUR",
                "4532015112830366",
                "SILVER"
        );

        System.out.println("Result 4: " + ticket4);
    }
}