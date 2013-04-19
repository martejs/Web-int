package Stemming;

public class htmlstripper {
	
	
	
	

	
	public String removeStopWords(String fil){
		
		String [] stoppOrd ={ "alle", "andre","at", "av", "bare", "begge", "ble", "bli","blir", "blitt", "bort", "bra", "bruke",
				"både", "da","de", "deg", "dem", "den", "denne", "der", "dere", "deres", "det", "dette", "din", "disse", "dit",
				"ditt", "du", "eller", "en", "ene", "eneste", "enhver", "enn", "er", "et", "ett", "etter", "for", "fordi", "forsøke",
				"fra", "fram", "før", "først", "få", "gjorde", "gjøre", "god", "gå", "ha", "hadde", "han", "hans", "har", "henne",
				"hennes", "her", "hit", "hun", "hva", "hvem", "hvilke", "hvilken", "hvis", "hvor", "hvordan", "hvorfor", "i",
				"ikke", "ingen", "inn", "inni", "innen", "ja" , "jeg", "kan", "kom", "kun", "kunne", "lage", "lang", "lik", "like",
				"man", "mange", "med", "meg", "meget", "mellom", "men", "mens", "mer", "mest", "min", "mine", "mitt", "mot", "mye", "må",
				"måte", "ned", "nei", "noe", "noen", "ny", "nå", "når", "og", "også", "om", "opp", "oss", "over", "på", "rett", 
				"riktig", "samme", "selv", "si", "siden", "sin", "sine", "sist", "sitt", "skal", "skulle", "slik", "slutte", "som",
				"start", "stille", "så", "sånn", "tid", "til", "tilbake", "under", "ut", "uten", "var", "ved", "verdi", "vi","ville",
				"vite", "være", "vært", "vår" };
		
		for (int i = 0; i < stoppOrd.length; i++) {
			fil = stoppOrd[i];
			
		}
		
		
		return fil;
		
	}
}
