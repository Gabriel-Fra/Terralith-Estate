package MyLib;

import java.time.LocalDate;

public class Reservation {
    private Client client;
    private Agent agent;
    private LocalDate expiryDate;
    private double balance;
    private String status;

    public Reservation(Client client, Agent agent)
    {
        this.client = client;
        this.agent = agent;
        this.expiryDate = LocalDate.now().plusDays(30);
        this.balance = 0.0;
        this.status = "ACTIVE";
    }

    public void forfeit()
    {
        this.status = "FORFEITED";
    }

    public void complete()
    {
        this.status = "COMPLETED";
    }

    public Client getClient()
    {
        return client;
    }

    public Agent getAgent()
    {
        return agent;
    }

    public LocalDate getExpiryDate()
    {
        return expiryDate;
    }

    public double getBalance()
    {
        return balance;
    }

    public String getStatus()
    {
        return status;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }
}
