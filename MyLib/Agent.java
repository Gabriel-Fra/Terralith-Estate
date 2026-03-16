package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Agent
{
    private String name;
    private String contactInfo;
    private double commissionRate;

    public Agent(String name, String contactInfo, double commissionRate)
    {
        this.name = name;
        this.contactInfo = contactInfo;
        this.commissionRate = commissionRate;
    }

    public List<Lot> filterLots(List<Lot> lots, double minArea, double maxPrice)
    {
        List<Lot> result = new ArrayList<>();
        for (Lot lot : lots)
        {
            if (lot.getArea() >= minArea && lot.getPrice() <= maxPrice && lot.getStatus().equals(Lot.AVAILABLE))
            {
                result.add(lot);
            }
        }
        return result;
    }

    public Transaction processSelling(Client client, Lot lot, Payment payment)
    {
        if (lot.getStatus().equals(Lot.AVAILABLE) || lot.getStatus().equals(Lot.RESERVED))
        {
            Transaction t = new Transaction(client, lot, payment);
            t.process();
            return t;
        }
        return null;
    }

    public void processReservation(Client client, Lot lot)
    {
        client.reserveLot(lot, this);
        Log.record("RES-" + lot.getLotNumber(), "RESERVED - Lot " + lot.getLotNumber() + " by " + client.getName());
    }

    public String getName()
    {
        return name;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }

    public double getCommissionRate()
    {
        return commissionRate;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setContactInfo(String info)
    {
        this.contactInfo = info;
    }

    public void setCommissionRate(double rate)
    {
        this.commissionRate = rate;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
