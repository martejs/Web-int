package Search;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import Stemming.HtmlStripper;
import Stemming.XMLParse;

public class SearchFiles {

	private IndexReader indexReader;
	private Directory directory;
	private static IndexSearcher indexSearcher;
	public static String INDEX_DIRECTORY = "Index/";

	private static String filename;
	private static File file;

	public SearchFiles() throws IOException{


		System.out.println(new File(INDEX_DIRECTORY).getAbsolutePath());

		directory = FSDirectory.open(new File(INDEX_DIRECTORY));
		indexReader = DirectoryReader.open(directory); //IndexReader.open(directory);
		indexSearcher = new IndexSearcher(indexReader);


	}


	public static void main(String[] args) throws Exception {


		filename = "Caser/case1.txt";
		file = new File(filename);


		Scanner scanner = new Scanner(file);;
		String searchString = "";

		while(scanner.hasNextLine()){

			searchString = scanner.nextLine();
			int topHits = 5;


			SearchFiles searchObject = new SearchFiles();

			searchString = queryPreprocess(searchString);

			searchIndex(searchString, topHits);
		}

		scanner.close();

	}


	/**
	 * 
	 * @param searchString
	 * @param topHits
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void searchIndex(String searchString, int topHits) throws IOException, ParseException {

		System.out.println("Searching for '" + searchString + "'");

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		QueryParser queryParser = new QueryParser(Version.LUCENE_42, "contents", analyzer);
		Query query = queryParser.parse(searchString);
		//Query q = new StandardQueryParser()
		TopDocs topDocs = indexSearcher.search(query, topHits);

		System.out.println("Number of hits: " + topDocs.totalHits);

		ScoreDoc[] hits = topDocs.scoreDocs;


		// Print all retrieved documents and their fields
		for (ScoreDoc iSDoc : hits) {
			int docId = iSDoc.doc;
			System.out.println("Doc id: " + docId + ", score: " + iSDoc.score);
			Document d = indexSearcher.doc(docId);



			List<IndexableField> fields = d.getFields();
			for (IndexableField iField : fields) {
				System.out.println("Feld name: " + iField.name() + ", val = " + iField.stringValue());
			}
		}


	}

	public static String queryPreprocess(String query){

		query = query.toLowerCase();
		String[] norwegianStopWords = {"alle", "andre", "at", "av", "bare", "begge", "ble", "bli", "blir", "blitt", "bort", "bra", "bruke", "b�de", "da", "de", "deg", "dem",
				"den", "denne", "der", "dere", "deres", "det", "dette", "din", "disse", "dit", "ditt", "du", "eller", "en", "ene", "eneste", "enhver", "enn", "er",
				"et", "ett","etter", "for", "fordi", "fors�ke", "fra", "fram", "f�r", "f�rst", "f�", "gjorde", "gj�re", "god", "g�", "ha", "hadde", "han", "hans",
				"har", "hennar", "henne", "hennes", "her", "hit", "hun", "hva", "hvem", "hver", "hvilke", "hvilken", "hvis", "hvor", "hvordan", "hvorfor", "i",
				"ikke", "ingen", "inn", "innen","inni", "ja", "jeg", "kan", "kom", "kun", "kunne", "lage", "lang", "lik", "like", "man", "mange", "med", "meg", "meget",
				"mellom", "men", "mens", "mer", "mest", "min", "mitt", "mot", "mye", "m�", "m�te", "ned", "nei", "noe","noen", "ny", "n�", "n�r", "og", "ogs�", "om",
				"opp", "oss", "over", "p�","rett", "riktig","samme","seg", "selv", "si", "siden", "sin","sine", "sist", "sitt", "sj�l", "skal", "skulle", "slik", "slutt",
				"som", "start", "stille", "s�", "s�nn", "tid", "til", "tilbake", "under", "ut", "uten", "var", "ved", "verdi", "vi", "vil", "ville", "vite", "v�re", "v�rt",
				"v�r", "�", "blei", "b�e", "dei", "deim", "deira",	"deires", "di", "dykk", "dykkar", "d�", "eg","ein", "eit", "eitt", "elles", "hj�", "ho", "hoe",	 "honom",
				"hoss", "hossen", "ikkje", "ingi", "inkje", "korleis", "korso", "kva", "kvar", "kvarhelst", "kven", "kvi", "kvifor", "me", "medan", "mi", "mine", "mykje",
				"no", "noka", "noko", "nokon", "nokor", "nokre", "si",	"sia", "sidan", "so", "somme", "somt", "um", "upp", "vart", "varte", "vere", "verte", "vore",
				"vors", "vort"};
		for(int i = 0; i < norwegianStopWords.length; i++){
			query = query.replaceAll((" " + norwegianStopWords[i] + " "), " ");
		}
		query = query.replaceAll(" alle ", "");


		return query;

	}
	

}


