package apirequests.proj_u1;

import apirequests.proj_u1.mgmt.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This project use a public News API to make the request based on the option selected by the user,
 * and show the result on the table below.
 *
 * @author Angel Monroy and Iago Oitaven
 * @version 1.0
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("NEWS API");
        stage.getIcons().add(new Image("file:src\\main\\resources\\img\\icon.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Default start method created using JavaFX
     *
     * @param args input arguments
     */
    public static void main(String[] args) {
        FileUtils.setConfig(FileUtils.loadConfig());
        launch();
    }
}