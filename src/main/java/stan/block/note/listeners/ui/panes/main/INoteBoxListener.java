package stan.block.note.listeners.ui.panes.main;

public interface INoteBoxListener
{
    void hover(boolean h);
    void moveBegin();
    void move(double x, double y, double height);
    void moveEnd();
}