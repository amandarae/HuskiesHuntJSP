package ca.aa_cv.huskieshunt;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
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
 * Servlet implementation class AddObjectServlet
 */
@WebServlet("/add")
public class AddObjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddObjectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();  
        if(session.getAttribute("authorized") != null){
			String page = "";
			if(request.getParameter("team") != null){
				page="add_team.jsp";
				getGames(request);
				getPaths(request);
			}
			if(request.getParameter("checkpoint") != null){
				page="add_checkpoint.jsp";
				getPaths(request);
			}
			if(request.getParameter("player") != null){
				page="add_player.jsp";
				getTeams(request);
			}
			if(request.getParameter("path") != null){
				page="add_path.jsp";
			}
			if(request.getParameter("game") != null){
				page="add_game.jsp";
			}
			RequestDispatcher disp = request.getRequestDispatcher(page);
	        disp.forward(request,response);
        }else{
        	response.sendRedirect("index.jsp?login=fail");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private void getGames(HttpServletRequest request){
		URL url = null;
		try {
			url = new URL(Constants.kGetGamesUrl + "?$select=id,name");
			String list = DataAccess.fetch(url);
	    	Type collectionType = new TypeToken<List<Games>>(){}.getType();
			List<Games> gamesList = new Gson().fromJson(list, collectionType);
			request.setAttribute("games", gamesList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void getPaths(HttpServletRequest request){
		URL url = null;
		try {
			url = new URL(Constants.kGetPathsUrl + "?$select=id,name");
			String list = DataAccess.fetch(url);
			Type collectionType = new TypeToken<List<Paths>>(){}.getType();
			List<Paths> pathsList = new Gson().fromJson(list, collectionType);
			request.setAttribute("paths", pathsList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void getTeams(HttpServletRequest request){
		URL url = null;
		try {
			url = new URL(Constants.kGetTeamsUrl + "?$select=id,name");
			String list = DataAccess.fetch(url);
			Type collectionType = new TypeToken<List<Teams>>(){}.getType();
			List<Teams> teamsList = new Gson().fromJson(list, collectionType);
			request.setAttribute("teams", teamsList);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}

