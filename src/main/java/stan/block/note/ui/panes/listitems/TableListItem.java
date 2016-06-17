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

import stan.block.note.core.Table;

import stan.block.note.listeners.ui.panes.IEditUnitBoxListener;

public class TableListItem
    extends HBox
{
    //VIEWS
    private Label name = new Label();

    //FIELDS

    public TableListItem(Table t)
    {
        super();
        this.setStyle("-fx-background-color: white");
        name.setText(t.name);
		name.setTextFill(Color.web("0x"+t.color));
        name.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().addAll(name);
    }
}