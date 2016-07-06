package stan.block.note.ui.panes.main;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import stan.block.note.core.BNCore;
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
    private HBox botButtons;
    private Pane back = new Pane();

    //FIELDS
    private double xOffset = 0;
    private double yOffset = 0;
    private INoteBoxListener listener;
    private String actualTableId;
    private String noteId;

    public NoteBox(String tableId, String id, INoteBoxListener l)
    {
        super();
		this.actualTableId = tableId;
		this.noteId = id;
        //this.setStyle("-fx-background-color: white");
        back.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 3, 0, 0, 0));
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
                botButtons.setVisible(newValue);
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
        StackPane.setMargin(back, new Insets(14));
        //StackPane.setAlignment(back,Pos.CENTER);
        move.setId("move_note");
        move.setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                listener.moveEnd();
            }
        });
        move.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                xOffset = getLayoutX() - event.getScreenX();
                yOffset = getLayoutY() - event.getScreenY();
                listener.moveBegin();
            }
        });
        move.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                drag(event);
            }
        });
        move.setVisible(false);
        StackPane.setAlignment(move, Pos.TOP_LEFT);
		//
        botButtons = initBotButtons();
        botButtons.setMaxSize(0, 0);
        botButtons.setVisible(false);
        StackPane.setAlignment(botButtons, Pos.BOTTOM_LEFT);
		//
        this.getChildren().addAll(back, move, botButtons);
		refresh();
    }
    private HBox initBotButtons()
    {
        HBox box = new HBox();
		//
		Button putNewMLTCase = new Button();
        putNewMLTCase.setId("put_new_table_button");
        putNewMLTCase.setText("+MLT");
        putNewMLTCase.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
				
            }
        });
		//
        box.getChildren().addAll(putNewMLTCase);
		return box;
	}
	
    private void refresh()
    {
		Note note = BNCore.getInstance().getNote(this.actualTableId, this.noteId);
        back.setStyle("-fx-background-color: #" + note.color);
		back.getChildren().remove(casesBoxes);
		casesBoxes = new VBox();
        casesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.8)");
        casesBoxes.setMinWidth(240);
        casesBoxes.setMinHeight(60);
        back.getChildren().add(casesBoxes);
        initCases(note.cases);
    }
	
    private void drag(MouseEvent event)
    {
        double x = event.getScreenX() + xOffset;
        if(x < 0)
        {
            x = 0;
        }
        setLayoutX(x);
        double y = event.getScreenY() + yOffset;
        if(y < 0)
        {
            y = 0;
        }
        setLayoutY(y);
        listener.move(getBoundsInParent().getMaxX(), getLayoutY(), NoteBox.this.getHeight());
    }
    private void initCases(List<Case> cases)
    {
        for(int i = 0; i < cases.size(); i++)
        {
            casesBoxes.getChildren().add(CaseConstructor.constructFromCase(cases.get(i)));
        }
    }
}