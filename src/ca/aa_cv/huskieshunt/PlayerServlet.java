package ca.aa_cv.huskieshunt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class PlayerServlet
 */
@WebServlet("/players")
public class PlayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlayerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();  
        if(session.getAttribute("authorized") != null){
			String filter = "";
			String page;
			if(request.getParameter("delete") != null){
				String id = request.getParameter("delete");
				String url = (Constants.kUpdatePlayersUrl + id);
				String result = DataAccess.delete(url);
				if(result.equals("SUCCESS")){
					response.sendRedirect("players");
				}else{
					response.sendRedirect("players?edit=fail");
				}
			}else {
				if(request.getParameter("id") != null){
					int id = Integer.parseInt(request.getParameter("id"));
					filter = "?$filter=(id%20eq%20" + id + ")";
					page="player.jsp";
				}else{
					page="players.jsp";	
				}
				URL url = new URL(Constants.kGetPlayersUrl + filter);
		    	String list = DataAccess.fetch(url);
		    	Type collectionType = new TypeToken<List<Players>>(){}.getType();
				List<Players> playerList = new Gson().fromJson(list, collectionType);
				if(request.getParameter("id") != null){
					Players playerSingle = playerList.get(0);
					url = new URL(Constants.kGetTeamsUrl);
			    	list = DataAccess.fetch(url);
			    	collectionType = new TypeToken<List<Teams>>(){}.getType();
					List<Teams> teamList = new Gson().fromJson(list, collectionType);
					request.setAttribute("teams", teamList);
					if(playerSingle.getTeamId() != 0){
						for (Teams t : teamList) {
							if(t.getId() == playerSingle.getTeamId()){
								request.setAttribute("team", t);
								break;
							}
						}
	
					}
					request.setAttribute("player", playerSingle);
				}else{
					request.setAttribute("players", playerList);
				}
		        RequestDispatcher disp = request.getRequestDispatcher(page);
		        disp.forward(request,response);
			}
        }else{
        	response.sendRedirect("index.jsp?login=fail");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		if(request.getParameter("playerUsername") != null){
			String[] players = request.getParameterValues("playerId");
			String[] playerUN = request.getParameterValues("playerUsername");
			String[] playerFN = request.getParameterValues("playerFName");
			String[] playerLN = request.getParameterValues("playerLName");
			String team = request.getParameter("teamId");
			String[] checked = request.getParameterValues("checked");
			String params[] = new String[4];
			for(int i=0;i<players.length;i++){
				if(checked[i].equals("0")){
					params[3] = "0";
				}else{
					params[3] = team;
				}
			 	params[0] = playerUN[i];
			 	params[1] = playerFN[i];
			 	params[2] = playerLN[i];
			 	DataAccess.savePlayer(params, "update", players[i]);
			}
			response.sendRedirect("teams?id=" + team);
		}else{
		String[] params = request.getParameterValues("playerInfo");
		
		if(request.getParameter("playerId") == null){
			//hash password
			try {
				String hashPass = computeHash(params[1]);
				params[1] = hashPass;
				//create a player
				result = DataAccess.savePlayer(params, "save", "");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}	
		}else{
			//update a player
			String id = request.getParameter("playerId");
			result = DataAccess.savePlayer(params, "update", id);
		}
		if(result.equals("SUCCESS")){
			response.sendRedirect("players");
		}else{
			response.sendRedirect("players?edit=fail");
		}
		}
	}

	private String computeHash(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    digest.reset();

	    byte[] byteData = digest.digest(input.getBytes("UTF-8"));
	    StringBuffer sb = new StringBuffer();

	    for (int i = 0; i < byteData.length; i++){
	      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	}
}
