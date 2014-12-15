package newsbook.datastoreInterface;

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
import newsbook.parsersDependencies.AbstractNewsParser;
import newsbook.storage.*;



@SuppressWarnings("serial")
public class NewsbookStoreNewsServlet extends HttpServlet {
	
	private void printAndStoreResult(HttpServletResponse response, AbstractNewsParser newsparser, 
						NewsDatastorageManager storageManager, int res) throws IOException{
		
		response.getWriter().println("<br>");
		response.getWriter().println("<div style='border-style: solid'>");
		response.getWriter().println("<h1 style='text-decoration: underline'>"+ newsparser.getName() +"</h1>");
		
		if(res == 1){
			response.getWriter().println("<h1>All Sections Filled.</h1>");
			storageManager.storeNewsData(newsparser);
		}
		else {
			response.getWriter().println("<h1>Failed to Fill All Sections!!</h1>");
		}
		response.getWriter().println("</div>");
		response.getWriter().println("<br>");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/html");
		resp.getWriter().println("<html>");
		
		resp.getWriter().println("<h1 style='color: #00A; text-decoration: underline; text-align:center; border-style:solid'>Storing News To Google Datastore</h1>");
		System.out.println("Executing NewsbookStoreNewsServlet ...");
		
		NewsDatastorageManager storageManager = new NewsDatastorageManager();
		AbstractNewsParser newsParser;
		int res;
		
		newsParser = new DiarioParser();
		res = newsParser.fillAllSections();
		this.printAndStoreResult(resp, newsParser, storageManager, res);
		System.out.println("ElDiario NewsObjects finished adding.");
		
		newsParser = new ElCaribeParser();
		res = newsParser.fillAllSections();
		this.printAndStoreResult(resp, newsParser, storageManager, res);
		System.out.println("ElCaribe NewsObjects finished adding.");
		
		newsParser = new ElListinParser();
		res = newsParser.fillAllSections();
		this.printAndStoreResult(resp, newsParser, storageManager, res);
		System.out.println("ElListin NewsObjects finished adding.");
		
		resp.getWriter().println("</html>");
	}
}
