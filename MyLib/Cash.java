package MyLib;

public class Cash extends Payment{
    private double discount;

    public Cash(double price, double reservationFee, double closingFee, double discountRate)
    {
        super(price, reservationFee, closingFee);
        this.discount = price * discountRate;
    }

    @Override
    public void processPayment()
    {
        System.out.println("Processing cash payment. Total due: " + getTotalDue());
    }

    @Override
    public String getPaymentSummary()
    {
        return String.format(
            "Type: Cash\nLot Price: PHP %.2f\nDiscount: PHP %.2f\nReservation Fee: PHP %.2f\nClosing Fee: PHP %.2f\nTotal Due: PHP %.2f",
            price, discount, reservationFee, closingFee, getTotalDue()
        );
    }

    @Override
    public double getTotalDue()
    {
        return super.getTotalDue() - discount;
    }

    public double getDiscount()
    {
        return discount;
    }
}
