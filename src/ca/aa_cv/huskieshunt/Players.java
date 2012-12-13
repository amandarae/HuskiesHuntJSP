package ca.aa_cv.huskieshunt;

import com.google.gson.annotations.SerializedName;

public class Players {

	@SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;
    
    @SerializedName("teamId")
    private int teamId;

    @SerializedName("createdOn")
    private String createdOn;
    
    @SerializedName("firstName")
    private String firstName;
    
    @SerializedName("lastName")
    private String lastName;
    
	public Players(int id, String username, String password, int teamId,
			String createdOn, String firstName, String lastName) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.teamId = teamId;
		this.createdOn = createdOn;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Players [id=" + id + ", username=" + username + ", password="
				+ password + ", teamId=" + teamId + ", createdOn=" + createdOn
				+ ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
   
}
