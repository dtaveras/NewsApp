package newsbook;

//NewsObject is designed mainly to allow easy access to the information about a particular article
//parsed from a particular news website
public class NewsObject {
	boolean isTopNews;
	private String previewTitle;
	private String previewText; //Contains the body of the news story
	private String placeOfOccurence;
	private String topicOrSection; //what type of news story is it sports or noticias or etc.
	private String author;
	
	private String date;
	private String sourceUrl; //Url from which we parsed this news Story
	private String imageUrl;

	//These two are filled in by the object itself
	private String fullTitle;
	private String fullText;
	
	public NewsObject(String previewTitle, String previewText, String placeOfOccurence, String topicOrSection, String author){
		this.previewTitle = previewTitle;
		this.previewText = previewText;
		this.placeOfOccurence = placeOfOccurence;
		this.topicOrSection = topicOrSection;
		this.author = author;
		this.date = "";
		this.sourceUrl = "";
		this.imageUrl = ""; 
	}
	
	public NewsObject(String previewTitle, String previewText, String placeOfOccurence, String topicOrSection){
		this(previewTitle, previewText, placeOfOccurence, topicOrSection, "");
	}
	
	public void printAllInfo(){
		System.out.println("\n-----------------------------------------------");
		if(isTopNews){
			System.out.println("TOP NEWS");
		}
		else{
			System.out.println("REGULAR NEWS");
		}
		
		System.out.println("Titulo: " + this.previewTitle);
		System.out.println("Text: " + this.previewText);
		System.out.println("Ciudad: " + this.placeOfOccurence);
		System.out.println("Section: " + this.topicOrSection);
		System.out.println("Date: " + this.date);
		System.out.println("SourceUrl: " + this.sourceUrl);
		System.out.println("ImageUrl: " + this.imageUrl);
		System.out.println("------------------------------------------------");
	}
	
	//prints the Object
	public String toString(){
		String out = "\n-----------------------------------------------\n";
		out += "Titulo: "+ previewTitle + "\n";
		out += "------------------------------------------------\n";
		out += "Text: "+ previewText + "\n";
		out += "------------------------------------------------\n";
		out += "Ciudad: "+placeOfOccurence + "\n";
		out += "------------------------------------------------\n";
		out += "Section: "+topicOrSection + "\n";
		out += "-----------------------------------------------\n";
		return out;
	}
	
	public boolean hasImage(){
		if(imageUrl.equals("")){
			return false;
		}
		return true;
	}

	public void setFullText(String fullText){
		this.fullText = fullText;
	}
	
	public void setFullTitle(String fullTitle){
		this.fullTitle = fullTitle;
	}
	public void setSourceUrl(String sourceUrl){
		this.sourceUrl = sourceUrl;
	}
	
	//int year, int month, int dayInMonth
	public void setDate(String date){
		this.date = date;
	}
	
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return this.imageUrl;
	}
	
	public String getPlaceOfOccurence(){
		return this.placeOfOccurence;
	}
	
	public String getTopicOrSection(){
		return this.topicOrSection;
	}
	
	public String getSourceUrl(){
		return this.sourceUrl;
	}
	
	public String getPreviewTitle(){
		return this.previewTitle;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getPreviewText(){
		return previewText;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
}
