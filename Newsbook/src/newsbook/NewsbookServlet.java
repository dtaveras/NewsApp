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
public class NewsbookServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		//Set up a couple of attributes to be retreived in the JSP
	    //resp.setContentType("text/plain");
	    //resp.getWriter().println("Hello, this is a testing servlet. \n\n");
		Vector<String> lotery = new Vector<String>(3);
		lotery.add(new String("03"));
		lotery.add(new String("76"));
		lotery.add(new String("88"));
		req.setAttribute("losNumeros", lotery);
		req.setAttribute("userName", "Andres");
		req.setAttribute("msg", 18);
		req.setAttribute("news", "Dominicanos");
		req.setAttribute("option", "display");
		
		RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher("/intro.jsp");
		reqDispatcher.forward(req,resp);
	}
}
