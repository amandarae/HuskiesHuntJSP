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
 * Servlet implementation class GameServlet
 */
@WebServlet("/games")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
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
				String url = (Constants.kUpdateGamesUrl + id);
				String result = DataAccess.delete(url);
				if(result.equals("SUCCESS")){
					response.sendRedirect("games");
				}else{
					response.sendRedirect("games?edit=fail");
				}
			}else {
				if(request.getParameter("id") != null){
					int id = Integer.parseInt(request.getParameter("id"));
					filter = "?$filter=(id%20eq%20" + id + ")";
					page="game.jsp";
				}else{
					page="games.jsp";	
				}
				URL url = new URL(Constants.kGetGamesUrl + filter);
		    	String list = DataAccess.fetch(url);
		    	Type collectionType = new TypeToken<List<Games>>(){}.getType();
				List<Games> gameList = new Gson().fromJson(list, collectionType);
				if(request.getParameter("id") != null){
					Games gameSingle = gameList.get(0);
					request.setAttribute("game", gameSingle);
				}else{
					request.setAttribute("games", gameList);
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
		
		String[] params = request.getParameterValues("gameInfo");
		
		if(request.getParameter("gameId") == null){
			result = DataAccess.saveGame(params, "save", "");
		}else{
			String id = request.getParameter("gameId");
			result = DataAccess.saveGame(params, "update", id);
		}
		if(result.equals("SUCCESS")){
			response.sendRedirect("games");
		}else{
			response.sendRedirect("games?edit=fail");
		}
	}
}
