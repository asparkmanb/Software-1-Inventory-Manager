package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import sparkman.inventoryproject.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Public class AddProductsController with an initialize method */
public class AddProductController implements Initializable {
    /** Private class member that creates a Button variable. */
    @FXML
    private Button addProductAddBttn;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> addProductIdCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addProductIdText;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> addProductInvCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addProductInvText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addProductMaxText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addProductMinText;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, String> addProductNameCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addProductNameText;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Double> addProductPriceCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addProductPriceText;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button addProductRemoveBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button addProductSaveBttn;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addProductSearchText;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> addProductIdColBottom;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> addProductInvColBottom;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, String> addProductNameColBottom;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Double> addProductPriceColBottom;
    /** Private class member that creates a TableView of Parts variable. */
    @FXML
    private TableView<Part> addProductTableViewBottom;
    /** Private class member that creates a TableView of Parts variable. */
    @FXML
    private TableView<Part> addProductTableViewTop;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button addProductSearchButton;
    /** Private class member that creates a Stage variable. */
    private Stage stage;
    /** Private class member that creates a Scene variable. */
    private Scene scene;

    /** A private list of parts for the top TableView */
    private ObservableList<Part> topAssociatedParts = FXCollections.observableArrayList();
    /** A private list of parts for the bottom TableView */
    private ObservableList<Part> bottomAssociatedParts = FXCollections.observableArrayList();

    /** Public method that allows the for searching of parts. */
    @FXML
    void onActionSearch(ActionEvent event) {

        String currentText = addProductSearchText.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(currentText);

        if(currentText.isEmpty()){
            addProductTableViewTop.setItems(Inventory.getAllParts());
            return;
        }

        try {
            if (foundParts.size() == 0) {
                int partId = Integer.parseInt(currentText);
                Part idSearch = Inventory.lookupPart(partId);
                if (idSearch != null) {
                    foundParts.add(idSearch);
                    addProductTableViewTop.getSelectionModel().select(Inventory.lookupPart(partId));
                }
                if(foundParts.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("No part has been found");
                    alert.showAndWait();
                    return;
                }
            }
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part has been found");
            alert.showAndWait();
            return;
        }
        addProductTableViewTop.setItems(foundParts);
    }
    /** Public method that allows the for cancel button to cancel adding a product. */
    @FXML
    public void cancelAddProductClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** Public method that sets the current part selected, and adds that to Observable List. */
    @FXML
    public void onActionAddProduct(ActionEvent event) {

        Part currentPart = addProductTableViewTop.getSelectionModel().getSelectedItem();
        bottomAssociatedParts.add(currentPart);
        addProductTableViewBottom.setItems(bottomAssociatedParts);
    }

    /** Public method that allows the for the user to remove an associated part from the product. */
    @FXML
    public void onActionRemoveAssociatedPart(ActionEvent event) throws IOException{

        Part currentPart = addProductTableViewBottom.getSelectionModel().getSelectedItem();

        if(currentPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part has been selected");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Performing this action will remove the part from the product, are you sure you want to remove?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            bottomAssociatedParts.remove(currentPart);
        }

    }

    /** Private method with parameters int min, max, and stock, returns a boolean is the operation is valid or not */
    private boolean isInvValid(int min, int max, int stock){
        boolean isValid = true;
        if(stock < min || stock > max){
            isValid = false;
        }
        return isValid;
    }

    /** Private method with parameters int min and max, returns a boolean is the operation is valid or not */
    private boolean minMax(int min,int max){
        boolean isMinMax = false;
        if(min > 0 && min < max){
            isMinMax = true;
        }
        else{
            isMinMax = false;
        }
        return isMinMax;
    }

    /** Public method that allows for the save button to be clicked and returns the user to the main screen. Adds a new product. Adds current list of parts to the associatedParts list and then copies the bottomAssociatedParts with these parts. Has error checking for various scenarios. . */
    @FXML
    void onActionSave(ActionEvent event) {

        try {
            int stock = Integer.parseInt(addProductInvText.getText());
            String name = addProductNameText.getText();
            double price = Double.parseDouble(addProductPriceText.getText());
            int max = Integer.parseInt(addProductMaxText.getText());
            int min = Integer.parseInt(addProductMinText.getText());

            if (!isInvValid(min, max, stock)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                if (min > max) {
                    alert.setContentText("Max must be greater than min");
                } else {
                    alert.setContentText("Stock must be greater than the minimum inventory and less than the maximum inventory");
                }
                alert.showAndWait();

            } else if (!minMax(min, max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Max stock must be greater than minimum stock");
                alert.showAndWait();

            } else if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Name must be not be empty");
                alert.showAndWait();

            }
            else {
                try {
                    Product newProduct =new Product(Inventory.generateProductId(), name, price, stock, min, max);

                    for(Part part : bottomAssociatedParts){
                        newProduct.addAssociatedPart(part);
                    }

                    Inventory.addProduct(newProduct);

                    Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Machine Id may only contain numbers");
                    alert.showAndWait();
                }
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding part, empty or invalid fields found");
            alert.showAndWait();
        }
    }

    /** Public method that populates the top TableView with all of the parts.  */
    public void populateAddPartTop(){
        for(Part currentPart : Inventory.getAllParts())
        topAssociatedParts.add(currentPart);
    }

    /**Initialize method that calls populateAddPartTop() to set the table, and then sets the cells for TableView.  */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateAddPartTop();

        addProductTableViewTop.setItems(topAssociatedParts);
        addProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductIdColBottom.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductInvColBottom.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductNameColBottom.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPriceColBottom.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
