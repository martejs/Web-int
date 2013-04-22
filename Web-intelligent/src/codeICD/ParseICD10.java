package codeICD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class ParseICD10 {

	public static void main (String [] args) throws UnsupportedEncodingException, FileNotFoundException{


		File fil = new File("ICD10/icd10no.owl");
		Scanner scanner = new Scanner(fil);
		ArrayList <Icd10> icdObjekter = new ArrayList<Icd10>(); 

		for(int i = 0; i < 80; i++){

			boolean formatert = false;

			String linje = scanner.nextLine();

			do{
				Icd10 icd10Objekt = new Icd10(null, null);
				
				if(linje.contains("<owl:Class rdf:about=\"&icd10")){
					System.out.println("if");
					linje = scanner.nextLine();
					if(linje.contains("<rdfs:label")){
						try {
							icd10Objekt.setLabel(linje.substring(linje.indexOf(">") + 1, linje.indexOf("</rdfs:label>"))) ;
						}catch ( StringIndexOutOfBoundsException e ){}

					}

					else if(linje.contains("<code_formatted")){
						icd10Objekt.setId(linje.substring(linje.indexOf(">") + 1, linje.indexOf("</code_formatted>")));
					}else if(linje.contains("<synonym")){

						icd10Objekt.addSynonym(linje.substring(linje.indexOf(">") + 1, linje.indexOf("</synonym>")));
					}else if(linje.contains("<code_compacted") && !formatert){
						icd10Objekt.setCompactId(linje.substring(linje.indexOf(">") + 1, linje.indexOf("</code_compacted>")));
					}


					icdObjekter.add(icd10Objekt);
				}
			}while(scanner.hasNextLine() && !linje.contains("</owl:Class>"));

			scanner.close();
			System.out.println(icdObjekter.get(0).getId());
		}
	}
}




