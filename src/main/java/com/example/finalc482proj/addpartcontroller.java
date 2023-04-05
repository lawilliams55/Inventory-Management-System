package com.example.finalc482proj;

import javafx.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * @author lukea
 *This class controls the add part screen
 * FUTURE ENHANCEMENT: allow the addition of multiple parts at the same time
 * */
public class addpartcontroller implements Initializable{

    @FXML private TextField partIdField;
    @FXML private TextField partNameField;
    @FXML private TextField partInventoryField;
    @FXML private TextField partPriceField;
    @FXML private TextField partMaxField;
    @FXML private TextField partMinField;
    @FXML private RadioButton partInHouseButton;
    @FXML private RadioButton partOutSourcedButton;
    @FXML public TextField partMachineTextField;
    @FXML public ToggleGroup partToggleType;
    @FXML public Text partMachineIdField;
    /**
     * This method calls the return to main screen method
     *
     * @param event represents the event that called the cancelButtonAction method
     */
    @FXML
    public void cancelButtonAction(ActionEvent event) throws IOException {
        if (confirmCancellation()) {
            returnToMainScreen(event);
        }
    }

    /**
     * This method returns to the main screen from the add product screen
     *
     * @param event represents the event that called the returnToMainScreen method
     */
    public void returnToMainScreen(ActionEvent event) throws IOException {
        Stage addProductStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addProductStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage mainStage = new Stage();
        mainStage.setScene(scene);
        mainStage.show();
    }

    /**
     * This method confirms whether to return to the main screen.
     *
     * @return returns a boolean on whether the user clicked ok
     */
    public boolean confirmCancellation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want cancel changes and return to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    /**
     * This method sets the label text to "Machine ID"
     * */
    @FXML
    public void inHouseRadioButtonAction() {
        partMachineIdField.setText("Machine ID");
    }
    /**
     * This method sets the label text to "Company Name"
     * RUNTIME ERROR: Was not setting text due to toggle group not being assigned.
     * */
    @FXML
    public void outSourcedRadioButtonAction() {
        partMachineIdField.setText("Company Name");
    }
    /**
     * This method validates there is no minimum below zero and the max is not below the minimum
     * @param min minimum value for part
     * @param max maximum value for part
     * @return returns string if check is invalid
     * @return returns null if check is valid
     * */
    public String validateMin(int min, int max) {
        if (min <= 0 || min >= max) {
            return "Min must be a number greater than 0 and less than Max.";
        }
        return null;
    }
    /**
     * This method validates the inventory by ensuring the stock is no less than min and no greater than max
     * @param max maximum value for part
     * @param min minimum value for part
     * @param stock number of parts in stock
     * */
    public String validateInventory(int min, int max, int stock) {
        if (stock < min || stock > max) {
            return "Inventory must be a number equal to or between Min and Max.";
        }
        return null;
    }
    /**
     * This method saves a new part
     * @param event accepts button action when user clicks button
     * */
    @FXML
    public void saveButtonAction(ActionEvent event) throws IOException {
        int id = 0;
        String name = partNameField.getText();
        Double price = Double.parseDouble(partPriceField.getText());
        int stock = Integer.parseInt(partInventoryField.getText());
        int min = Integer.parseInt(partMinField.getText());
        int max = Integer.parseInt(partMaxField.getText());
        int machineId;
        String companyName;
        boolean partAddSuccessful = false;
        String errorMessage = null;

        if (name.isEmpty()) {
            errorMessage = "Name cannot be empty.";
        } else {
            errorMessage = validateMin(min, max);
            if (errorMessage == null) {
                errorMessage = validateInventory(min, max, stock);
                if (errorMessage == null) {
                    if (partInHouseButton.isSelected()) {
                        try {
                            machineId = Integer.parseInt(partMachineTextField.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            partAddSuccessful = true;
                        } catch (NumberFormatException e) {
                            errorMessage = "Machine ID may only contain numbers.";
                        }
                    }

                    if (partOutSourcedButton.isSelected()) {
                        companyName = partMachineTextField.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourcedPart.setId(Inventory.getNewPartId());
                        Inventory.addPart(newOutsourcedPart);
                        partAddSuccessful = true;
                    }

                    if (partAddSuccessful) {
                        returnToMainScreen(event);
                    }
                }
            }
        }

        if (errorMessage != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText(errorMessage);
            alert.showAndWait();
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
     * This method initializes and populates tables
     * @param resourceBundle specifies resource bundle to be used for localization
     * @param url resolves location for the object(s)
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
