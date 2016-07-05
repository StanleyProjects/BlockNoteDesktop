package stan.block.note.core.notes;

import java.util.HashMap;
import java.util.List;

import stan.block.note.core.Colors;

public class Note
{
    public String id;
    public String color = Colors.BLUE;
    public List<Case> cases;
    public HashMap settings;

    public Note()
    {
    }
}