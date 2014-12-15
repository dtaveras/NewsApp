package newsbook.datastoreInterface;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.Iterator;
import java.util.Vector;
import java.util.List;

import newsbook.parsers.*;
import newsbook.storage.*;

@SuppressWarnings("serial")
public class CleanUpDataServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		System.out.println("CLEAN UP TIME!! Inside Cleanup Servlet");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		//Kindless query for keys only and delete all Entities
		List<Entity> keysList= datastore.prepare(new Query().setKeysOnly()).asList(FetchOptions.Builder.withLimit(100));
		int totalDeleted = 0;
		
		while(keysList.size() > 0){
			totalDeleted += keysList.size();
			
			Iterator<Entity> entItr = keysList.iterator();
			while(entItr.hasNext()){
				Key k = entItr.next().getKey();
				datastore.delete(k);
			}
			keysList= datastore.prepare(new Query().setKeysOnly()).asList(FetchOptions.Builder.withLimit(100));
		}
		
		resp.setContentType("text/html");
		resp.getWriter().println("<html>");
		resp.getWriter().println("<h2 style='color:#0f0'> DONE DELETING !!!</h2>");
		resp.getWriter().println("<h2 style='color:#f00;'>"+"Deleted: " + totalDeleted + " Entities.</h2>");
		resp.getWriter().println("</html>");
	}
}