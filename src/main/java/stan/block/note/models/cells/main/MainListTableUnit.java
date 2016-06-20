package stan.block.note.models.cells.main;

import stan.block.note.core.Table;

public class MainListTableUnit
	extends MainListUnit
{
	public Table table;
	public boolean select;
	
    public MainListTableUnit(Table t, boolean s)
    {
		this.table = t;
		this.select = s;
    }
}