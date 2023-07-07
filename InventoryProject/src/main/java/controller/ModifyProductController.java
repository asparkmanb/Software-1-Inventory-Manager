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

/** Public class ModifyProductController with an initialize method */
public class ModifyProductController implements Initializable {

    /** Private class member that creates a Button variable. */
    @FXML
    private Button modifyProductAddBttn;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> modifyProductIdColBottom;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> modifyProductInvColBottom;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, String> modifyProductNameColBottom;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Double> modifyProductPriceColBottom;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> modifyProductIdCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyProductIdText;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> modifyProductInvCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyProductInvText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyProductMaxText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyProductMinText;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, String> modifyProductNameCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyProductNameText;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyProductPriceText;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button modifyProductRemoveBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button modifyProductSaveBttn;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyProductSearchText;
    /** Private class member that creates a TableView of Parts variable. */
    @FXML
    private TableView<Part> modifyProductTableViewBottom;
    /** Private class member that creates a TableView of Parts variable. */
    @FXML
    private TableView<Part> modifyProductTableViewTop;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button modifyProductSearch;
    /** Private class member that creates a Product variable for current product. */
    Product currentProduct;
    /** Private class member that creates an int variable for current product index. */
    int currentProductIndex;

    /** A private list of parts for the top TableView */
    private ObservableList<Part> topAssociatedParts = FXCollections.observableArrayList();
    /** A private list of parts for the bottom TableView */
    private ObservableList<Part> bottomAssociatedParts = FXCollections.observableArrayList();
    private Stage stage;
    private Scene scene;


    /** Public method that populates the top TableView with all of the parts.  */
    public void populateAddPartTop(){
        for(Part part : Inventory.getAllParts()){
            topAssociatedParts.add(part);
        }
    }

    /** Public method that allows the for searching of parts. */
    @FXML
    void onActionSearch(ActionEvent event) {
        String currentText = modifyProductSearchText.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(currentText);

        if(currentText.isEmpty()){
            modifyProductTableViewTop.setItems(Inventory.getAllParts());
            return;
        }

        try {
            if (foundParts.size() == 0) {
                int partId = Integer.parseInt(currentText);
                Part idSearch = Inventory.lookupPart(partId);
                if (idSearch != null) {
                    foundParts.add(idSearch);
                    modifyProductTableViewTop.getSelectionModel().select(Inventory.lookupPart(partId));
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
        modifyProductTableViewTop.setItems(foundParts);
    }

    /** Public method that allows the for cancel button to cancel adding a product. */
    @FXML
    public void cancelModifyProductClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** Public method that sets the current part selected, and adds that to Observable List. */
    @FXML
    void onActionAddPart(ActionEvent event) {

        Part currentPart = modifyProductTableViewTop.getSelectionModel().getSelectedItem();
        bottomAssociatedParts.add(currentPart);

    }

    /** Public method that allows the for the user to remove an associated part from the product. */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {

        Part currentPart = modifyProductTableViewBottom.getSelectionModel().getSelectedItem();

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
    private boolean isInvValid(int min, int max, int stock) {
        boolean isValid = true;
        if (stock < min || stock > max) {
            isValid = false;
        }
        return isValid;
    }

    /** Private method with parameters int min and max, returns a boolean is the operation is valid or not */
    private boolean minMax(int min, int max) {
        boolean isMinMax = false;
        if (min > 0 && min < max) {
            isMinMax = true;
        } else {
            isMinMax = false;
        }
        return isMinMax;
    }

    /** Public method that allows for the save button to be clicked and returns the user to the main screen. Modifies the existing product. Adds current list of parts to the associatedParts list and then copies the bottomAssociatedParts with these parts. Has error checking for various scenarios. */
    @FXML
    void onActionSaveModifyProduct(ActionEvent event) {

        try {
            int id = Integer.parseInt(modifyProductIdText.getText());
            int stock = Integer.parseInt(modifyProductInvText.getText());
            String name = modifyProductNameText.getText();
            double price = Double.parseDouble(modifyProductPriceText.getText());
            int max = Integer.parseInt(modifyProductMaxText.getText());
            int min = Integer.parseInt(modifyProductMinText.getText());

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
            } else {
                try {
                    Product modifiedProduct = new Product(id, name, price, stock, min, max);
                    Inventory.getAllProducts().set(currentProductIndex, modifiedProduct);
                    for(Part part : bottomAssociatedParts){
                        modifiedProduct.addAssociatedPart(part);

                    }

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
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding product, empty or invalid fields found");
            alert.showAndWait();
        }
    }

    /** Public method with parameters Product and Index, which takes the information given from the MainScreenController and sets the product to the selected product from the Main. */
    public void sendProduct(Product product, int index) {

        currentProduct = product;
        currentProductIndex = index;

        for (Part part : currentProduct.getAllAssociatedParts()){
            bottomAssociatedParts.add(part);
        }
        modifyProductTableViewBottom.setItems(bottomAssociatedParts);

        modifyProductIdText.setText(String.valueOf(product.getId()));
        modifyProductInvText.setText(String.valueOf(product.getStock()));
        modifyProductMaxText.setText(String.valueOf(product.getMax()));
        modifyProductMinText.setText(String.valueOf(product.getMin()));
        modifyProductNameText.setText(product.getName());
        modifyProductPriceText.setText(String.valueOf(product.getPrice()));

    }

    /** Public initialize method that calls populateAddPartTop() and sets the top table full of the parts from Inventory. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateAddPartTop();

        modifyProductTableViewTop.setItems(topAssociatedParts);
        modifyProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductIdColBottom.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductInvColBottom.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductNameColBottom.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPriceColBottom.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


}




