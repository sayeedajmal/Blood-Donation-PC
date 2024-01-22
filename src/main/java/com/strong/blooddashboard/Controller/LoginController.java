package com.strong.blooddashboard.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    JFXTextField username;

    @FXML
    JFXPasswordField password;

    @FXML
    Label error;

    public void Login() {
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            error.setText(null);
            System.out.println("HELLO" + username.getText() + password.getText());
        } else {
            error.setText("Fill Correct Fields");
            error.setAlignment(Pos.CENTER);
        }
    }

    public void Donor() throws IOException {
        openPanel("/fxml/donorSignup.fxml", "Donor Signup");

    }

    public void history() {

    }

    public void appointment() {

    }

    public void staff() {

    }

    public void donation() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void openPanel(String path, String Title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toString());
        Stage primaryStage = new Stage();
        primaryStage.setTitle(Title);
        primaryStage.setScene(scene);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
