package stan.block.note.ui.panes.listitems;

import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

import javafx.scene.paint.Color;

import stan.block.note.core.Block;
import stan.block.note.core.Table;

import stan.block.note.listeners.ui.panes.IEditUnitBoxListener;

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
        name.setText(block.name);
		name.setTextFill(Color.web("0x"+block.color));
        name.prefHeightProperty().bind(this.heightProperty());
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