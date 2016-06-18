package stan.block.note.ui.panes.listitems;

import javafx.geometry.Insets;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

import javafx.scene.paint.Color;

import stan.block.note.core.Block;

public class BlockListItem
    extends HBox
{
    //VIEWS
    private Label name = new Label();

    //FIELDS

    public BlockListItem(Block block)
    {
        super();
        this.setStyle("-fx-background-color: white");
        name.setId("unit_list_item_name");
        name.setText(block.name);
		name.setTextFill(Color.web("0x"+block.color));
        name.prefHeightProperty().bind(this.heightProperty());
        name.prefWidthProperty().bind(this.widthProperty());
        name.setPadding(new Insets(0, 0, 0, 4));
		VBox icoBox = new VBox();
        icoBox.setMaxWidth(18);
		icoBox.setStyle("-fx-background-color: #"+block.color);
		ImageView blockIco = new ImageView();
		blockIco.setId("block_ico");
		icoBox.getChildren().addAll(blockIco);
        HBox.setHgrow(icoBox, Priority.ALWAYS);
        icoBox.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().addAll(icoBox, name);
    }
}