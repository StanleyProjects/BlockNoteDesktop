package stan.block.note.ui.panes.start;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;

import stan.block.note.listeners.ui.panes.start.IStartScreenPaneListener;
import stan.block.note.listeners.ui.panes.start.IStartTopBoxListener;

public class StartScreenPane
    extends VBox
{
    private double xOffset = 0;
    private double yOffset = 0;
	
    //VIEWS
    private StartTopBox startTopBox;
    private Button openBlockNote = new Button();
    private Button newBlockNote = new Button();

    //FIELDS
    private IStartScreenPaneListener listener;

    public StartScreenPane(final Stage primaryStage, IStartScreenPaneListener l)
    {
        super();
        this.listener = l;
        this.setStyle("-fx-background-color: null");
		startTopBox = new StartTopBox(new IStartTopBoxListener()
        {
            public void moveDrag(double x, double y)
            {
                primaryStage.setX(x + xOffset);
                primaryStage.setY(y + yOffset);
            }
            public void moveStart(double x, double y)
            {
                xOffset = primaryStage.getX() - x;
                yOffset = primaryStage.getY() - y;
            }
            public void exit()
            {
                listener.exit();
            }
        });
		initViews();
    }
    private void initViews()
    {
        startTopBox.setMinHeight(36);
		VBox container = new VBox();
        container.setId("start_screen");
        container.prefHeightProperty().bind(this.heightProperty());
		StackPane main = new StackPane();
        main.prefHeightProperty().bind(container.heightProperty());
		//
        HBox botButtons = new HBox(5);
        botButtons.setMaxHeight(36);
        //
        openBlockNote.setId("open_block_note");
        openBlockNote.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.openBlockNote();
            }
        });
        newBlockNote.setId("new_block_note");
        newBlockNote.setText("+T");
        newBlockNote.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                listener.newBlockNote();
            }
        });
        //
		Pane circle_back = new Pane();
        circle_back.setId("circle_back");
        circle_back.setStyle("-fx-background-color: red");
		StackPane openStack = new StackPane();
        openStack.getChildren().addAll(circle_back, openBlockNote);
        botButtons.getChildren().addAll(openStack, newBlockNote);
        StackPane.setMargin(botButtons,new Insets(5));
        botButtons.setAlignment(Pos.CENTER_RIGHT);
        //
        main.getChildren().addAll(botButtons);
        main.setAlignment(Pos.BOTTOM_CENTER);
        container.getChildren().addAll(startTopBox ,main);
        VBox.setMargin(container,new Insets(5));
        this.getChildren().addAll(container);
    }
}