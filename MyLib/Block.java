package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Block
{
    private int blockNumber;
    private List<Lot> lots;
    private List<Agent> assignedAgents;

    public Block(int blockNumber)
    {
        this.blockNumber = blockNumber;
        this.lots = new ArrayList<>();
        this.assignedAgents = new ArrayList<>();
        initializeLots();
    }

    private void initializeLots()
    {
        for (int i = 1; i <= 20; i++)
        {
            double area = 100.0 + (i % 5) * 20;
            double price = area * 15000;
            lots.add(new Lot(i, area, price));
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
