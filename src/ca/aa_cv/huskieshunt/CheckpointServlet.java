package ca.aa_cv.huskieshunt;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
@WebServlet("/checkpoints")
public class CheckpointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<Checkpoints> checkpointList = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckpointServlet() {
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
				Checkpoints cs = null;
				for (Checkpoints c : checkpointList) {
					if(c.getId() == Integer.parseInt(id)){
						cs = c;
						break;
					}
				}
				String url = (Constants.kUpdateCheckpointsUrl + id);
				String result = DataAccess.delete(url);
				if(result.equals("SUCCESS")){
					//get any checkpoints after the one deleted
					filter = "?$filter=(pathID%20eq%20" + cs.getPathId() +")%20and%20(pointOrder%20gt%20" + cs.getPointOrder() + ")";
					loadCheckpoints(filter);
					if(checkpointList.size() > 0){
						//update each checkpoint with an order # one less, to correct the order
						for (Checkpoints c : checkpointList) {
							int newOrderInt = c.getPointOrder() - 1;
							String[] newOrder = {newOrderInt + ""};
							String idStr = c.getId()  + "";
							DataAccess.saveCheckpoint(newOrder,"updateOrder",idStr);
						}
					}
					response.sendRedirect("checkpoints");
				}else{
					response.sendRedirect("checkpoints?edit=fail");
				}
			}else {
				if(request.getParameter("id") != null){
					int id = Integer.parseInt(request.getParameter("id"));
					filter = "?$filter=(id%20eq%20" + id + ")";
					page="checkpoint.jsp";
				}else{
					page="checkpoints.jsp";	
				}
				loadCheckpoints(filter);
				if(request.getParameter("id") != null){
					Checkpoints checkpointSingle = checkpointList.get(0);
					request.setAttribute("checkpoint", checkpointSingle);
				}else{
					request.setAttribute("checkpoints", checkpointList);
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
		
		String[] params = request.getParameterValues("checkpointInfo");
		
		if(request.getParameter("checkpointId") == null){
			//get last pointOrder for the Path being saved if not null
			if(!params[3].equals("")){
				URL url = new URL(Constants.kGetCheckpointsUrl + "?$filter=(pathId%20eq%20" + params[3] + ")");
		    	String list = DataAccess.fetch(url);
		    	Type collectionType = new TypeToken<List<Checkpoints>>(){}.getType();
				List<Checkpoints> cList = new Gson().fromJson(list, collectionType);
				int max = 0;
				for(Checkpoints c : cList){
					if( c.getPointOrder() > max)
						max = c.getPointOrder();
				}
				max++;
				String pointOrder = max + "";
				List<String> strList= new ArrayList<String>();
				for(String p : params){
					strList.add(p);
				}
				strList.add(pointOrder);
				String[] newParams = new String[9];
				for(int i = 0;i<strList.size();i++){
					newParams[i] = strList.get(i);
				}
				result = DataAccess.saveCheckpoint(newParams, "save", "");
			}else{
				result = DataAccess.saveCheckpoint(params, "save", "");
			}
			
			if(result.equals("SUCCESS")){
				response.sendRedirect("checkpoints");
			}else{
				response.sendRedirect("checkpoints?edit=fail");
			}
		}else if(request.getParameter("pathId") != null){
			String id = request.getParameter("checkpointId");
				//update pointOrders
				String filter = "?$filter=(pathId%20eq%20" + request.getParameter("pathId") + ")&$orderby=pointOrder";
				loadCheckpoints(filter);
				String secondId = null; String[] newOrderBefore = new String[1]; String[] newOrder = new String[1];
				if(request.getParameter("orderMovement").equals("up")){
					int newOrderInt = Integer.parseInt(request.getParameter("listIndex")) - 1;
					Checkpoints cs = checkpointList.get(newOrderInt - 1);
					secondId = cs.getId() + "";
					newOrder[0] = newOrderInt + "";
					newOrderBefore[0] = request.getParameter("listIndex");
				}
				if(request.getParameter("orderMovement").equals("down")){
					int newOrderInt = Integer.parseInt(request.getParameter("listIndex")) + 1;
					Checkpoints cs = checkpointList.get(newOrderInt - 1);
					secondId = cs.getId() + "";
					newOrder[0] = newOrderInt + "";
					newOrderBefore[0] = request.getParameter("listIndex");
				}
				String result1 = DataAccess.saveCheckpoint(newOrder,"updateOrder", id);
				String result2 = DataAccess.saveCheckpoint(newOrderBefore,"updateOrder", secondId);
				if(result1.equals("SUCCESS") && result2.equals("SUCCESS")){
					response.sendRedirect("paths?id=" + request.getParameter("pathId"));
				}else{
					response.sendRedirect("paths?edit=fail");
				}
			}
			else{
				String id = request.getParameter("checkpointId");
				result = DataAccess.saveCheckpoint(params, "update", id);
				if(result.equals("SUCCESS")){
					response.sendRedirect("checkpoints");
				}else{
					response.sendRedirect("checkpoints?edit=fail");
				}
			}
		}
	
	private void loadCheckpoints(String filter){
		URL url;
		try {
			url = new URL(Constants.kGetCheckpointsUrl + filter);
	    	String list = DataAccess.fetch(url);
	    	Type collectionType = new TypeToken<List<Checkpoints>>(){}.getType();
			checkpointList = new Gson().fromJson(list, collectionType);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
	}
}
