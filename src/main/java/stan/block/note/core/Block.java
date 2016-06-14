package stan.block.note.core;

import java.util.List;

public class Block
	extends Unit
{
    public List<Block> blocks;
    public List<Table> tables;

    public Block(String i, String n, Dates d)
    {
    	super(i, n, d.whenCreate());
    	dates.update = d.update;
    	dates.sync = d.sync;
    }
}