package com.example.finalc482proj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author lukea
 * This class stores inventory data
 * FUTURE ENHANCEMENT: Update all method. Add a method to do a blanket update to all parts/products at the same time.
 * */

public class Inventory {
    //Setting variable for partId
    private static int partId = 0;

    //Setting variable for productId
    private static int productId = 0;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Gets list of all parts
     * @return ObservableList part
     * */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Gets list of all products
     * @return ObservableList product
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    /**
     * Allows for adding new parts into inventory
     * @param newPart the new part to add
     * */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /**
     * Allows for adding new products into inventory
     * @param newProduct the new product to add
     * */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * This method looks up a part in the list by ID
     * @param partId accepts part ID to look up
     * @return returns the part found if it exists, otherwise it is null
     * RUNTIME ERROR: I encountered a type error where I tried to implement the part class in a method without previously stating it.
     *                       I fixed this by simply implementing the correct class.
     * */
    public static Part lookupPart(Integer partId) {
        Part partFound = null;

        for (Part part:allParts) {
            if (part.getId() == partId) {
                partFound = part;
            }
        }
        return partFound;
    }
    /**
     * This method looks up a product in the list by ID
     * @param productId accepts product ID to be looked up
     * @return returns the product looked up or null
     * */
    public static Product lookupProduct(Integer productId) {
        Product productFound = null;

        for (Product product:allProducts) {
            if (product.getId() == productId) {
                productFound = product;
            }
        }
        return productFound;
    }
    /**
     * This method looks up a part in the list by name
     * @param partName is the part to be looked up
     * @return returns the part name if it was found
     * */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partFound = FXCollections.observableArrayList();

        for(Part part:allParts) {
            if(part.getName().equals(partName)) {
                partFound.add(part);
            }
        }
        return partFound;
    }
    /**
     * This method looks up a product in the list by name
     * @param productName accepts name of product
     * @return returns product found
     * */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productFound = FXCollections.observableArrayList();

        for(Product product:allProducts) {
            if(product.getName().equals(productName)) {
                productFound.add(product);
            }
        }
        return productFound;
    }
    /**
     * This method updates a part
     * @param index selects index of part being updated
     * @param selectedPart name of part that is being used to update the index
     * */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    /**
     * This method updates a product
     * @param index selects index of product being updated
     * @param selectedProduct name of product that is being used to update the index
     * */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }
    /**
     * This method deletes a part
     * @param selectedPart selects the part to be deleted
     * @return is a boolean for deletion
     * */
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * This method deletes a product
     * @param selectedProduct selects the product to be deleted
     * @return is a boolean for deletion
     * */
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else{
            return false;
        }
    }
    /**
     * This method creates a part ID
     * @return returns part ID
     * */
    public static int getNewPartId() {
        return partId++;
    }
    /**
     * This method creates a product ID
     * @return returns product ID
     * */
    public static int getNewProductId() {
        return productId++;
    }
}
