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

import newsbook.parsers.*;
import newsbook.storage.*;

@SuppressWarnings("serial")
public class NewsbookServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/html");
		resp.getWriter().println("<html>");
		
		AbstractNewsParser elDiarioNews = new DiarioParser();
		
		if (elDiarioNews.fillAllSections() == 0) {
			resp.getWriter().println(
					"<h1 style='color: #00f'>Filled All Sections</h1>");
		} else {
			resp.getWriter().println(
					"<h1 style='color: #f00'>Failed to fill Sections</h1>");
			return;
		}

		NewsDataStorageManager datamanager = new NewsDataStorageManager();
		datamanager.storeNewsData(elDiarioNews);

		resp.getWriter().println("<h2 style='color: #0f0'>Stored Data!!!<h2>");
		resp.getWriter().println("</html>");
	}
}
