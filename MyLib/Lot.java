package MyLib;

public class Lot
{
    public static final String AVAILABLE = "AVAILABLE";
    public static final String RESERVED = "RESERVED";
    public static final String SOLD = "SOLD";

    private static final double LEGAL_MISC_RATE = 0.05;

    private int lotNumber;
    private double area;
    private String modelName;
    private double sellingPrice;
    private double downPaymentPercent;
    private int downPaymentTermMonths;
    private double reservationFee;
    private double interestRate;
    private String status;
    private Client owner;
    private Reservation reservation;

    public Lot(int lotNumber, double area, String modelName, double sellingPrice, double downPaymentPercent, int downPaymentTermMonths, double reservationFee, double interestRate)
    {
        this.lotNumber = lotNumber;
        this.area = area;
        this.modelName = modelName;
        this.sellingPrice = sellingPrice;
        this.downPaymentPercent = downPaymentPercent;
        this.downPaymentTermMonths = downPaymentTermMonths;
        this.reservationFee = reservationFee;
        this.interestRate = interestRate;
        this.status = AVAILABLE;
    }

    // --- TCP Computation ---

    public double getLegalAndMiscFees()
    {
        return sellingPrice * LEGAL_MISC_RATE;
    }

    public double getTotalContractPrice()
    {
        return sellingPrice + getLegalAndMiscFees();
    }

    // --- Pag-IBIG Computations ---

    public double getRequiredDownPayment()
    {
        return getTotalContractPrice() * downPaymentPercent;
    }

    public double getNetDownPayment()
    {
        return getRequiredDownPayment() - reservationFee;
    }

    public double getMonthlyDownPayment()
    {
        return getNetDownPayment() / downPaymentTermMonths;
    }

    public double getLoanableAmount()
    {
        return getTotalContractPrice() - getRequiredDownPayment();
    }

    public double getMonthlyAmortization(int loanTermYears)
    {
        double loanable = getLoanableAmount();
        int months = loanTermYears * 12;
        if (interestRate == 0)
        {
            return loanable / months;
        }
        double r = interestRate / 12;
        return loanable * r / (1 - Math.pow(1 + r, -months));
    }

    // --- Getters ---

    public int getLotNumber()
    {
        return lotNumber;
    }

    public double getArea()
    {
        return area;
    }

    public String getModelName()
    {
        return modelName;
    }

    public double getSellingPrice()
    {
        return sellingPrice;
    }

    public double getPrice()
    {
        return getTotalContractPrice();
    }

    public double getDownPaymentPercent()
    {
        return downPaymentPercent;
    }

    public int getDownPaymentTermMonths()
    {
        return downPaymentTermMonths;
    }

    public double getReservationFee()
    {
        return reservationFee;
    }

    public double getInterestRate()
    {
        return interestRate;
    }

    public String getStatus()
    {
        return status;
    }

    public Client getOwner()
    {
        return owner;
    }

    public Reservation getReservation()
    {
        return reservation;
    }

    // --- Setters ---

    public void setStatus(String status)
    {
        this.status = status;
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
        return String.format("Lot %d | %s | %.1f sqm | PHP %,.2f | %s", lotNumber, modelName, area, getTotalContractPrice(), status);
    }
}
