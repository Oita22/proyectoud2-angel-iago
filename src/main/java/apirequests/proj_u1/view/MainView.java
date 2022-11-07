package apirequests.proj_u1.view;

import apirequests.proj_u1.Main;
import apirequests.proj_u1.mgmt.Log;
import apirequests.proj_u1.model.News;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Main view class model
 *
 * @see javafx.fxml.Initializable
 */
public class MainView implements Initializable {
    @FXML
    private VBox mainWnd;
    @FXML
    private Pane loginPanel;
    @FXML
    private Pane pnlBtns;
    @FXML
    private Pane pnlSearch;
    @FXML
    private TableView resultTable;
    @FXML
    private ComboBox<String> cboOptions;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnSaveSelected;
    @FXML
    private Button btnSaveAll;
    @FXML
    private Button btnLoadNews;
    @FXML
    private CheckBox chkSaveSession;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPsswd;
    @FXML
    private Label lblErrLogin;

    @FXML
    private AnchorPane ancView;

    /**
     * Contains the News to be displayed in the table
     */
    private ObservableList<News> tableNews;
    /**
     * Link between this class and the model
     */
    private Relational relational;


    /**
     * Initial configuration to be executed before program start.
     *
     * @param url Implemented with the Initializable Interface
     * @param resourceBundle Implemented with the Initializable Interface
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWnd.getChildren().remove(pnlBtns);
        mainWnd.getChildren().remove(resultTable);
        mainWnd.getChildren().remove(pnlSearch);
        mainWnd.getChildren().remove(cboOptions);

        btnSearch.setDisable(true);
        btnSaveSelected.setDisable(true);
        btnSaveAll.setDisable(true);
        cboOptions.setItems(FXCollections.observableArrayList(News.categories));
        resultTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        relational = new Relational();
        chkSaveSession.setSelected(relational.chkSaveSessionSelected());
        if (chkSaveSession.isSelected()) {
            try {
                tableNews = relational.getTableColumns(new File(System.getProperty("java.io.tmpdir") + "\\newsCache.bin"));
                resultTable.setItems(tableNews);
                btnSaveSelected.setDisable(false);
                btnSaveAll.setDisable(false);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
        resultTable.getColumns().addAll(relational.getColumnsName());
    }

    /**
     * Unlock search button after selecting a category
     */
    public void searchBtnState() {
        if (cboOptions.getValue() != null)
            btnSearch.setDisable(false);
    }

