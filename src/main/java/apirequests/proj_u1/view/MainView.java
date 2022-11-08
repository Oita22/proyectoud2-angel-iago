package apirequests.proj_u1.view;

import apirequests.proj_u1.mgmt.Log;
import apirequests.proj_u1.model.News;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
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
    public Button btnCreate;
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
    @FXML
    private ComboBox<String> cboOptions1;
    @FXML
    public TextField txtTitle;
    @FXML
    public TextField txtAuthor;
    @FXML
    public TextArea txtContent;
    @FXML
    public TextField txtImageUrl;
    @FXML
    public TextField txtNewUrl;
    @FXML
    public ImageView imgViewImageShow;
    @FXML
    public Button btnUpdateDB;
    private News currentNew;
    enum PanelState {
        LOGIN,
        TABLE_VIEW,
        DETAIL_VIEW,
    }




    /**
     * Initial configuration to be executed before program start.
     *
     * @param url Implemented with the Initializable Interface
     * @param resourceBundle Implemented with the Initializable Interface
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changePanels(PanelState.LOGIN, null);

        txtContent.setWrapText(true);

        btnSearch.setDisable(true);
        btnSaveSelected.setDisable(true);
        btnSaveAll.setDisable(true);
        cboOptions.setItems(FXCollections.observableArrayList(News.categories));
        cboOptions1.setItems(FXCollections.observableArrayList(News.categories));
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
            currentNew = (News) resultTable.getSelectionModel().getSelectedItem();
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {

                changePanels(PanelState.DETAIL_VIEW, currentNew);
                btnUpdateDB.setText("Update");
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



    private void changePanels(PanelState panelState, News myNew) {
        switch (panelState) {
            case LOGIN:
                mainWnd.getChildren().remove(pnlBtns);
                mainWnd.getChildren().remove(resultTable);
                mainWnd.getChildren().remove(pnlSearch);
                mainWnd.getChildren().remove(cboOptions);

                mainWnd.getChildren().remove(ancView);
                break;

            case TABLE_VIEW:
                mainWnd.getChildren().remove(loginPanel);

                mainWnd.getChildren().add(cboOptions);
                mainWnd.getChildren().add(pnlSearch);
                mainWnd.getChildren().add(resultTable);
                mainWnd.getChildren().add(pnlBtns);

                mainWnd.getChildren().remove(ancView);
                break;

            case DETAIL_VIEW:
                mainWnd.getChildren().remove(loginPanel);

                mainWnd.getChildren().remove(pnlBtns);
                mainWnd.getChildren().remove(resultTable);
                mainWnd.getChildren().remove(pnlSearch);
                mainWnd.getChildren().remove(cboOptions);

                mainWnd.getChildren().add(ancView);
                if (myNew != null) {
                    cboOptions1.setValue(relational.getRawNews().getCategory());
                    cboOptions1.setDisable(true);
                    txtTitle.setText(myNew.getTitle());
                    txtAuthor.setText(myNew.getAuthor());
                    txtContent.setText(myNew.getContent());
                    txtImageUrl.setText(myNew.getImageUrl());
                    txtNewUrl.setText(myNew.getUrl());
                    imgViewImageShow.setImage(new Image(myNew.getImageUrl()));
                    imgViewImageShow.setFitWidth(206);
                    imgViewImageShow.setFitHeight(333);
                }
                break;

            default:
                break;
        }
    }

    public void goBack() {
        changePanels(PanelState.TABLE_VIEW, null);
    }

    public void createNews(ActionEvent actionEvent) {
        changePanels(PanelState.DETAIL_VIEW, null);
        btnUpdateDB.setText("Add new");
        cleanFormNews();
    }

    public void updateNews(ActionEvent actionEvent) {
        //changePanels(PanelState.DETAIL_VIEW, null);


        if (btnUpdateDB.getText().equals("Add new")) {
            String[] data = {txtTitle.getText(), txtAuthor.getText(), txtContent.getText(), txtImageUrl.getText(), txtNewUrl.getText()};
            currentNew = new News();
            // String title, String author, String content, String imageUrl, String url
            String category = cboOptions1.getValue();
            boolean correct = true;

            for (String aux : data)
                if (aux.equals("")) {
                    correct = false;
                    break;
                }

            if (correct && !category.equals("")) {
                relational.updateNewData(currentNew, data);
                relational.executeOp(0, currentNew, category);

                //updateInfo(actionEvent);
            }
        } else {
            String[] data = {txtTitle.getText(), txtAuthor.getText(), txtContent.getText(), txtImageUrl.getText(), txtNewUrl.getText()};
            relational.updateNewData(currentNew, data);
            relational.executeOp(1, currentNew, cboOptions1.getValue());
        }

        //updateInfo(actionEvent);
    }

    public void deleteNews(ActionEvent actionEvent) {
        relational.executeOp(2, currentNew, cboOptions1.getValue());

        //updateInfo(actionEvent);
        changePanels(PanelState.TABLE_VIEW, null);
    }

    private void cleanFormNews() {
        cboOptions1.setValue(News.categories[0]);
        cboOptions1.setDisable(false);
        txtTitle.setText("");
        txtAuthor.setText("");
        txtContent.setText("");
        txtImageUrl.setText("");
        txtNewUrl.setText("");
        imgViewImageShow.setImage(null);
    }
}