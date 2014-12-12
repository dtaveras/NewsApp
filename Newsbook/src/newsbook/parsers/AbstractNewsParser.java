package newsbook.parsers;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public abstract class AbstractNewsParser {
	protected String name;
	protected String site_url;
	protected NEWSTYPE newsType;
	protected Vector<NewsSection> sections;
	protected final String const_ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";

	//The newstype enum and string should be in corresponding order
	protected enum NEWSTYPE {
		NEWS_SITE, NEWS_BLOGSPOT
	}
	
	//This was created because I wanted subclasses to use an Enum to select the type
	//however I wanted that when I returned the type that it should be of type string
	private EnumMap<NEWSTYPE, String> newsTypeMap;
	
	AbstractNewsParser(){
		newsTypeMap = new EnumMap<NEWSTYPE, String>(NEWSTYPE.class);
		newsTypeMap.put(NEWSTYPE.NEWS_SITE, "News_Site");
		newsTypeMap.put(NEWSTYPE.NEWS_BLOGSPOT, "News_Blogspot");
	}
	
	//NewsSection Class and really the heart of the parser
	public class NewsSection{
		private String topic;		
		private final String news_link;
		public List<NewsObject> newsList;
		
		public NewsSection(String topic, String news_link){
			this.news_link = news_link;
			this.topic = topic;
		}
		
		public String getLink(){
			return this.news_link;
		}
		
		public String getTopic(){
			return this.topic;
		}
		
		public void printAllArticlesInfo(){
			int size = newsList.size();
			for(int i=0; i< size; i++){
				System.out.println("Article Number: " + i);
				newsList.get(i).printAllInfo();
			}
		}
	};
	//----------------------------------------

	protected Document getDoc(String site) {
		Document doc = null;
		try {
			doc = Jsoup.connect(site).userAgent(const_ua).get();
		} catch (IOException e) {
			System.out.println("Failed to get Document");
			e.printStackTrace();
			return null;
		}

		return doc;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getUrl(){
		return this.site_url;
	}
	
	public String getNewsType(){
		return newsTypeMap.get(newsType);
	}
	
	public Vector<NewsSection> getSections(){
		return this.sections;
	}
	
	//Get specific section
	public NewsSection getSection(String sectionTopic){
		int num_sections = sections.size();
		for(int i=0; i< num_sections; i++){
			NewsSection ns = sections.get(i);
			if(sectionTopic.equals(ns.topic)){
				return ns;
			}
		}
		return null;
	}
	
	//Get Section names is to be used in conjunction with getSection
	public Vector<String> getSectionNames(){
		int num_sections = sections.size();
		Vector<String> nameVect = new Vector<String>(num_sections);
		for(int i=0; i< num_sections; i++){
			nameVect.add(sections.get(i).getTopic());
		}
		return nameVect;
	}
	
	//This function should intiliaze all the sections of the news parser
	abstract public void initializeSections();
	
	abstract public int fillSection(String sectionTopic);
	abstract public int fillSection(int sectionInd);
	
	abstract public int fillAllSections();
	
	//true: all links worked 
	//false: some linked failed
	//checks whether we are able to connect to the links used for parsing
	abstract boolean checkLinks();
}