    /**
     * Set the News data in the table. It can be from an API request, then simply perform a query by the selected
     * category, by a file saved by the user, which must be selected, or a cache file upload.
     *
     * @param actionEvent push button action
     */
    public void updateInfo(ActionEvent actionEvent) {
        String err = "An error has been occurred during the search.\nTry again.";
        try {
            if (Objects.equals(((Button) actionEvent.getSource()).getId(), btnLoadNews.getId())) {
                err = "The file cannot be read.";
                FileChooser dc = new FileChooser();
                File f = dc.showOpenDialog(mainWnd.getScene().getWindow());
                tableNews = relational.getTableColumns(f);
            } else
                tableNews = relational.getTableColumns(cboOptions.getValue());
            tableWidthPreferences();

            resultTable.setItems(tableNews);
            relational.saveState();

            btnSaveSelected.setDisable(false);
            btnSaveAll.setDisable(false);
        } catch (Exception e) {
            Log.writeErr("Empty request", e);
            if (!e.getMessage().equals("Cannot invoke \"java.io.File.toString()\" because \"file\" is null")) {
                Alert alert = new Alert(Alert.AlertType.WARNING, err, ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Save the news (RawNews Object) on a file. Can be a selected number of news, or all the news displayed on the table
     *
     * @param actionEvent push button action
     */
    public void saveRequest(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        try {
            FileChooser.ExtensionFilter[] extensionFilter = {new FileChooser.ExtensionFilter("Binary compressed file", ".bin"),
                    new FileChooser.ExtensionFilter("JSON file", ".json"),
                    new FileChooser.ExtensionFilter("Unformatted text file", ".txt"),
                    new FileChooser.ExtensionFilter("XML file", ".xml")};
            fileChooser.getExtensionFilters().addAll(extensionFilter);

            relational.setFile(fileChooser.showSaveDialog(mainWnd.getScene().getWindow()));

            if (Objects.equals(((Button) actionEvent.getSource()).getId(), btnSaveAll.getId()))
                relational.saveNews(resultTable.getItems());
            else if (Objects.equals(((Button) actionEvent.getSource()).getId(), btnSaveSelected.getId()) && resultTable.getSelectionModel().getSelectedItems().size() >= 1)
                relational.saveNews((ObservableList<News>) resultTable.getSelectionModel().getSelectedItems());
        } catch (RuntimeException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Make the displayed data more accessible. Right-click on the news row and copy the URL to the clipboard.
     * Double left click to open a new window with the image and summary.
     *
     * @param mouseEvent mouse button action
     */
    public void mouseShortcuts(MouseEvent mouseEvent) {
        try {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                // Detailed View extends Stage -> No pone los datos de la News en los TextField -> error null
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("detailedView.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                DetailedView stage = new DetailedView((News) resultTable.getSelectionModel().getSelectedItem());
                stage.setScene(scene);
                stage.show();/**/

                // Inicio desde esta clase
                /*Stage stage = new Stage();
                ancView = (AnchorPane) FXMLLoader.load(Main.class.getResource("detailedView.fxml"));
                Scene scene = new Scene(ancView);
                stage.setScene(scene);
                stage.setTitle("Ventya");
                stage.show();*/

                // Iniciando el stage en la nueva clase para la vista_detalles
                /*DetailedView detailedView = new DetailedView((News) resultTable.getSelectionModel().getSelectedItem());
                detailedView.showStage();*/

                // ...
                /*DetailedView detailedView = new DetailedView((News) resultTable.getSelectionModel().getSelectedItem());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailedView.fxml"));
                loader.setController(detailedView);
                Pane detail = loader.load();*/
            }

            if (mouseEvent.getButton().equals(MouseButton.MIDDLE)) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(((News) resultTable.getSelectionModel().getSelectedItem()).getReadMoreUrl());
                clipboard.setContent(content);
            }

            if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                if (mouseEvent.getClickCount() == 2) {
                    Dialog<Image> dlg = new Dialog<>();
                    dlg.setTitle(((News) resultTable.getSelectionModel().getSelectedItem()).getTitle());
                    dlg.getDialogPane().getButtonTypes().add(new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE));
                    ImageView imageView = new ImageView(new Image(((News) resultTable.getSelectionModel().getSelectedItem()).getImageUrl()));
                    imageView.setFitWidth(500);
                    imageView.setFitHeight(500);
                    dlg.setGraphic(imageView);
                    dlg.setContentText(((News) resultTable.getSelectionModel().getSelectedItem()).getContent());
                    dlg.show();
                }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.writeErr("Table click error", e);
        }
    }

    /**
     * Save the current state of the checkbox button.
     */
    public void saveSession() {
        relational.saveSession(chkSaveSession.isSelected());
    }

    /**
     * Initialize the width of the column names in the table
     */
    private void tableWidthPreferences() {
        double[] width = relational.columnsWidth();
        for (int i = 0; i < width.length; i++)
            ((TableColumn) resultTable.getColumns().get(i)).setPrefWidth(width[i]);
    }

    public void loginAction(ActionEvent actionEvent) {
        try {
            if (!txtUser.getText().equals("") && !txtPsswd.getText().equals("") && relational.login(txtUser.getText(), txtPsswd.getText())) {
            //if (true) {
                mainWnd.getChildren().remove(loginPanel);

                mainWnd.getChildren().add(cboOptions);
                mainWnd.getChildren().add(pnlSearch);
                mainWnd.getChildren().add(resultTable);
                mainWnd.getChildren().add(pnlBtns);
            } else {
                System.out.println("User not found");
                lblErrLogin.setText("User or password not match");
                lblErrLogin.setCenterShape(true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}