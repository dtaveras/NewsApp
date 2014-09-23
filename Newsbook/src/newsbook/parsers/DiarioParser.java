package newsbook.parsers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.*;
import org.jsoup.select.Elements;

import newsbook.parsers.AbstractNewsParser.NewsSection;

//This class parses the site el diaro digital de la provincia de montecristi
public class DiarioParser extends AbstractNewsParser{
	// The top news list can have news relating to all sections of the news

	final String const_name = "ElDiarioNews";
	final String const_nacionales_link = "http://diariodom.com/listado/?cat=204&view=sub";
	final String const_educacion_link = "http://diariodom.com/listado/?cat=210";
	final String const_ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30";

	public static final boolean DEBUG = false;
	public static final boolean DEBUG_2 = false;

	public DiarioParser() {
		this.name = this.const_name;
		this.setUrl("http://diariodom.com");
		this.initializeSections();
	}

	@Override
	void initializeSections(){
		NewsSection topNews = new NewsSection("TopNews", "");
		topNews.newsList = new LinkedList<NewsObject>();
		
		NewsSection nacionalesNews = new NewsSection("Nacionales", this.const_educacion_link);
		nacionalesNews.newsList = new LinkedList<NewsObject>();
		
		NewsSection educacionNews = new NewsSection("Educacion", this.const_educacion_link);
		educacionNews.newsList = new LinkedList<NewsObject>();
		
		//Note topNews should always be added first
		//when parsing the other news sites it expects
		//the section with index 0 to be topNews
		sections.add(topNews);
		sections.add(nacionalesNews);
		sections.add(educacionNews);
	}
		
	private boolean isTopNewsElem(Element obj) {
		if (obj.select("p").hasClass("gk_news_intro_image")) {
			return false;
		}
		return true;
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

		if (DEBUG) {
			System.out.println(doc.html());
		}

		return doc;
	}

	// Deletes spaces and dots typically found in the preview text
	private String cleanPreviewText(String pText) {
		int firstDotIndex = pText.indexOf('.', pText.length() - 1);
		// System.out.println(firstDotIndex);
		if (firstDotIndex != -1) {
			while (pText.charAt(firstDotIndex - 1) == '.'
					|| pText.charAt(firstDotIndex - 1) == ' ') {
				firstDotIndex -= 1;
			}
		} else {
			firstDotIndex = pText.length();
		}

		return pText.substring(0, firstDotIndex);
	}

	// The best way to always find the fulltext seems like getting the
	// 'p' element with the largest length
	private boolean getFullArticleInfo(NewsObject newsObj)
			throws NullPointerException {
		String articleLink = newsObj.getSourceUrl();

		Document doc = null;
		doc = getDoc(articleLink);

		if (doc == null)
			return false;
		// System.out.println(doc.html());

		// the full text is contained in the p tag with the most characters
		Elements pList = doc.select("p");
		if (pList == null || pList.isEmpty())
			return false;
		int index = 0;
		int length = pList.get(0).html().length();
		for (int i = 1; i < pList.size(); i++) {
			String curString = pList.get(i).html();
			if (curString.length() > length) {
				index = i;
				length = curString.length();
			}
		}

		Element fullTextElem = pList.get(index);
		fullTextElem.select("b").remove(); // remove city
		newsObj.setFullText(fullTextElem.text());

		String articleFullTitle = doc.select(".headline").select(".title")
				.first().html();
		newsObj.setFullTitle(articleFullTitle);
		
		/* System.out.println(fullTextElem.text()); */

		return true;
	}

	private NewsObject parseRegularNewsElem(Elements newsElemList, int index,
			String section) {
		Element newsObj = newsElemList.get(index);

		Element titleElem = newsObj.select(".gk_news_intro_title").first();
		Element titleTag = titleElem.select("a").first();
		String titlePreview = titleTag.html();
		String articleLink = titleTag.attr("abs:href");

		Element imgElem = newsObj.select(".gk_news_intro_image").first();
		// articleLink actually appears twice so I decided to use one of them
		// in this case the link contained in the title tag
		// String articleLink = imgElem.select("a").first().attr("abs:href");
		String imgLink = imgElem.select("img").first().attr("abs:src");

		Element textElem = newsObj.select("div").get(2);
		String ciudad = textElem.select("b").first().html();
		Element textBodyElem = textElem.select("p").first();
		textBodyElem.select("b").remove();

		String textPreview = textBodyElem.html();

		Element dateInfoElem = newsObj.select(".gk_news_intro_info").first();
		String newsIntroInfo = dateInfoElem.html();
		String dateInfo = newsIntroInfo
				.substring(0, newsIntroInfo.indexOf('<'));

		NewsObject parsedNewsObj = new NewsObject(titlePreview, textPreview,
				ciudad, section);
		parsedNewsObj.setDate(dateInfo);
		parsedNewsObj.setImageUrl(imgLink);
		parsedNewsObj.setSourceUrl(articleLink);
		parsedNewsObj.isTopNews = false;
		this.getFullArticleInfo(parsedNewsObj);

		if (DEBUG_2) {
			parsedNewsObj.printAllInfo();
		}
		return parsedNewsObj;
	}

