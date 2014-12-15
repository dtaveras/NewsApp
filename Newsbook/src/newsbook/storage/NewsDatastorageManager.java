package newsbook.storage;

import newsbook.parsers.*;
import newsbook.parsersDependencies.AbstractNewsParser;
import newsbook.parsersDependencies.NewsObject;
import newsbook.parsersDependencies.AbstractNewsParser.NewsSection;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
/**
 * 
 * @author delvistaveras
 * This class is in charge of committing the parsers results to the Datastore
 * Its going to be using the low level api. It converts NewsObj to Entities
 * In addition it will eventually have the logic to manage and update the
 * datastore when multiple fetches of news happen. 
 * Avoid storing repeated news at all cost!!!
 * Manages the datastore folder News_Sources which contains News_Site and News_Blogspot
 * each site or blogspot then contains a bunch of articles of the kind "News_Article"
 * Data Hierarchy
 * 
 * News_Sources
 * News_Site News_Blogspot
 * News_Section
 * News_Article
 */

//Add all delete support
public class NewsDatastorageManager {
	private DatastoreService datastore = null;
	private DatastoreWrapper datastoreSecure;
	
	//Data Store Wrapper that catches exceptions
	//Makes the code more sane and easier to manage
	
	private class DatastoreWrapper{
		private DatastoreService datastore;
		
		public DatastoreWrapper(DatastoreService ds){
			datastore = ds;
		}
		
		public Entity get(Key k){
			Entity entity = null;
			try {
				entity = datastore.get(k);
			} catch (EntityNotFoundException e) {
				entity = null;
				System.out.println("Error: Entity not found");
				//e.printStackTrace();
			} catch (java.lang.IllegalArgumentException e){
				entity = null;
				System.out.println("Error: Invalid key");
			} catch (DatastoreFailureException e){
				entity = null;
				System.out.println("Error: Datastore failure");
			}
			return entity;
		}
	};
	
	public static final String const_topname = "NoticiasDominicana";
	public static final String const_topkind = "News_Sources";
	
	public void initService(){
		datastore = DatastoreServiceFactory.getDatastoreService();
		datastoreSecure = new DatastoreWrapper(datastore);
	}
	
	public void storeNewsData(AbstractNewsParser newsparser){
		//Two ways of accessing the datastore, the wrapper should be used in case we want 
		//sane checked exceptions and the other is for general use.
		if(datastore == null){
			this.initService();
		}
		
		Key folderKey = KeyFactory.createKey(newsparser.getNewsType(), newsparser.getName());
		
		Entity newsfolder = new Entity(folderKey);
		newsfolder.setProperty("dateAdded", new Date());
		datastore.put(newsfolder);
		
		//Go through all the sections set each entity with the corresponding newsobject
		//and put it in the datastore
		Vector<NewsSection> newsSec = newsparser.getSections();
		int numSec = newsSec.size();
		for(int i=0; i< numSec; i++){
			NewsSection ns = newsSec.get(i);
			
			Key secKey = KeyFactory.createKey(folderKey, "News_Section", ns.getTopic());
			Entity secEntity = new Entity(secKey);
			datastore.put(secEntity);
			
			Iterator<NewsObject> nsItr = ns.newsList.iterator();
			while(nsItr.hasNext()){
				Entity articleEntity = new Entity("News_Article", secEntity.getKey());
				//set Entity with newsObject
				setEntity(articleEntity, nsItr.next());
				datastore.put(articleEntity);
			}
		}
		
	}

	//Simple function to copy newsobj data to entity
	private int setEntity(Entity entity, NewsObject newsobj){
		if(newsobj == null || entity == null) return 0;
		entity.setProperty("author", newsobj.getAuthor());
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
		entity.setProperty("dateAdded", new Date());
		return 1;
	}
	
}
