package stan.block.note.ui.controls;

import java.util.concurrent.Callable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.geometry.Bounds;

import javafx.scene.text.Text;
import javafx.scene.control.TextArea;

public class ResizeTextArea
    extends TextArea
{
    private double oldHeight = 0;
    private double oldWidth = 0;
    private Text textHolder = new Text();

    public ResizeTextArea()
    {
    	super();
        //this.setWrapText(true);
        //
        setPrefRowCount(1);
        setPrefColumnCount(1);
        //setMinHeight(0);
        //setMinWidth(0);
        textHolder.textProperty().bind(this.textProperty());
        textHolder.layoutBoundsProperty().addListener(new ChangeListener<Bounds>()
        {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue)
            {
                if(oldHeight != newValue.getHeight())
                {
                    oldHeight = newValue.getHeight();
                    String text = getText();
                    text += ".";
                    int lines = text.split("\n").length;
                    //setPrefHeight(textHolder.getLayoutBounds().getHeight() + 20 + lines);
                    setPrefRowCount(lines);
                }
                if(oldWidth != newValue.getWidth())
                {
                    oldWidth = newValue.getWidth();
                    setPrefWidth(textHolder.getLayoutBounds().getWidth() + 20);
                }
            }
        });
    }
}