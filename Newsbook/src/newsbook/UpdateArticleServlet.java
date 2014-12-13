package newsbook;

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

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Enumeration;

import newsbook.parsers.DiarioParser;
import newsbook.parsers.LoteriaNacional;
import newsbook.parsers.LoteriaNacional.LotteryType;
import newsbook.parsersDependencies.NewsObject;
import newsbook.parsersDependencies.AbstractNewsParser.NewsSection;

@SuppressWarnings("serial")
public class UpdateArticleServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/plain");
		//We create a hashmap mapping sources to sections so that it is easy to test for valid
		//and invalid sources and sections
		Map<String, Vector<String>> sourceAndSectMap = new HashMap<String, Vector<String>>();
		Vector<String> diarioSect = new Vector<String>();
		diarioSect.add("TopNews");
		diarioSect.add("Nacionales");
		diarioSect.add("Educacion");
		sourceAndSectMap.put("ElDiarioNews", diarioSect);
		
		String news_source = request.getParameter("News_Source");
		String section = request.getParameter("News_Sections");
		
		//Check for correct source and section
		if(!sourceAndSectMap.containsKey(news_source)){
			response.getWriter().write("Failed: Incorrect Source");
			return;
		}
		else{
			Vector<String> sect = (Vector<String>)sourceAndSectMap.get(news_source);
			if(!sect.contains(section)){
				response.getWriter().write("Failed: Incorrect Section");
				return;
			}
		}
		
		System.out.println("In the servlet");
		System.out.println(request.getParameter("user"));
		System.out.println(request.getParameter("user2"));
		response.getWriter().write("Yes it is working");
	}
}