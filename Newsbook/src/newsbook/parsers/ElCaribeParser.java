package newsbook.parsers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import newsbook.parsersDependencies.AbstractNewsParser;
import newsbook.parsersDependencies.NewsObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElCaribeParser extends AbstractNewsParser{

	public static final String const_NAME = "ElCaribeNews";
	public static String const_NEWSTYPE;
	
	public static final String NEWS_SEC0 = "Panorama";
	public static final String NEWS_SEC1 = "Deportes";
	
	//The links refer to the archives and parses three days worth of articles
	private final String NEWSEC0_LINK = "http://www.elcaribe.com.do/archivos?palabras=todas&seccion=Panorama&antiguedad=3+d%C3%ADas&multimedia=Art%C3%ADculo&buscaren=titulo";
	private final String NEWSEC1_LINK = "http://www.elcaribe.com.do/archivos?palabras=todas&seccion=Deportes&antiguedad=3+d%C3%ADas&multimedia=Art%C3%ADculo&buscaren=titulo";
	private final String const_ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";

	public ElCaribeParser(){
		super();
		//set the news type
		this.newsType = NEWSTYPE.NEWS_SITE;
		const_NEWSTYPE = this.getNewsType();
		
		this.site_url = "http://www.elcaribe.com.do/";
		this.name = const_NAME;
		this.initializeSections();
	}
	
	@Override
	public void initializeSections() {
		// TODO Auto-generated method stub
		this.sections = new Vector<NewsSection>(2);
		
		NewsSection ns = new NewsSection(NEWS_SEC0, this.NEWSEC0_LINK);
		ns.newsList = new LinkedList<NewsObject>();
		sections.add(ns);
		
		ns = new NewsSection(NEWS_SEC1, this.NEWSEC1_LINK);
		ns.newsList = new LinkedList<NewsObject>();
		sections.add(ns);
	}
	
	@Override
	public boolean checkLinks() {
		Document doc = this.getDoc(this.NEWSEC0_LINK);
		if(doc == null) return false;
		doc = this.getDoc(this.NEWSEC1_LINK);
		if(doc == null) return false;
		
		return true;
	}
	
	
	private NewsObject parseRegularNews(Element artElem, String section){
		String date = artElem.select(".fechaArchivo").first().text();
		
		//typically the 1st and 2nd link are the article links
		String articleLink = artElem.select("a").first().attr("abs:href");
		
		String articleTitle = artElem.select("div > h1").first().text();
		String articlePreviewText = artElem.select("div > p").first().text();
		Elements articleImg = artElem.select("img");
		String articleImgLink = "";
		if(!articleImg.isEmpty()){
			articleImgLink = articleImg.first().attr("abs:src");
		}
		
		Document doc = getDoc(articleLink);
		Element fullArticle = doc.select("article").first();
		
		//Try to get the image associated with the article again
		//some articles don't have pictures on the preview but have some in the full article
		if(articleImg.isEmpty()){
			articleImg = fullArticle.select("img");
			if(!articleImg.isEmpty()){
				articleImgLink = articleImg.first().attr("abs:src");
			}
		}
		
		Element articleFullText = fullArticle.select(".cuerpoNoticia").first();
		String articleFullTextString = articleFullText.text();
		
		//Extract city which is typically in the front seperated by a dash
		int dashIndex = articlePreviewText.indexOf('—');
		String cuidad = "";
		if(dashIndex != -1 || dashIndex > 20){
			cuidad = articlePreviewText.substring(0, dashIndex);
		}
		
		//Fill in NewsObject
		NewsObject newsParsedObj = new NewsObject(articleTitle, articlePreviewText,
										cuidad, section);
		newsParsedObj.setDate(date);
		newsParsedObj.setSourceUrl(articleLink);
		newsParsedObj.setFullTitle(articleTitle);
		newsParsedObj.setImageUrl(articleImgLink);
		newsParsedObj.setFullText(articleFullTextString);
		return newsParsedObj;
	}
	
	//A wrapper that catches null pointer exceptions because there maybe times when
	//a mismatched html element might cause our parser to fail
	private NewsObject parseRegularNewsWrapper(Element artElem, String section){
		NewsObject parsedNewsObj = null;
		try {
			parsedNewsObj = parseRegularNews(artElem, section);
		} catch (NullPointerException e) {
			System.out.println("ParseRegularNews Failed!!");
			return null;
		}
		return parsedNewsObj;
	}
	
	private int fillNewsObjects(NewsSection ns){
		Document doc = getDoc(ns.getLink());
		Elements articleElemList = doc.select("article");
		if(articleElemList.isEmpty()){
			return 0;
		}
		int numArticles = articleElemList.size();
		
		for(int i=0; i< numArticles; i++){
			Element articleElem = articleElemList.get(i);
			NewsObject articleNewsObj = parseRegularNewsWrapper(articleElem, ns.getTopic());
			if(articleNewsObj != null){
				ns.newsList.add(articleNewsObj);
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
