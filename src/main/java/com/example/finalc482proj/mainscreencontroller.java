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
 * This class controls the main screen
 * RUNTIME ERROR: Not abstract class//no supertype or method implemented
 * FUTURE EHANCEMENT: Add a login page that opens to the main screen to limit access to the company inventory
 * */
public class mainscreencontroller implements Initializable{

    @FXML
    private TextField partSearchField;
    @FXML
    private TextField productSearchField;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part,Integer> partIdColumn;
    @FXML
    private TableColumn<Part,String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part,Double> partPriceColumn;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product,Integer> productIdColumn;
    @FXML
    private TableColumn<Product,String> productNameColumn;
    @FXML
    private TableColumn<Product,Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product,Double>productPriceColumn;

    public static Part partToModify;
    public static Product productToModify;

    /**
     *Exits the UI
     * @param event handles the ActionEvent when the user clicks to close the UI
     * */
    @FXML
    public void exitButtonAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Gets part to be changed
     * @return returns part being changed
     * */
    public static Part getPartToModify(){
        return partToModify;
    }
    /**
     * Gets product to be changed
     * @return returns product being changed
     * */
    public static Product getProductToModify(){
        return productToModify;
    }
    /**
     * Opens up the add part UI
     * @param event handles the ActionEvent when the user clicks to open the UI
     * RUNTIME ERROR: Forgot to import classes for this, fixed by importing them.
     * RUNTIME ERROR: did not assign fx:controller for this class, after assigning it the code ran properly
     * */
    @FXML
    public void partAddAction(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("addpart.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method retrieves a part and displays a confirmation to delete, then deletes said part
     * @param event accepts button action when user clicks button
     * @return returns the method and cancels the delete action
     * */
    @FXML
    public void partDeleteAction(ActionEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            displayAlert(3);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the selected part?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Deletion");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }
    /**
     * This method directly uses a lookup to find the part being modified and loads the new screen
     * @param event accepts button action when user clicks button
     * @return returns the method and cancels the delete action
     * RUNTIME ERROR: Not loading modifypart screen.
     * */
    @FXML
    public void partModifyAction(ActionEvent event) throws IOException {
        Part partToModify = partTableView.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            displayAlert(3);
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifypart.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        modifypartcontroller controller = loader.getController();
        controller.sendPart(partTableView.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method initiates a search using a custom value entered by a user. Also automatically converts to lowercase to avoid case issues
     *          and will display all parts if the search field is empty
     * @param event accepts an action when user clicks the field to set a value
     * @return returns the method
     * */
    @FXML
    public void setPartSearchField(ActionEvent event) {
        String searchString = partSearchField.getText().trim().toLowerCase();

        if (searchString.isEmpty()) {
            partTableView.setItems(Inventory.getAllParts());
            return;
        }

        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        for (Part part : Inventory.getAllParts()) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().toLowerCase().contains(searchString)) {
                partsFound.add(part);
            }
        }

        partTableView.setItems(partsFound);

        if (partsFound.isEmpty()) {
            displayAlert(1);
        }
    }
    /**
     * Opens up the add product UI
     * @param event handles the ActionEvent when the user clicks to open the UI
     * */
    @FXML
    public void productAddAction(ActionEvent event){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("addproduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method retrieves a product and displays a confirmation to delete, then deletes said product
     * @param event accepts button action when user clicks button
     * @return returns the method and cancels the delete action
     * */
    @FXML
    public void productDeleteAction(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            displayAlert(3);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete the selected product?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Deletion");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }
    }
    /**
     * This method directly uses a lookup to find the part being modified and loads the new screen
     * @param event accepts button action when user clicks button
     * @return returns the method and cancels the delete action
     * */
    @FXML
    public void productModifyAction(ActionEvent event) throws IOException {
        Product productToModify = productTableView.getSelectionModel().getSelectedItem();

        if (productToModify == null) {
            displayAlert(4);
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyproduct.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        modifyproductcontroller controller = loader.getController();
        controller.sendProduct(productTableView.getSelectionModel().getSelectedIndex(),productTableView.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        stage.setScene(scene);
        stage.show();
    }
    /**
     * This method initiates a search using a custom value entered by a user. Also, automatically converts to lowercase to avoid case issues
     *          and will display all products if the search field is empty
     * @param event accepts an action when user clicks the field to set a value
     * @return returns the method
     * */
    @FXML
    public void setProductSearchField(ActionEvent event) {
        String searchString = productSearchField.getText().trim().toLowerCase();

        if (searchString.isEmpty()) {
            productTableView.setItems(Inventory.getAllProducts());
            return;
        }

        ObservableList<Product> productsFound = FXCollections.observableArrayList();
        for (Product product : Inventory.getAllProducts()) {
            if (String.valueOf(product.getId()).contains(searchString) ||
                    product.getName().toLowerCase().contains(searchString)) {
                productsFound.add(product);
            }
        }

        productTableView.setItems(productsFound);

        if (productsFound.isEmpty()) {
            displayAlert(2);
        }
    }

    /**
     * This method displays an alert
     * @param alertType selects the alert message
     * */
    public void displayAlert(int alertType) {
        Map<Integer, String[]> alertMap = new HashMap<>();
        alertMap.put(1, new String[]{"Information", "Part not found"});
        alertMap.put(2, new String[]{"Information", "Product not found"});
        alertMap.put(3, new String[]{"Error", "Part not selected"});
        alertMap.put(4, new String[]{"Error", "Product not selected"});
        alertMap.put(5, new String[]{"Error", "Parts Associated", "All parts must be removed from product before deletion."});

        String[] alertData = alertMap.getOrDefault(alertType, new String[]{"", ""});

        Alert alert;
        if (alertData[0].equals("Error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.setTitle(alertData[0]);
        alert.setHeaderText(alertData[1]);

        if (alertType == 5) {
            alert.setContentText(alertData[2]);
        }

        alert.showAndWait();
    }

    /**
     * This method initializes and populates tables
     * @param resources specifies resource bundle to be used for localization
     * @param location resolves location for the object(s)
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Populate parts table view
        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populates product table view
        productTableView.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

