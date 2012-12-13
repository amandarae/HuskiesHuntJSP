package ca.aa_cv.huskieshunt;

import com.google.gson.annotations.SerializedName;

public class Paths {

	@SerializedName("id")
    private int id;
	
	@SerializedName("teamId")
    private int teamId;
	
	@SerializedName("name")
    private String name;
		
	public Paths(int id, int teamId, String name) {
		super();
		this.id = id;
		this.teamId = teamId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Paths [id=" + id + ", teamId=" + teamId + ", name=" + name
				+ "]";
	}
	
}
