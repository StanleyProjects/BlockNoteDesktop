package stan.block.note.ui.panes.main;

import java.util.HashMap;
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

import stan.block.note.core.BNCore;
import stan.block.note.core.notes.Note;
import stan.block.note.core.units.Table;

import stan.block.note.helpers.BNDesktopSettings;

import stan.block.note.listeners.ui.panes.main.INoteBoxListener;
import stan.block.note.listeners.ui.panes.main.INotesPaneListener;

public class NotesPane
    extends Pane
{
    //VIEWS
    private Pane notesBoxes;
    private ScrollPane scrollPane;
    //

    //FIELDS
    private boolean hover;
    private INotesPaneListener listener;
    private INoteBoxListener noteBoxListener;
    private String actualTableId;

    public NotesPane()
    {
        super();
        this.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 3, 0, 0, 0));
        this.noteBoxListener = new INoteBoxListener()
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
                if(notesBoxes.getHeight() - y <= height || notesBoxes.getWidth() - x <= 0)
                {
                    scrollTo(scrollPane, x, y + height);
                }
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
						HashMap settings = new HashMap<>();
						Bounds bounds = NotesPane.this.getBoundsInLocal();
						Bounds screenBounds = NotesPane.this.localToScreen(bounds);
						settings.put("x", event.getScreenX() - screenBounds.getMinX());
						settings.put("y", event.getScreenY() - screenBounds.getMinY());
						BNCore.getInstance().putNewNote(actualTableId, BNDesktopSettings.NOTE_COORDINATE_SETTINGS, settings);
						refresh();
						//listener.addNew(event.getScreenX(), event.getScreenY());
                        //System.out.println("ScreenX - " + event.getScreenX() + " ScreenY - " + event.getScreenY());
                    }
                }
            }
        });
        this.hover = false;
        scrollPane = new ScrollPane();
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
                //System.out.println(new_val.intValue());
            }
        });
    }
    private void scrollTo(ScrollPane pane, double x, double y)
    {
        double width = pane.getContent().getBoundsInLocal().getWidth();
        double height = pane.getContent().getBoundsInLocal().getHeight();
        pane.setVvalue(y / height);
        pane.setHvalue(x / width);
        //node.requestFocus();
    }
    private void refresh()
    {
		Table table = BNCore.getInstance().getTable(actualTableId);
        initViews(table.notes);
    }

    private void initViews(List<Note> notes)
    {
        //this.getChildren().remove(notesBoxes);
        this.getChildren().remove(scrollPane);
        notesBoxes = new Pane();
        //notesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.6)");
        //notesBoxes.prefWidthProperty().bind(scrollPane.widthProperty());
        //notesBoxes.prefHeightProperty().bind(scrollPane.heightProperty());
		for(int i=0; i<notes.size(); i++)
		{
			NoteBox noteBox = new NoteBox(actualTableId, notes.get(i).id, this.noteBoxListener);
			HashMap settings = notes.get(i).settings;
			HashMap note_coordinate_settings = (HashMap)settings.get(BNDesktopSettings.NOTE_COORDINATE_SETTINGS);
			if(note_coordinate_settings == null)
			{
				note_coordinate_settings = new HashMap<>();
				note_coordinate_settings.put("x", 25);
				note_coordinate_settings.put("y", 25);
			}
			noteBox.setLayoutX((Double)note_coordinate_settings.get("x"));
			noteBox.setLayoutY((Double)note_coordinate_settings.get("y"));
			notesBoxes.getChildren().add(noteBox);
			//notesBoxes.getChildren().addAll(noteBox, center);
		}
		
        //scrollPane.vmaxProperty().bind(notesBoxes.widthProperty());
        scrollPane.setContent(notesBoxes);
        //this.getChildren().add(notesBoxes);
        this.getChildren().add(scrollPane);
    }

    public void setListener(INotesPaneListener l)
    {
		this.listener = l;
	}
	
    public void show(Table table)
    {
		this.actualTableId = table.id;
        this.setStyle("-fx-background-color: #" + table.color);
        initViews(table.notes);
        this.setVisible(true);
    }
    public void hide()
    {
        this.setVisible(false);
    }
}