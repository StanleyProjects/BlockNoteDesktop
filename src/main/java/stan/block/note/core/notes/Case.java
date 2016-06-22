package stan.block.note.core.notes;

public class Case<DATA extends Data>
{
	private int type;
	public DATA data;

    public Case(int t)
    {
    	type = t;
    }

    public int getType()
    {
    	return type;
    }
}