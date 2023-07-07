package sparkman.inventoryproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.spi.CalendarDataProvider;

/**
 * FUTURE ENHANCEMENT. One future enhancement that I would like to be able to implement would be a field for the products that totals all of the associated parts cost and calculates how much that product costs to make from the parts that are associated with it. This implementation would not be too difficult, I would only need to create a method that adds the price of the associatedParts list for each product and then create a field for this data to be displayed.
 * *<p><b>
 *
 * Javadocs folder will be found under C482/Project/javadoc
 *
 * *</b></p>
 *
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method, generates placeholder objects and calls the launch method.
     */

    public static void main(String[] args) {

        InHouse wheels = new InHouse(Inventory.generatePartId(),"Wheels", 100.99,6,1,10,101);
        Inventory.addPart(wheels);
        InHouse handlebars = new InHouse(Inventory.generatePartId(),"Handlebars", 40.99,2,1,10,102);
        Inventory.addPart(handlebars);
        InHouse brakes = new InHouse(Inventory.generatePartId(),"Brakes", 78.99,9,1,10,103);
        Inventory.addPart(brakes);
        Outsourced seat = new Outsourced(Inventory.generatePartId(),"Seat",25.35, 4, 1,10,"SeatsRUs");
        Inventory.addPart(seat);
        Outsourced body = new Outsourced(Inventory.generatePartId(),"Body",85.99, 2, 1,10,"Bike Body LLC");
        Inventory.addPart(body);

        Product mountainBike = new Product(Inventory.generateProductId(),"Mountain Bike", 250.00,2,1,5);
        Inventory.addProduct(mountainBike);
        Product streetBike = new Product(Inventory.generateProductId(),"Street Bike", 200.00, 1, 1, 5);
        Inventory.addProduct(streetBike);
        Product tricycle = new Product(Inventory.generateProductId(), "Tricycle", 305.00, 3,1,5);
        Inventory.addProduct(tricycle);
        Product dirtbike = new Product(Inventory.generateProductId(), "Dirt Bike", 999.00, 4,1,5);
        Inventory.addProduct(dirtbike);



        launch();
    }
}