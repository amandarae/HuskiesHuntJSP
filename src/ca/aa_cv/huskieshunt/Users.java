package ca.aa_cv.huskieshunt;

import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Users {
	
	@SerializedName("id")
    private int id;
	
	@SerializedName("username")
    private String username;
	
	@SerializedName("password")
    private String password;
	
	@SerializedName("createdOn")
    private String createdOn;

	public Users(int id, String username, String password, String createdOn) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.createdOn = createdOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password="
				+ password + ", createdOn=" + createdOn + "]";
	}
}
