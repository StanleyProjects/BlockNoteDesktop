package stan.block.note.helpers.ui;

import javafx.scene.Node;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import stan.block.note.ui.controls.ResizeTextArea;

import stan.block.note.core.notes.Case;
import stan.block.note.core.notes.cases.MultiLineText;
import stan.block.note.core.notes.cases.SingleLineText;

public class CaseConstructor
{
    static public Node constructFromCase(Case oneCase)
    {
        if(oneCase instanceof SingleLineText)
        {
            TextField singleLineTextEdit = new TextField();
            //singleLineTextEdit.lengthProperty().bind(singleLineTextEdit.prefWidthProperty());
            return singleLineTextEdit;
        }
        else if(oneCase instanceof MultiLineText)
        {
            ResizeTextArea multiLineTextEdit = new ResizeTextArea();
            return multiLineTextEdit;
        }
        return null;
    }
}