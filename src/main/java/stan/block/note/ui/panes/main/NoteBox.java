package stan.block.note.ui.panes.main;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.EventHandler;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import stan.block.note.core.notes.Case;
import stan.block.note.core.notes.Note;

import stan.block.note.helpers.ui.CaseConstructor;
import stan.block.note.listeners.ui.panes.main.INoteBoxListener;

public class NoteBox
    extends StackPane
{
    //VIEWS
    private VBox casesBoxes = new VBox();
    private Button move = new Button();
    private Pane back = new Pane();

    //FIELDS
    private double xOffset = 0;
    private double yOffset = 0;
    private INoteBoxListener listener;

    public NoteBox(Note note, INoteBoxListener l)
    {
        super();
        //this.setStyle("-fx-background-color: white");
        back.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 3, 0, 0, 0));
        back.setStyle("-fx-background-color: #" + note.color);
        //this.setPadding(new Insets(60));
        this.listener = l;
        this.hoverProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                //System.out.println("Hover: " + oldValue + " -> " + newValue);
                listener.hover(newValue);
                move.setVisible(newValue);
                if(newValue)
                {
                    back.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 5, 0, 0, 0));
                }
                else
                {
                    back.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 3, 0, 0, 0));
                }
            }
        });
        //this.setMinWidth(240);
        //this.setMinHeight(60);
        casesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.8)");
        //casesBoxes.prefWidthProperty().bind(this.widthProperty());
        //casesBoxes.prefHeightProperty().bind(this.heightProperty());
        casesBoxes.setMinWidth(240);
        casesBoxes.setMinHeight(60);
        initCases(note.cases);
        back.getChildren().addAll(casesBoxes);
        StackPane.setMargin(back, new Insets(14));
        //StackPane.setAlignment(back,Pos.CENTER);
        move.setId("move_note");
        move.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                xOffset = getLayoutX() - event.getScreenX();
                yOffset = getLayoutY() - event.getScreenY();
            }
        });
        move.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                setLayoutX(event.getScreenX() + xOffset);
                setLayoutY(event.getScreenY() + yOffset);
            }
        });
        move.setVisible(false);
        StackPane.setAlignment(move, Pos.TOP_LEFT);
        this.getChildren().addAll(back, move);
    }
    private void initCases(List<Case> cases)
    {
        for(int i = 0; i < cases.size(); i++)
        {
            casesBoxes.getChildren().add(CaseConstructor.constructFromCase(cases.get(i)));
        }
    }
}