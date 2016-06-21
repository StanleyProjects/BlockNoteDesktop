package stan.block.note.ui.panes.start;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.paint.Color;

import stan.block.note.listeners.ui.panes.start.IStartTopBoxListener;

public class StartTopBox
    extends HBox
{
    //VIEWS
    private Button exit = new Button();
    private Label appName = new Label();

    //FIELDS
    private IStartTopBoxListener listener;

    public StartTopBox(IStartTopBoxListener l)
    {
        super();
        this.listener = l;
        this.setStyle("-fx-background-color: red");
		initViews();
    }
    private void initViews()
    {
        int boxH = 36;
        this.setMinHeight(boxH);
        //
        exit.setId("exit_button");
        exit.setMinWidth(boxH);
        exit.prefHeightProperty().bind(this.heightProperty());
        exit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.exit();
            }
        });
        //
        appName.setText("BlockNote");
        appName.setId("start_top_app_name");
        appName.prefHeightProperty().bind(this.heightProperty());
        HBox.setHgrow(appName, Priority.ALWAYS);
        appName.setMaxWidth(Double.MAX_VALUE);
		appName.setTextFill(Color.web("white"));
        appName.setPadding(new Insets(0, 0, 0, 6));
        appName.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                listener.moveStart(event.getScreenX(), event.getScreenY());
            }
        });
        appName.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                listener.moveDrag(event.getScreenX(), event.getScreenY());
            }
        });
        //
        this.getChildren().addAll(appName, exit);
    }
}