package stan.block.note.ui.panes.main;

import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import stan.block.note.core.notes.Case;
import stan.block.note.core.notes.Note;

public class NoteBox
    extends Pane
{
    //VIEWS
    private VBox casesBoxes = new VBox();

    //FIELDS

    public NoteBox(Note note)
    {
        super();
        this.setId("note_box");
        this.setStyle("-fx-background-color: #" + note.color);
        this.setMinWidth(240);
        this.setMinHeight(60);
        casesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.6)");
        casesBoxes.prefWidthProperty().bind(this.widthProperty());
        casesBoxes.prefHeightProperty().bind(this.heightProperty());
        initViews(note.cases);
    }
    private void initViews(List<Case> cases)
    {
        this.getChildren().add(casesBoxes);
    }
}