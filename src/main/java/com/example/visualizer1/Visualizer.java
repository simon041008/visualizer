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
import javafx.stage.Stage;

public class Visualizer extends Application {


    private boolean playing = false;
    private int canvas_hight = 300;
    private int canvas_width = 800;
    private double bardistance = 4.5;
    private int barcount = 32;
    private int visiblebars = barcount/2;
    private double barwidth = (double) canvas_width / visiblebars;
    private String bgColor = "#111111";
    private boolean rainbow = true;
    private double[] smoothing = new double[barcount];
    private double gain = 1.6;

    private float barweight = 0.0089F;
    private String titel = "Ether Disco";
    Media song;
    MediaPlayer player;




    @Override
    public void start(Stage stage){

        //Buttons erstellen
        Button b = new Button("▷");
        Button setting = new Button("⚙");
        Button skip = new Button("▷▷");
        Button back = new Button("◁◁");

        //buttons designen
        buttonstyle(b);
        buttonstyle(setting);
        buttonstyle(skip);
        buttonstyle(back);


        Label a = new Label("Audio Visualizer");
        a.setTextFill(Color.WHITESMOKE);

        //Layout erstellen
        HBox buttons = new HBox(10,back,b,skip,setting);
        Canvas visual = new Canvas(canvas_width,canvas_hight);
        GraphicsContext gc = visual.getGraphicsContext2D();
        VBox stack1 = new VBox(10,a,buttons, visual);
        stack1.setStyle("-fx-background-color: " + bgColor + ";");



        song = new Media(getClass().getResource("ether-disco.mp3").toExternalForm());
        player = new MediaPlayer(song);
        player.setAudioSpectrumNumBands(barcount);
        player.setAudioSpectrumListener((timestamp, duration, magnitudes, phases) -> {


            gc.setFill(Color.web(bgColor));

            gc.fillRect(0,0,canvas_width,canvas_hight);
            for (int i = 0; i < visiblebars; i++){       //BALKEN ZEICHNEN HIER

               if (rainbow == true){    //Rainbow mode
                   gc.setFill(Color.hsb(i * 360.0 / visiblebars, 0.75,1));

               }
               else {
                   gc.setFill(Color.WHITE);
               }

               //SMOOTHING
                float hoehe = Math.max(0, (magnitudes[i] + 60) / 60);
                if (hoehe >= smoothing[i]){
                    smoothing[i] = hoehe;
                }
                else{
                    smoothing[i] = Math.max(smoothing[i] - barweight, hoehe);

                }
                double pixel = Math.min(canvas_hight, smoothing[i] * canvas_hight * gain);
                //ZEICHNEN
                gc.fillRoundRect(
                        i * barwidth,
                        canvas_hight - pixel,
                        barwidth - bardistance,
                        pixel * 1.5,
                        10,
                        10);
            }

        });

        player.setAudioSpectrumInterval(0.01); //Refresh rate


        //Play Button
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

        setting.setOnAction(event -> {

            System.out.println("Settings hier");



        });


        //Stage
        Scene scene = new Scene(stack1, canvas_width + 80, canvas_hight + 80);
        stage.setTitle("Audio Visualizer");

        stage.setScene(scene);
        stage.show();
    }

    public void buttonstyle(Button b){
        b.setBackground(Background.fill(Color.web(bgColor)));
        b.setTextFill(Color.WHITESMOKE);
    }
}
