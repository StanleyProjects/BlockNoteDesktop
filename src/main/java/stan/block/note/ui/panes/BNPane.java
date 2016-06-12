package stan.block.note.ui.panes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.util.Callback;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BNPane
    extends VBox
{
    //VIEWS
    Button back = new Button();
    Button close = new Button();
    Label blockName = new Label();
    Label tableName = new Label();
    ListView<String> listView;

	public BNPane()
	{
        super();
        this.setStyle("-fx-background-color: null");
        //this.getChildren().addAll(initTestPane());
        this.getChildren().addAll(initTopPane(), initMainPane());
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
        blockName.setText("blockName nameaaaaaaaaaaaaaaaaaaaaaa!!!!---+!");
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

    private VBox initLeftPane()
    {
		int boxW = 192;
        VBox blockLeft = new VBox();
        blockLeft.setStyle("-fx-background-color: red");
		blockLeft.setPrefWidth(boxW);
		listView = initListView();
		listView.prefHeightProperty().bind(blockLeft.heightProperty());
        blockLeft.getChildren().addAll(listView);
        return blockLeft;
    }
    private ListView<String> initListView()
    {
		ObservableList<String> items = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");
		ListView<String> list = new ListView<String>();
        list.setItems(items);
        list.setCellFactory(new Callback<ListView<String>, 
            ListCell<String>>()
			{
                @Override 
                public ListCell<String> call(ListView<String> list)
				{
                    return new ColorRectCell();
                }
            }
        );
		return list;
	}
	
    static class ColorRectCell extends ListCell<String>
	{
        @Override
        public void updateItem(String item, boolean empty)
		{
            super.updateItem(item, empty);
			setId("main_list_cell");
            if (item != null)
			{
				setGraphic(null);
                setText(item);
            }
        }
    }
	
}