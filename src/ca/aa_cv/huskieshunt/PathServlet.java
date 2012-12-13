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
@WebServlet("/paths")
public class PathServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PathServlet() {
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
				String url = (Constants.kUpdatePathsUrl + id);
				String result = DataAccess.delete(url);
				if(result.equals("SUCCESS")){
					response.sendRedirect("paths");
				}else{
					response.sendRedirect("paths?edit=fail");
				}
			}else {
				if(request.getParameter("id") != null){
					int id = Integer.parseInt(request.getParameter("id"));
					filter = "?$filter=(id%20eq%20" + id + ")";
					page="path.jsp";
				}else{
					page="paths.jsp";	
				}
				URL url = new URL(Constants.kGetPathsUrl + filter);
		    	String list = DataAccess.fetch(url);
		    	Type collectionType = new TypeToken<List<Paths>>(){}.getType();
				List<Paths> pathList = new Gson().fromJson(list, collectionType);
				if(request.getParameter("id") != null){
					Paths pathSingle = pathList.get(0);
					request.setAttribute("path", pathSingle);
					//get checkpoints
					filter = "?$filter=(pathId%20eq%20" + pathSingle.getId() + ")&$orderby=pointOrder";
					url = new URL(Constants.kGetCheckpointsUrl + filter);
			    	list = DataAccess.fetch(url);
			    	collectionType = new TypeToken<List<Checkpoints>>(){}.getType();
					List<Checkpoints> checkpointsList = new Gson().fromJson(list, collectionType);
					request.setAttribute("checkpoints", checkpointsList);
				}else{
					request.setAttribute("paths", pathList);
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
		
		String[] params = request.getParameterValues("pathInfo");
		
		if(request.getParameter("pathId") == null){
			result = DataAccess.savePath(params, "save", "");
		}else{
			String id = request.getParameter("pathId");
			result = DataAccess.savePath(params, "update", id);
		}
		if(result.equals("SUCCESS")){
			response.sendRedirect("paths");
		}else{
			response.sendRedirect("paths?edit=fail");
		}
	}
}
