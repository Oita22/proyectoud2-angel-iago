package apirequests.proj_u1.mgmt;

import apirequests.proj_u1.model.News;
import apirequests.proj_u1.model.RawNews;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private static String dbUrl = "jdbc:mysql://localhost:3306/db_news";
    private static String usr = "root";
    private static String pswd = "root";

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
        ResultSet rs;
        rawNews.setCategory(category);
        rawNews.setSuccess(true);
        List<News> news = new ArrayList<>();

        try (Connection connection = getConnection()) {
            Statement stm = connection.createStatement();
            rs = stm.executeQuery("SELECT * FROM NEWS WHERE '" + category + "' = category");

            while (rs.next()) {
                News noticia = new News();
                noticia.setDate(rs.getString("date"));
                noticia.setAuthor(rs.getString("author"));
                noticia.setContent(rs.getString("content"));
                noticia.setId(rs.getString("id"));
                noticia.setTime(rs.getString("time"));
                noticia.setUrl(rs.getString("url"));
                noticia.setTitle(rs.getString("title"));
                noticia.setReadMoreUrl(rs.getString("readMoreUrl"));
                noticia.setImageUrl(rs.getString("imageUrl"));

                news.add(noticia);
            }

        } catch (SQLException error) {
            System.out.println("Error al conectar con la base de datos: " + error);
            rawNews.setSuccess(false);
            System.exit(0);
        }

        rawNews.setData(news);

        return rawNews;
    }
}
