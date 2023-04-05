package com.example.finalc482proj;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author lukea
 * This class controls the modify product screen
 * FUTURE ENHANCEMENT: Enable modification of multiple products
 * */
public class modifyproductcontroller implements Initializable {

    Product selectedProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    @FXML
    private TableView<Part> associatedPartTableView;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TextField productIdText;
    @FXML
    private TextField productNameText;
    @FXML
    private TextField productInventoryText;
    @FXML
    private TextField productPriceText;
    @FXML
    private TextField productMaxText;
    @FXML
    private TextField productMinText;
    @FXML
    private TextField searchPartsField;
    public ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    int selectedProductIndex = 0;

    /**
     * This method adds a part to the associated parts table
     * @param event handles the add button being clicked
     * RUNTIME ERROR: Was not adding parts to the associated parts list. Rewrote the method to grab a part
     *           from the partsTableView then use setItems() to update the associatedPartTableView.
     * */
    @FXML
    public void addButtonAction(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
        }else {

            selectedProduct.addAssociatedPart(selectedPart);
            associatedPartTableView.setItems(selectedProduct.getAllAssociatedParts());
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
     * This method removed a part from the associated parts table
     * @param event handles the user interaction
     * @return returns the method
     * @return returns confirmation
     * */
    @FXML
    public void removeButtonAction(ActionEvent event) {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(5);
            return;
        }

        showConfirmationDialog("Do you want to remove the selected part?")
                .filter(result -> result == ButtonType.OK)
                .ifPresent(result -> {
                    selectedProduct.deleteAssociatedPart(selectedPart);
                    //associatedPartTableView.setItems(associatedPartsList);
                });
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
     *
     * */
    public boolean validateInventory(int min, int max, int stock) {
        return stock >= min && stock <= max;
    }
    /**
     * This method saves a new part and validates min, max, and inventory
     * @param event accepts button action when user clicks button
     * */
    @FXML
    public void saveButtonAction(ActionEvent event) throws IOException {
        try {
            selectedProduct.setName(productNameText.getText());
            selectedProduct.setPrice(Double.parseDouble(productPriceText.getText()));
            selectedProduct.setStock(Integer.parseInt(productInventoryText.getText()));
            selectedProduct.setMin(Integer.parseInt(productMinText.getText()));
            selectedProduct.setMax(Integer.parseInt(productMaxText.getText()));

            if (selectedProduct.getName().isEmpty()) {
                displayAlert(5);
                return;
            }

            if (!validateMin(selectedProduct.getMin(), selectedProduct.getMax()) || !validateInventory(selectedProduct.getMin(), selectedProduct.getMax(), selectedProduct.getStock())) {
                displayAlert(4);
                return;
            }

            selectedProduct.setId(Inventory.getNewProductId());
            Inventory.updateProduct(selectedProductIndex, selectedProduct);
            returnToMainScreen(event);
        } catch (NumberFormatException e) {
            displayAlert(1);
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
            partTableView.setItems(Inventory.getAllParts());
        } else {
            ObservableList<Part> filteredParts = Inventory.getAllParts().filtered(part ->
                    Integer.toString(part.getId()).contains(searchString) ||
                            part.getName().contains(searchString)
            );

            partTableView.setItems(filteredParts);

            if (filteredParts.isEmpty()) {
                displayAlert(1);
            }
        }
    }
    /**
     * This method sends products to another controller
     *
     * @param product handles the product being selected
     * */
    public void sendProduct(int index, Product product) {
        selectedProduct = product;
        selectedProductIndex = index;
        productIdText.setText(String.valueOf(selectedProduct.getId()));
        productNameText.setText(selectedProduct.getName());
        productInventoryText.setText(String.valueOf(selectedProduct.getStock()));
        productPriceText.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxText.setText(String.valueOf(selectedProduct.getMax()));
        productMinText.setText(String.valueOf(selectedProduct.getMin()));
        associatedPartTableView.setItems(selectedProduct.getAllAssociatedParts());

    }
    /**
     * Initializes class and populates tables
     * @param resources specifies resource bundle to be used for localization
     * @param location resolves location for the object(s)
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}
