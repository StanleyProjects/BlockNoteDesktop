package stan.block.note.ui.panes.main;

import java.util.List;

import javafx.scene.effect.BlurType; 
import javafx.scene.effect.DropShadow;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;

import stan.block.note.core.notes.Case;
import stan.block.note.core.notes.Note;

import stan.block.note.helpers.ui.CaseConstructor;

public class NoteBox
    extends Pane
{
    //VIEWS
    private VBox casesBoxes = new VBox();

    //FIELDS

    public NoteBox(Note note)
    {
        super();
        this.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK,3,0,0,0));
        this.setStyle("-fx-background-color: #" + note.color);
        //this.setMinWidth(240);
        //this.setMinHeight(60);
        casesBoxes.setStyle("-fx-background-color: rgba(255,255,255,0.8)");
        //casesBoxes.prefWidthProperty().bind(this.widthProperty());
        //casesBoxes.prefHeightProperty().bind(this.heightProperty());
        casesBoxes.setMinWidth(240);
        casesBoxes.setMinHeight(60);
        initViews(note.cases);
    }
    private void initViews(List<Case> cases)
    {
        for(int i=0; i<cases.size(); i++)
        {
            casesBoxes.getChildren().add(CaseConstructor.constructFromCase(cases.get(i)));
        }
        this.getChildren().add(casesBoxes);
    }
}