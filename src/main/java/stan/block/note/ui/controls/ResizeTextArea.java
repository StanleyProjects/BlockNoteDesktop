package stan.block.note.ui.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.geometry.Bounds;

import javafx.scene.text.Text;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;

public class ResizeTextArea
    extends TextArea
{
	//15.9609375
    private double oldHeight = 0;
    private double oldWidth = 0;
    private boolean hideScroll;
    private Text textHolder = new Text();

    public ResizeTextArea()
    {
    	super();
        //this.setWrapText(true);
        //
		hideScroll = false;
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
					String text = getText()+".";
					int lines = text.split("\n").length;
					setPrefRowCount(lines);
				}
                if(oldWidth != newValue.getWidth())
                {
					setPrefWidth(textHolder.getLayoutBounds().getWidth() + 20);
                    oldWidth = newValue.getWidth();
				}
				if(!hideScroll)
				{
					hideScroll = true;
					ScrollBar scrollBarv = (ScrollBar)lookup(".scroll-bar:vertical");
					scrollBarv.setMaxWidth(Control.USE_PREF_SIZE);
					scrollBarv.setMinWidth(Control.USE_PREF_SIZE);
					scrollBarv.setPrefWidth(0.5);
					scrollBarv.setDisable(true);
					scrollBarv.setOpacity(0);
				}
            }
        });
    }
}