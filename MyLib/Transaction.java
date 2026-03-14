package MyLib;

import java.time.LocalDate;

public class Transaction {
    private String transactionId;
    private LocalDate transactionDate;
    private Client client;
    private Lot lot;
    private Payment payment;

    // For the Transaction
    // Maybe use a rng for the transactionID
    // And check if that transactionID exists in the list
    // though it might be slow
    // Try parin
}
