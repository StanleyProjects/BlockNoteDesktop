package stan.block.note.ui.stages;

import javafx.application.Platform;
import javafx.application.Application;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;

import stan.block.note.listeners.ui.panes.main.IBNPaneListener;

import stan.block.note.ui.panes.main.BNPane;

public class MainStage
    extends Stage
{
    static private MainStage instance;
    static public MainStage getInstance()
    {
        if(instance == null)
        {
            instance = new MainStage();
        }
        return instance;
    }

	private BNPane bnpane;
	private IBNPaneListener iBNPaneListener;
	
    private MainStage()
    {
        super();
        this.initStyle(StageStyle.TRANSPARENT);
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		bnpane = new BNPane();
		iBNPaneListener = new IBNPaneListener()
		{
			public void close()
			{
				MainStage.this.hide();
				StartScreenStage.getInstance().showStartScreen();
			}
		};
        this.setScene(new Scene(bnpane, screen.getWidth(), screen.getHeight(), Color.TRANSPARENT));
        this.getScene().getStylesheets().add("css/theme.css");
    }

    public void hideMainScreen()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                if(MainStage.getInstance().isShowing())
                {
                    MainStage.getInstance().hide();
                }
            }
        });
    }
    public void showMainScreen(String path)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                if(MainStage.getInstance().isShowing())
                {
                    return;
                }
				MainStage.getInstance().bnpane.init(iBNPaneListener, path);
                MainStage.getInstance().show();
            }
        });
    }
}