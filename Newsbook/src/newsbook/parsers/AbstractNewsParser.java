package newsbook.parsers;

import java.util.List;
import java.util.Vector;

public abstract class AbstractNewsParser {
	protected String name;
	protected String site_url;
	protected String newsType;
	protected Vector<NewsSection> sections;
	
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
	};

	public String getName(){
		return this.name;
	}
	
	public String getUrl(){
		return this.site_url;
	}
	
	public String getNewsType(){
		return this.newsType;
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
	
	//1: success, 0 section is already filled must call reset, -1 failed
	abstract public int fillSection(String sectionTopic);
	abstract public int fillSection(int sectionInd);
	
	//0: Success, anything greater than 0 is the index+1 of the last failed section
	abstract public int fillAllSections();
}
