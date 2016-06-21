package stan.block.note.ui.stages;

import java.io.File;

import javafx.application.Platform;

import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

import stan.block.note.ui.panes.start.StartScreenPane;

import stan.block.note.listeners.ui.panes.start.IStartScreenPaneListener;

public class StartScreenStage
    extends Stage
{
    static private StartScreenStage instance;
    static public StartScreenStage getInstance()
    {
        if(instance == null)
        {
            instance = new StartScreenStage();
        }
        return instance;
    }

    private FileChooser fileChooser;
	
    private StartScreenStage()
    {
        super();
        this.fileChooser = new FileChooser();
        this.setScene(new Scene(new StartScreenPane(this, new IStartScreenPaneListener()
        {
            public void openBlockNote()
            {
				File file = fileChooser.showOpenDialog(StartScreenStage.this);
				if(file != null && file.exists())
				{
					StartScreenStage.this.hide();
					MainStage.getInstance().showMainScreen(file.getAbsolutePath());
				}
            }
            public void newBlockNote()
            {
            }
            public void exit()
            {
				Platform.exit();
				System.exit(0);
            }
        }), 480, 480, Color.TRANSPARENT));
        this.initStyle(StageStyle.TRANSPARENT);
        this.getScene().getStylesheets().add("css/theme.css");
    }

    public void hideStartScreen()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                if(StartScreenStage.getInstance().isShowing())
                {
                    StartScreenStage.getInstance().hide();
                }
            }
        });
    }
    public void showStartScreen()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                if(StartScreenStage.getInstance().isShowing())
                {
                    return;
                }
                StartScreenStage.getInstance().show();
            }
        });
    }
}