package stan.block.note.ui.panes;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.paint.Color;

import stan.block.note.listeners.ui.panes.IMainTopBoxListener;

public class MainTopBox
    extends HBox
{
    //VIEWS
    private Button back = new Button();
    private Button edit = new Button();
    private Button close = new Button();
    private Label blockName = new Label();
    private Label tableName = new Label();

    //FIELDS
    private IMainTopBoxListener listener;

    public MainTopBox(IMainTopBoxListener l)
    {
        super();
        this.listener = l;
        this.setId("edit_unit_box");
		initViews();
    }
    private void initViews()
    {
        int boxH = 36;
        this.setMinHeight(boxH);
        //
        back.setId("back_button");
        back.setMinWidth(boxH);
        back.prefHeightProperty().bind(this.heightProperty());
        back.managedProperty().bind(back.visibleProperty());
        back.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.back();
            }
        });
        //
        edit.setId("edit_button");
        edit.setMinWidth(boxH);
        edit.prefHeightProperty().bind(this.heightProperty());
        edit.managedProperty().bind(edit.visibleProperty());
        edit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.edit();
            }
        });
        //
        blockName.setId("main_top_block_name");
        blockName.prefHeightProperty().bind(this.heightProperty());
        HBox.setHgrow(blockName, Priority.ALWAYS);
        blockName.setMinWidth(444);
        blockName.setMaxWidth(Double.MAX_VALUE);
		blockName.setTextFill(Color.web("white"));
        blockName.setPadding(new Insets(0, 0, 0, 6));
        //
        tableName.setId("main_top_table_name");
        tableName.prefHeightProperty().bind(this.heightProperty());
        tableName.prefWidthProperty().bind(this.widthProperty());
        tableName.managedProperty().bind(tableName.visibleProperty());
        tableName.setPadding(new Insets(0, 0, 0, 6));
        tableName.setVisible(false);
        //
        close.setId("close_button");
        close.setMinWidth(boxH);
        close.prefHeightProperty().bind(this.heightProperty());
        close.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.close();
            }
        });
        //
        this.getChildren().addAll(back, edit, blockName, tableName, close);
    }
	
	public void setVisibleHead(boolean v)
	{
        back.setVisible(v);
        edit.setVisible(!v);
	}
	public void setBlock(String name, String color)
	{
        blockName.setText(name);
        this.setStyle("-fx-background-color: #" + color);
	}
	public void clearTable()
	{
        tableName.setVisible(false);
	}
	public void setTable(String name, String color)
	{
        tableName.setText(name);
        tableName.setStyle("-fx-background-color: #" + color);
        tableName.setVisible(true);
	}
}