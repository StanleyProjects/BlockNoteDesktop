package stan.block.note.listeners.ui.panes;

import stan.block.note.core.Unit;

public interface IEditUnitBoxListener
{
    void save(Unit item);
    void cancel();
}