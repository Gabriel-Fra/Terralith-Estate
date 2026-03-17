package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Block
{
    private int blockNumber;
    private String modelName;
    private double area;
    private double totalContractPrice;
    private double downPaymentPercent;
    private int downPaymentTermMonths;
    private double reservationFee;
    private double interestRate;
    private List<Lot> lots;
    private List<Agent> assignedAgents;

    public Block(int blockNumber, String modelName, double area, double totalContractPrice, double downPaymentPercent, int downPaymentTermMonths, double reservationFee, double interestRate)
    {
        this.blockNumber = blockNumber;
        this.modelName = modelName;
        this.area = area;
        this.totalContractPrice = totalContractPrice;
        this.downPaymentPercent = downPaymentPercent;
        this.downPaymentTermMonths = downPaymentTermMonths;
        this.reservationFee = reservationFee;
        this.interestRate = interestRate;
        this.lots = new ArrayList<>();
        this.assignedAgents = new ArrayList<>();
        initializeLots();
    }

    private void initializeLots()
    {
        for (int i = 1; i <= 20; i++)
        {
            lots.add(new Lot(i, area, modelName, totalContractPrice, downPaymentPercent, downPaymentTermMonths, reservationFee, interestRate));
        }
    }

    public List<Lot> getAvailableLots()
    {
        List<Lot> available = new ArrayList<>();
        for (Lot lot : lots)
        {
            if (lot.getStatus().equals(Lot.AVAILABLE))
            {
                available.add(lot);
            }
        }
        return available;
    }

    public Lot getLot(int lotNumber)
    {
        for (Lot lot : lots)
        {
            if (lot.getLotNumber() == lotNumber)
            {
                return lot;
            }
        }
        return null;
    }

    public int getBlockNumber()
    {
        return blockNumber;
    }

    public String getModelName()
    {
        return modelName;
    }

    public List<Lot> getLots()
    {
        return lots;
    }

    public List<Agent> getAssignedAgents()
    {
        return assignedAgents;
    }

    public void addAgent(Agent agent)
    {
        assignedAgents.add(agent);
    }
}
