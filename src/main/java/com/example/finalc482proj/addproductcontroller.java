package com.example.finalc482proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author lukea
 * This class controls the add product screen
 * FUTURE ENHANCEMENT: allow the addition of multiple products at the same time
 */
public class addproductcontroller implements Initializable {

    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productInventoryField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField productMinField;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;
    @FXML
    private TableView<Part> allPartsTable;
    @FXML
    private TableColumn<Part, Integer> allPartIdColumn;
    @FXML
    private TableColumn<Part, String> allPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> allPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> allPartPriceColumn;
    @FXML
    private TextField searchPartsField;
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();


    /**
     * This method adds a part to the associated parts table
     *
     * @param event handles the add button being clicked
     * RUNTIME ERROR: Would only add a part once. Fixed by changing method to an if-else statement and used the setItems() method
     * */
    @FXML
    public void addButtonAction(ActionEvent event) {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        }else {

            associatedPartsList.add(selectedPart);
            associatedPartsTable.setItems(associatedPartsList);
        }
    }
    /**
     *This method searches a part in the allparts table. Populates all parts if the search field is empty
     * @param event handles user interaction
     * */
    @FXML
   public void searchPartsFieldAction(ActionEvent event) {
        String searchString = searchPartsField.getText();

        if (searchString.isEmpty()) {
            allPartsTable.setItems(Inventory.getAllParts());
            return;
        }
        if (searchString.isEmpty()) {
            allPartsTable.setItems(Inventory.getAllParts());
            return;
        }

        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : Inventory.getAllParts()) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().toLowerCase().contains(searchString)) {
                partsFound.add(part);
            }
        }

        allPartsTable.setItems(partsFound);

        if (partsFound.isEmpty()) {
            displayAlert(6);
        }
    }
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
     * This method validates there is no minimum below zero and the max is not below the minimum
     * @param min minimum value for product
     * @param max maximum value for product
     * @return returns string if check is invalid
     * @return returns null if check is valid
     * */
    public boolean validateMin(int min, int max) {
        return min > 0 && min < max;
    }
    /**
     * This method validates the inventory by ensuring the stock is no less than min and no greater than max
     * @param max maximum value for product
     * @param min minimum value for product
     * @param stock number of products in stock
     * */
    public boolean validateInventory(int min, int max, int stock) {
        return stock >= min && stock <= max;
    }
    /**
     * This method saves a new product
     * @param event accepts button action when user clicks button
     * */
    @FXML
    public void saveButtonAction(ActionEvent event) throws IOException {
        try {
            String name = productNameField.getText();
            double price = Double.parseDouble(productPriceField.getText());
            int stock = Integer.parseInt(productInventoryField.getText());
            int min = Integer.parseInt(productMinField.getText());
            int max = Integer.parseInt(productMaxField.getText());

            if (name.isEmpty()) {
                displayAlert(5);
                return;
            }

            if (!validateMin(min, max) || !validateInventory(min, max, stock)) {
                displayAlert(4);
                return;
            }

            Product newProduct = new Product(0, name, price, stock, min, max);

            for (Part part : associatedPartsList) {
                newProduct.addAssociatedPart(part);
            }

            newProduct.setId(Inventory.getNewProductId());
            Inventory.addProduct(newProduct);
            returnToMainScreen(event);
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
        alerts.put(6, new String[]{"Error", "Invalid search", "Part does not exist."});

        String[] alert = alerts.get(alertType);

        Alert alertBox = new Alert(Alert.AlertType.ERROR);
        alertBox.setTitle(alert[0]);
        alertBox.setHeaderText(alert[1]);
        alertBox.setContentText(alert[2]);
        alertBox.showAndWait();
    }
    /**
     * This method removed a part from the associated parts table
     * @param event handles the user interaction
     * @return returns the method
     * @return returns confirmation
     * */
    @FXML
    public void removeButtonAction(ActionEvent event) {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
            return;
        }

        showConfirmationDialog("Do you want to remove the selected part?")
                .filter(result -> result == ButtonType.OK)
                .ifPresent(result -> {
                    associatedPartsList.remove(selectedPart);
                    associatedPartsTable.setItems(associatedPartsList);
                });
    }
    /**
     * This method creates a confirmation dialog when an event occurs
     * @param message holds the confirmation dialog string
     * */
    public Optional<ButtonType> showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText(message);
        return alert.showAndWait();
    }

    /**
     * This method initializes and populates tables
     * @param resources specifies resource bundle to be used for localization
     * @param location resolves location for the object(s)
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allPartsTable.setItems(Inventory.getAllParts());

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
