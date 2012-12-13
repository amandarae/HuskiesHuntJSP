package ca.aa_cv.huskieshunt;

import com.google.gson.annotations.SerializedName;

public class Teams {

	@SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    
    @SerializedName("pathId")
    private int pathId;
    
    @SerializedName("gameId")
    private int gameId;

    @SerializedName("createdOn")
    private String createdOn;

	public Teams(int id, String name, int pathId, int gameId, String createdOn) {
		super();
		this.id = id;
		this.name = name;
		this.pathId = pathId;
		this.gameId = gameId;
		this.createdOn = createdOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	@Override
	public String toString() {
		return "Teams [id=" + id + ", name=" + name + ", pathId=" + pathId
				+ ", gameId=" + gameId + ", createdOn=" + createdOn + "]";
	}
	

}
