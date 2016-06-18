package stan.block.note.ui.panes.listitems;

import javafx.geometry.Insets;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;

import stan.block.note.core.Table;

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
        name.setId("unit_list_item_name");
        name.setText(t.name);
		name.setTextFill(Color.web("0x"+t.color));
        name.prefHeightProperty().bind(this.heightProperty());
        name.prefWidthProperty().bind(this.widthProperty());
        name.setPadding(new Insets(0, 0, 0, 4));
        this.getChildren().addAll(name);
    }
}