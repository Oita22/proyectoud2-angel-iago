package apirequests.proj_u1.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Class model for API request mapping
 *
 * @see java.io.Serializable
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonPropertyOrder({"category", "data", "success"})
public class RawNews implements Serializable {
	/**
	 * Version number
	 */
	private static final byte serialVersionUID = 1;
	@JsonProperty("category")
	private String category;
	@JsonProperty("data")
	private List<News> data;
	@JsonProperty("success")
	private boolean success;

	public String getCategory(){
		return category;
	}

	public List<News> getNews(){
		return data;
	}

	public void setData(List<News> data) {
		this.data = data;
	}

	public boolean isSuccess(){
		return success;
	}

	@Override
	public String toString() {
		return "Response{" +
				"category='" + category + '\'' +
				", data=" + data +
				", success=" + success +
				'}';
	}
}