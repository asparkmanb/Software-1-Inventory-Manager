/** Module */
module sparkman.inventoryproject {
    requires javafx.controls;
    requires javafx.fxml;



    exports controller;
    opens controller to javafx.graphics, javafx.fxml, javafx.base;
    exports sparkman.inventoryproject;
    opens sparkman.inventoryproject to javafx.graphics, javafx.fxml, javafx.base;
    opens model to javafx.graphics, javafx.fxml, javafx.base;
}