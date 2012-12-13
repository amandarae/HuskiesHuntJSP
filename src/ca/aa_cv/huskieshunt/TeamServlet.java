package ca.aa_cv.huskieshunt;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
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
 * Servlet implementation class TeamServlet
 */
@WebServlet("/teams")
public class TeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamServlet() {
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
				String url = (Constants.kUpdateTeamsUrl + id);
				String result = DataAccess.delete(url);
				if(result.equals("SUCCESS")){
					response.sendRedirect("teams");
				}else{
					response.sendRedirect("teams?edit=fail");
				}
			}else {
				if(request.getParameter("id") != null){
				int id = Integer.parseInt(request.getParameter("id"));
				filter = "?$filter=(id%20eq%20" + id + ")";
				page="team.jsp";
				}else{
					page="teams.jsp";	
				}
				URL url = new URL(Constants.kGetTeamsUrl + filter);
		    	String list = DataAccess.fetch(url);
		    	Type collectionType = new TypeToken<List<Teams>>(){}.getType();
				List<Teams> teamList = new Gson().fromJson(list, collectionType);
				//get players
				url = new URL(Constants.kGetPlayersUrl);
		    	list = DataAccess.fetch(url);
		    	collectionType = new TypeToken<List<Players>>(){}.getType();
				List<Players> playersList = new Gson().fromJson(list, collectionType);
				request.setAttribute("players", playersList);
				if(request.getParameter("id") != null){
					Teams teamSingle = teamList.get(0);
					request.setAttribute("team", teamSingle);
					//get team's game info
					url = new URL(Constants.kGetGamesUrl);
			    	list = DataAccess.fetch(url);
			    	collectionType = new TypeToken<List<Games>>(){}.getType();
					List<Games> gamesList = new Gson().fromJson(list, collectionType);
					request.setAttribute("games", gamesList);
					if(teamSingle.getGameId() != 0){
						for (Games g : gamesList) {
							if(g.getId() == teamSingle.getGameId()){
								request.setAttribute("game", g);
								break;
							}
						}
					}
					//get path
					url = new URL(Constants.kGetPathsUrl);
			    	list = DataAccess.fetch(url);
			    	collectionType = new TypeToken<List<Paths>>(){}.getType();
					List<Paths> pathList = new Gson().fromJson(list, collectionType);
					request.setAttribute("paths", pathList);
					if(teamSingle.getPathId() != 0){
						for (Paths p : pathList) {
							if(p.getId() == teamSingle.getPathId()){
								request.setAttribute("path", p);
								break;
							}
						}
					}
					//get progression
					filter = "?$filter=(teamId%20eq%20" + teamSingle.getId() + ")";
					url = new URL(Constants.kGetProgressionUrl + filter);
			    	list = DataAccess.fetch(url);
			    	collectionType = new TypeToken<List<Progression>>(){}.getType();
					List<Progression> progressionList = new Gson().fromJson(list, collectionType);
					if(progressionList.size() != 0)
						request.setAttribute("progression", progressionList);
					//get checkpoints for team's path
					if(teamSingle.getPathId() != 0){
						filter = "?$filter=(pathId%20eq%20" + teamSingle.getPathId() + ")";
						url = new URL(Constants.kGetCheckpointsUrl + filter);
				    	list = DataAccess.fetch(url);
				    	collectionType = new TypeToken<List<Checkpoints>>(){}.getType();
						List<Checkpoints> checkpointsList = new Gson().fromJson(list, collectionType);
						if(checkpointsList.size() != 0)
							request.setAttribute("checkpoints", checkpointsList);
					}
				}else{
					request.setAttribute("teams", teamList);
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
		
		String[] params = request.getParameterValues("teamInfo");
		
		if(request.getParameter("teamId") == null){
			result = DataAccess.saveTeam(params, "save", "");
		}else{
			String id = request.getParameter("teamId");
			result = DataAccess.saveTeam(params, "update", id);
		}
		if(result.equals("SUCCESS")){
			response.sendRedirect("teams");
		}else{
			response.sendRedirect("teams?edit=fail");
		}
	}

}
