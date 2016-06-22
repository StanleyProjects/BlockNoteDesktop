package stan.block.note.core.units;

import stan.block.note.core.Dates;
import stan.block.note.core.Unit;
import stan.block.note.core.notes.Note;

import java.util.List;

public class Table
	extends Unit
{
    public List<Note> notes;

    public Table(String i, String n, String c, Dates d)
    {
    	super(i, n, d.whenCreate());
    	this.color = c;
    	dates.update = d.update;
    	dates.sync = d.sync;
    }
}