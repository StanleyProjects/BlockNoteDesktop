package stan.block.note.ui.cells;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import javafx.scene.control.ListCell;

import stan.block.note.core.Unit;

import stan.block.note.listeners.ui.cells.IUnitCellListener;

public class UnitCell
    extends ListCell<Unit>
{
    private IUnitCellListener listener;

    public UnitCell(IUnitCellListener l)
    {
        this.listener = l;
    }

    @Override
    public void updateItem(Unit item, boolean empty)
    {
        super.updateItem(item, empty);
        setId("main_list_cell");
        //setGraphic(null);
        if(empty)
        {
            setText("");
        }
        else
        {
            setText(item.name);
            setContextMenu(initContextMenu(item));
        }
    }
    private ContextMenu initContextMenu(Unit item)
    {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.editUnit(item);
            }
        });
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.deleteUnit(item);
            }
        });
        contextMenu.getItems().addAll(edit, delete);
        return contextMenu;
    }
}