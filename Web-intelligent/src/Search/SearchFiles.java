package Search;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

public class SearchFiles {

	private IndexReader indexReader;
	private Directory directory;
	private static IndexSearcher indexSearcher;
	public static String INDEX_DIRECTORY = "Index/";
	
	public SearchFiles() throws IOException{
		
		
		System.out.println(new File(INDEX_DIRECTORY).getAbsolutePath());
		
		directory = FSDirectory.open(new File(INDEX_DIRECTORY));
		indexReader = DirectoryReader.open(directory); //IndexReader.open(directory);
		indexSearcher = new IndexSearcher(indexReader);
		
		
	}
	
	
	
	public static void main(String[] args) throws Exception {
		
		String searchString = "bekken";
		int topHits = 5;
		
		
		SearchFiles searchObject = new SearchFiles();
		
	
		searchIndex(searchString, topHits);

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

}


