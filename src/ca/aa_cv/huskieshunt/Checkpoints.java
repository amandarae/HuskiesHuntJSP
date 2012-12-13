package ca.aa_cv.huskieshunt;

import com.google.gson.annotations.SerializedName;

public class Checkpoints {

	@SerializedName("id")
    private int id;
	
	@SerializedName("pointOrder")
    private int pointOrder;
	
	@SerializedName("pathId")
	 private int pathId;
	
	@SerializedName("longitude")
    private float longitude;
	
	@SerializedName("latitude")
    private float latitude;
	
	@SerializedName("code")
    private String code;
	
	@SerializedName("challenge")
    private String challenge;
	
	@SerializedName("challengeAnswer")
    private String challengeAnswer;
	
	@SerializedName("clue")
    private String clue;
	
	@SerializedName("name")
    private String name;
	
	public Checkpoints(int id, int pointOrder, int pathId, float longitude,
			float latitude, String code, String challenge,
			String challengeAnswer, String clue, String name) {
		super();
		this.id = id;
		this.pointOrder = pointOrder;
		this.pathId = pathId;
		this.longitude = longitude;
		this.latitude = latitude;
		this.code = code;
		this.challenge = challenge;
		this.challengeAnswer = challengeAnswer;
		this.clue = clue;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPointOrder() {
		return pointOrder;
	}

	public void setPointOrder(int order) {
		this.pointOrder = order;
	}

	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChallenge() {
		return challenge;
	}

	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}

	public String getChallengeAnswer() {
		return challengeAnswer;
	}

	public void setChallengeAnswer(String challengeAnswer) {
		this.challengeAnswer = challengeAnswer;
	}

	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Checkpoints [id=" + id + ", pointOrder=" + pointOrder + ", pathId="
				+ pathId + ", longitude=" + longitude + ", latitude="
				+ latitude + ", code=" + code + ", challenge=" + challenge
				+ ", challengeAnswer=" + challengeAnswer + ", clue=" + clue
				+ ", name=" + name + "]";
	}

}
