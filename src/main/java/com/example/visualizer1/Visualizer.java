package com.example.visualizer1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Visualizer extends Application {
    private boolean playing = false;
    //hier neue sachen eifügen
    Media Song = new Media(getClass().getResource("ether-disco.mp3").toExternalForm());
    MediaPlayer Player = new MediaPlayer(Song);
    @Override
    public void start(Stage stage){
        Button b = new Button("Start");
        Label a = new Label("Hallo Visualizer");
        VBox stack1 = new VBox(10,a,b);
        b.setOnAction(e -> {
            if (playing == false){
                a.setText("PLAYING" + "[Songtitel]");
                b.setText("STOP");
                playing = true;
                Player.play();
            }
            else {
                a.setText("PAUSED");
                b.setText("START");
                playing = false;
                Player.pause();
            }
        });

        //szene laden
        Scene scene = new Scene(stack1, 800, 300);
        stage.setTitle("Audio Visualizer");
        stage.setScene(scene);
        stage.show();
    }
}
