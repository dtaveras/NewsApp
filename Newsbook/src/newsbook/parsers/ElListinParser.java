package newsbook.parsers;

import java.util.Calendar;

import java.util.LinkedList;
import java.util.TimeZone;
import java.util.Vector;

import newsbook.parsersDependencies.AbstractNewsParser;
import newsbook.parsersDependencies.NewsObject;
import newsbook.parsersDependencies.AbstractNewsParser.NewsSection;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElListinParser extends AbstractNewsParser{

	public static final String NAME = "ElListinNews";
	public static final String NEWSTYPE = "News_Source";
	
	private static final String NEWS_SEC0 = "Entretenimiento";
	private static final String NEWS_SEC1 = "El Deporte";
	
	private final String NEWS_SEC0_LINK = "http://www.listindiario.com/archivo?d=9&m=12&y=2014&submit2=&filter=on&s=46";
	private final String NEWS_SEC1_LINK = "http://www.listindiario.com/archivo?d=9&m=12&y=2014&submit2=&filter=on&s=23";

	public ElListinParser(){
		super();
		this.name = NAME;
		this.newsType = NEWSTYPE;
		this.site_url = "http://www.listindiario.com/";
		this.initializeSections();
	}
	
	@Override
	public void initializeSections() {
		this.sections = new Vector<NewsSection>(2);
		
		NewsSection ns = new NewsSection(NEWS_SEC0, this.NEWS_SEC0_LINK);
		ns.newsList = new LinkedList<NewsObject>();
		this.sections.add(ns);
		
		ns = new NewsSection(NEWS_SEC1, this.NEWS_SEC1_LINK);
		ns.newsList = new LinkedList<NewsObject>();
		this.sections.add(ns);
	}
	
	@Override
	public boolean checkLinks() {
		Document doc = null;
		
		doc = this.getDoc(this.NEWS_SEC0_LINK);
		if(doc == null) return false;
		
		doc = this.getDoc(this.NEWS_SEC1_LINK);
		if(doc == null) return false;
		
		return true;
	}
	
	private String updateListinUrl(String listinUrl, int day_of_month, int month, int year){
		StringBuffer listinUrlBuffer = new StringBuffer(listinUrl);
	
  		int st = listinUrlBuffer.indexOf("d=");
  		st+= 2;
  		int end = listinUrlBuffer.indexOf("&m");
  		listinUrlBuffer.delete(st, end);
  		listinUrlBuffer.insert(st, day_of_month);
  		
  		st = listinUrlBuffer.indexOf("m=");
  		st+= 2;
  		end = listinUrlBuffer.indexOf("&y");
  		listinUrlBuffer.delete(st, end);
  		listinUrlBuffer.insert(st, month);
  		
  		st = listinUrlBuffer.indexOf("y=");
  		st+=2;
  		end = listinUrlBuffer.indexOf("&submit");
  		listinUrlBuffer.delete(st, end);
  		listinUrlBuffer.insert(st, year);
  		String listinUrlString = listinUrlBuffer.toString();

  		return listinUrlString;
	}

	private NewsObject parseRegularNews(Document doc, int index, String section){
 		//Article Preview Parsing
  		String articleTitle = doc.select(".result-title > a").get(index).text();
  		String articlePreviewText = doc.select(".result-summary").get(index).text();
  		String articleLink = doc.select(".result-title > a").get(index).attr("abs:href");
  		
  		doc = this.getDoc(articleLink);
  		
  		//Full Article Parsing
  		String ArticleAuthor = "";
  		Elements authorElem = doc.select("#ArticleAuthorDiv");
  		if(!authorElem.isEmpty()){
  			ArticleAuthor = authorElem.first().text(); 
  		}
  		String ciudad = "";
  		Elements ciudadElem = doc.select("#ArticleSourceDiv");
  		if(!ciudadElem.isEmpty()){
  			ciudad = ciudadElem.first().text();
  		}
  		
  		String date = doc.select(".art_sly_1 > span").first().text();
  		Element articleImgElem = doc.select(".art_body").select("img").first();
  		String articleImgLink = "";
  		if(articleImgElem != null){
  			articleImgLink = articleImgElem.attr("abs:src");
  		}
  		
  		String articleFullText = doc.select("#ArticleBody").first().text();

  		NewsObject parsedNewsObject = new NewsObject(articleTitle, articlePreviewText,
  											ciudad, section, ArticleAuthor);

  		parsedNewsObject.setDate(date);
  		parsedNewsObject.setFullTitle(articleTitle);
  		parsedNewsObject.setFullText(articleFullText);
  		parsedNewsObject.setSourceUrl(articleLink);
  		parsedNewsObject.setImageUrl(articleImgLink);
  		return parsedNewsObject;
	}
	
	private NewsObject parseRegularNewsWrapper(Document doc, int index, String section){
		NewsObject parsedNewsObj = null;
		/*try {
			parsedNewsObj = parseRegularNews(doc, index, section);
		} catch (NullPointerException e) {
			System.out.println("ParseRegularNews Failed!!");
			return null;
		}*/
		parsedNewsObj = parseRegularNews(doc, index, section);

		return parsedNewsObj;
	}
	
	private int fillNewsObjects(NewsSection ns){
		TimeZone drTZ = TimeZone.getTimeZone("America/Santo_Domingo");
  		Calendar today = new java.util.GregorianCalendar(drTZ);
  		
  		int day_of_month = today.get(Calendar.DAY_OF_MONTH) - 1;
  		int month = today.get(Calendar.MONTH)+1; //starts at 0
  		int year = today.get(Calendar.YEAR);
  		System.out.println("Day: " + day_of_month + " Month: " + month + " Year: " + year);
  		
  		String updatedLink = updateListinUrl(ns.getLink(), day_of_month,
  											month, year);
  		System.out.println(updatedLink);
  		Document doc = this.getDoc(updatedLink);
  		int numArticles = doc.select(".result-summary").size();
  		
  		if(numArticles == 0) return 0;
  		
  		for(int i=0; i< numArticles; i++){
  			NewsObject parsedNewsObj = parseRegularNewsWrapper(doc, i, ns.getTopic());
  			if(parsedNewsObj != null){
  				ns.newsList.add(parsedNewsObj);
  			}
  		}
  		
  		if(ns.newsList.size() == 0) return 0;
  		
  		return 1;
	}
	
	
	@Override
	public int fillSection(String sectionTopic) {
		NewsSection ns = getSection(sectionTopic);
		if(ns == null) return 0;
		return fillNewsObjects(ns);
	}

	@Override
	public int fillSection(int sectionInd) {
		if(sectionInd < 0 || (sectionInd > this.sections.size()-1) ){ 
			return 0;
		}
		NewsSection ns = this.sections.get(sectionInd);
		if(ns == null) return 0;
		return fillNewsObjects(ns);
	}

	@Override
	public int fillAllSections() {
		int numSections = this.sections.size();
		for(int i=0; i< numSections; i++){
			int res = fillSection(i);
			if(res == 0){
				return res;
			}
		}
		
		return 1;
	}


}
