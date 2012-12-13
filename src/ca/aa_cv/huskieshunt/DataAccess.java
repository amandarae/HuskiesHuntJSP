package ca.aa_cv.huskieshunt;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import net.sf.json.JSONObject;

import com.google.gson.JsonParseException;
import ca.aa_cv.huskieshunt.Constants;

public class DataAccess {

	public static String fetch(URL url){
		String jsonObject = "";
		
		try {

			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.addRequestProperty("Content-Type", "application/json");
			urlConnection.addRequestProperty("ACCEPT", "application/json");
			urlConnection.addRequestProperty("X-ZUMO-APPLICATION", Constants.kMobileServiceAppId);

			try {
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
				StringBuilder stringBuilderResponse = new StringBuilder();
				String line;
				while ((line = bufferReader.readLine()) != null) {
					stringBuilderResponse.append(line);
				}
				jsonObject = stringBuilderResponse.toString();
				
				return jsonObject;

			} catch (Exception ex) {
				return null;
			} finally {
				urlConnection.disconnect();
			}
		} catch (Exception ex) {
			return null;
		}
		
	}
	
	//SAVES
	
	public static String savePlayer(String[] params, String action, String id){
		JSONObject jsonUrl = new JSONObject();
		URL url = null;
		if(action.equals("update")){
			try {
				jsonUrl.put("username", params[0]);
				jsonUrl.put("firstName",params[1]);
				jsonUrl.put("lastName", params[2]);
				if(params[3] != null)
					jsonUrl.put("teamId",params[3]);
				url = new URL(Constants.kUpdatePlayersUrl + id);
			} catch (JsonParseException e) {
				e.getStackTrace();
			}catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}else{
			try {
				jsonUrl.put("username", params[0]);
				jsonUrl.put("password",params[1]);
				jsonUrl.put("firstName",params[2]);
				jsonUrl.put("lastName", params[3]);
				if(params[4] != "0")
					jsonUrl.put("teamId",params[4]);
				url = new URL(Constants.kGetPlayersUrl);
			} catch (JsonParseException e) {
				e.getStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		String result = saveToDb(url, jsonUrl, action, id);
		return result;
	}
	
	public static String saveTeam(String[] params, String action, String id){
		JSONObject jsonUrl = new JSONObject();
		try {
			jsonUrl.put("name", params[0]);
			jsonUrl.put("pathId",params[1]);
			jsonUrl.put("gameId", params[2]);
		} catch (JsonParseException e) {
			e.getStackTrace();
		}
		URL url = null;
		if(action.equals("update")){
			try {
				url = new URL(Constants.kUpdateTeamsUrl + id);
			}catch (MalformedURLException me) {
				me.printStackTrace();
			}
		}else{
			try {
				url = new URL(Constants.kGetTeamsUrl);
			} catch (MalformedURLException me) {
				me.printStackTrace();
			}
		}
		String result = saveToDb(url, jsonUrl, action, id);
		return result;
	}
	
	public static String saveGame(String[] params, String action, String id){
		JSONObject jsonUrl = new JSONObject();
		try {
			jsonUrl.put("name", params[0]);
			jsonUrl.put("rules",params[1]);
			jsonUrl.put("startDate", params[2]);
			jsonUrl.put("endDate", params[3]);
			jsonUrl.put("type", params[4]);
		} catch (JsonParseException e) {
			e.getStackTrace();
		}
		URL url = null;
		if(action.equals("update")){
			try {
				url = new URL(Constants.kUpdateGamesUrl + id);
			}catch (MalformedURLException me) {
				me.printStackTrace();
			}
		}else{
			try {
				url = new URL(Constants.kGetGamesUrl);
			} catch (MalformedURLException me) {
				me.printStackTrace();
			}
		}
		String result = saveToDb(url, jsonUrl, action, id);
		return result;
	}
	
	public static String savePath(String[] params, String action, String id){
		JSONObject jsonUrl = new JSONObject();
		try {
			jsonUrl.put("name", params[0]);
		} catch (JsonParseException e) {
			e.getStackTrace();
		}
		URL url = null;
		if(action.equals("update")){
			try {
				url = new URL(Constants.kUpdatePathsUrl + id);
			}catch (MalformedURLException me) {
				me.printStackTrace();
			}
		}else{
			try {
				url = new URL(Constants.kGetPathsUrl);
			} catch (MalformedURLException me) {
				me.printStackTrace();
			}
		}
		String result = saveToDb(url, jsonUrl, action, id);
		return result;
	}
	
	public static String saveCheckpoint(String[] params, String action, String id){
		JSONObject jsonUrl = new JSONObject();
		URL url = null;
		if(action.equals("updateOrder")){
			try{
			jsonUrl.put("pointOrder", params[0]);
			url = new URL(Constants.kUpdateCheckpointsUrl + id);
			String result = saveToDb(url, jsonUrl, "update", id);
			return result;
			} catch (JsonParseException e) {
				e.getStackTrace();
			}catch (MalformedURLException me) {
				me.printStackTrace();
			}
		}else{
			try {
				jsonUrl.put("name", params[0]);
				jsonUrl.put("longitude", params[1]);
				jsonUrl.put("latitude", params[2]);
				jsonUrl.put("pathId", params[3]);
				jsonUrl.put("code", params[4]);
				jsonUrl.put("challenge", params[5]);
				jsonUrl.put("challengeAnswer", params[6]);
				jsonUrl.put("clue", params[7]);
				jsonUrl.put("pointOrder", params[8]);
				
			} catch (JsonParseException e) {
				e.getStackTrace();
			}
		
			if(action.equals("update")){
				try {
					url = new URL(Constants.kUpdateCheckpointsUrl + id);
				}catch (MalformedURLException me) {
					me.printStackTrace();
				}
			}else{
				try {
					url = new URL(Constants.kGetCheckpointsUrl);
				} catch (MalformedURLException me) {
					me.printStackTrace();
				}
			}
			String result = saveToDb(url, jsonUrl, action, id);
			return result;
		}
		return null; 
	}
	
	private static String saveToDb(URL url, JSONObject jsonUrl, String action, String id){
		HttpPatch  httpPatch = null;
		HttpURLConnection urlConnection = null; 
		if(action.equals("save")){
			try {
				urlConnection = (HttpURLConnection) url.openConnection();
				urlConnection.setDoOutput(true);
				urlConnection.setDoInput(true);
				urlConnection.setRequestMethod("POST");
				urlConnection.addRequestProperty("Content-Type","application/json");
				urlConnection.addRequestProperty("ACCEPT", "application/json");
				urlConnection.addRequestProperty("X-ZUMO-APPLICATION",Constants.kMobileServiceAppId);
				// Write JSON to Server
				DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
				wr.writeBytes(jsonUrl.toString());
				wr.flush();
				wr.close();
				int response = urlConnection.getResponseCode();
				InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				StringBuilder stringBuilderResult = new StringBuilder();
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilderResult.append(line);
				}
				if (response == 201)
					return "SUCCESS";
				return "FAIL";
	
			} catch (IOException e) {
				e.printStackTrace();
				return "IOERROR";
			} finally {
				urlConnection.disconnect();
			}
		}else if(action.equals("update")){
			try {
				String uri = url.toString();
				HttpClient httpClient = new DefaultHttpClient();
				httpPatch = new HttpPatch(uri);
				httpPatch.addHeader("Content-Type", "application/json");
				httpPatch.addHeader("ACCEPT", "application/json");
				httpPatch.addHeader("X-ZUMO-APPLICATION", Constants.kMobileServiceAppId);
				StringEntity body = new StringEntity(jsonUrl.toString());
				body.setContentType("application/json");
				httpPatch.setEntity(body);
				org.apache.http.HttpResponse response = httpClient.execute(httpPatch);
				if (response.getStatusLine().getStatusCode() == 200)
					return "SUCCESS";
				else
					return response.getStatusLine().getStatusCode() + "";
			} catch (Exception ex) {
				return ex.getMessage();
			}
		}
		return null;
	}
	
	public static String delete(String url){
		try {
			String uri = url.toString();
			HttpClient httpClient = new DefaultHttpClient();
			HttpDelete httpdelete = new HttpDelete(uri);
			httpdelete.addHeader("Content-Type", "application/json");
			httpdelete.addHeader("ACCEPT", "application/json");
			httpdelete.addHeader("X-ZUMO-APPLICATION", Constants.kMobileServiceAppId);
			org.apache.http.HttpResponse response = httpClient.execute(httpdelete);
			if (response.getStatusLine().getStatusCode() == 204)
				return "SUCCESS";
			return "FAIL";
		} catch (IOException e) {
			e.printStackTrace();
			return "IOERROR";
		} 
	}
}
