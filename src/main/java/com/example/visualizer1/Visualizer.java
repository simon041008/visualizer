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
    Media song;
    MediaPlayer player;
    @Override
    public void start(Stage stage){
        Button b = new Button("START");
        Label a = new Label("Hallo Visualizer");
        VBox stack1 = new VBox(10,a,b);
        song = new Media(getClass().getResource("ether-disco.wav").toExternalForm());
        player = new MediaPlayer(song);
        b.setOnAction(e -> {
            if (playing == false){
                player.play();
                playing = true;
                a.setText("PLAYING" + "[Songtitel]");
                b.setText("STOP");
            }
            else {
                player.pause();
                playing = false;
                a.setText("PAUSED");
                b.setText("START");

            }
        });

        //szene laden
        Scene scene = new Scene(stack1, 800, 300);
        stage.setTitle("Audio Visualizer");
        stage.setScene(scene);
        stage.show();
    }
}
