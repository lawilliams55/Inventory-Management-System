module com.example.finalc482proj {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.finalc482proj to javafx.fxml;
    exports com.example.finalc482proj;
}