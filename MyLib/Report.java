package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Report {
    private List<Lot> availableLots;
    private List<Lot> occupiedLots;
    private Property property;

    public Report(Property property)
    {
        this.property = property;
        this.availableLots = new ArrayList<>();
        this.occupiedLots = new ArrayList<>();
    }

    public void compile()
    {
        availableLots.clear();
        occupiedLots.clear();
        for (Lot lot : property.getAllLots())
        {
            if (lot.getStatus() == LotStatus.AVAILABLE)
            {
                availableLots.add(lot);
            }
            else
            {
                occupiedLots.add(lot);
            }
        }
    }

    public String generateReport()
    {
        compile();
        StringBuilder sb = new StringBuilder();
        sb.append("===== REAL ESTATE SALES REPORT =====\n");
        sb.append("Property: ").append(property.getPropertyName()).append("\n\n");

        for (Block block : property.getBlocks())
        {
            sb.append("--- Block ").append(block.getBlockNumber()).append(" ---\n");
            for (Lot lot : block.getLots())
            {
                sb.append(String.format(
                    "  Lot %-3d | %-12s | %6.1f sqm | PHP %,10.2f | Owner: %s\n",
                    lot.getLotNumber(),
                    lot.getStatus(),
                    lot.getArea(),
                    lot.getPrice(),
                    lot.getOwner() != null ? lot.getOwner().getName() : "None"
                ));
            }
            sb.append("\n");
        }

        sb.append("SUMMARY\n");
        sb.append("  Total Lots    : ").append(availableLots.size() + occupiedLots.size()).append("\n");
        sb.append("  Available     : ").append(availableLots.size()).append("\n");
        sb.append("  Reserved/Sold : ").append(occupiedLots.size()).append("\n");
        return sb.toString();
    }

    public List<Lot> getAvailableLots()
    {
        return availableLots;
    }

    public List<Lot> getOccupiedLots()
    {
        return occupiedLots;
    }
}
