package stan.block.note.ui.panes.listitems;

import javafx.geometry.Insets;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;

import stan.block.note.core.units.Table;

public class TableListItem
    extends HBox
{
    //VIEWS
    private Label name = new Label();

    //FIELDS

    public TableListItem(Table t, boolean select)
    {
        super();
        name.setId("unit_list_item_name");
		if(select)
		{
			this.setStyle("-fx-background-color: #" + t.color);
			name.setTextFill(Color.web("white"));
		}
		else
		{
			this.setStyle("-fx-background-color: white");
			name.setTextFill(Color.web("0x"+t.color));
		}
        name.setText(t.name);
        name.prefHeightProperty().bind(this.heightProperty());
        name.prefWidthProperty().bind(this.widthProperty());
        name.setPadding(new Insets(0, 0, 0, 4));
        this.getChildren().addAll(name);
    }
}