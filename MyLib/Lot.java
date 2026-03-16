package MyLib;

public class Lot
{
    public static final String AVAILABLE = "AVAILABLE";
    public static final String RESERVED = "RESERVED";
    public static final String SOLD = "SOLD";

    private int lotNumber;
    private double area;
    private double price;
    private String status;
    private String lotType;
    private Client owner;
    private Reservation reservation;

    public Lot(int lotNumber, double area, double price)
    {
        this.lotNumber = lotNumber;
        this.area = area;
        this.price = price;
        this.status = AVAILABLE;
        this.lotType = "Residential";
    }

    public int getLotNumber()
    {
        return lotNumber;
    }

    public double getArea()
    {
        return area;
    }

    public double getPrice()
    {
        return price;
    }

    public String getStatus()
    {
        return status;
    }

    public String getLotType()
    {
        return lotType;
    }

    public Client getOwner()
    {
        return owner;
    }

    public Reservation getReservation()
    {
        return reservation;
    }

    public void setArea(double area)
    {
        this.area = area;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setLotType(String lotType)
    {
        this.lotType = lotType;
    }

    public void updateOwner(Client owner)
    {
        this.owner = owner;
    }

    public void setReservation(Reservation res)
    {
        this.reservation = res;
    }

    @Override
    public String toString()
    {
        return String.format("Lot %d | %.1f sqm | PHP %.2f | %s", lotNumber, area, price, status);
    }
}
