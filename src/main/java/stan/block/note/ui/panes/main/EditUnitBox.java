package stan.block.note.ui.panes.main;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import stan.block.note.core.Colors;
import stan.block.note.core.Unit;

import stan.block.note.listeners.ui.panes.main.IEditUnitBoxListener;

public class EditUnitBox
    extends VBox
{
    //VIEWS
    private Button cancel = new Button("CANCEL");
    private Button save = new Button("SAVE");
    private TextField nameEdit = new TextField();
    private ListView<ColorSelect> listColors;
    private Rectangle rect = new Rectangle();

    //FIELDS
    private IEditUnitBoxListener listener;
    private Unit item;
    private String color;
    private List<ColorSelect> listColorSelects = new ArrayList<ColorSelect>();

    public EditUnitBox(IEditUnitBoxListener l)
    {
        super();
        listener = l;
        this.setId("edit_unit_box");
	    //this.setPadding(new Insets(12));
        //
        //nameEdit.setId("name_edit");
        Label name = new Label("Name");
		name.setStyle("-fx-font-size: 12;-fx-text-fill: #000000");
        VBox.setMargin(name,new Insets(12, 0, 0, 12));
        VBox.setMargin(nameEdit,new Insets(0, 12, 0, 12));
		rect.setHeight(1);
        rect.widthProperty().bind(nameEdit.widthProperty());
        VBox.setMargin(rect,new Insets(0, 12, 12, 12));
	    //
        VBox bottomBox = new VBox();
        bottomBox.prefHeightProperty().bind(this.heightProperty());
        bottomBox.getChildren().add(initButtonsBox());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        //
        Label color = new Label("Color");
        color.setStyle("-fx-font-size: 12;-fx-text-fill: #000000");
        VBox.setMargin(color,new Insets(0, 0, 0, 12));
        listColors = initListView();
        listColors.setMaxHeight(36);
        listColors.setMinHeight(36);
        listColors.setMinWidth(36);
        initListColorSelects();
        VBox.setMargin(listColors,new Insets(12));
        //
        this.getChildren().addAll(name, nameEdit, rect, color, listColors, bottomBox);
    }
    private HBox initButtonsBox()
    {
        HBox buttonsBox = new HBox();
        buttonsBox.setMinHeight(48);
        cancel.setId("cancel_button");
        cancel.prefHeightProperty().bind(buttonsBox.heightProperty());
        cancel.setPadding(new Insets(12));
        cancel.setMinWidth(96);
        cancel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.cancel();
            }
        });
        save.setId("save_button");
        save.prefHeightProperty().bind(buttonsBox.heightProperty());
        save.setPadding(new Insets(12));
        save.setMinWidth(96);
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
        return buttonsBox;
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
        listColorSelects.add(new ColorSelect(Colors.ORANGE));
    }
    private void refreshList()
    {
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
		rect.setFill(Color.web("0x"+color));
		cancel.setTextFill(Color.web("0x"+color));
		save.setTextFill(Color.web("0x"+color));
		nameEdit.setStyle("-fx-background-color: null;"
            +"-fx-font-family: Roboto;-fx-font-size: 16;-fx-font-weight: bold;"
            +"-fx-background-radius: 0;"
            +"-fx-highlight-fill: #"+color + ";"
			+"-fx-text-fill: #"+color);
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
        extends StackPane
    {
        public ColorListItem(ColorSelect item)
        {
            super();
            this.setMinHeight(36);
            this.setMinWidth(36);
            this.setStyle("-fx-background-color: #" + item.color);
            if(item.select)
            {
				ImageView doneIco = new ImageView();
				doneIco.setId("done_ico");
	            this.getChildren().add(doneIco);
            }
            else
            {
	            Pane back = new Pane();
		        back.minHeightProperty().bind(this.minHeightProperty());
		        back.minWidthProperty().bind(this.minWidthProperty());
	            back.setId("color_list_item");
	            this.getChildren().add(back);
	            //
	            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
	            {
	                @Override
	                public void handle(MouseEvent event)
	                {
	                    color = item.color;
	                    refreshList();
	                }
	            });
            }
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