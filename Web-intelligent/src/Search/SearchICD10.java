package Search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SearchICD10 {

	private static File file;
	private static String filename;

	public static void main(String[] args) throws FileNotFoundException {

		int tall = 1;
		filename = "Caser/case" + tall + ".txt";
		file = new File(filename);


		for (int i = 0; i < 8; i++){
			Scanner scanner = new Scanner(file);

			System.out.println(filename);

			String searchString = "";

			while(scanner.hasNextLine()){

				searchString = scanner.nextLine();
				searchString = queryPreprocess(searchString);
				matching(searchString);

			}

			tall++;
			filename = "Caser/case" + tall + ".txt";
			file = new File(filename);
			scanner.close();
		}

	}

	public static void matching(String query) throws FileNotFoundException{
		File file = new File("ICD/ICD.txt");
		String icd10 = "";

		for(int i = 0; i < query.length(); i++){
			Scanner scan = new Scanner(file);
			while (scan.hasNextLine()){
				icd10 = scan.nextLine();

				System.out.println("Inneholder:' " + query + "  '" + "\n Dette: " + icd10);
				
				if (query.contains(icd10)){
					System.out.println("Ja");
					
				}
			}

			scan.close();
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

		query = query.replaceAll("/", "");

		return query;
	}
}
