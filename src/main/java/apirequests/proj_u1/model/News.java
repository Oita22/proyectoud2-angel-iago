package apirequests.proj_u1.model;

import apirequests.proj_u1.mgmt.Request;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * News class model
 *
 * @see java.io.Serializable
 */
public class News implements Serializable {
	/**
	 * Version number
	 */
	private static final byte serialVersionUID = 1;
	/**
	 * Array with all the categories allowed on API request
	 */
	public static String[] categories = {"All", "National", "Business", "Sports", "World", "Politics", "Technology", "Startup",
			"Entertainment", "Miscellaneous", "Hatke", "Science", "Automobile"};

	@JsonProperty("author")
	private String author;
	@JsonProperty("content")
	private String content;
	@JsonProperty("date")
	private String date;
	@JsonProperty("id")
	private String id;
	@JsonProperty("imageUrl")
	private String imageUrl;
	@JsonProperty("readMoreUrl")
	private String readMoreUrl;
	@JsonProperty("time")
	private String time;
	@JsonProperty("title")
	private String title;
	@JsonProperty("url")
	private String url;

	public String getDate(){
		return date;
	}

	public String getReadMoreUrl(){
		return readMoreUrl;
	}

	public String getAuthor(){
		return author;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public String getTitle(){
		return title;
	}

	public String getContent(){
		return content;
	}

	public String getUrl(){
		return url;
	}

	public static void setCategories(String[] categories) {
		News.categories = categories;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setReadMoreUrl(String readMoreUrl) {
		this.readMoreUrl = readMoreUrl;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void update(String category) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		setDate(dateFormat.format(date));
		setTime(timeFormat.format(date));
		Request.updateFromDatabase(this, category);
	}

	public void save(String category) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		setDate(dateFormat.format(date));
		setTime(timeFormat.format(date));
		// pendiente el resto de campos y bien los formatos
		Request.updateFromDatabase(this, category);
	}

	public void delete(String category) {
		Request.deleteFromDatabase(this, category);
	}

	@Override
	public String toString() {
		return "DataItem{" +
				"author='" + author + '\'' +
				", content='" + content + '\'' +
				", date='" + date + '\'' +
				", id='" + id + '\'' +
				", imageUrl='" + imageUrl + '\'' +
				", readMoreUrl='" + readMoreUrl + '\'' +
				", time='" + time + '\'' +
				", title='" + title + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}
