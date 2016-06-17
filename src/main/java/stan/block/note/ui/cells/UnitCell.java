package stan.block.note.ui.cells;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

import stan.block.note.core.Unit;
import stan.block.note.core.Block;
import stan.block.note.core.Table;

import stan.block.note.listeners.ui.cells.IUnitCellListener;

import stan.block.note.ui.panes.listitems.BlockListItem;
import stan.block.note.ui.panes.listitems.TableListItem;

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
		setPrefHeight(36);
        setGraphic(null);
        if(empty)
        {
            setText("");
        }
        else
        {
			if(item instanceof Block)
			{
				setGraphic(new BlockListItem((Block)item));
			}
			else if(item instanceof Table)
			{
                setGraphic(new TableListItem((Table)item));
			}
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