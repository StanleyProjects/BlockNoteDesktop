package stan.block.note.listeners.ui.cells;

import stan.block.note.core.Unit;

public interface IUnitCellListener
{
    void select(Unit item);
    void editUnit(Unit item);
    void deleteUnit(Unit item);
}