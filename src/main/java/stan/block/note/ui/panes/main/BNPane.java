package stan.block.note.ui.panes.main;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.util.Callback;

import stan.block.note.core.BNCore;
import stan.block.note.core.units.Block;
import stan.block.note.core.units.Table;
import stan.block.note.core.Unit;

import stan.block.note.ui.cells.UnitCell;

import stan.block.note.listeners.ui.cells.IUnitCellListener;
import stan.block.note.listeners.ui.panes.main.IBNPaneListener;
import stan.block.note.listeners.ui.panes.main.IEditUnitBoxListener;
import stan.block.note.listeners.ui.panes.main.IMainTopBoxListener;

import stan.block.note.models.cells.main.MainListUnit;
import stan.block.note.models.cells.main.MainListBlockUnit;
import stan.block.note.models.cells.main.MainListTableUnit;

public class BNPane
    extends VBox
{
    //VIEWS
    Button putNewBlock = new Button();
    Button putNewTable = new Button();
    MainTopBox mainTopBox;
    //
    NotesPane notesPane;
    StackPane editUnit = new StackPane();
    EditUnitBox editBox;
    //
    ListView<MainListUnit> listView;

	//FIELDS
    private IBNPaneListener listener;
    private String actualTableId;
	
    public BNPane()
    {
        super();
        this.setStyle("-fx-background-color: null");
        //this.getChildren().addAll(initTestPane());
		mainTopBox = new MainTopBox(new IMainTopBoxListener()
        {
            public void edit()
            {
            	showEdit(BNCore.getInstance().getActualBlock());
            }
            public void back()
            {
				BNCore.getInstance().backActualBlock();
				refresh();
            }
            public void close()
            {
				listener.close();
            }
        });
        mainTopBox.setMinHeight(36);
        this.getChildren().addAll(mainTopBox, initMainPane());
    }
    public void init(IBNPaneListener l, String path)
    {
		listener = l;
        BNCore.getInstance().openBlockNote(path);
		actualTableId = null;
        refresh();
    }
    private void refresh()
    {
        BNCore.getInstance().updateData();
        Block block = BNCore.getInstance().getActualBlock();
		mainTopBox.setBlock(block.name, block.color);
		if(actualTableId == null)
		{
			mainTopBox.clearTable();
		}
		else
		{
			Table table = BNCore.getInstance().getTable(actualTableId);
			if(table == null)
			{
				actualTableId = null;
				mainTopBox.clearTable();
                notesPane.hide();
			}
			else
			{
				mainTopBox.setTable(table.name, table.color);
                notesPane.show(table);
			}
		}
		if(BNCore.getInstance().isHead())
		{
			mainTopBox.setVisibleHead(false);
		}
		else
		{
			mainTopBox.setVisibleHead(true);
		}
        setListUnits(block);
    }

    private HBox initMainPane()
    {
        HBox blockMain = new HBox();
        blockMain.setStyle("-fx-background-color: null");
        VBox.setVgrow(blockMain, Priority.ALWAYS);
        blockMain.getChildren().addAll(initLeftPane(), initRightPane());
        return blockMain;
    }

    private StackPane initLeftPane()
    {
        int boxH = 36;
        int boxW = 192;
        StackPane blockLeft = new StackPane();
        blockLeft.setStyle("-fx-background-color: white");
        blockLeft.setPrefWidth(boxW);
        //
        listView = initListView();
        listView.prefHeightProperty().bind(blockLeft.heightProperty());
        //
        HBox botButtons = new HBox();
        botButtons.setMaxHeight(boxH);
        //
        putNewBlock.setId("put_new_block_button");
        putNewBlock.setText("+B");
        putNewBlock.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                BNCore.getInstance().putNewBlock();
                refresh();
            }
        });
        putNewTable.setId("put_new_table_button");
        putNewTable.setText("+T");
        putNewTable.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                BNCore.getInstance().putNewTable();
                refresh();
            }
        });
        //
        botButtons.getChildren().addAll(putNewBlock, putNewTable);
        botButtons.setAlignment(Pos.CENTER);
        //
        blockLeft.getChildren().addAll(listView, botButtons);
        blockLeft.setAlignment(Pos.BOTTOM_CENTER);
        //blockLeft.getChildren().addAll(listView);
        return blockLeft;
    }
    private ListView<MainListUnit> initListView()
    {
        ListView<MainListUnit> list = new ListView<MainListUnit>();
        list.setCellFactory(new Callback<ListView<MainListUnit>, ListCell<MainListUnit>>()
        {
            @Override
            public ListCell<MainListUnit> call(ListView<MainListUnit> list)
            {
                return new UnitCell(new IUnitCellListener()
                {
                    @Override
                    public void select(Unit item)
                    {
						if(item instanceof Block)
						{
                        	BNCore.getInstance().setActualBlock(item.id);
                            refresh();
						}
						else if(item instanceof Table)
						{
                            if(actualTableId == null || !actualTableId.equals(item.id))
                            {
                                actualTableId = item.id;
                                refresh();
                            }
						}
                    }
                    @Override
                    public void editUnit(Unit item)
                    {
                        showEdit(item);
                    }
                    @Override
                    public void deleteUnit(Unit item)
                    {
		                if(item instanceof Block)
		                {
                        	BNCore.getInstance().deleteBlock(item.id);
		                }
		                else if(item instanceof Table)
		                {
                        	BNCore.getInstance().deleteTable(item.id);
		                }
                        refresh();
                    }
                });
            }
        });
        return list;
    }
    private StackPane initRightPane()
    {
        StackPane blockRight = new StackPane();
        blockRight.setStyle("-fx-background-color: white");
        HBox.setHgrow(blockRight, Priority.ALWAYS);
        //
        editUnit.setStyle("-fx-background-color: rgba(0,0,0,0.5)");
        editUnit.setAlignment(Pos.CENTER);
        editBox = new EditUnitBox(new IEditUnitBoxListener()
        {
            public void cancel()
            {
                hideEdit();
            }
            public void save(Unit item)
            {
                if(item instanceof Block)
                {
                    BNCore.getInstance().editBlock(item.id, item.name, item.color);
                }
                else if(item instanceof Table)
                {
                    BNCore.getInstance().editTable(item.id, item.name, item.color);
                }
                refresh();
                hideEdit();
            }
        });
        editBox.setMinSize(320, 256);
        editBox.setMaxSize(0, 0);
        editUnit.getChildren().addAll(editBox);
        editUnit.setVisible(false);
        //
        notesPane = new NotesPane();
        notesPane.hide();
        //
        blockRight.getChildren().addAll(notesPane, editUnit);
        return blockRight;
    }

    private void setListUnits(Block block)
    {
        List<MainListUnit> list = new ArrayList<MainListUnit>();
        for(int i = 0; i < block.blocks.size(); i++)
        {
            list.add(new MainListBlockUnit(block.blocks.get(i)));
        }
        for(int i = 0; i < block.tables.size(); i++)
        {
			boolean select = false;
			if(actualTableId != null)
			{
				select = actualTableId.equals(block.tables.get(i).id);
			}
            list.add(new MainListTableUnit(block.tables.get(i), select));
        }
        ObservableList<MainListUnit> items = FXCollections.observableList(list);
        listView.setItems(null);
        listView.setItems(items);
    }

    private void showEdit(Unit item)
    {
        editUnit.setVisible(true);
        editBox.setItem(item);
    }
    private void hideEdit()
    {
        editUnit.setVisible(false);
    }
}