	// Wrapper around parseRegularNewsElem in order to prevent from failing if
	// we
	// encounter a null value
	private NewsObject parseRegularNews(Elements newsElemList, int index,
			String section) {
		NewsObject parsedRegularNewsObj = null;
		try {
			parsedRegularNewsObj = parseRegularNewsElem(newsElemList, index,
					section);
		} catch (NullPointerException e) {
			System.out
					.println("Failed to find expected elements while parsing!");
			return null;
		}
		return parsedRegularNewsObj;
	}

	// Always use first so that we can generate a null pointer exception if the
	// element is null
	private NewsObject parseTopNewsElem(Element newsElem, String section) {
		// if we are here it is because we have verified that it is the top
		// story

		Element titleElem = newsElem.select(".gk_news_intro_title").select("a")
				.first();
		String articleLink = titleElem.attr("abs:href");
		String previewTitle = titleElem.html();

		String news_intro_info = newsElem.select(".gk_news_intro_info").first()
				.html(); // contains date
		String date = news_intro_info
				.substring(0, news_intro_info.indexOf('<'));

		Element newsPreviewTextElem = newsElem.select("p").first(); // news text
		Element imageElem = newsPreviewTextElem.select("img").first();
		String imageLink = imageElem.attr("abs:src");

		String city = newsPreviewTextElem.select("b").first().html();

		newsPreviewTextElem.select("a").remove();
		newsPreviewTextElem.select("b").remove();
		newsPreviewTextElem.removeAttr("style");
		String newsPreviewText = newsPreviewTextElem.html();

		NewsObject newsParsedObj = new NewsObject(previewTitle,
				newsPreviewText, city, section);
		newsParsedObj.setDate(date);
		newsParsedObj.setSourceUrl(articleLink);
		newsParsedObj.setImageUrl(imageLink);
		newsParsedObj.isTopNews = true;
		this.getFullArticleInfo(newsParsedObj);

		// System.out.println(newsParsedObj);
		if (DEBUG_2) {
			newsParsedObj.printAllInfo();
		}
		return newsParsedObj;
	}

	// Wrapper around parseTopNewsObject in order to catch null pointer
	// exceptions
	// Although it is not advised to catch null pointer exceptions in this case
	// it makes sense because otherwise we would be stuck checking for null
	// and because we cant just have a #define function like is c++ I thought
	// this was the
	// best way to go
	private NewsObject parseTopNews(Element newsElem, String section) {
		NewsObject newsParsedObj = null;
		try {
			newsParsedObj = parseTopNewsElem(newsElem, section);
		} catch (NullPointerException e) {
			System.out
					.println("Failed to find expected elements while parsing!");
			return null;
		}
		return newsParsedObj;
	}

	// -1 failed to successfully extract news stories from the site
	// 0 list is already filled you need to call reset before filling up the
	// list
	// 1 successful
	public int fillNewsList(List<NewsObject> newsList2, String const_link,
			String section) {
		if (!newsList2.isEmpty())
			return 0;

		Document doc = null;
		doc = getDoc(const_link);
		if (doc == null)
			return -1;

		Elements newsList = doc.select(".gk_news_cat_wrap");
		if (newsList == null || newsList.isEmpty())
			return -1;

		if (DEBUG_2) {
			System.out.println("NumStories:" + newsList.size());
		}
		
		//By design Top News is store in the 0th Index
		List<NewsObject> topNewsList = sections.get(0).newsList;
		int stIndex = 0;
		int size = newsList.size();
		if (isTopNewsElem(newsList.first())) {
			NewsObject newsObj = parseTopNews(newsList.first(), section);
			if (newsObj != null) {
				topNewsList.add(newsObj);
				stIndex = 1;
			}
		}
		for (int i = stIndex; i < size; i++) {
			NewsObject tmpNewsObj = parseRegularNews(newsList, i, section);
			if (tmpNewsObj != null) {
				newsList2.add(tmpNewsObj);
			}
		}

		// if we were not able to successfully parse out any stories
		if (stIndex == 0 && newsList.isEmpty())
			return -1;

		return 1;

	}

	@Override
	int fillSection(String sectionTopic){
		NewsSection ns = getSection(sectionTopic);
		if(ns == null) return -1;
		return this.fillNewsList(ns.newsList, ns.getLink(), ns.getTopic());
	}

	@Override
	int fillSection(int sectionInd){
		NewsSection ns = sections.get(sectionInd);
		return this.fillSection(ns.getTopic());
	}

}
