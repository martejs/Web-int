package Stemming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class htmlstripper {
	
	

	private File [] files;
	private String url;
	private String orginalFile = "";
	private String pBoundary = "" ;
	private String sBoundary = "";
	private XMLParse xmlP ;

	/**
	 * Konstruktoren som tar inn en url og som henter inn parametre fra konfigurasjonsfila
	 * @param url String
	 * @param sBoundary String
	 * @param pBoundary String
	 * @param allsmall String
	 * @param xmlP XMLParser
	 */
	public htmlstripper(String url, String sBoundary, String pBoundary, String allsmall, XMLParse xmlP){
		this.url = url;
		this.pBoundary = pBoundary;
		this.sBoundary = sBoundary;
		this.xmlP = xmlP;
		try {
			orginalFile = findSource(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		parseFile(orginalFile, "");
	}

	/**
	 * Konstruktoren som tar inn html fil fra fil
	 * @param filename String
	 * @param rootname String
	 * @param url String
	 */
	public htmlstripper(String filename,   XMLParse xmlP, String sBoundary, String pBoundary, String name){
		File file = new File(filename);
		this.url = file.getName();
		this.xmlP = xmlP;
		this.pBoundary = pBoundary;
		this.sBoundary = sBoundary;
		if(file.isDirectory()){//sjekker om det sendes inn en katalog
			System.out.println("Filen er en directory");
			files = file.listFiles();//henter filene som er i den katalogen
			for(int i = 0; i<files.length; i++){
				new htmlstripper(files[i].getAbsolutePath(),  xmlP, sBoundary, pBoundary, file.getName());
			}
		}
		else{//hvis ikke en katalog
			System.out.println("Filen er en fil " +file.getName());
			try {
				orginalFile = readFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parseFile(orginalFile, name);
		}

	}

	/**Metode som henter ut metadata fra fil
	 * Mulighet for utvidelse av system
	 */
	public ArrayList getMeta(String file){
		ArrayList meta = new ArrayList();
		int start = file.indexOf("meta name=\"keywords\" content=\"");
		int slutt = file.indexOf("\">", start);
		String keyWords = file.substring(start+30,slutt);
		StringTokenizer st = new StringTokenizer(keyWords,",");
		while(st.hasMoreTokens()){
			meta.add(st.nextToken());
		}
		start = file.indexOf("<meta name=\"description\" content=\"");
		slutt = file.indexOf("\">", start);
		keyWords = file.substring(start+31,slutt);
		StringTokenizer st2 = new StringTokenizer(keyWords,",");
		while(st2.hasMoreTokens()){
			meta.add(st.nextToken());
		}
		return meta;
	}


	/**
	 * Denne metoden kaller alle de forskjellige metodene som parser file
	 * @param file String
	 */
	public void parseFile(String file, String name){
		String tagsRemoved = "";
		ArrayList sourceList = searchFrame(file);
		String ferdigFil = file.toLowerCase();
		String title = searchTitle(ferdigFil);
		String strippedFrame ;
		orginalFile = ferdigFil;
		tagsRemoved = strip(orginalFile);
		tagsRemoved = stripWhiteSpace(tagsRemoved);
		for(int i = 0; i< sourceList.size(); i++){
			strippedFrame =parseFrame((String)sourceList.get(i));
			tagsRemoved += strippedFrame;
		}
		try {
			xmlP.makeXmlTokenizer(title, url,tagsRemoved, name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Metoden kaller de metodene som m� kalles for � parse innholdet i en frame
	 * @param frame String
	 * @return String
	 */
	private String parseFrame(String frame) {
		String tagsRemovedFromeFrame  = "";
		ArrayList sourceList = searchFrame(frame);
		frame = frame.toLowerCase();
		frame = letterStripping(frame);
		frame = stripSpecialChar(frame);
		tagsRemovedFromeFrame = strip(frame);
		tagsRemovedFromeFrame = removeStopWords(tagsRemovedFromeFrame);
		tagsRemovedFromeFrame = stripWhiteSpace(tagsRemovedFromeFrame);
		//		  indexFile(tagsRemovedFromeFrame);
		return tagsRemovedFromeFrame;
	}



	private void indexFile(String tagsRemovedFromeFrame) {
		// TODO Auto-generated method stub

	}
	
	
	//hallo

	
	public String removeStopWords(String fil){
		
		String [] stoppOrd ={ "alle", "andre","at", "av", "bare", "begge", "ble", "bli","blir", "blitt", "bort", "bra", "bruke",
				"b�de", "da","de", "deg", "dem", "den", "denne", "der", "dere", "deres", "det", "dette", "din", "disse", "dit",
				"ditt", "du", "eller", "en", "ene", "eneste", "enhver", "enn", "er", "et", "ett", "etter", "for", "fordi", "fors�ke",
				"fra", "fram", "f�r", "f�rst", "f�", "gjorde", "gj�re", "god", "g�", "ha", "hadde", "han", "hans", "har", "henne",
				"hennes", "her", "hit", "hun", "hva", "hvem", "hvilke", "hvilken", "hvis", "hvor", "hvordan", "hvorfor", "i",
				"ikke", "ingen", "inn", "inni", "innen", "ja" , "jeg", "kan", "kom", "kun", "kunne", "lage", "lang", "lik", "like",
				"man", "mange", "med", "meg", "meget", "mellom", "men", "mens", "mer", "mest", "min", "mine", "mitt", "mot", "mye", "m�",
				"m�te", "ned", "nei", "noe", "noen", "ny", "n�", "n�r", "og", "ogs�", "om", "opp", "oss", "over", "p�", "rett", 
				"riktig", "samme", "selv", "si", "siden", "sin", "sine", "sist", "sitt", "skal", "skulle", "slik", "slutte", "som",
				"start", "stille", "s�", "s�nn", "tid", "til", "tilbake", "under", "ut", "uten", "var", "ved", "verdi", "vi","ville",
				"vite", "v�re", "v�rt", "v�r" };
		
		for (int i = 0; i < stoppOrd.length; i++) {
			fil = stoppOrd[i];
			
		}
		
		
		return fil;
		
	}
	/**
	 * Metoden leser filen og legger den i en string variabel
	 * @param filename File
	 * @return String
	 * @throws java.io.IOException
	 */
	public String readFile(File filename) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String file = "";
		String temp;
		while((temp = in.readLine())!=null){
			file+=temp;
			file+=" ";
		}
		return file;
	}


	/**
	 * Metoden finner tittlen til html siden
	 * @param file String
	 * @return String
	 */
	public String searchTitle(String file){
		String title = "";
		int start = file.indexOf("<title>");
		int slutt = file.lastIndexOf("</title>");
		if(start==-1||slutt==-1){
			return title;
		}
		else{
			title = file.substring(start+7,slutt);
			title = letterStripping(title);
			title = stripSpecialChar(title);
			return title;
		}
	}


	/**
	 * Metoden finner eventuelle frames som er i html fila
	 * @param file String
	 * @return ArrayList
	 */
	public ArrayList searchFrame(String file){
		if(file == null){
			System.out.println("file er null");
		}
		String frame = "";
		ArrayList frameList = new ArrayList();
		file = file.replaceAll("\\<(FRAME|frame) (SRC|src) =\"(.*?)\".+?\\>", "�"+"$1"+ "�L" );
		while((file.indexOf("�"))!= -1 && (file.indexOf("�L"))!= -1){
			int first = file.indexOf("�");
			int last = file.indexOf("�L");
			System.out.println(file);
			frame = file.substring(first+1, last);
			file = file.replaceFirst("�" + frame + "�L", " ");
			if(frame.startsWith("http")){
				frameList.add(frame);
			}
			else{
				frame = url + frame;
				frameList.add(frame);
			}
		}
		String source;
		ArrayList sourceList = new ArrayList();
		for(int i = 0; i < frameList.size(); i++){
			try {
				source	= findSource((String)frameList.get(i));
				sourceList.add(source);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sourceList;
	}


	/**
	 * Metoden finner kildekoden til en url
	 * @param urlForFrame String
	 * @return String
	 * @throws java.net.MalformedURLException
	 * @throws java.io.IOException
	 */
	public String findSource(String urlForFrame) throws MalformedURLException,  IOException{
		URL frameUrl = new URL(urlForFrame);
		Object source = frameUrl.getContent();
		InputStream is = (InputStream)source;
		BufferedReader br = new BufferedReader(new InputStreamReader(frameUrl.openStream()));
		String content = "";
		String linje = br.readLine();
		while(linje != null){
			content = content.concat(linje);
			linje = br.readLine();
		}
		return content;
	}


	/**
	 * Metoden fjerner de fleste html tagger og mellomrom
	 * @param file String
	 * @return
	 */
	public String strip(String file){
		file = file.replaceAll("(\\p{Space}){2,}", " ");
		//file = file.replaceAll("\\s{2,}", " ");
		file = file.replaceAll("<a href=\"http://(.*?)\".+?/a>", " "+"http://$1"+ " " );
		file = file.replaceAll("<a href=\"#(.*?)\".+?/a>", " "+url+"#$1"+ " " );
		file = file.replaceAll("<a href=\"(.*?)\".+?/a>", " "+url+"$1"+ " " );
		file = file.replaceAll("(<script.+?</script>)+", "");

		file = file.replaceAll("<p>", pBoundary);
		file = file.replaceAll("<[^>]*>", " ");
		file = file.replaceAll("</[^>]*>", " ");
		file = file.replaceAll("(<meta.*>)+?","");

		file = file.replaceAll("#.*\\{[^\\}]*\\}"," "); // tar stylesheet oppmerking
		file = letterStripping(file);
		file = stripSpecialChar(file);

		file = file.replaceAll("mailto:(.*?)", "$1");

		file =removeStopWords(file);

		char[] tegn = {',', ';', '!'};
		for(int i = 0; i < tegn.length ; i++){
			file = file.replaceAll((String.valueOf(tegn[i])), sBoundary);
		}

		file = file.replaceAll("\"", " ");
		file = file.replaceAll("\'", " ");
		return file;
	}


	/**
	 * Metoden fjerner spesialtegn som kan forekomme i html
	 * @param file String
	 * @return
	 */
	public String stripSpecialChar(String file){
		file = file.replaceAll("&nbsp;", " ");
		file = file.replaceAll("&lt;", " ");
		file = file.replaceAll("&gt;", " ");
		file = file.replaceAll("&copy;", " ");
		file = file.replaceAll("&#[^;];"," ");
		file = file.replaceAll("&#150", " ");//dette er bindestrek
		file = file.replaceAll("&", " ");
		file = file.replaceAll("#" , " ");
		file = file.replaceAll("\\|", " ");
		file = file.replaceAll("-", " ");
		file = file.replaceAll("\"", " ");
		file = file.replaceAll("\\(", " ");
		file = file.replaceAll("\\)", " ");
		return file;
	}


	/**
	 * Metoden fjerner alle whiteSpaces som er overfl�dige
	 * @param file String
	 * @return String
	 */
	public String stripWhiteSpace(String file){
		char[] liste = file.toCharArray();

		for (int i = 0; i < (liste.length -1); i++){
			if(Character.isWhitespace(liste[i])|| liste[i] == 'S' ){
				if(Character.isWhitespace(liste[i+1])|| liste[i+1] == 'S'){
					liste[i] = '�';
				}
			}
		}

		String newfile = String.valueOf(liste );
		newfile = newfile.replaceAll("�", "");
		newfile = newfile.replaceAll("S", " ");
		return newfile;
	}


	/**
	 * Metoden gj�r om bokstaver som har spesialtegn i html til vanlige bokstaver
	 * @param file String
	 * @return String
	 */

	public String letterStripping(String file){

		
		file = file.replaceAll("&aelig;", "�");
		

		file = file.replaceAll("&aring;", "�");

		return file;
	}


	/**
	 * Main metoden som starter alt
	 * @param args String
	 */
	public static void main(String [] args){
		XMLParse xmlP = new XMLParse(new File("config.xml"), false);
	}
}
