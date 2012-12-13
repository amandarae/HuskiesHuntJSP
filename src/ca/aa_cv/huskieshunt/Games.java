package ca.aa_cv.huskieshunt;

import com.google.gson.annotations.SerializedName;

public class Games {

	@SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    
    @SerializedName("rules")
    private String rules;

    @SerializedName("startDate")
    private String startDate;
    
    @SerializedName("endDate")
    private String endDate;
    
    @SerializedName("createdOn")
    private String createdOn;

    @SerializedName("type")
    private int type;
    
	public Games(int id, String name, String rules, String startDate,
			String endDate, String createdOn, int type) {
		this.id = id;
		this.name = name;
		this.rules = rules;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdOn = createdOn;
		this.type = type;
	}
	

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
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

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
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

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", name=" + name + ", rules=" + rules
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", createdOn=" + createdOn + "gameType=" + type + "]";
	}
}