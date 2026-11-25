package com.example.visualizer1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private boolean playing = false;
    @Override
    public void start(Stage stage){
       //hier neue sachen eifügen (ui elemente)
        Button b = new Button("Start");
        Label a = new Label("Hallo Visualizer");
        VBox stack1 = new VBox(10,a,b);
        b.setOnAction(e -> {
            if (playing == false){
                a.setText("PLAYING" + "[Songtitel]");
                b.setText("STOP");
                playing = true;
            }
            else {
                a.setText("PAUSED");
                b.setText("START");
                playing = false;
            }
        });

        //szene laden
        Scene scene = new Scene(stack1, 800, 300);
        stage.setTitle("Audio Visualizer");
        stage.setScene(scene);
        stage.show();
    }
}
