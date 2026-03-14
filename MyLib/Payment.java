package MyLib;

public abstract class Payment {
    protected double price;
    protected double reservationFee;
    protected double closingFee;

    public Payment(double price, double reservationFee, double closingFee)
    {
        this.price = price;
        this.reservationFee = reservationFee;
        this.closingFee = closingFee;
    }

    public abstract void processPayment();
    public abstract String getPaymentSummary();

    public double getPrice()
    {
        return price;
    }

    public double getReservationFee()
    {
        return reservationFee;
    }

    public double getClosingFee()
    {
        return closingFee;
    }

    public double getTotalDue()
    {
        return price + reservationFee + closingFee;
    }
}
