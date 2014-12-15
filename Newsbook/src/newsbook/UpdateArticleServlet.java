package newsbook;

import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Enumeration;

import newsbook.parsers.DiarioParser;
import newsbook.parsers.ElCaribeParser;
import newsbook.parsers.LoteriaNacional;
import newsbook.parsers.LoteriaNacional.LotteryType;
import newsbook.parsersDependencies.NewsObject;
import newsbook.parsersDependencies.AbstractNewsParser.NewsSection;

//A query can specify an entity kind, zero or more filters, and zero or more sort orders.

//Caution: Note that using an OR Composite filter will cause multiple queries to be executed 
//against the underlying Datastore.

//A query can not have more than one NOT_EQUAL filter, and a query that has one cannot have any 
//other inequality filters. Also Not_equal produces to queries just like the or filter

//A single query containing NOT_EQUAL or IN operators is limited to no more than 30 subqueries.
//Note: Setting an ancestor filter allows for strongly consistent queries. Queries without an 
//ancestor filter only return eventually consistent results.


@SuppressWarnings("serial")
public class UpdateArticleServlet extends HttpServlet {
	int Count = 0;
	
	/*entity.setProperty("author", newsobj.getAuthor());
	entity.setProperty("date", newsobj.getDate());
	entity.setProperty("placeOfOccurence", newsobj.getPlaceOfOccurence());
	
	if(newsobj.getPreviewText().length() > 300){
		Text textobj = new Text(newsobj.getPreviewText());
		entity.setProperty("previewText", textobj);
	}
	else{
		entity.setProperty("previewText", newsobj.getPreviewText());
	}
	
	entity.setProperty("previewTitle", newsobj.getPreviewTitle());
	//fullTextObj is assumed to always be greater than 500 chars
	Text fullTextObj = new Text(newsobj.getFullText());
	entity.setProperty("fullText", fullTextObj);
	entity.setProperty("fullTitle", newsobj.getFullTitle());
	entity.setProperty("imageUrl", newsobj.getImageUrl());
	entity.setProperty("sourceUrl", newsobj.getSourceUrl());
	entity.setProperty("dateAdded", new Date());*/
	
	private JSONObject convertEntity(Entity entt) throws JSONException{
		JSONObject webObj = new JSONObject();
		webObj.put("author", entt.getProperty("author"));
		webObj.put("date", entt.getProperty("date"));
		webObj.put("placeOfOccurence", entt.getProperty("placeOfOccurence"));
		webObj.put("previewText", entt.getProperty("previewText"));//preview text could be of type Text
		webObj.put("fullTitle", entt.getProperty("fullTitle"));
		webObj.put("imageUrl", entt.getProperty("imageUrl"));
		webObj.put("sourceUrl", entt.getProperty("sourceUrl"));
		return webObj;
	}
	
	private JSONArray convertListToJSONArray(List<Entity> entityList) throws JSONException{
	    int sz = entityList.size();
	    JSONArray webObjArray = new JSONArray();
	    
	    for(int i = 0; i < sz; i++){
	    	Entity tmpEntity = entityList.get(i);
	    	webObjArray.put(convertEntity(tmpEntity));
	    }
	    
	    return webObjArray;
	}
	
	private JSONArray convertListToArray(List<Entity> entityList){
		JSONArray webObjArray = null;
		try {
			webObjArray = convertListToJSONArray(entityList);
		} catch (JSONException e) {
			System.out.println("Failed to Convert Entire EntityList to JSONArray!!");
			e.printStackTrace();
			return null;
		}
		return webObjArray;
	}
	
	private JSONObject getJSONObject(JSONArray array, int index){
		JSONObject webObj = null;
		
		try {
			webObj = array.getJSONObject(index);
		} catch (JSONException e){
			e.printStackTrace();
			return null;
		}
		
		return webObj;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		response.setContentType("text/html");
		System.out.println("Inside update Article Servlet ....");
		
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		
		String newsType = DiarioParser.NEWSTYPE;
		String sourceName = DiarioParser.NAME;
		
		System.out.println("NewsType: "+ newsType);
		System.out.println("SourceName: " + sourceName);
		
		Key DiarioTopFolderKey = KeyFactory.createKey(newsType, sourceName);
		Key sectionKey = KeyFactory.createKey(DiarioTopFolderKey, "News_Section", "TopNews");
		
		Query allNewsQuery = new Query("News_Article").setAncestor(sectionKey);
		int numArticles = datastoreService.prepare(allNewsQuery).countEntities(FetchOptions.Builder.withLimit(100));
		List<Entity> readEntityList = datastoreService.prepare(allNewsQuery).asList(FetchOptions.Builder.withLimit(100));
		
		JSONArray webObjArray = convertListToArray(readEntityList);
		JSONObject webObj = this.getJSONObject(webObjArray, 0);
		
		System.out.println(webObj);
		System.out.println("Retreived "+numArticles+" Articles");
		try {
			webObj.write(response.getWriter());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//response.getWriter().println("<h1>"+"Read "+ numArticles +" Articles"+"</h1>");
		
		/*String news_source = request.getParameter("News_Source");
		String section = request.getParameter("News_Sections");
		//friendResponseArray.write(response.getWriter());

		System.out.println("In the servlet");
		System.out.println(request.getParameter("user"));
		System.out.println(request.getParameter("user2"));*/
		//response.getWriter().write("<h1>Yes it is working</h1>");
		
	}
}