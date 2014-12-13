package newsbook.parsers.unittests;

import static org.junit.Assert.*;
import newsbook.parsers.*;
import newsbook.parsersDependencies.AbstractNewsParser.NewsSection;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

public class ParserUnitTests {

	DiarioParser diarioParser;
	ElCaribeParser elCaribeParser;
	ElListinParser elListinParser;
	
	/*public void test() {
		fail("Not yet implemented");
	}*/

	@Before
	public void initiliazeParsers(){
		System.out.println("Prepared for testing \n");
		diarioParser = new DiarioParser();
		elCaribeParser = new ElCaribeParser();
		elListinParser = new ElListinParser();
	}
	
	private boolean checkForConnection(){
		URLConnection testConnection = null;
		try {
			URL testURL = new URL("http://www.google.com");
			testConnection = testURL.openConnection();
			testConnection.connect();
		} catch (IOException e){
			System.out.println("Exception could not connect!");
			e.printStackTrace();
			return false;
		}
		
		//Close connection just in case
		try {
			testConnection.getInputStream().close();
		} catch (IOException e) {
			System.out.println("Failed to close connection successfully");
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void testConnectionAndLinksDiario(){
		System.out.println("Connection and Links Test ....");
		
		assertTrue(checkForConnection());
		System.out.println("Connection seems to be working");
		
		assertTrue(diarioParser.checkLinks());
		System.out.println("Links all seem to be working");
	}
	
	public void testDiarioArticleParsing(){
		System.out.println("ElDiario Article Parsing Test ....");
		
		long startTime = System.nanoTime();
		assertEquals(diarioParser.fillAllSections(), 1);
		long endTime = System.nanoTime();
		long elapsedTime = (endTime-startTime)/(1000000);
		
		System.out.println("FillAllSections Worked Finished in : "+elapsedTime +"ms");
		
		Vector<NewsSection> newsSectionVect = diarioParser.getSections();
		assertEquals(newsSectionVect.get(0).newsList.size(), 2);
		assertEquals(newsSectionVect.get(1).newsList.size(), 15);
		assertEquals(newsSectionVect.get(2).newsList.size(), 15);
		
		System.out.println("Section sizes seem to be correct");
		
		System.out.print("\n");
		newsSectionVect.get(0).printAllArticlesInfo();
		newsSectionVect.get(1).printAllArticlesInfo();
		newsSectionVect.get(2).printAllArticlesInfo();
	}
	
	public void testConnectionAndLinksCaribe(){
		System.out.println("Connection and Links Test ....");
		
		assertTrue(checkForConnection());
		System.out.println("Connection seems to be working");
		
		assertTrue(elCaribeParser.checkLinks());
		System.out.println("Links all seem to be working");
	}

	public void testElCaribeParsing(){
		System.out.println("ElCaribe Article Parsing Test ....");
		long startTime = System.nanoTime();
		assertEquals(elCaribeParser.fillAllSections() , 1);
		long endTime = System.nanoTime();
		long elapsedTime = (endTime-startTime)/(1000000);
		
		System.out.println("FillAllSections Worked Finished in : "+elapsedTime +"ms");

		Vector<NewsSection> newsSectionVect = elCaribeParser.getSections();
		
		System.out.print("\n");
		newsSectionVect.get(0).printAllArticlesInfo();
		newsSectionVect.get(1).printAllArticlesInfo();
	}
	
	public void testConnectionAndLinksListin(){
		System.out.println("Connection and Links Test ....");
		
		assertTrue(checkForConnection());
		System.out.println("Connection seems to be working");
		
		assertTrue(elListinParser.checkLinks());
		System.out.println("Links all seem to be working");
	}
	
	public void testElListinParser(){
		System.out.println("Testing ElListinParser ....");
		elListinParser.fillAllSections();
		
		Vector<NewsSection> newsSectionVect = elListinParser.getSections();
		
		newsSectionVect.get(0).printAllArticlesInfo();
		newsSectionVect.get(1).printAllArticlesInfo();
	}
	
	@Test
	public void testAll(){
		testConnectionAndLinksListin();
		testElListinParser();
	}
}
