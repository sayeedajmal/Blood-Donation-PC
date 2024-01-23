package com.strong.blooddashboard.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.strong.blooddashboard.Utils.ApiRequest;
import com.strong.blooddashboard.model.Staff;

public class DashboardController implements Initializable {

    @FXML
    private TableView<Staff> staffTableView = new TableView<>();

    @FXML
    private TableColumn<Staff, Integer> staffId;

    @FXML
    private TableColumn<Staff, String> staffName;

    @FXML
    private TableColumn<Staff, String> email;

    @FXML
    private TableColumn<Staff, String> contactNumber;

    @FXML
    private TableColumn<Staff, LocalDateTime> createdAt;

    @FXML
    private TableColumn<Staff, LocalDateTime> updatedAt;

    @FXML
    private TableColumn<Staff, Boolean> enabled;

    @FXML
    private TableColumn<Staff, String> position;

    private ObservableList<Staff> staffList;
    private JsonArray response;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize columns using PropertyValueFactory
        staffId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        staffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        updatedAt.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        enabled.setCellValueFactory(new PropertyValueFactory<>("enabled"));

        String API_URL = "http://localhost:8080/api/v1/staff/showStaff";
        try {
            response = ApiRequest.makeAPIGet(API_URL);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            staffList = convertJsonArrayToStaffList(response);
        }
        staffTableView.setItems(staffList);

    }

    private ObservableList<Staff> convertJsonArrayToStaffList(JsonArray jsonArray) {
        ObservableList<Staff> staffList = FXCollections.observableArrayList();

        for (JsonElement element : jsonArray) {
            JsonObject staffObject = element.getAsJsonObject();
            Staff staff = new Staff();

            staff.setStaffId(staffObject.get("staffId").getAsInt());
            staff.setStaffName(staffObject.get("staffName").getAsString());
            staff.setPosition(staffObject.get("position").getAsString());
            staff.setContactNumber(staffObject.get("contactNumber").getAsString());
            staff.setEmail(staffObject.get("email").getAsString());
            staff.setPassword(staffObject.get("password").getAsString());
            staff.setEnabled(staffObject.get("enabled").getAsBoolean());
            staff.setCreatedAt(getLocalDateTimeOrNull(staffObject, "createdAt"));
            staff.setUpdatedAt(getLocalDateTimeOrNull(staffObject, "updatedAt"));
            staffList.add(staff);
        }
        return staffList;
    }

    private LocalDateTime getLocalDateTimeOrNull(JsonObject jsonObject, String key) {
        return jsonObject.has(key) && !jsonObject.get(key).isJsonNull()
                ? LocalDateTime.parse(jsonObject.getAsJsonPrimitive(key).getAsString())
                : null;
    }
}
