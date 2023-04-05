package com.example.finalc482proj;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author lukea
 * This class controls the modify part screen
 * FUTURE ENHANCEMENT: Enable modification of multiple parts

 * */
public class modifypartcontroller implements Initializable {

    @FXML
    private TextField partNameField;
    @FXML
    private TextField partIdField;
    @FXML
    private TextField partPriceField;
    @FXML
    private TextField partInventoryField;
    @FXML
    private TextField partMax;
    @FXML
    private TextField partMin;
    @FXML
    public Text partMachineCompanyToggle;
    @FXML
    public TextField partMachineCompanyField;
    @FXML
    public RadioButton OutSourcedButton;
    @FXML
    public RadioButton InHouseButton;
    @FXML
    public ToggleGroup modifyPartToggleGroup;

    Part selectedPart;


    /**
     * This method initiates the returnToMainScreen method
     * @param event handles user input
     * */
    @FXML
    public void cancelButtonAction(ActionEvent event) throws IOException {
        showConfirmationDialog("Do you want to cancel changes and return to the main screen?")
                .filter(result -> result == ButtonType.OK)
                .ifPresent(result -> returnToMainScreen(event));
    }
    /**
     * This method gives a confirmation for cancellation
     * @param message displays text to the user
     * */
    public Optional<ButtonType> showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(message);
        return alert.showAndWait();
    }
    /**
     * This method returns to main screen
     * @param event handles user input
     * */
    public void returnToMainScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method sets the label text to "Machine ID"
     * */
    @FXML
    public void inHouseRadioButtonAction() {
        partMachineCompanyToggle.setText("Machine ID");
    }
    /**
     * This method sets the label text to "Company Name"
     * */
    @FXML
    public void outSourcedRadioButtonAction() {

        partMachineCompanyToggle.setText("Company Name");
    }
    /**
     * This method validates there is no minimum below zero and the max is not below the minimum
     * @param min minimum value for part
     * @param max maximum value for part
     * @return returns string if check is invalid
     * @return returns null if check is valid
     * */
    public boolean validateMin(int min, int max) {
        if (min < 0) {
            return false;
        } else if (min > max) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * This method validates the inventory by ensuring the stock is no less than min and no greater than max
     * @param max maximum value for part
     * @param min minimum value for part
     * @param stock number of parts in stock
     * */
    public boolean validateInventory(int min, int max, int stock) {
        if (stock < 0) {
            return false;
        } else if (stock < min) {
            return false;
        } else if (stock > max) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * This method saves a new part
     * @param event accepts button action when user clicks button
     * */
    @FXML
    public void saveButtonAction(ActionEvent event) throws IOException {

        try {
            int id = selectedPart.getId();
            String name = partNameField.getText();
            Double price = Double.parseDouble(partPriceField.getText());
            int stock = Integer.parseInt(partInventoryField.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if (!validateMin(min, max) || !validateInventory(min, max, stock)) {
                displayAlert(1);
                return;
            }

            if (InHouseButton.isSelected()) {
                try {
                    machineId = Integer.parseInt(partMachineCompanyField.getText());
                    InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(newInHousePart);
                    partAddSuccessful = true;
                } catch (NumberFormatException e) {
                    displayAlert(2);
                    return;
                }
            }

            if (OutSourcedButton.isSelected()) {
                companyName = partMachineCompanyField.getText();
                Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(newOutsourcedPart);
                partAddSuccessful = true;
            }

            if (partAddSuccessful) {
                Inventory.deletePart(selectedPart);
                returnToMainScreen(event);
            }
        } catch (NumberFormatException e) {
            displayAlert(1);
        }
    }
    /**
     * This method displays an alert
     * @param alertType selects the alert message
     * */
    public void displayAlert(int alertType) {
        Map<Integer, String[]> alerts = new HashMap<>();
        alerts.put(1, new String[]{"Error", "Error Adding Part", "Form contains blank fields or invalid values."});
        alerts.put(2, new String[]{"Error", "Invalid value for Machine ID", "Machine ID may only contain numbers."});
        alerts.put(3, new String[]{"Error", "Invalid value for Min", "Min must be a number greater than 0 and less than Max."});
        alerts.put(4, new String[]{"Error", "Invalid value for Inventory", "Inventory must be a number equal to or between Min and Max."});
        alerts.put(5, new String[]{"Error", "Name Empty", "Name cannot be empty."});

        String[] alert = alerts.get(alertType);

        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setTitle(alert[0]);
        alertBox.setHeaderText(alert[1]);
        alertBox.setContentText(alert[2]);
        alertBox.showAndWait();
    }
    /**
     * This method sends part data to another controller
     * @param part handles part data of selected part
     * RUNTIME ERROR: Was auto defaulting machineID/company name field to "Machine ID". Fixed by have partMachineCompanyToggle.setText() implemented
     * */
    public void sendPart(Part part) {
        selectedPart = part;
        partIdField.setText(String.valueOf(selectedPart.getId()));
        partNameField.setText(selectedPart.getName());
        partInventoryField.setText(String.valueOf(selectedPart.getStock()));
        partPriceField.setText(String.valueOf(selectedPart.getPrice()));
        partMax.setText(String.valueOf(selectedPart.getMax()));
        partMin.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
                InHouseButton.setSelected(true);
                partMachineCompanyToggle.setText("Machine ID");
                partMachineCompanyField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
            }
        if (selectedPart instanceof Outsourced) {
                OutSourcedButton.setSelected(true);
                partNameField.setText(selectedPart.getName());
                partMachineCompanyToggle.setText("Company Name");
                partMachineCompanyField.setText(((Outsourced) selectedPart).getCompanyName());
            }


    }
    /**
     * Initializes class
     * @param resources specifies resource bundle to be used for localization
     * @param location resolves location for the object(s)
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
