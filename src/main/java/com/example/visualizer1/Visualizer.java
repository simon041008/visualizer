package com.example.visualizer1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.util.Arrays;

public class Visualizer extends Application {
    private boolean playing = false;
    private int canvas_hight = 300;
    private int canvas_width = 800;
    private double bardistance = 4.5;
    private int barheight = 120;
    private int barcount = 32;
    private double barwidth = (double) canvas_width / barcount;
    Media song;
    MediaPlayer player;
    @Override
    public void start(Stage stage){

        Button b = new Button("START");
        Label a = new Label("Hallo Visualizer");
        Canvas visual = new Canvas(canvas_width,canvas_hight);
        GraphicsContext gc = visual.getGraphicsContext2D();
        VBox stack1 = new VBox(10,a,b, visual);



        song = new Media(getClass().getResource("ether-disco.mp3").toExternalForm());
        player = new MediaPlayer(song);
        player.setAudioSpectrumNumBands(barcount);
        player.setAudioSpectrumListener((timestamp, duration, magnitudes, phases) -> {
            gc.setFill(Color.DARKSLATEGRAY);
            gc.clearRect(0,0,canvas_width,canvas_hight);
            for (int i = 0; i < barcount; i++){

                float hoehe = Math.max(0, (magnitudes[i] + 60) / 60);
                double hoeheinpixel = hoehe * canvas_hight;
                gc.fillRoundRect(
                        i * barwidth,
                        canvas_hight - hoeheinpixel * 1.5,
                        barwidth - bardistance,
                        hoeheinpixel * 1.5,
                        23,
                        23);
            }
        });
        player.setAudioSpectrumInterval(0.01);
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

        Scene scene = new Scene(stack1, canvas_width + 80, canvas_hight + 80);
        stage.setTitle("Audio Visualizer");
        stage.setScene(scene);
        stage.show();
    }
}
