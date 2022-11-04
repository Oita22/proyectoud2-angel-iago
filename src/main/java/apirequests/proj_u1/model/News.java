package apirequests.proj_u1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

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
