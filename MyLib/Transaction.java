package MyLib;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction
{
    private String transactionId;
    private LocalDate transactionDate;
    private Client client;
    private Lot lot;
    private Payment payment;

    public Transaction(Client client, Lot lot, Payment payment)
    {
        this.transactionId = UUID.randomUUID().toString().substring(0, 8);
        this.transactionDate = LocalDate.now();
        this.client = client;
        this.lot = lot;
        this.payment = payment;
    }

    public void process()
    {
        lot.setStatus(Lot.SOLD);
        lot.updateOwner(client);
        if (lot.getReservation() != null)
        {
            lot.getReservation().complete();
        }
        client.addLot(lot);
        payment.processPayment();
        Log.record(transactionId, "SOLD - Lot " + lot.getLotNumber() + " to " + client.getName() + " | PHP " + String.format("%.2f", payment.getTotalDue()));
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public LocalDate getTransactionDate()
    {
        return transactionDate;
    }

    public Client getClient()
    {
        return client;
    }

    public Lot getLot()
    {
        return lot;
    }

    public Payment getPayment()
    {
        return payment;
    }
}
