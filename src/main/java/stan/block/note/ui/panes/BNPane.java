package stan.block.note.ui.panes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

public class BNPane
    extends HBox
{
    //VIEWS
    Button back = new Button();
    Label blockName = new Label();
    ListView<String> listView;

	public BNPane()
	{
        super();
        this.setStyle("-fx-background-color: null");
        this.getChildren().add(initLeftPane());
	}

    private VBox initLeftPane()
    {
		int boxW = 192;
        VBox blockInfo = new VBox();
        blockInfo.setStyle("-fx-background-color: red");
		blockInfo.setPrefWidth(boxW);
		listView = initListView();
		listView.prefHeightProperty().bind(blockInfo.heightProperty());
        blockInfo.getChildren().addAll(initLeftTopPane(), listView);
        return blockInfo;
    }
    private HBox initLeftTopPane()
    {
		int boxH = 36;
        HBox blockTop = new HBox();
		blockTop.setMinHeight(boxH);
        blockTop.setStyle("-fx-background-color: green");
        back.setId("back_button");
		back.setMinWidth(boxH);
		back.prefHeightProperty().bind(blockTop.heightProperty());
        blockName.setText("block nameaaaaaaaaaaaaaaaaaaaaaa!!!!---+aaaaaaaaaaaaaaaaaaaaaaa!");
        blockName.setStyle("-fx-background-color: orange");
		blockName.prefHeightProperty().bind(blockTop.heightProperty());
        blockTop.getChildren().addAll(back, blockName);
        return blockTop;
    }
    private ListView<String> initListView()
    {
        ObservableList<String> items = FXCollections.observableArrayList("Single", "Double", "Suite", "Family App");
		ListView<String> list = new ListView<String>();
        list.setItems(items);
		return list;
	}
}