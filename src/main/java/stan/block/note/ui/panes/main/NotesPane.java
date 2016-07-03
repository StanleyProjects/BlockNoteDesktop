package stan.block.note.ui.panes.main;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Bounds;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

import stan.block.note.core.notes.Note;
import stan.block.note.core.units.Table;
import stan.block.note.listeners.ui.panes.main.INoteBoxListener;

public class NotesPane
    extends Pane
{
    //VIEWS
    private Pane notesBoxes;
    private ScrollPane scrollPane;
    //

    //FIELDS
    private ContextMenu menu;
    private boolean hover;
    private INoteBoxListener listener;

    public NotesPane()
    {
        super();
        this.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 3, 0, 0, 0));
        this.menu = initContextMenu();
        this.listener = new INoteBoxListener()
        {
            @Override
            public void hover(boolean h)
            {
                hover = h;
            }
            @Override
            public void moveBegin()
            {
                //notesBoxes.setMinWidth(notesBoxes.getWidth());
                //notesBoxes.setMinHeight(notesBoxes.getHeight());
            }
            @Override
            public void move(double x, double y, double height)
            {
                //System.out.println("--- --- y - " + y);
                //System.out.println("--- --- notesBoxesHeight - " + notesBoxes.getHeight());
                //System.out.println("--- --- NotesPane.this.getHeight() - " + NotesPane.this.getHeight());
                
                if(notesBoxes.getHeight() - y <= height || notesBoxes.getWidth() - x <= 0)
                {
                    scrollTo(scrollPane, x, y + height);
                }
                /*
                else if(notesBoxes.getHeight() - y >= NotesPane.this.getHeight())
                {
                    scrollTo(scrollPane, x, y + NotesPane.this.getHeight());
                }
                */
            }
            @Override
            public void moveEnd()
            {
                //notesBoxes.setMinWidth(0);
                //notesBoxes.setMinHeight(0);
            }
        };
        this.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                if(event.isPrimaryButtonDown())
                {
                    if(!hover)
                    {
                        System.out.println("ScreenX - " + event.getScreenX() + " ScreenY - " + event.getScreenY());
                    }
                }
            }
        });
        this.hover = false;
        /*
        center = new Pane();
        center.setStyle("-fx-background-color: red");
        center.prefWidthProperty().bind(this.widthProperty());
        center.setMinHeight(5);
        */
        scrollPane = new ScrollPane();
        //scrollPane.setStyle("-fx-background-color: null");
        scrollPane.setStyle("-fx-background-color: rgba(255,255,255,0.6)");
        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.prefWidthProperty().bind(this.widthProperty());
        scrollPane.prefHeightProperty().bind(this.heightProperty());
        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val)
            {
                System.out.println(new_val.intValue());
            }
        });
        //setContextMenu(initContextMenu(b));
    }
    /*
    private void scrollTo(ScrollPane pane, Node node)
    {
        double width = pane.getContent().getBoundsInLocal().getWidth();
        double height = pane.getContent().getBoundsInLocal().getHeight();
        double x = node.getBoundsInParent().getMaxX();
        double y = node.getBoundsInParent().getMaxY();
        pane.setVvalue(y/height);
        pane.setHvalue(x/width);
        node.requestFocus();
    }
    */
    private void scrollToOld(double x, double y)
    {
        double v = 1;
        //v = (y+scrollPane.getHeight()/2) / notesBoxes.getHeight();
        v = 1 / (notesBoxes.getHeight() / (y + NotesPane.this.getHeight() / 2));
        //v = 1/(notesBoxes.getHeight()/y);
        //v = (y+scrollPane.getHeight()/2)/(notesBoxes.getHeight()/1000)/1000;
        //v = y/(notesBoxes.getHeight()/10)/10;
        if(v > 1)
        {
            v = 1;
        }
        v = y;
        //v = Math.sqrt(v);
        System.out.println("--- --- scrollPaneVmax - " + scrollPane.getVmax());
        System.out.println("--- --- notesBoxesHeight - " + notesBoxes.getHeight());
        System.out.println("--- --- y - " + y);
        System.out.println("--- --- scrollPaneHeight - " + scrollPane.getHeight());
        System.out.println("--- v - " + v);
        scrollPane.setVvalue(v);
    }
    private void scrollTo(ScrollPane pane, double x, double y)
    {
        double width = pane.getContent().getBoundsInLocal().getWidth();
        double height = pane.getContent().getBoundsInLocal().getHeight();
        pane.setVvalue(y / height);
        pane.setHvalue(x / width);
        //node.requestFocus();
    }
    private ContextMenu initContextMenu()
    {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem newNote = new MenuItem("New note");
        newNote.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //listener.newNote(item);
            }
        });
        contextMenu.getItems().add(newNote);
        return contextMenu;
    }

    private void initViews(List<Note> notes)
    {
        //this.getChildren().remove(notesBoxes);
        this.getChildren().remove(scrollPane);
        notesBoxes = new Pane();
        //notesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.6)");
        //notesBoxes.prefWidthProperty().bind(scrollPane.widthProperty());
        //notesBoxes.prefHeightProperty().bind(scrollPane.heightProperty());
        NoteBox noteBox = new NoteBox(notes.get(0), this.listener);
        noteBox.setLayoutX(25);
        noteBox.setLayoutY(25);
        notesBoxes.getChildren().add(noteBox);
        //notesBoxes.getChildren().addAll(noteBox, center);
        //scrollPane.vmaxProperty().bind(notesBoxes.widthProperty());
        scrollPane.setContent(notesBoxes);
        //this.getChildren().add(notesBoxes);
        this.getChildren().add(scrollPane);
    }

    public void show(Table table)
    {
        this.setStyle("-fx-background-color: #" + table.color);
        initViews(table.notes);
        this.setVisible(true);
    }
    public void hide()
    {
        this.setVisible(false);
    }
}