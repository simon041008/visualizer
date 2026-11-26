package com.example.visualizer1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.util.Arrays;

public class Visualizer extends Application {
    private boolean playing = false;
    private int canvas_hight = 300;
    private int canvas_width = 800;
    private double bardistance = 4.5;
    private int barheight = 120;
    private int barcount = 32;
    private int visiblebars = barcount/2;
    private double barwidth = (double) canvas_width / visiblebars;
    private String bgColor = "#111111";
    private boolean rainbow = true;
    private double[] smoothing = new double[barcount];
    private float barweight = 0.02F;
    private String titel = "Ether Disco";
    Media song;
    MediaPlayer player;
    @Override
    public void start(Stage stage){

        Button b = new Button("▷");
        b.setTextFill(Color.BLACK);
        Button setting = new Button("⚙");
        Button skip = new Button("▷▷");
        Button back = new Button("◁◁");
        b.setBackground(Background.fill(Color.web(bgColor)));
        skip.setBackground(Background.fill(Color.web(bgColor)));
        b.setTextFill(Color.WHITESMOKE);
        skip.setTextFill(Color.WHITESMOKE);
        back.setBackground(Background.fill(Color.web(bgColor)));
        setting.setBackground(Background.fill(Color.web(bgColor)));
        setting.setTextFill(Color.WHITESMOKE);
        back.setTextFill(Color.WHITESMOKE);
        Label a = new Label("Audio Visualizer");
        a.resize(190,190);
        a.setTextFill(Color.WHITESMOKE);
        HBox buttons = new HBox(10,back,b,skip,setting);
        Canvas visual = new Canvas(canvas_width,canvas_hight);
        GraphicsContext gc = visual.getGraphicsContext2D();
        VBox stack1 = new VBox(10,a,buttons, visual);
        stack1.setStyle("-fx-background-color: #111111;");



        song = new Media(getClass().getResource("ether-disco.mp3").toExternalForm());
        player = new MediaPlayer(song);
        player.setAudioSpectrumNumBands(barcount);
        player.setAudioSpectrumListener((timestamp, duration, magnitudes, phases) -> {
            gc.setFill(Color.web(bgColor));
            gc.fillRect(0,0,canvas_width,canvas_hight);
            for (int i = 0; i < barcount; i++){
               if (rainbow == true){
                   gc.setFill(Color.hsb(i * 360 / barcount, 0.75,1));

               }
               else {
                   gc.setFill(Color.WHITE);
               }

                float hoehe = Math.max(0, (magnitudes[i] + 60) / 60);
                double hoeheinpixel = hoehe * canvas_hight;
                if (hoeheinpixel > smoothing[i]){
                    hoehe = hoehe - barweight;}

                gc.fillRoundRect(
                        i * barwidth,
                        canvas_hight - hoeheinpixel * 1.5,
                        barwidth - bardistance,
                        hoeheinpixel * 1.5,
                        23,
                        23);
                smoothing[i] = hoeheinpixel;

            }

        });
        player.setAudioSpectrumInterval(0.01);
        b.setOnAction(e -> {
            if (playing == false){
                player.play();
                playing = true;
                a.setText("PLAYING:  " + titel);
                b.setText("||");
            }
            else {
                player.pause();
                playing = false;
                a.setText("PAUSED");
                b.setText("▷");

            }
        });

        Scene scene = new Scene(stack1, canvas_width + 80, canvas_hight + 80);
        stage.setTitle("Audio Visualizer");

        stage.setScene(scene);
        stage.show();
    }
}

