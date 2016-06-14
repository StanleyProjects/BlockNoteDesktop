package stan.block.note.ui.panes;

import java.util.ArrayList;
import java.util.List;

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

import javafx.util.Callback;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import stan.block.note.core.BNCore;
import stan.block.note.core.Block;
import stan.block.note.core.Unit;

import stan.block.note.ui.cells.UnitCell;
import stan.block.note.ui.panes.EditUnitBox;

import stan.block.note.listeners.ui.cells.IUnitCellListener;
import stan.block.note.listeners.ui.panes.IEditUnitBoxListener;

public class BNPane
    extends VBox
{
    //VIEWS
    Button back = new Button();
    Button close = new Button();
    Button putNewBlock = new Button();
    Button putNewTable = new Button();
    Label blockName = new Label();
    Label tableName = new Label();
    //
    StackPane editUnit = new StackPane();
    EditUnitBox editBox;
    //
    ListView<Unit> listView;

    public BNPane()
    {
        super();
        this.setStyle("-fx-background-color: null");
        //this.getChildren().addAll(initTestPane());
        this.getChildren().addAll(initTopPane(), initMainPane());
        init();
    }
    private void init()
    {
        tableName.setVisible(false);
        refresh();
    }
    private void refresh()
    {
        BNCore.getInstance().openBlockNote("test.star");
        Block block = BNCore.getInstance().getActualBlock();
        blockName.setText(block.name);
        setListUnits(block);
    }

    private HBox initTestPane()
    {
        HBox hbox = new HBox(10);
        hbox.setStyle("-fx-background-color: green");
        Label field = new Label();
        field.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(field, Priority.ALWAYS);
        hbox.getChildren().addAll(
            new Label("Search:"), field, new Button("Go")
        );
        return hbox;
    }
    private HBox initTopPane()
    {
        int boxH = 36;
        HBox blockTop = new HBox();
        blockTop.setMinHeight(boxH);
        blockTop.setStyle("-fx-background-color: green");
        //
        back.setId("back_button");
        back.setMinWidth(boxH);
        back.prefHeightProperty().bind(blockTop.heightProperty());
        //
        //blockName.setText("blockName nameaaaaaaaaaaaaaaaaaaaaaa!!!!---+!");
        blockName.setStyle("-fx-background-color: orange");
        blockName.prefHeightProperty().bind(blockTop.heightProperty());
        //blockName.prefWidthProperty().bind(blockTop.widthProperty());
        HBox.setHgrow(blockName, Priority.ALWAYS);
        blockName.setMinWidth(256);
        blockName.setMaxWidth(Double.MAX_VALUE);
        //
        tableName.setText("tableName nameaaaaaaaaaaaaaaaaaaaaaa!!!!---+aaaaaaaaaaaaaaaaaaaaaaa!");
        tableName.setStyle("-fx-background-color: pink");
        tableName.prefHeightProperty().bind(blockTop.heightProperty());
        tableName.prefWidthProperty().bind(blockTop.widthProperty());
        tableName.managedProperty().bind(tableName.visibleProperty());
        //tableName.setVisible(false);
        //
        close.setId("close_button");
        close.setMinWidth(boxH);
        close.prefHeightProperty().bind(blockTop.heightProperty());
        //
        blockTop.getChildren().addAll(back, blockName, tableName, close);
        //blockTop.getChildren().addAll(back, blockName, close);
        return blockTop;
    }
    private HBox initMainPane()
    {
        HBox blockMain = new HBox();
        blockMain.setStyle("-fx-background-color: purple");
        VBox.setVgrow(blockMain, Priority.ALWAYS);
        blockMain.getChildren().addAll(initLeftPane(), initRightPane());
        return blockMain;
    }

    private StackPane initLeftPane()
    {
        int boxH = 36;
        int boxW = 192;
        StackPane blockLeft = new StackPane();
        blockLeft.setStyle("-fx-background-color: red");
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
    private ListView<Unit> initListView()
    {
        ListView<Unit> list = new ListView<Unit>();
        list.setCellFactory(new Callback<ListView<Unit>, ListCell<Unit>>()
        {
            @Override
            public ListCell<Unit> call(ListView<Unit> list)
            {
                return new UnitCell(new IUnitCellListener()
            	{
					public void editUnit(Unit item)
					{
						showEdit(item);
					}
					public void deleteUnit(Unit item)
					{
		                BNCore.getInstance().deleteBlock(item.id);
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
        blockRight.setStyle("-fx-background-color: orange");
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
                BNCore.getInstance().editBlock(item.id, item.name);
                refresh();
				hideEdit();
			}
    	});
		editBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        editUnit.getChildren().addAll(editBox);
    	editUnit.setVisible(false);
        //
        blockRight.getChildren().addAll(editUnit);
        return blockRight;
    }

    private void setListUnits(Block block)
    {
        List<Unit> list = new ArrayList<Unit>();
        for(int i = 0; i < block.blocks.size(); i++)
        {
            list.add(block.blocks.get(i));
        }
        for(int i = 0; i < block.tables.size(); i++)
        {
            list.add(block.tables.get(i));
        }
        ObservableList<Unit> items = FXCollections.observableList(list);
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