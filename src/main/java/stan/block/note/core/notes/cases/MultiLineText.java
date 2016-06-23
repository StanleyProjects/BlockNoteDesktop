package stan.block.note.core.notes.cases;

import stan.block.note.core.notes.Case;

public class MultiLineText
	extends Case<MultiLineTextData>
{
    public MultiLineText()
    {
    	super(0);
    	data = new MultiLineTextData();
    }

    public void setText(String s)
    {
    	data.multiLineTextData = s;
    }
}