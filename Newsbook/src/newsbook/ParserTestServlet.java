package newsbook;

import java.io.IOException;


import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.Vector;

@SuppressWarnings("serial")
public class ParserTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setAttribute("title", "News Jsp Tester");
		
		RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/intro.jsp");
		reqDispatcher.forward(req,resp);		
	}
}