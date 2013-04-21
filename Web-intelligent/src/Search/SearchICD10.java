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

		String[] norwegianStopWords = {"alle", "andre", "at", "av", "bare", "begge", "ble", "bli", "blir", "blitt", "bort", "bra", "bruke", "bŒde", "da", "de", "deg", "dem",
				"den", "denne", "der", "dere", "deres", "det", "dette", "din", "disse", "dit", "ditt", "du", "eller", "en", "ene", "eneste", "enhver", "enn", "er",
				"et", "ett","etter", "for", "fordi", "forsøke", "fra", "fram", "f¿r", "f¿rst", "fŒ", "gjorde", "gj¿re", "god", "gŒ", "ha", "hadde", "han", "hans",
				"har", "hennar", "henne", "hennes", "her", "hit", "hun", "hva", "hvem", "hver", "hvilke", "hvilken", "hvis", "hvor", "hvordan", "hvorfor", "i",
				"ikke", "ingen", "inn", "innen","inni", "ja", "jeg", "kan", "kom", "kun", "kunne", "lage", "lang", "lik", "like", "man", "mange", "med", "meg", "meget",
				"mellom", "men", "mens", "mer", "mest", "min", "mitt", "mot", "mye", "mŒ", "mŒte", "ned", "nei", "noe","noen", "ny", "nŒ", "nŒr", "og", "ogsŒ", "om",
				"opp", "oss", "over", "pŒ","rett", "riktig","samme","seg", "selv", "si", "siden", "sin","sine", "sist", "sitt", "sj¿l", "skal", "skulle", "slik", "slutt",
				"som", "start", "stille", "sŒ", "sŒnn", "tid", "til", "tilbake", "under", "ut", "uten", "var", "ved", "verdi", "vi", "vil", "ville", "vite", "v¾re", "v¾rt",
				"vŒr", "Œ", "blei", "bŒe", "dei", "deim", "deira",	"deires", "di", "dykk", "dykkar", "dŒ", "eg","ein", "eit", "eitt", "elles", "hjŒ", "ho", "hoe",	 "honom",
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
