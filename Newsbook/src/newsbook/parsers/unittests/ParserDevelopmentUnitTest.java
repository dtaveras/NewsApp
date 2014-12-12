package newsbook.parsers.unittests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.*;
import org.jsoup.select.Elements;

public class ParserDevelopmentUnitTest {

	private final String const_ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";
	private final String const_montecristialdia = "http://montecristialdia.blogspot.com/";
	
	@Before
	public void setUp() throws Exception {
	}


	private Document getDoc(String site) {
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
	

	//@Test
	public void simpleParsing(){
		Document doc = getDoc(const_montecristialdia);
		Elements articleList = doc.select(".post.hentry");
		
		Element article = articleList.get(2);
		Element title = article.select(".post-title.entry-title").first();
		String articleLink = title.select("a").first().attr("abs:href");
		String titleString = title.select("a").first().html();
		
		Element body = article.select(".post-body.entry-content").first();
		String imageLink = body.select("img").first().attr("abs:src");
		
		String cuidad = "";
		if(!body.select("b").isEmpty()){
			cuidad = body.select("b").first().html();
			body.select("b").first().remove(); //remove cuidad
		}
		String bodyText = body.text();
		
		System.out.println("\n----------------------------------");
		System.out.println(titleString);
		System.out.println(cuidad);
		System.out.println(articleLink);
		System.out.println(imageLink);
		System.out.println(bodyText);
		System.out.println("----------------------------------");
		
		//System.out.println(articleList);
		//System.out.println(doc.html());
	}
	
	//@Test
	public void listinParsing(){
		Document doc = getDoc("http://www.listindiario.com/archivo?d=10&m=12&y=2014&submit2=&filter=on&s=46");
		System.out.println(doc.html());
	}
	
	public void elCaribeParseArticle(Elements articleList, int index){
		Element article = articleList.get(index);
		
		//Parse Regular Article
		String date = article.select(".fechaArchivo").first().text();
		
		//typically the 1st and 2nd link are the article links
		String articleLink = article.select("a").first().attr("abs:href");
		
		String articleTitle = article.select("div > h1").first().text();
		String articlePreviewText = article.select("div > p").first().text();
		Elements articleImg = article.select("img");
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
		
		System.out.println("\n------------------------------------------");
		System.out.println(articleTitle);
		System.out.println(date);
		System.out.println(articleLink);
		System.out.println(articleImgLink);
		System.out.println(articlePreviewText);
		System.out.println(articleFullTextString);
		System.out.println("-------------------------------------------");
	}
	
	//@Test
	public void elCaribeParsing(){
		Document doc = getDoc("http://www.elcaribe.com.do/archivos?palabras=todas&seccion=Panorama&antiguedad=24+horas&multimedia=Art%C3%ADculo&buscaren=titulo");
		assertEquals(doc.select("article").size(), 12);
		
		Elements articleList = doc.select("article");
		int numArticles = articleList.size();
		
		for(int i=0; i< numArticles; i++){
			this.elCaribeParseArticle(articleList, i);
		}
		
	}
	
	@Test
	public void elListinParsing(){
		String listinUrl = "http://www.listindiario.com/archivo?d=12&m=12&y=2014&submit2=&filter=on&s=46";
		
		StringBuffer listinUrlBuffer = new StringBuffer(listinUrl);
		TimeZone drTZ = TimeZone.getTimeZone("America/Santo_Domingo");
  		Calendar today = new java.util.GregorianCalendar(drTZ);
  		//today.getTime()
  		int st = listinUrlBuffer.indexOf("d=");
  		st+= 2;
  		int end = listinUrlBuffer.indexOf("&m");
  		listinUrlBuffer.delete(st, end);
  		listinUrlBuffer.insert(st, today.get(Calendar.DAY_OF_MONTH)-1);
  		
  		st = listinUrlBuffer.indexOf("m=");
  		st+= 2;
  		end = listinUrlBuffer.indexOf("&y");
  		listinUrlBuffer.delete(st, end);
  		listinUrlBuffer.insert(st, (today.get(Calendar.MONTH)+1));
  		
  		st = listinUrlBuffer.indexOf("y=");
  		st+=2;
  		end = listinUrlBuffer.indexOf("&submit");
  		listinUrlBuffer.delete(st, end);
  		listinUrlBuffer.insert(st, today.get(Calendar.YEAR));
  		listinUrl = listinUrlBuffer.toString();
  		
  		System.out.println(listinUrl);
  		Document doc = this.getDoc(listinUrl);
  		int index = 1;
  		
  		//Article Preview Parsing
  		String articleTitle = doc.select(".result-title > a").get(index).text();
  		String articlePreviewText = doc.select(".result-summary").get(index).text();
  		String articleLink = doc.select(".result-title > a").get(index).attr("abs:href");
  		System.out.println(articleTitle);
  		System.out.println(articlePreviewText);
  		System.out.println(articleLink);
  		articleLink = "http://www.listindiario.com/la-republica/2014/12/9/348489/Director-de-CONAVIHSIDA-llama-a-eliminar-discriminacion-en-servicios-de";
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
  		
  		System.out.println(ArticleAuthor);
  		System.out.println(ciudad);
  		
  		String date = doc.select(".art_sly_1 > span").first().text();
  		Element articleImgElem = doc.select(".art_body").select("img").first();
  		String articleImgLink = articleImgElem.attr("abs:src");
  		String articleFullText = doc.select("#ArticleBody").first().text();
  		System.out.println(date);
  		System.out.println(articleImgLink);
  		System.out.println(articleFullText);
  		//System.out.println(doc.html());
  		
  		
	}
	
	
	
	
	
	
	
	
}
