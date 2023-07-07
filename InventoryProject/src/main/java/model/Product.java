package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Product class, which allows for the creation of products. */
public class Product {
    /** Private class member that creates an int variable. */
    private int id;
    /** Private class member that creates a String variable. */
    private String name;
    /** Private class member that creates a double variable. */
    private double price;
    /** Private class member that creates an int variable. */
    private int stock;
    /** Private class member that creates an int variable. */
    private int min;
    /** Private class member that creates an int variable. */
    private int max;

    /** An ObservableList of parts used for associating parts to a product */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Product default constructor */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** @returns id */
    public int getId() {
        return id;
    }
    /** @param id sets the id for the product */
    public void setId(int id) {
        this.id = id;
    }
    /** @returns name */
    public String getName() {
        return name;
    }
    /** @param name sets the name for the product */
    public void setName(String name) {
        this.name = name;
    }
    /** @returns price */
    public double getPrice() {
        return price;
    }
    /** @param price sets the price for the product */
    public void setPrice(double price) {
        this.price = price;
    }
    /** @returns stock */
    public int getStock() {
        return stock;
    }
    /** @param stock sets the stock for the product */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /** @returns min */
    public int getMin() {
        return min;
    }
    /** @param min sets the min for the product */
    public void setMin(int min) {
        this.min = min;
    }
    /** @returns max */
    public int getMax() {
        return max;
    }
    /** @param max sets the max for the product */
    public void setMax(int max) {
        this.max = max;
    }

    /** Public method that takes a Part and adds it to the associatedParts list */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /** Public method that deletes the Part from the associatedPart list */
    public void deleteAssociatedPart(Part associatedPart){
        for(Part part : getAllAssociatedParts()){
            if(part.getId() == associatedPart.getId()){
                associatedParts.remove(part);
            }
        }
    }

    /** @returns associatedParts */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
