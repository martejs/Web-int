package indexFiles;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class ICDIndexer {



	/**
	 * This terminal application creates an Apache Lucene index in a folder and adds files into this index
	 * based on the input of the user.
	 */

		
		private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
		private static String indexLocation = "ICDIndex/";
		private static String ICD = "ICD/";
		
		private IndexWriter writer;
		private List<File> queue = new ArrayList<File>();
		

		public static void main(String[] args) throws IOException {
			
			
			
			
			createIndexDir();
			
			ICDIndexer indexer = new ICDIndexer(indexLocation);
			indexer.indexFileOrDirectory(ICD);

			//===================================================
			//after adding, we always have to call the
			//closeIndex, otherwise the index is not created    
			//===================================================
			indexer.closeIndex();
			
			//=========================================================
			// Now search
			//=========================================================
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexLocation)));
			IndexSearcher searcher = new IndexSearcher(reader);
			
			TopScoreDocCollector collector = TopScoreDocCollector.create(5, true);
		}
		
		private static void createIndexDir() {
			File file = new File(indexLocation);
			file.mkdirs();
		}

		/**
		 * Constructor
		 * @param indexDir the name of the folder in which the index should be created
		 * @throws java.io.IOException when exception creating index.
		 */
		ICDIndexer(String indexDir) throws IOException {
			// the boolean true parameter means to create a new index everytime, 
			// potentially overwriting any existing files there.
			FSDirectory dir = FSDirectory.open(new File(indexDir));
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);

			writer = new IndexWriter(dir, config);
		}

		/**
		 * Indexes a file or directory
		 * @param fileName the name of a text file or a folder we wish to add to the index
		 * @throws java.io.IOException when exception
		 */
		public void indexFileOrDirectory(String fileName) throws IOException {
			//===================================================
			//gets the list of files in a folder (if user has submitted
			//the name of a folder) or gets a single file name (is user
			//has submitted only the file name) 
			//===================================================
			addFiles(new File(fileName));

			int originalNumDocs = writer.numDocs();
			for (File f : queue) {
				FileReader fr = null;
				try {
					Document doc = new Document();

					//===================================================
					// add contents of file
					//===================================================
					fr = new FileReader(f);
					doc.add(new TextField("contents", fr));
					doc.add(new StringField("path", f.getPath(), Field.Store.YES));
					doc.add(new StringField("filename", f.getName(), Field.Store.YES));

					writer.addDocument(doc);
					System.out.println("Added: " + f);
				} catch (Exception e) {
					System.out.println("Could not add: " + f);
				} finally {
					fr.close();
				}
			}

			int newNumDocs = writer.numDocs();
			System.out.println("");
			System.out.println("************************");
			System.out.println((newNumDocs - originalNumDocs) + " documents added.");
			System.out.println("************************");

			queue.clear();
		}

		private void addFiles(File file) {
			if (!file.exists()) {
				System.out.println(file + " does not exist.");
			}
			if (file.isDirectory()) {
				for (File f : file.listFiles()) {
					addFiles(f);
				}
			} else {
				String filename = file.getName().toLowerCase();
				//===================================================
				// Only index text files
				//===================================================
				if (filename.endsWith(".htm") || filename.endsWith(".html") || 
						filename.endsWith(".xml") || filename.endsWith(".txt")) {
					queue.add(file);
				} else {
					System.out.println("Skipped " + filename);
				}
			}
		}

		/**
		 * Close the index.
		 * @throws java.io.IOException when exception closing
		 */
		public void closeIndex() throws IOException {
			writer.close();
		}	
		
	}




