package MyLib;

import java.util.ArrayList;
import java.util.List;

public class Property {
    private String propertyName;
    private List<Block> blocks;

    public Property(String propertyName)
    {
        this.propertyName = propertyName;
        this.blocks = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
        {
            blocks.add(new Block(i));
        }
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