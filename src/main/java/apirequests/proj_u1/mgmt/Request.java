package apirequests.proj_u1.mgmt;

import apirequests.proj_u1.model.News;
import apirequests.proj_u1.model.RawNews;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private final static String DB_NEWS = "jdbc:mysql://localhost:3306/db_news";
    private final static String DB_USERS = "jdbc:mysql://localhost:3306/db_users";
    private static String usr = "root";
    private static String pswd = "root";

    private static Connection getConnection(String db) throws SQLException {
        return DriverManager.getConnection(db, usr, pswd);
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
        rawNews.setCategory(category);
        rawNews.setSuccess(true);
        List<News> news = new ArrayList<>();

        try (Connection connection = getConnection(DB_NEWS)) {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM NEWS WHERE '" + category + "' = category");

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

    /**
     * Modifies new from database
     *
     * @param newToModify The new you want to modify
     * @param category The category of the new
     * @return true if the operation was successful or false if it wasn't
     */
    public static boolean update(News newToModify, String category) {
        boolean successfulOperation;
        String query = "UPDATE news SET author=?, content=?, `date`=?, imageUrl=?, readMoreUrl=?, `time`=?, title=?, url=? WHERE (category='" + category + "') and (id='" + newToModify.getId() + "');";

        try (Connection connection = getConnection(DB_NEWS)) {
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, newToModify.getAuthor());
            ps.setString(2, newToModify.getContent());
            ps.setString(3, newToModify.getDate());
            ps.setString(4, newToModify.getImageUrl());
            ps.setString(5, newToModify.getReadMoreUrl());
            ps.setString(6, newToModify.getTime());
            ps.setString(7, newToModify.getTitle());
            ps.setString(8, newToModify.getUrl());

            ps.executeUpdate();
            successfulOperation = true;
        } catch (SQLException error) {
            System.out.println("Error al conectar con la base de datos: " + error);
            successfulOperation = false;
        }

        return successfulOperation;
    }

    /**
     * Deletes new from database
     *
     * @param newToDelete The new to be deleted
     * @param category The category of the new to be deleted
     * @return true if operation was successful or false if it wasn't
     */
    public static boolean delete(News newToDelete, String category) {
        boolean successfulOperation;
        String query = "delete from news where id='" + newToDelete.getId() + "' and category='" + category + "';";

        try (Connection connection = getConnection(DB_NEWS)) {
            Statement stm = connection.createStatement();
            stm.execute(query);

            successfulOperation = true;
        } catch (SQLException error) {
            System.out.println("Error al conectar con la base de datos: " + error);
            Log.writeErr("Error al conectar con la base de datos: ", error);
            successfulOperation = false;
        }

        return successfulOperation;
    }

    /**
     * Insert new to database
     *
     * @param newToSave The new to be inserted
     * @param category The category of the new to be inserted
     * @return true if operation was successful or false if it wasn't
     */
    public static boolean insert(News newToSave, String category) {
        boolean successfulOperation = false;

        try (Connection connection = getConnection(DB_NEWS)) {
            Statement stm = connection.createStatement();
            stm.execute("INSERT INTO NEWS VALUES ('" + category + "', '" + newToSave.getAuthor() + "', '" + newToSave.getContent()
                    + "', '" + newToSave.getDate() + "', '" + newToSave.getId() + "', '" + newToSave.getImageUrl() +
                    "', '" + newToSave.getReadMoreUrl() + "', '" + newToSave.getTime() + "', '" + newToSave.getTitle() +
                    "', '" + newToSave.getUrl() + "')");
            successfulOperation = true;
        } catch (SQLException error) {
            System.out.println("Error al guardar la noticia en la base de datos: " + error);
        }

        return successfulOperation;
    }

    /**
     * Checks if the user is in the database
     *
     * @param username username
     * @param pswd password
     * @return true if the user is in the database or false if the user isn't
     */
    public static boolean checkLogin(String username, String pswd) {
        pswd = encryptPswd(pswd);
        boolean successfulOperation = false;

        try (Connection connection = getConnection(DB_USERS)) {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM USERS WHERE '" + username + "' = USERNAME AND PSD = '" + pswd + "'");

            if (rs.next()) {
                successfulOperation = true;
            }
        } catch (SQLException error) {
            System.out.println("Error al conectar con la base de datos: " + error);
            System.exit(0);
        }

        return successfulOperation;
    }

    /* public static boolean checkLogin(String username, String pswd) {  This method creates a user
        pswd = encryptPswd(pswd);
        boolean successfulOperation = false;

        try (Connection connection = getConnection(DB_USERS)) {
            Statement stm = connection.createStatement();
            stm.execute("INSERT INTO USERS (username, psd) VALUES ('" + username + "', '" + pswd + "')");

        } catch (SQLException error) {
            System.out.println("Error al conectar con la base de datos: " + error);
            System.exit(0);
        }

        System.out.println(pswd);

        return successfulOperation;
    } */

    /**
     * Encrypts the password before comparing it to the one saved in the database
     *
     * @param pswd The password before encription
     * @return The encripted password
     */
    private static String encryptPswd(String pswd) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, no such algorithm:\n" + e);
            System.exit(-1);
        }

        return new String (md.digest(pswd.getBytes(StandardCharsets.UTF_8)));
    }
}
