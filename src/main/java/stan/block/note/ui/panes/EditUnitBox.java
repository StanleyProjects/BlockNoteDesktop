package stan.block.note.ui.panes;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Pos;
import javafx.geometry.Orientation;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.input.MouseEvent;

import stan.block.note.core.Colors;
import stan.block.note.core.Unit;

import stan.block.note.listeners.ui.panes.IEditUnitBoxListener;

public class EditUnitBox
    extends VBox
{
    //VIEWS
    private Button cancel = new Button("CANCEL");
    private Button save = new Button("SAVE");
    private TextField nameEdit = new TextField();
    private ListView<ColorSelect> listColors;

    //FIELDS
    private IEditUnitBoxListener listener;
    private Unit item;
    private String color;
    private List<ColorSelect> listColorSelects = new ArrayList<ColorSelect>();

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
        //
        listColors = initListView();
        listColors.setMaxHeight(36);
        listColors.setMinWidth(36);
        initListColorSelects();
        //setListColors();
        //refreshList();
        //
        //this.getChildren().addAll(nameEdit, buttonsBox);
        this.getChildren().addAll(nameEdit, listColors, buttonsBox);
    }
    private ListView<ColorSelect> initListView()
    {
        ListView<ColorSelect> list = new ListView<ColorSelect>();
        list.setOrientation(Orientation.HORIZONTAL);
        list.setCellFactory(new Callback<ListView<ColorSelect>, ListCell<ColorSelect>>()
        {
            @Override
            public ListCell<ColorSelect> call(ListView<ColorSelect> list)
            {
                return new ColorCell();
            }
        });
        return list;
    }
    private void initListColorSelects()
    {
        listColorSelects = new ArrayList<ColorSelect>();
        listColorSelects.add(new ColorSelect(Colors.RED));
        listColorSelects.add(new ColorSelect(Colors.BLUE));
    }
    private void refreshList()
    {
        System.out.println("ColorListItem - refreshList color " + color);
        for(int i = 0; i < listColorSelects.size(); i++)
        {
            if(listColorSelects.get(i).color.equals(color))
            {
                listColorSelects.get(i).select = true;
            }
            else
            {
                listColorSelects.get(i).select = false;
            }
        }
        setListColors();
    }
    private void setListColors()
    {
        ObservableList<ColorSelect> items = FXCollections.observableList(listColorSelects);
        listColors.setItems(null);
        listColors.setItems(items);
    }

    public void setItem(Unit i)
    {
        item = i;
        color = item.color;
        nameEdit.setText(item.name);
        refreshList();
    }
    public void updateItem()
    {
        item.name = nameEdit.getText();
        item.color = color;
    }


    private class ColorCell
        extends ListCell<ColorSelect>
    {
        @Override
        public void updateItem(ColorSelect item, boolean empty)
        {
            super.updateItem(item, empty);
            setId("color_list_cell");
            setGraphic(null);
            if(!empty)
            {
                setGraphic(new ColorListItem(item));
            }
        }
    }
    private class ColorListItem
        extends HBox
    {
        public ColorListItem(ColorSelect item)
        {
            super();
            setMinHeight(36);
            setMinWidth(36);
            if(item.select)
            {
                System.out.println("ColorListItem - select");
                this.setStyle("-fx-background-color: white");
            }
            else
            {
                this.setStyle("-fx-background-color: #" + item.color);
            }
            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    color = item.color;
                    System.out.println("ColorListItem - handle color " + color);
                    refreshList();
                }
            });
        }
    }
    private class ColorSelect
    {
        public String color;
        public boolean select;

        public ColorSelect(String c)
        {
            this.color = c;
            this.select = false;
        }
    }
}