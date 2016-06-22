package stan.block.note.core.notes.cases;

import stan.block.note.core.notes.Case;

public class SingleLineText
	extends Case<SingleLineTextData>
{
    public SingleLineText()
    {
    	super(0);
    	data = new SingleLineTextData();
    }

    public void setText(String s)
    {
    	data.singleLineText = s;
    }
}