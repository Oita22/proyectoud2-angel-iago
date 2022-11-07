package apirequests.proj_u1.mgmt;

import apirequests.proj_u1.model.Config;
import apirequests.proj_u1.model.RawNews;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * File utility class. To read and write the data.
 */
public class FileUtils {
    private static Config config;

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config) {
        FileUtils.config = config;
    }

    /**
     * Save the file with the correct extension. If the extension is not supported, the file will be saved as plain text.
     *
     * @param file Path to file
     * @param rawNews RawNews object to write
     */
    public static void saveNews(File file, RawNews rawNews) {
        String[] ext = file.toString().split("\\.");
        if (ext[ext.length-1].equals("bin")) {
            writeInBinary(rawNews, file);
        } else if (ext[ext.length-1].equals("json")) {
            writeInJson(rawNews, file);
        } else if (ext[ext.length-1].equals("xml")) {
            writeInXml(rawNews, file);
        } else {
            writeInText(rawNews, file);
        }
    }

    /**
     * Load the data from an existing file. May return null if the file cannot be read or mapped
     *
     * @param file Path to file
     * @return RawNews object, or null if went wrong
     */
    public static RawNews loadNews(File file) {
        String[] ext = file.toString().split("\\.");
        RawNews request = null;
        if (ext[ext.length-1].equals("bin")) {
            request = chargeBinaryRawNews(file);
        } else if (ext[ext.length-1].equals("json")) {
            request = chargeJSONRawNews(file);
        } else if (ext[ext.length-1].equals("xml")) {
            request = chargeXMLRawNews(file);
        }

        return request;
    }

    /**
     * Saves RawNews in plain JSON.
     *
     * @param rawNews RawNews to save in JSON.
     * @param path    Path where the file will be saved.
     */
    private static void writeInJson(RawNews rawNews, File path) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(path, rawNews);
        } catch (StreamWriteException e) {
            Log.writeErr("Stream write exception:\n", e);
        } catch (DatabindException e) {
            Log.writeErr("Databinding exception:\n", e);
        } catch (IOException e) {
            Log.writeErr("Input output exception:\n", e);
        }

    }

    /**
     * Saves RawNews in plain XML.
     *
     * @param rawNews RawNews to save in XML.
     * @param path    Path where the file will be saved.
     */
    private static void writeInXml(RawNews rawNews, File path) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(path, rawNews);
        } catch (java.io.IOException e) {
            Log.writeErr("Input output exception:\n", e);
        }
    }

    /**
     * Saves RawNews in plain binary.
     *
     * @param rawNews RawNews to save in binary.
     * @param path    Path where the file will be saved.
     */
    private static void writeInBinary(RawNews rawNews, File path) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(path))) {
            writer.writeObject(rawNews);
        } catch (FileNotFoundException e) {
            Log.writeErr("File not found exception:\n", e);
        } catch (IOException e) {
            Log.writeErr("Input output exception:\n", e);
        }
    }

    /**
     * Saves RawNews in plain text.
     *
     * @param rawNews RawNews to save in plain text.
     * @param path    Path where the file will be saved.
     */
    private static void writeInText(RawNews rawNews, File path) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(rawNews.toString());
        } catch (IOException e) {
            Log.writeErr("Input output:\n", e);
        }
    }

    /**
     * Reads and returns an Object. Supposedly should be a RawNews object but user COULD potentially return any object
     * saved in binary, so caution should be exercised and expect a scenario where that could happen.
     *
     * @param path Path to the file to be red.
     * @return RawNews IF the user selected a RawNews object in binary else returns null
     */
    private static RawNews chargeBinaryRawNews(File path) {
        RawNews rw = null;

        try (var in = new ObjectInputStream(new FileInputStream(path))) {
            while (true)
                rw = (RawNews) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }

        return rw;
    }

    /**
     * Reads and returns RawNews in JSON.
     *
     * @param path Path to the file to be red
     * @return RawNews IF the user selected a RawNews object in JSON else returns null
     */
    private static RawNews chargeJSONRawNews(File path) {
        RawNews rawNews = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            rawNews = objectMapper.readValue(path, RawNews.class);
        } catch (StreamReadException e) {
            Log.writeErr("Stream read exception:\n", e);
        } catch (DatabindException e) {
            Log.writeErr("Databind exception:\n", e);
        } catch (IOException e) {
            Log.writeErr("Input output exception:\n", e);
        }

        return rawNews;
    }

    /**
     * Reads and returns RawNews in XML
     *
     * @param path Path to the file to be read
     * @return RawNews IF the user selected a RawNews object in XML else returns null
     */
    private static RawNews chargeXMLRawNews(File path) {
        RawNews rawNews = null;

        try {
            XmlMapper xml = new XmlMapper();
            rawNews = xml.readValue(path, RawNews.class);
        } catch (StreamReadException e) {
            Log.writeErr("Stream read exception:\n", e);
        } catch (DatabindException e) {
            Log.writeErr("Databind exception:\n", e);
        } catch (IOException e) {
            Log.writeErr("Input output exception:\n", e);
        }

        return rawNews;
    }

    /**
     * Read of the cofiguration file.
     *
     * @return Config object, or create a new one if it cannot be read.
     */
    public static Config loadConfig() {
        Config config = null;

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream("src\\main\\resources\\config\\NewsAPI.config"))) {
            config = (Config) reader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            saveConfig(new Config());
        }

        return config;
    }

    /**
     * Save the Config object with the current status.
     *
     * @param config Config object to save.
     */
    public static void saveConfig(Config config) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("src\\main\\resources\\config\\NewsAPI.config"))) {
            writer.writeObject(config);
        } catch (FileNotFoundException e) {
            Log.writeErr("File not found exception:\n", e);
        } catch (IOException e) {
            Log.writeErr("Input output exception:\n", e);
        }
    }

    /**
     * Save RawNews on cache.
     *
     * @param rawNews RawNews object
     */
    public static void saveCache(RawNews rawNews) {
        saveNews(Config.tempPathCache, rawNews);
    }

    /**
     * Read RawNews from the cache.
     *
     * @return RawNews object, or null if went wrong
     */
    public static RawNews loadCache() {
        return loadNews(Config.tempPathCache);
    }

    /**
     * Read RawNews from a file
     *
     * @param file Path to the file to be read
     * @return RawNews object, or null if went wrong
     */
    private static RawNews loadNewsJson(File file) {
        RawNews request = null;
        try {
            ObjectMapper om = new ObjectMapper();
            request = om.readValue(file, RawNews.class);
            Log.writeRequest(file.getAbsolutePath());
        } catch (Exception e) {
            Log.writeErr("Invalid data to deserialize:\n", e);
        }

        return request;
    }
}
