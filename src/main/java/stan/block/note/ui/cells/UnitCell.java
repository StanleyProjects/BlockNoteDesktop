package stan.block.note.ui.cells;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ListCell;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;

import stan.block.note.core.Unit;
import stan.block.note.core.units.Block;
import stan.block.note.core.units.Table;

import stan.block.note.listeners.ui.cells.IUnitCellListener;

import stan.block.note.ui.panes.listitems.BlockListItem;
import stan.block.note.ui.panes.listitems.TableListItem;

import stan.block.note.models.cells.main.MainListUnit;
import stan.block.note.models.cells.main.MainListBlockUnit;
import stan.block.note.models.cells.main.MainListTableUnit;

public class UnitCell
    extends ListCell<MainListUnit>
{
    private IUnitCellListener listener;

    public UnitCell(IUnitCellListener l)
    {
        this.listener = l;
    }

    @Override
    public void updateItem(MainListUnit item, boolean empty)
    {
        super.updateItem(item, empty);
        setId("main_list_cell");
		setPrefHeight(36);
        setGraphic(null);
        if(empty)
        {
            setText("");
			return;
        }
		HBox gra = null;
		if(item instanceof MainListBlockUnit)
		{
			Block b = ((MainListBlockUnit)item).block;
			gra = new BlockListItem(b);
			gra.setOnMouseReleased(new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.PRIMARY)
					{
						listener.select(b);
					}
					else if(event.getButton() == MouseButton.SECONDARY)
					{
					}
				}
			});
			setContextMenu(initContextMenu(b));	
		}
		else if(item instanceof MainListTableUnit)
		{
			MainListTableUnit mltu = (MainListTableUnit)item;
			Table t = mltu.table;
			gra = new TableListItem(t, mltu.select);
			gra.setOnMouseReleased(new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					if(event.getButton() == MouseButton.PRIMARY)
					{
						listener.select(t);
					}
					else if(event.getButton() == MouseButton.SECONDARY)
					{
					}
				}
			});
			setContextMenu(initContextMenu(t));	
		}
		setGraphic(gra);
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