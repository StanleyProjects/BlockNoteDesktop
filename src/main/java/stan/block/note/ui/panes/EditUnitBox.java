package stan.block.note.ui.panes;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import stan.block.note.core.Unit;

import stan.block.note.listeners.ui.panes.IEditUnitBoxListener;

public class EditUnitBox
    extends VBox
{
    //VIEWS
    private Button cancel = new Button("CANCEL");
    private Button save = new Button("SAVE");
    private TextField nameEdit = new TextField();

    //FIELDS
    private IEditUnitBoxListener listener;
    private Unit item;

    public EditUnitBox(IEditUnitBoxListener l)
    {
        super();
        listener = l;
        this.setStyle("-fx-background-color: white");
        HBox buttonsBox = new HBox();
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.cancel();
            }
        });
        save.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                updateItem();
                listener.save(item);
            }
        });
        buttonsBox.getChildren().addAll(cancel, save);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll(nameEdit, buttonsBox);
    }

    public void setItem(Unit i)
    {
        item = i;
        nameEdit.setText(item.name);
    }
    public void updateItem()
    {
        item.name = nameEdit.getText();
    }
}