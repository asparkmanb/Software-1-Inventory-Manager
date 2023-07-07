package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import sparkman.inventoryproject.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Public class AddPartsController with an initialize method */
public class AddPartController implements Initializable {

    /** Private class member that creates a Stage variable. */
    private Stage stage;
    /** Private class member that creates a Scene variable. */
    private Scene scene;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addPartIdText;
    /** Private class member that creates a RadioButton variable. */
    @FXML
    private RadioButton addPartInHouseBttn;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addPartInvText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addPartMIdText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addPartMaxText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addPartMinText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addPartNameText;
    /** Private class member that creates a RadioButton variable. */
    @FXML
    private RadioButton addPartOutsourcedBttn;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField addPartPriceText;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button addPartSaveBttn;
    /** Private class member that creates a Label variable. */
    @FXML
    private Label machineIdLabel;

    /** Public method that allows for the cancel button to be clicked and returns the user to the main screen after a confirmation prompt. */
    @FXML
    public void cancelAddPartClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove all data provided in the fields, are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /** Public method that sets the label to either Machine ID or Company Name based off the radial button selected. */
    public void addPartRadioButton(ActionEvent event){
     if(addPartInHouseBttn.isSelected()){
        machineIdLabel.setText("Machine ID");
     }
        else{
        machineIdLabel.setText("Company Name");
     }
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

    /** Private method with parameters int min, max, and stock, returns a boolean is the operation is valid or not */
    private boolean isInvValid(int min, int max, int stock){
        boolean isValid = true;
        if(stock < min || stock > max){
            isValid = false;
        }
        return isValid;
    }


    /** Public method that allows for the save button to be clicked and returns the user to the main screen. Adds a new part to the Inventory based upon which radio button is selected. Has error checking for various scenarios. . */
    @FXML
    void onActionSaveAddPart(ActionEvent event) throws IOException{

        try{
            int stock = Integer.parseInt(addPartInvText.getText());
            String name = addPartNameText.getText();
            double price = Double.parseDouble(addPartPriceText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());
            int min = Integer.parseInt(addPartMinText.getText());
            String companyName = addPartMIdText.getText();

            if(addPartInHouseBttn.isSelected()){
                if(!isInvValid(min, max, stock)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    if(min > max){
                        alert.setContentText("Max must be greater than min");
                    }
                    else {
                        alert.setContentText("Stock must be greater than the minimum inventory and less than the maximum inventory");
                    }
                    alert.showAndWait();
                }
                else if(!minMax(min,max)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Max stock must be greater than minimum stock");
                    alert.showAndWait();
                }
                else if(name.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Name must be not be empty");
                    alert.showAndWait();
                }
                else{
                    try{
                        int machineId = Integer.parseInt(addPartMIdText.getText());
                        Inventory.addPart(new InHouse(Inventory.generatePartId(), name, price, stock ,min, max, machineId));


                        Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    }
                    catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Machine Id may only contain numbers");
                        alert.showAndWait();
                    }
                }
            }
            if(addPartOutsourcedBttn.isSelected()){
                if(!isInvValid(min, max, stock)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    if(min > max){
                        alert.setContentText("Max must be greater than min");
                    }
                    else {
                        alert.setContentText("Stock must be greater than min and less than max");
                    }
                    alert.showAndWait();
                }
                else if(!minMax(min,max)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Max must be greater than min");
                    alert.showAndWait();
                }
                else if(name.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Name must be not be empty");
                    alert.showAndWait();
                }
                else if(companyName.isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Company name must be not be empty");
                    alert.showAndWait();
                }
                else {
                    Inventory.addPart(new Outsourced(Inventory.generatePartId(), name, price, stock, min, max, companyName));

                    Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

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


/** Public initialize method that sets the InHouse radio button to selected by defaul */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartInHouseBttn.setSelected(true);
    }
}
