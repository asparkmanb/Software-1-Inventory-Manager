package model;

import controller.MainScreenController;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Inventory class, which allows for InHouse and Outsourced parts to be created. */
public class Inventory {
    /** A private integer for auto incrementing part IDs*/
    private static int partAutoIncrementId = 0;

    /** Created a private integer for auto incrementing product IDs*/
    private static int productAutoIncrementId = 0;

    /** Public method that generated a part ID. */
    public static int generatePartId(){
        partAutoIncrementId += 1;
        return partAutoIncrementId;
    }

    /** Public method that generated a product ID. */
    public static int generateProductId(){
        productAutoIncrementId += 1;
        return productAutoIncrementId;
    }

    /** Creates an ObservableList of Parts called allParts. */
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Creates an ObservableList of Products called allProducts. */
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Public method that adds a new part to the allParts list. */
    public static void addPart(Part newPart){

        allParts.add(newPart);
    };

    /** Public method that adds a new product to the allProducts list. */
    public static void addProduct(Product newProduct){

        allProducts.add(newProduct);
    };

    /** Public method that takes an partId integer and checks for a match in the allParts list. */
    public static Part lookupPart(int partId){
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == partId)
                return part;
        }
        return null;
    }

    /** Public method that takes an productId integer and checks for a match in the allProducts list. */
    public static Product lookupProduct(int productId){
        for (Product product : Inventory.getAllProducts()){
            if(product.getId() == productId)
                return product;
        }
        return null;
    }

    /** Public method that takes a part name and checks for a match in the allParts list. */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (Part part : allParts){
            if(part.getName().contains(partName)){
                foundPart.add(part);
            }
        }
        return foundPart;
    }

    /** Public method that takes a product name and checks for a match in the allProducts list. */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        ObservableList<Product> allProduct = Inventory.getAllProducts();

        for (Product product : Inventory.getAllProducts()){
            if(product.getName().contains(productName)){
                foundProduct.add(product);
            }
        }

        return foundProduct;
    }

    /** Public method that takes an index integer and a part, updates that part in the allParts list. */
    public static void updatePart(int index, Part selectedPart){
        index = -1;

        for(Part part : Inventory.getAllParts()) {
            index++;
            if(part.getId() == index){
                Inventory.getAllParts().set(index,part);
            }
        }
    }

    /** Public method that takes an index integer and a product, updates that part in the allProducts list. */
    public static void updateProduct(int index, Product newProduct){
        index = -1;

        for(Product product : Inventory.getAllProducts()){
            index++;
            if(product.getId() == index){
                Inventory.getAllProducts().set(index, product);
            }
        }
    }

    /** Public method that takes a part and removes it from the allParts list. */
    public static boolean deletePart(Part selectedPart){
        for(Part part : Inventory.getAllParts()){
            if(part.getId() == selectedPart.getId()){
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }

    /** Public method that takes a product and removes it from the allProducts list. */
    public static boolean deleteProduct(Product selectedProduct){
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == selectedProduct.getId()){
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }

    /** Public method that returns the allParts list. */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Public method that returns the allProducts list. */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


}


