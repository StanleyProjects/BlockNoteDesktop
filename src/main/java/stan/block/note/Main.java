package stan.block.note;

import javafx.application.Application;
import javafx.stage.Stage;

import stan.block.note.ui.stages.StartScreenStage;

public class Main
    extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
		StartScreenStage.getInstance().showStartScreen();
    }
}