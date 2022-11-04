package apirequests.proj_u1.mgmt;

import apirequests.proj_u1.model.RawNews;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;

import java.net.URL;

public class Request {
    private static String dbUrl = "jdbc:mysql://localhost:3306/db_news";
    private static String usr = "";
    private static String pswd = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, usr, pswd);
    }

    /**
     * Performs the API request based on the selected category. May return null, if the request is unreadable, has
     * an unknown structure or cannot connect.
     *
     * @param category News category to make a request about
     * @return a RawNews object, or null if went wrong
     */
    public static RawNews getRawNewsList(String category) {
        RawNews rawNews = new RawNews();

        // SQL

        return rawNews;
    }
}
