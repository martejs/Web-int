package Stemming;

public class htmlstripper {
	
	
	
	

	
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
}
