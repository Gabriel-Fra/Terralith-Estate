package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Client
{
    private String name;
    private String contactInfo;
    private Payment paymentType;
    private List<Lot> lots;

    public Client(String name, String contactInfo)
    {
        this.name = name;
        this.contactInfo = contactInfo;
        this.lots = new ArrayList<>();
    }

    public void addLot(Lot lot)
    {
        lots.add(lot);
    }

    public void reserveLot(Lot lot, Agent agent)
    {
        if (lot.getStatus().equals(Lot.AVAILABLE))
        {
            lot.setStatus(Lot.RESERVED);
            lot.updateOwner(this);
            lots.add(lot);
            Reservation res = new Reservation(this, agent);
            lot.setReservation(res);
        }
    }

    public String getName()
    {
        return name;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }

    public Payment getPaymentType()
    {
        return paymentType;
    }

    public List<Lot> getLots()
    {
        return lots;
    }

    public void setPaymentType(Payment paymentType)
    {
        this.paymentType = paymentType;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setContactInfo(String info)
    {
        this.contactInfo = info;
    }

    @Override
    public String toString()
    {
        return name + " (" + contactInfo + ")";
    }
}
