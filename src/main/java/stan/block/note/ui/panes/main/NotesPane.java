package stan.block.note.ui.panes.main;

import java.util.List;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import javafx.scene.effect.BlurType; 
import javafx.scene.effect.InnerShadow;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.Pane;

import javafx.scene.paint.Color; 

import javafx.scene.shape.Rectangle;

import stan.block.note.core.notes.Note;
import stan.block.note.core.units.Table;

public class NotesPane
    extends Pane
{
    //VIEWS
    Pane notesBoxes;

    //FIELDS
	ContextMenu menu;

    public NotesPane()
    {
        super();
        this.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK,3,0,0,0));
		this.menu = initContextMenu();
		this.setOnMousePressed(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if(event.isPrimaryButtonDown())
				{
					System.out.println("ScreenX - " + event.getScreenX() + " ScreenY - " + event.getScreenY());
				}
			}
		});
		//setContextMenu(initContextMenu(b));	
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
        this.getChildren().remove(notesBoxes);
        notesBoxes = new Pane();
        notesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.6)");
        notesBoxes.prefWidthProperty().bind(this.widthProperty());
        notesBoxes.prefHeightProperty().bind(this.heightProperty());
        NoteBox noteBox = new NoteBox(notes.get(0));
        noteBox.setLayoutX(25);
        noteBox.setLayoutY(25);
        notesBoxes.getChildren().add(noteBox);
        this.getChildren().add(notesBoxes);
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