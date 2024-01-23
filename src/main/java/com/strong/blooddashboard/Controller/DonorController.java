package com.strong.blooddashboard.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXTextField;
import com.strong.blooddashboard.Utils.ApiRequest;
import com.strong.blooddashboard.model.Donor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    /* ShowDonor */
    @FXML
    private TableView<Donor> tableView = new TableView<>();
    @FXML
    private TableColumn<Donor, Integer> donorId;
    @FXML
    private TableColumn<Donor, String> firstName;
    @FXML
    private TableColumn<Donor, String> lastName;
    @FXML
    private TableColumn<Donor, String> dateofBirth;
    @FXML
    private TableColumn<Donor, String> contactNumber;
    @FXML
    private TableColumn<Donor, String> donorEmail;
    @FXML
    private TableColumn<Donor, String> bloodGroup;
    @FXML
    private TableColumn<Donor, String> lastDonationDate;

    String response;

    JsonArray donorResponse;
    private ObservableList<Donor> donorList;

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
        if (donorId != null) {
            ShowDonor();
        }
    }

    private void ShowDonor() {
        donorId.setCellValueFactory(new PropertyValueFactory<>("donorId"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateofBirth.setCellValueFactory(new PropertyValueFactory<>("dob"));
        contactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        donorEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        bloodGroup.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        lastDonationDate.setCellValueFactory(new PropertyValueFactory<>("lastDonationDate"));

        String API_URL = "http://localhost:8080/api/v1/donor/showDonor";
        try {
            donorResponse = ApiRequest.makeAPIGet(API_URL);
            donorList = convertJsonObjectToDonorList(donorResponse);
        } catch (IOException e) {

        }

        tableView.setItems(donorList);
    }

    private ObservableList<Donor> convertJsonObjectToDonorList(JsonArray jsonArray) {
        ObservableList<Donor> donorList = FXCollections.observableArrayList();

        for (JsonElement element : jsonArray) {
            JsonObject donorObject = element.getAsJsonObject();
            Donor donor = new Donor();

            donor.setDonorId(donorObject.get("donorId").getAsInt());
            donor.setFirstName(donorObject.get("firstName").getAsString());
            donor.setLastName(donorObject.get("lastName").getAsString());
            donor.setContactNumber(donorObject.get("contactNumber").getAsString());
            donor.setEmail(donorObject.get("email").getAsString());
            donor.setBloodGroup(donorObject.get("bloodGroup").getAsString());
            donor.setDob(donorObject.get("dob").getAsString());
            donor.setLastDonationDate(getLocalDateTimeOrNull(donorObject, "lastDonationDate"));
            donorList.add(donor);

        }
        return donorList;
    }

    private LocalDateTime getLocalDateTimeOrNull(JsonObject jsonObject, String key) {
        return jsonObject.has(key) && !jsonObject.get(key).isJsonNull()
                ? LocalDateTime.parse(jsonObject.getAsJsonPrimitive(key).getAsString())
                : null;
    }

    public void PayloadPOST() {
        final String API_URL = "http://localhost:8080/api/v1/donor/createDonor";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", first.getText());
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
            error.setText(e.getMessage());
        }
    }
}
