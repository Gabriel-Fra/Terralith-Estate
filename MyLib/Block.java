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
            if (i <= 5)
            {
                // Template 1 - Lia: 50 sqm | Selling Price 3,455,238.10 | TCP = SP + 5% = 3,628,000 | 15% DP | 24 months | Res 15,000 | 7.5%
                lots.add(new Lot(i, 50, "Lia", 3455238.10, 0.15, 24, 15000, 0.075));
            }
            else if (i <= 10)
            {
                // Template 2 - Alice: 50 sqm | Selling Price 2,650,476.19 | TCP = SP + 5% = 2,783,000 | 5% DP | 12 months | Res 15,000 | 7%
                lots.add(new Lot(i, 50, "Alice", 2650476.19, 0.05, 12, 15000, 0.07));
            }
            else if (i <= 15)
            {
                // Template 3 - Thea: 50 sqm | Selling Price 3,728,952.38 | TCP = SP + 5% = 3,915,400 | 5% DP | 9 months | Res 15,000 | 7%
                lots.add(new Lot(i, 50, "Thea", 3728952.38, 0.05, 9, 15000, 0.07));
            }
            else
            {
                // Template 4 - Aira with Balcony: 120 sqm | Selling Price 7,756,876.19 | TCP = SP + 5% = 8,144,720 | 5% DP | 12 months | Res 40,000 | 7%
                lots.add(new Lot(i, 120, "Aira with Balcony", 7756876.19, 0.05, 12, 40000, 0.07));
            }
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
