package Search;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexableField;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.payloads.*;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class SearchFiles {

	//public static final String FILES_TO_INDEX_DIRECTORY = "filesToIndex";
	public static String INDEX_DIRECTORY = "index/";

	//public static final String FIELD_PATH = "path";
	//public static final String FIELD_CONTENTS = "contents";

	/*public SearchFiles(String indexDir) {
		INDEX_DIRECTORY = indexDir;
	}*/
	
	public static void main(String[] args) throws Exception {

//		createIndex();
//		searchIndex("mushrooms");
//		searchIndex("steak");
//		searchIndex("steak AND cheese");
//		searchIndex("steak and cheese");
//		searchIndex("bacon OR cheese");

	}

//	public static void createIndex() throws CorruptIndexException, LockObtainFailedException, IOException {
//		Analyzer analyzer = new StandardAnalyzer();
//		boolean recreateIndexIfExists = true;
//		IndexWriter indexWriter = new IndexWriter(INDEX_DIRECTORY, analyzer, recreateIndexIfExists);
//		File dir = new File(FILES_TO_INDEX_DIRECTORY);
//		File[] files = dir.listFiles();
//		for (File file : files) {
//			Document document = new Document();
//
//			String path = file.getCanonicalPath();
//			document.add(new Field(FIELD_PATH, path, Field.Store.YES, Field.Index.UN_TOKENIZED));
//
//			Reader reader = new FileReader(file);
//			document.add(new Field(FIELD_CONTENTS, reader));
//
//			indexWriter.addDocument(document);
//		}
//		indexWriter.optimize();
//		indexWriter.close();
//	}

	/**
	 * 
	 * @param searchString
	 * @param topHits
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void searchIndex(String searchString, int topHits) throws IOException, ParseException {
		System.out.println("Searching for '" + searchString + "'");
		Directory directory = FSDirectory.open(new File(INDEX_DIRECTORY));
		IndexReader indexReader = DirectoryReader.open(directory); //IndexReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		QueryParser queryParser = new QueryParser(Version.LUCENE_42, "", analyzer);
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
		/*
		Iterator<Hit> it = hits.iterator();
		while (it.hasNext()) {
			Hit hit = it.next();
			Document document = hit.getDocument();
			String path = document.get(FIELD_PATH);
			System.out.println("Hit: " + path);
		}*/

	}
	/*
	DirectoryReader ireader = DirectoryReader.open(directory);
    IndexSearcher isearcher = new IndexSearcher(ireader);
    // Parse a simple query that searches for "text":
    QueryParser parser = new QueryParser(Version.LUCENE_42, "fieldname", analyzer);
    Query query = parser.parse("text");
    ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
    assertEquals(1, hits.length);
    // Iterate through the results:
    for (int i = 0; i < hits.length; i++) {
      Document hitDoc = isearcher.doc(hits[i].doc);
      assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
    }
    ireader.close();
    directory.close();
	 */

}


