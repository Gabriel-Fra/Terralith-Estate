package MyLib;

public class Installment extends Payment{
    private int durationMonths;
    private double balance;
    private double interestRate;
    private double amortization;

    public Installment(double price, double reservationFee, double closingFee, int durationMonths, double interestRate)
    {
        super(price, reservationFee, closingFee);
        this.durationMonths = durationMonths;
        this.interestRate = interestRate;
        this.balance = price;
        this.amortization = computeAmortization();
    }

    private double computeAmortization()
    {
        if (interestRate == 0)
        {
            return balance / durationMonths;
        }
        double r = interestRate / 12;
        return balance * r / (1 - Math.pow(1 + r, -durationMonths));
    }

    @Override
    public void processPayment()
    {
        System.out.println("Processing installment payment. Monthly amortization: " + amortization);
    }

    @Override
    public String getPaymentSummary()
    {
        return String.format(
            "Type: Installment\nLot Price: PHP %.2f\nReservation Fee: PHP %.2f\nClosing Fee: PHP %.2f\nDuration: %d months\nInterest Rate: %.1f%%\nMonthly Amortization: PHP %.2f\nTotal Due: PHP %.2f",
            price, reservationFee, closingFee, durationMonths,
            interestRate * 100, amortization, getTotalDue()
        );
    }

    public void checkBalance()
    {
        System.out.println("Remaining balance: " + balance);
    }

    public double getAmortization()
    {
        return amortization;
    }

    public double getBalance()
    {
        return balance;
    }

    public int getDurationMonths()
    {
        return durationMonths;
    }

    public double getInterestRate()
    {
        return interestRate;
    }
}
