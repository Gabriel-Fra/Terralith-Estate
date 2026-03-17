package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Property
{
    private String propertyName;
    private List<Block> blocks;

    public Property(String propertyName)
    {
        this.propertyName = propertyName;
        this.blocks = new ArrayList<>();
        initializeBlocks();
    }

    private void initializeBlocks()
    {
        // Block 1 - Lia: TCP 3,628,000 | 15% DP | 24 months | Res 15,000 | 7.5%
        blocks.add(new Block(1, "Lia", 3628000, 0.15, 24, 15000, 0.075));

        // Block 2 - Alice: TCP 2,783,000 | 5% DP | 12 months | Res 15,000 | 7%
        blocks.add(new Block(2, "Alice", 2783000, 0.05, 12, 15000, 0.07));

        // Block 3 - Thea: TCP 3,915,400 | 5% DP | 9 months | Res 15,000 | 7%
        blocks.add(new Block(3, "Thea", 3915400, 0.05, 9, 15000, 0.07));

        // Block 4 - Aira with Balcony: TCP 8,144,720 | 5% DP | 12 months | Res 40,000 | 7%
        blocks.add(new Block(4, "Aira with Balcony", 8144720, 0.05, 12, 40000, 0.07));

        // Block 5 - Lia: TCP 3,628,000 | 15% DP | 24 months | Res 15,000 | 7.5%
        blocks.add(new Block(5, "Lia", 3628000, 0.15, 24, 15000, 0.075));
    }

    public List<Lot> getAvailableLots()
    {
        List<Lot> available = new ArrayList<>();
        for (Block b : blocks)
        {
            available.addAll(b.getAvailableLots());
        }
        return available;
    }

    public List<Lot> getAllLots()
    {
        List<Lot> all = new ArrayList<>();
        for (Block b : blocks)
        {
            all.addAll(b.getLots());
        }
        return all;
    }

    public Block getBlock(int blockNumber)
    {
        for (Block b : blocks)
        {
            if (b.getBlockNumber() == blockNumber)
            {
                return b;
            }
        }
        return null;
    }

    public String getPropertyName()
    {
        return propertyName;
    }

    public List<Block> getBlocks()
    {
        return blocks;
    }
}
