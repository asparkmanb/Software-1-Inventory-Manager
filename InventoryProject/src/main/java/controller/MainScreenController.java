package controller;


import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
import model.Inventory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Public class MainScreenController with an initialize method */
public class MainScreenController implements Initializable {

    /** Private class member that creates a TableView of Parts variable. */
    @FXML
    private TableView<Part> mainScreenPartsTableView;
    /** Private class member that creates a TableView of Products variable. */
    @FXML
    private TableView<Product> mainScreenProductsTableView;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msAddPartsBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msAddProductsBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msDeletePartsBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msDeleteProductsBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msExitBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msModifyPartsBttn;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msModifyProductsBttn;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> msPartIdCol;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Integer> msPartInvCol;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, String> msPartNameCol;
    /** Private class member that creates a TableColumn of Parts variable. */
    @FXML
    private TableColumn<Part, Double> msPartPriceCol;
    /** Private class member that creates a TableColumn of Products variable. */
    @FXML
    private TableColumn<Product, Integer> msProductIdCol;
    /** Private class member that creates a TableColumn of Products variable. */
    @FXML
    private TableColumn<Product, Integer> msProductInvCol;
    /** Private class member that creates a TableColumn of Products variable. */
    @FXML
    private TableColumn<Product, String> msProductNameCol;
    /** Private class member that creates a TableColumn of Products variable. */
    @FXML
    private TableColumn<Product, Double> msProductPriceCol;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField msSearchPartsTxt;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField msSearchProductsTxt;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msSearchButtonPart;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button msSearchButtonProduct;
    /** Private class member that creates a Part variable. */
    Part currentPart;
    /** Private class member that creates a Product variable. */
    Product currentProduct;
    /** Private class member that creates an int variable for current index. */
    int currentIndex;
    /** Private class member that creates an int variable for current product index. */
    int currentProductIndex;
    /** Private class member that creates a Stage variable. */
    private Stage stage;
    /** Private class member that creates a Scene variable. */
    private Scene scene;
    /** Private class member that creates a Parent variable. */
    private Parent root;

    /** Public method that allows for the AddPart button to be clicked and loads a new scene to AddPart.fxml */
    @FXML
    public void switchToAddPartButtonMS(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("AddPart.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** Public method that allows for the Modify button to be clicked and loads a new scene to ModifyPart.fxml. Also sends the current part and current parts index to ModifyPart.fxml */
    @FXML
    public void switchToModifyPartMS(ActionEvent event) throws IOException {

        currentPart = mainScreenPartsTableView.getSelectionModel().getSelectedItem();
        currentIndex = mainScreenPartsTableView.getSelectionModel().getSelectedIndex();

        if(currentPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part has been selected");
            alert.showAndWait();
        }

        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPARTController = loader.getController();
            MPARTController.sendPart(currentPart, currentIndex);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Public method that allows for the AddProduct button to be clicked and loads a new scene to AddProduct.fxml */
    @FXML
    public void switchToAddProductMS(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("AddProduct.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /** Public method that allows for the Modify button to be clicked and loads a new scene to ModifyProduct.fxml. Also sends the current product and current product index to ModifyPart.fxml */
    @FXML
    public void switchToModifyProductMS(ActionEvent event) throws IOException {

        currentProduct = mainScreenProductsTableView.getSelectionModel().getSelectedItem();
        currentProductIndex = mainScreenProductsTableView.getSelectionModel().getSelectedIndex();

        if(currentProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No product has been selected");
            alert.showAndWait();
        }

        else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPRODUCTController = loader.getController();
            MPRODUCTController.sendProduct(currentProduct, currentProductIndex);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Public method that allows the delete part to be clicked, which then will delete the selected part from the Inventory. */
    @FXML
    void msOnActionDeletePart(ActionEvent event) {
        Part selectedPart = (Part)mainScreenPartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part has been selected");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Performing this action will delete the part, are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /** Public method that allows the delete product to be clicked, which then will delete the selected product from the Inventory. Has error checking to ensure that the product to be deleted has no associated parts. */
    @FXML
    void msOnActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = (Product)mainScreenProductsTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No product has been selected");
            alert.showAndWait();
            return;
        }

        if(selectedProduct.getAllAssociatedParts().size() >= 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("You cannot delete a product that has an associated part with it");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Performing this action will delete the product, are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }
    }

    /** Public method that allows the for searching of parts. */
    @FXML
    void msSearchPartsButton(ActionEvent event) {

        String currentText = msSearchPartsTxt.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(currentText);

        if(currentText.isEmpty()){
            mainScreenPartsTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            if (foundParts.size() == 0) {
                int partId = Integer.parseInt(currentText);
                Part idSearch = Inventory.lookupPart(partId);
                if (idSearch != null) {
                    foundParts.add(idSearch);
                    mainScreenPartsTableView.getSelectionModel().select(Inventory.lookupPart(partId));
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
        mainScreenPartsTableView.setItems(foundParts);
    }

    /** Public method that allows the for searching of products. */
    @FXML
    void msSearchProductButton(ActionEvent event) {
        String currentText = msSearchProductsTxt.getText();
        ObservableList<Product> foundProducts = Inventory.lookupProduct(currentText);
        try {
            if (foundProducts.size() == 0) {
                int productId = Integer.parseInt(currentText);
                Product idSearch = Inventory.lookupProduct(productId);
                if (idSearch != null) {
                    foundProducts.add(idSearch);
                    mainScreenProductsTableView.getSelectionModel().select(Inventory.lookupProduct(productId));
                     }
                if(foundProducts.size() == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("No product has been found");
                    alert.showAndWait();
                    return;
                    }
                }
            }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No product has been found");
            alert.showAndWait();
            return;
        }
        mainScreenProductsTableView.setItems(foundProducts);
    }



    /** Public method that allows the for exit button to exit the program. */
    @FXML
    void msOnActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Performing this action will delete the product, are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


    /** Initialize method that sets the tableviews for both parts and products. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainScreenPartsTableView.setItems(Inventory.getAllParts());
        mainScreenProductsTableView.setItems(Inventory.getAllProducts());

        msPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        msPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        msPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        msPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        msProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        msProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        msProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        msProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}


