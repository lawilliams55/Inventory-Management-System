package com.example.finalc482proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * @author lukea
 * This class stores product data
 * FUTURE ENHANCEMENT: add a description paramater for the product member
 * RUNTIME ERROR: None occured while making this class
 * */

public class Product {
    /**
     * ID of the product
     */
    private int id;

    /**
     * Name of the product
     */
    private String name;

    /**
     * Price of the product
     */
    private double price;

    /**
     * Inventory level of the product
     */
    private int stock;

    /**
     * Minimum amount for the product
     */
    private int min;

    /**
     * Maximum amount for the product
     */
    private int max;

    /**
     * A list of associated parts for the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Creates a new instance for a product
     *
     * @param id the ID for the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the inventory level of the product
     * @param min the minimum level for the product
     * @param max the maximum level for the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets the id
     * @return id of the product
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id for the product
     * @param id accepts an integer as the id of the product
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name
     * @return name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product
     * @param name is product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price
     * @return price of product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price
     * @param price accepts a double as the product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method gets stock level and returns the amount
     * @return returns the amount in stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method sets the stock of the product for inventory
     * @param stock accepts integer as stock level
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method gets the minimum
     * @return returns the minimum of the product
     */
    public int getMin() {
        return min;
    }

    /**
     * This method sets the minimum
     * @param min accepts integer as a minimum for a product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets the max
     * @return returns the maximum for the product
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum
     * @param max accepts an integer as a maximum for the product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a part to the associated parts list
     * @param part accepts a part to be added
     */
    public void  addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a part from the associated parts list for the product and verifies the deletion has occured
     * @param selectedAssociatedPart selects the part to be deleted
     * @return indicates true or false on whether the part has been deleted
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * Gets associated parts for the product and returns them
     * @return returns all associated parts in the list
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}
}

