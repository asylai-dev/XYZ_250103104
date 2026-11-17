//Phase 1
//package com.assylay;
//public class FlightBookingFacade {
//    private final SeatInventorySystem seatSystem;
//    private final BaggagePricingEngine baggageEngine;
//    private final CurrencyConverter currencyConverter;
//    private final PaymentProcessor paymentProcessor;
//    private final TicketIssuer ticketIssuer;
//
//    public FlightBookingFacade() {
//        this.seatSystem = new SeatInventorySystem();
//        this.baggageEngine = new BaggagePricingEngine();
//        this.currencyConverter = new CurrencyConverter();
//        this.paymentProcessor = new PaymentProcessor();
//        this.ticketIssuer = new TicketIssuer();
//    }
//
//    public String bookFlight(String passengerName,
//                             String flightCode,
//                             String seatNumber,
//                             double baseFareUSD,
//                             int bagCount,
//                             double bagWeightKg,
//                             String currency,
//                             String cardNumber) {
//
//        String lockToken = null;
//
//        try {
//            seatSystem.connectToReservationServer();
//
//            lockToken = seatSystem.lockSeat(
//                    flightCode, seatNumber, passengerName
//            );
//
//            double baggageFee = baggageEngine.calculateBaggageFeeUSD(
//                    bagCount, bagWeightKg
//            );
//
//            double totalUSD = baseFareUSD + baggageFee;
//
//            double convertedTotal = currencyConverter.convertFromUSD(
//                    totalUSD, currency
//            );
//
//            paymentProcessor.initializeGateway();
//
//            String receiptId = paymentProcessor.chargeCard(
//                    cardNumber, convertedTotal, currency
//            );
//
//            return ticketIssuer.generateTicket(
//                    passengerName, flightCode, lockToken, receiptId
//            );
//
//        } catch (RuntimeException ex) {
//
//            if (lockToken != null) {
//                seatSystem.releaseSeatLock(lockToken);
//            }
//
//            System.out.println("[BookingFacade] Booking failed: " + ex.getMessage());
//            return "BOOKING_FAILED: " + ex.getMessage();
//
//        } finally {
//            seatSystem.disconnect();
//        }
//    }
//}

//Phase 2
package com.assylay;

public class FlightBookingFacade {

    private final SeatInventorySystem seatSystem;
    private final BaggagePricingEngine baggageEngine;
    private final CurrencyConverter currencyConverter;
    private final PaymentProcessor paymentProcessor;
    private final TicketIssuer ticketIssuer;

    public FlightBookingFacade() {
        seatSystem = new SeatInventorySystem();
        baggageEngine = new BaggagePricingEngine();
        currencyConverter = new CurrencyConverter();
        paymentProcessor = new PaymentProcessor();
        ticketIssuer = new TicketIssuer();
    }

    // Original method: STANDARD by default
    public String bookFlight(String passengerName,
                             String flightCode,
                             String seatNumber,
                             double baseFareUSD,
                             int bagCount,
                             double bagWeightKg,
                             String currency,
                             String cardNumber) {

        return bookFlight(passengerName, flightCode, seatNumber,
                baseFareUSD, bagCount, bagWeightKg,
                currency, cardNumber, "STANDARD");
    }

    // Overloaded method with loyalty tier
    public String bookFlight(String passengerName,
                             String flightCode,
                             String seatNumber,
                             double baseFareUSD,
                             int bagCount,
                             double bagWeightKg,
                             String currency,
                             String cardNumber,
                             String loyaltyTier) {

        String lockToken = null;

        try {
            seatSystem.connectToReservationServer();

            lockToken = seatSystem.lockSeat(
                    flightCode, seatNumber, passengerName
            );

            // SILVER and GOLD passengers have free baggage
            double baggageFee = 0.0;

            if (!"SILVER".equalsIgnoreCase(loyaltyTier)
                    && !"GOLD".equalsIgnoreCase(loyaltyTier)) {

                baggageFee = baggageEngine.calculateBaggageFeeUSD(
                        bagCount, bagWeightKg
                );
            }

            double totalUSD = baseFareUSD + baggageFee;

            double convertedTotal = currencyConverter.convertFromUSD(
                    totalUSD, currency
            );

            paymentProcessor.initializeGateway();

            // SILVER passengers receive an additional 10% discount
            if ("SILVER".equalsIgnoreCase(loyaltyTier)) {
                paymentProcessor.applyPromoCode("SILVER-FREE-BAG");
            }

            String receiptId = paymentProcessor.chargeCard(
                    cardNumber, convertedTotal, currency
            );

            return ticketIssuer.generateTicket(
                    passengerName, flightCode, lockToken, receiptId
            );

        } catch (RuntimeException ex) {

            if (lockToken != null) {
                seatSystem.releaseSeatLock(lockToken);
            }

            System.out.println("[BookingFacade] Booking failed: " + ex.getMessage());

            return "BOOKING_FAILED: " + ex.getMessage();

        } finally {
            seatSystem.disconnect();
        }
    }
}