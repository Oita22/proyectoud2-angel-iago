package apirequests.proj_u1.view;

import apirequests.proj_u1.Main;
import apirequests.proj_u1.model.News;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailedView extends Stage/**/ implements Initializable {
    private Stage thisStage;
    @FXML
    private AnchorPane ancView;
    @FXML
    private ComboBox<String> cboOptions;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextArea txtContent;
    @FXML
    private TextField txtImageUrl;
    @FXML
    private TextField txtNewUrl;
    @FXML
    private ImageView imgViewImageShow;

    private News news;

    public DetailedView () {
        super(StageStyle.DECORATED);
        //initStage();
    }

    public DetailedView(News myNew) {
        super(StageStyle.DECORATED);
        //initStage();
        news = myNew;
        System.out.println(news);
        if (news != null) {
            txtTitle.setText(news.getTitle());
            txtAuthor.setText(news.getAuthor());
            txtContent.setText(news.getContent());
            txtImageUrl.setText(news.getImageUrl());
            txtNewUrl.setText(news.getReadMoreUrl());
            imgViewImageShow.setImage(new Image(news.getImageUrl()));
            imgViewImageShow.setFitWidth(250);
            imgViewImageShow.setFitHeight(250);
        }
    }
    private void initStage() {
        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("detailedView.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Edited Mode");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showStage() {
        thisStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboOptions.setItems(FXCollections.observableArrayList(News.categories));
    }

    public void updateNews() {
        System.out.println(txtTitle.getText() + " - " + txtNewUrl.getText() + " - " + txtAuthor.getText() + " - " + txtContent.getText() + " - " + txtImageUrl.getText());
    }

}
