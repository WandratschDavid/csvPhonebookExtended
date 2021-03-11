package main;

import javafx.application.Application;
import javafx.stage.Stage;
import viewcontroller.PhonebookC;

import java.io.File;

/**
 * @author David Wandratsch
 * @version 1.0
 */

public class TheMain extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        File db = new File("phonebook.csv");
        PhonebookC.show(primaryStage, db);
    }

    public static void main(String[] args)
    {
        //launch the JavaFX Application
        launch(args);
    }
}