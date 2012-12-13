package ca.aa_cv.huskieshunt;

import com.google.gson.annotations.SerializedName;

public class Progression {

	@SerializedName("id")
    private int id;
	
	@SerializedName("teamId")
    private int teamId;
	
	@SerializedName("checkpointId")
    private int checkpointId;
	
	@SerializedName("completed")
    private boolean completed;
	
	@SerializedName("startDate")
    private String startDate;
	
	@SerializedName("endDate")
    private String endDate;
		
	@SerializedName("clueSave")
    private String clueSave;

	public Progression(int id, int teamId, int checkpointId, boolean completed,
			String startDate, String endDate, String clueSave) {
		super();
		this.id = id;
		this.teamId = teamId;
		this.checkpointId = checkpointId;
		this.completed = completed;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clueSave = clueSave;
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

	public int getCheckpointId() {
		return checkpointId;
	}

	public void setCheckpointId(int checkpointId) {
		this.checkpointId = checkpointId;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getClueSave() {
		return clueSave;
	}

	public void setClueSave(String clueSave) {
		this.clueSave = clueSave;
	}

	@Override
	public String toString() {
		return "Progression [id=" + id + ", teamId=" + teamId
				+ ", checkpointId=" + checkpointId + ", completed=" + completed
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", clueSave=" + clueSave + "]";
	}
		
	
}
