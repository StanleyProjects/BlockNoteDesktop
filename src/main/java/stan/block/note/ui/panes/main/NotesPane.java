package stan.block.note.ui.panes.main;

import java.util.List;

import javafx.scene.effect.BlurType; 
import javafx.scene.effect.InnerShadow;

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

    public NotesPane()
    {
        super();
        //this.setId("notes_pane");
        this.setEffect(new InnerShadow(BlurType.THREE_PASS_BOX, Color.BLACK,3,0,0,0));
    }

    private void initViews(List<Note> notes)
    {
        this.getChildren().remove(notesBoxes);
        notesBoxes = new Pane();
        notesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.9)");
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