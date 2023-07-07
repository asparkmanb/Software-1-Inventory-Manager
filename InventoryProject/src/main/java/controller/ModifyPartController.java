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
import model.Part;
import sparkman.inventoryproject.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Public class ModifyPartsController with an initialize method */
public class ModifyPartController implements Initializable {

    /** Private class member that creates a Label variable. */
    @FXML
    private Label modifyMachineIdLabel;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyPartIdText;
    /** Private class member that creates a Label variable. */
    @FXML
    private Label machineIdLabel;
    /** Private class member that creates a RadioButton variable. */
    @FXML
    private RadioButton modifyPartInHouseBttn;
    /** Private class member that creates a RadioButton variable. */
    @FXML
    private RadioButton modifyPartOutsourcedBttn;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyPartInvText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyPartMachineIdText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyPartMaxText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyPartMinText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyPartNameText;
    /** Private class member that creates a TextField variable. */
    @FXML
    private TextField modifyPartPriceText;
    /** Private class member that creates a Button variable. */
    @FXML
    private Button modifyPartSaveBttn;
    /** Private class member that creates a Stage variable. */
    private Stage stage;
    /** Private class member that creates a Scene variable. */
    private Scene scene;
    /** Private class member that creates a Part variable for the current part. */
    Part currentPart;
    /** Private class member that creates an int variable for the current index. */
    int currentIndex;

    /** Public method that sets the text field label to Machine ID or Company Name based upon which radio button is selected */
    @FXML
    public void isInHouse(ActionEvent event) {
        if (modifyPartInHouseBttn.isSelected()) {
            modifyMachineIdLabel.setText("Machine ID");
        } else {
            modifyMachineIdLabel.setText("Company Name");
        }
    }

    /** Public method that allows the user to cancel the operation and return to the home screen. */
    @FXML
    public void cancelModifyPartClick(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel modifying the part, would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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

    /** LOGICAL ERROR
     * The logical error that I was encountering was during the onActionSaveModifyPart method.
     * When I was saving the modified product, it would create a new product and add it to the list on Inventory so that there were two copies of the object.
     * I found that this was because I was using the .add method, and this was solved once I learned about the .set method.
     * Public method that allows for the save button to be clicked and returns the user to the main screen. Modifies the current part to the Inventory based upon which radio button is selected. Has error checking for various scenarios.
     */
    @FXML
    void onActionSaveModifyPart(ActionEvent event) throws IOException{
        try{
            int id = Integer.parseInt(modifyPartIdText.getText());
            int stock = Integer.parseInt(modifyPartInvText.getText());
            String name = modifyPartNameText.getText();
            double price = Double.parseDouble(modifyPartPriceText.getText());
            int max = Integer.parseInt(modifyPartMaxText.getText());
            int min = Integer.parseInt(modifyPartMinText.getText());

            if(modifyPartInHouseBttn.isSelected()){
                modifyMachineIdLabel.setText("Machine ID");

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
                else {
                    try{
                        int machineId = Integer.parseInt(modifyPartMachineIdText.getText());
                        InHouse modifiedInhouse = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.getAllParts().set(currentIndex, modifiedInhouse);

                        Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
                        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    }
                    catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("Machine Id may only contain numbers");
                        alert.showAndWait();
                    }
                }
            }
            else if (modifyPartOutsourcedBttn.isSelected()) {
                modifyMachineIdLabel.setText("Company Name");
                String companyName = modifyPartMachineIdText.getText();

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
                else{
                    Outsourced modifiedOutSourced = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.getAllParts().set(currentIndex, modifiedOutSourced);

                    Parent root = FXMLLoader.load(Main.class.getResource("Main.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }
            };
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding part, empty or invalid fields found");
            alert.showAndWait();
        }
    }

/** Public method with parameters Part and Index, which takes the information given from the MainScreenController and sets the part to the selected part from the Main. */
    @FXML
    public void sendPart(Part part,int index) {

        currentPart = part;
        currentIndex = index;

        modifyPartIdText.setText(String.valueOf(part.getId()));
        modifyPartInvText.setText(String.valueOf(part.getStock()));
        modifyPartMaxText.setText(String.valueOf(part.getMax()));
        modifyPartMinText.setText(String.valueOf(part.getMin()));
        modifyPartNameText.setText(part.getName());
        modifyPartPriceText.setText(String.valueOf(part.getPrice()));

        if(part instanceof InHouse){
            modifyPartInHouseBttn.setSelected(true);
            modifyPartMachineIdText.setText(String.valueOf(((InHouse) part).getMachineId()));
        }

        if(part instanceof Outsourced){
            modifyPartOutsourcedBttn.setSelected(true);
            modifyMachineIdLabel.setText("Company Name");
            modifyPartMachineIdText.setText(String.valueOf(((Outsourced) part).getCompanyName()));
        }
    }

    /** Public initialize method. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}



