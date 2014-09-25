package newsbook;

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
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.Vector;
import java.util.List;

import newsbook.parsers.*;
import newsbook.storage.*;

@SuppressWarnings("serial")
public class NewsbookFetchServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key topKey = KeyFactory.createKey("News_Sources", "NoticiasDominicana");
		Key siteKey = KeyFactory.createKey(topKey, DiarioParser.const_newsType, DiarioParser.const_name);
		
		Query allQuery = new Query("News_Article").setAncestor(siteKey);
		List<Entity> hitList = datastore.prepare(allQuery).asList(FetchOptions.Builder.withLimit(20));
		
		resp.setContentType("text/html");
		resp.getWriter().println("<html>");
		resp.getWriter().println("<span>"+DiarioParser.const_newsType+"</span><br>");
		resp.getWriter().println("<span>"+DiarioParser.const_name+"</span><br>");
		resp.getWriter().println("<h2>"+"HitList: "+hitList.size()+"</h2>");

		Entity siteEntity;
		
		try {
			siteEntity = datastore.get(siteKey);
		} catch (EntityNotFoundException e) {
			siteEntity = null;
			e.printStackTrace();
		}
		

		if(siteEntity == null){
			resp.getWriter().println("<h1>Failed Entity is NULL</h1>");
		}
		else{
			resp.getWriter().println("<h1>Success</h1>");
			resp.getWriter().println("<span><h2>" + siteEntity.getKind()+"</h2></span>  ");
			resp.getWriter().println("<span><h2>" + siteEntity.getKey().getName() + "</h2></span><br>");
		}
		
		
		//resp.getWriter().println("<h1 style='#0f0'> " + hitList.size() + "</h1>");
		resp.getWriter().println("</html>");
	}
}