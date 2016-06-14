package stan.block.note.core;

public class Table
	extends Unit
{
    public Table(String i, String n, Dates d)
    {
    	super(i, n, d.whenCreate());
    	dates.update = d.update;
    	dates.sync = d.sync;
    }
}