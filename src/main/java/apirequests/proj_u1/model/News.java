package apirequests.proj_u1.model;

import apirequests.proj_u1.mgmt.Request;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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


	public void updateData(String[] newData) {
		// String title, String author, String content, String imageUrl, String url

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd LLL yyyy, E");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm,E");


		this.setTitle(newData[0]);
		this.setAuthor(newData[1]);
		this.setContent(newData[2]);
		this.setImageUrl(newData[3]);
		this.setUrl(newData[4]);


		this.setReadMoreUrl(url);
		this.setDate(dateFormat.format(date));
		this.setTime(timeFormat.format(date));
		if (id == null || id.equals(""))
			id = generateId();
	}

	public void update(String category) {
		Request.update(this, category);
	}

	public void save(String category) {
		Request.insert(this, category);
	}

	public void delete(String category) {
		Request.delete(this, category);
	}

	private String generateId() {
		String aux = "";
		Random rd = new Random();

		for (int i = 0; i < 38; i++)
			aux += Integer.toHexString(rd.nextInt(16));

		return aux;
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
