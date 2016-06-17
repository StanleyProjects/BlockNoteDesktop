package stan.block.note.core;

public class Table
	extends Unit
{
    public Table(String i, String n, String c, Dates d)
    {
    	super(i, n, d.whenCreate());
    	this.color = c;
    	dates.update = d.update;
    	dates.sync = d.sync;
    }
}