package apirequests.proj_u1.view;

import apirequests.proj_u1.mgmt.FileUtils;
import apirequests.proj_u1.mgmt.Request;
import apirequests.proj_u1.model.News;
import apirequests.proj_u1.model.RawNews;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;

/**
 * Main class using as controller, where connect the view's request with the data
 */
public class Relational {
    /**
     * Title's column width
     */
    private final double titleWidth = 400;
    /**
     * Content's column width
     */
    private final double contentWidth = 2000;
    /**
     * Date's column width
     */
    private final double dateWidth = 120;
    /**
     * Time's column width
     */
    private final double timeWidth = 60;
    private TableColumn<Object, Object> titleColumn;
    private TableColumn<Object, Object> contentColumn;
    private TableColumn<Object, Object> dateColumn;
    private TableColumn<Object, Object> timeColumn;
    private RawNews rawNews;
    private File file;

    /**
     * Class builder. Initialize all columns to be displayed in the view table.
     */
    public Relational() {
        titleColumn = new TableColumn<>("Title");
        contentColumn = new TableColumn<>("Content");
        dateColumn = new TableColumn<>("Date");
        timeColumn = new TableColumn<>("Time");

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setRawNews(RawNews rawNews) {
        this.rawNews = rawNews;
    }

    /**
     * It receives the mapping of the file based on the news category. It gets a RawNews object with all the data to
     * be set in the table, or may return null, if the request is unreadable or has an unknown structure.
     * Then, initialize an ObservableList with the news.
     *
     * @param category Type of news to search by
     * @return an ObservableList object of News
     */
    public ObservableList<News> getTableColumns(String category) {
        rawNews = Request.getRawNewsList(category.toLowerCase());
        ObservableList<News> tableNews = FXCollections.observableArrayList();

        tableNews.addAll(rawNews.getNews());
        return tableNews;
    }

    /**
     * It receives the mapping of the file passed, it can be from cache or one saved by the user. From the request it
     * will get a RawNews object with all the data to set in the table, or may return null, if the file is unreadable,
     * obsolete or has an unknown structure.
     *
     * @param file File to read and map
     * @return an ObservableList object with the news to be displayed in the table
     */
    public ObservableList<News> getTableColumns(File file) {
        rawNews = FileUtils.loadNews(file);
        ObservableList<News> tableNews = FXCollections.observableArrayList();

        tableNews.addAll(rawNews.getNews());
        return tableNews;
    }

    /**
     * An array of TableColumn object initialized with the name of each column and the type of the attribute to contain
     *
     * @return TableColumn initialized array of objects
     */
    public TableColumn[] getColumnsName() {
        return new TableColumn[]{dateColumn, timeColumn, titleColumn, contentColumn};
    }

    /**
     * Array with specified limits to set a custom width for each column name
     *
     * @return Array of Double with the width of each column
     */
    public double[] columnsWidth() {
        return new double[]{dateWidth, timeWidth, titleWidth, contentWidth};
    }

    /**
     * Establish a list of the news selected by the user to be saved
     *
     * @param newsList ObservableList with the news selected
     */
    public void saveNews(ObservableList<News> newsList) {
        rawNews.setData(newsList.stream().toList());
        FileUtils.saveNews(file, rawNews);
    }

    /**
     * The status of the Save session checkbox in the configuration
     *
     * @return The state of the Save Session check box in the configuration
     */
    public boolean chkSaveSessionSelected() {
        return FileUtils.getConfig().isRestoreLastSession();
    }

    /**
     * Saves in the configuration whether the last session is restored when the program is opened or not.
     *
     * @param save boolean, decides whether to restore the last session or not
     */
    public void saveSession(boolean save) {
        FileUtils.getConfig().setRestoreLastSession(save);
        FileUtils.saveConfig(FileUtils.getConfig());
    }

    /**
     * Saves on cache the object RawNews to be read on the next load of the program
     */
    public void saveCache() {
         FileUtils.saveCache(rawNews);
    }
}
