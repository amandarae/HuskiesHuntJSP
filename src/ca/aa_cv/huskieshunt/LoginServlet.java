package ca.aa_cv.huskieshunt;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();  
		if(request.getParameter("logout") != null){
			session.removeAttribute("authorized");
			session.removeAttribute("username");
			RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
			disp.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();  
        String username = request.getParameter("username");
        String password = request.getParameter("password").toLowerCase().trim();
        String error = "Invalid username and/or password";
        
        if (username != null && password != null) {
        	String filter = "?$filter=(username%20eq%20'"+username+"')";
        	URL url = new URL(Constants.kGetUsersUrl + filter);
        	String list = DataAccess.fetch(url);
        	Type collectionType = new TypeToken<List<Users>>(){}.getType();
    		List<Users> u = new Gson().fromJson(list, collectionType);
    		
            if(u.size() != 0 && u.get(0).getPassword().toLowerCase().trim().equals(password) ) {
                session.setAttribute("authorized", "true");
                session.setAttribute("username", username);
                session.setMaxInactiveInterval(1800);
                response.sendRedirect("players");
        	}
        	else{
        		request.setAttribute("error", error);
                RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
                disp.forward(request,response);
        	}
        		
        }else{
    		request.setAttribute("error", error);
            RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
            disp.forward(request,response);
        }
	}
}
	