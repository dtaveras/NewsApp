package newsbook;
import java.io.IOException;

import java.util.Objects;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.*;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

//This class provides a way creating an object that can be used to update
//the lottery numbers in the Database.
/****Warning this class needs to be changed so that the information provided is always accurate
 * Right now it would fail it were used around the time the new lottery numbers came out***/
public class LoteriaNacional {
	final String const_ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";
	final String const_leidsaSite = "http://www.leidsa.com/";
	
	static public enum lotteryType
	{
		LOTERIA_NATIONAL_SORTEO_1, LOTERIA_NATIONAL_SORTEO_2,
		PEGA_MAS, QUINIELA_PALE
	};
	
	public LoteriaNacional(){
	 
	}
	
	private Document getDoc(){
    	Document doc = null;
    	try {
    		doc = Jsoup.connect(const_leidsaSite).userAgent(const_ua).get();
		} catch (IOException e) {
			System.out.println("Failed to get Document");
			e.printStackTrace();
			return null;
		}
    	return doc;
	}
	
	public Vector<String> getLotteryNumbers(lotteryType ltr){
		Document doc = getDoc();
		if (doc == null) return null;
		Elements lotBody = null;

		switch(ltr){
		case LOTERIA_NATIONAL_SORTEO_1:
			lotBody = doc.select("#loteriaNacional").first().select(".game-results");
			break;
		case LOTERIA_NATIONAL_SORTEO_2:
			lotBody = doc.select("#loteriaNacional").last().select(".game-results");
			break;
		case PEGA_MAS:
			lotBody = doc.select("#quinielaPale").select(".game-results");
			break;
		case QUINIELA_PALE:
			lotBody = doc.select("#pega123").select(".game-results");
			break;
		}
		
		Object[] numObjs = lotBody.select(".numeros-juego span").toArray();
		Vector<String> numVec = new Vector<String>(3);//Only 3 Numbers announced
		numVec.add(((Element)numObjs[0]).html());
		numVec.add(((Element)numObjs[1]).html());
		numVec.add(((Element)numObjs[2]).html());
		return numVec;
	}
	
	// This only applies to #loteriaNational since the others don't have a 1:30
	// and 9:00 time
	public String getLotteryTime(lotteryType ltr) {
		Document doc = getDoc();
		
		if (doc == null) return "";
		Elements tmpBody = null;
		
		switch (ltr) {
		case LOTERIA_NATIONAL_SORTEO_1:
			tmpBody = doc.select("#loteriaNacional").first()
					.select(".game-results");
			break;
		case LOTERIA_NATIONAL_SORTEO_2:
			tmpBody = doc.select("#loteriaNacional").last()
					.select(".game-results");
			break;
		default:
			return "";
		}
		
		return tmpBody.select(".time-juego b").html();
	}
	
	public String getLotteryDate(lotteryType ltr){
		Document doc = getDoc();
		if (doc == null) return null;
		Elements lotBody = null;
		
		switch(ltr){
		case LOTERIA_NATIONAL_SORTEO_1:
			lotBody = doc.select("#loteriaNacional").first().select(".game-results");
			break;
		case LOTERIA_NATIONAL_SORTEO_2:
			lotBody = doc.select("#loteriaNacional").last().select(".game-results");
			break;
		case PEGA_MAS:
			lotBody = doc.select("#quinielaPale").select(".game-results");
			break;
		case QUINIELA_PALE:
			lotBody = doc.select("#pega123").select(".game-results");
			break;
		}
		
		return lotBody.select(".fecha-juego").html();
	}
	
	//Checks whether datastore needs to be updated and does so if it needs to
	//Needs to be updated to work with google datastore
	public void updateLotteryDatastore(){
		
	}
	
}
