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

import stan.block.note.listeners.ui.cells.IUnitCellListener;

public class BNPane
    extends VBox
{
    //VIEWS
    Button back = new Button();
    Button close = new Button();
    Button putNewBlock = new Button();
    Label blockName = new Label();
    Label tableName = new Label();
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
        BNCore.getInstance().openBlockNote("E:/Downloads/blocknote/test.star");
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
        blockMain.getChildren().addAll(initLeftPane());
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
        botButtons.getChildren().addAll(putNewBlock);
        botButtons.setAlignment(Pos.CENTER_RIGHT);
        //
        blockLeft.getChildren().addAll(listView, botButtons);
        blockLeft.setAlignment(Pos.BOTTOM_RIGHT);
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
    private void setListUnits(Block block)
    {
        List<Unit> list = new ArrayList<Unit>();
        for(int i = 0; i < block.blocks.size(); i++)
        {
            list.add(block.blocks.get(i));
        }
        ObservableList<Unit> items = FXCollections.observableList(list);
        listView.setItems(items);
    }
}