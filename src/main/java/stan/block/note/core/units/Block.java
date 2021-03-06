package stan.block.note.core.units;

import stan.block.note.core.Dates;
import stan.block.note.core.Unit;

import java.util.List;

public class Block
	extends Unit
{
    public List<Block> blocks;
    public List<Table> tables;

    public Block(String i, String n, String c, Dates d)
    {
    	super(i, n, d.whenCreate());
    	this.color = c;
    	dates.update = d.update;
    	dates.sync = d.sync;
    }
}