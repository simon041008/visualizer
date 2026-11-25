module com.example.visualizer1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.visualizer1 to javafx.fxml;
    exports com.example.visualizer1;
}