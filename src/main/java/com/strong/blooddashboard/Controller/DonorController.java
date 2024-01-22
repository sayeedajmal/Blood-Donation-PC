package com.strong.blooddashboard.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXTextField;
import com.strong.blooddashboard.Utils.ApiRequest;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class DonorController implements Initializable {

    @FXML
    JFXTextField first;
    @FXML
    JFXTextField last;
    @FXML
    JFXTextField email;
    @FXML
    JFXTextField contact;
    @FXML
    DatePicker dob;
    @FXML
    JFXTextField bloodgroup;
    @FXML
    JFXTextField address;
    @FXML
    Label error;

    String response;

    public void Signup() {
        if (!first.getText().isEmpty() && !last.getText().isEmpty() && !email.getText().isEmpty()
                && !contact.getText().isEmpty()
                && !bloodgroup.getText().isEmpty()) {
            error.setText(null);
            PayloadPOST();
        } else {
            error.setText("Fill All Fields");
            error.setAlignment(Pos.CENTER);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void PayloadPOST() {
        final String API_URL = "http://localhost:8080/api/v1/donor/createDonor";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName",first.getText());
        jsonObject.addProperty("lastName", last.getText());
        jsonObject.addProperty("dob", dob.getValue().toString());
        jsonObject.addProperty("contactNumber", contact.getText());
        jsonObject.addProperty("email", email.getText());
        jsonObject.addProperty("bloodGroup", bloodgroup.getText());
        jsonObject.addProperty("address", address.getText());
        try {
            response = ApiRequest.makeAPIPost(API_URL, jsonObject);
            error.setText(response);
            error.setTextAlignment(TextAlignment.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
            error.setText(e.getMessage());
        }
    }

}
