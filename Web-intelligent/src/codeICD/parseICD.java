package codeICD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.apple.eio.FileManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.tools.example.debug.gui.SourceModel.Line;


public class parseICD {
	public static void main(String [] args){

		try{
			File fil = new File("ICD/icd10no.owl");
			InputStreamReader is = new InputStreamReader(new FileInputStream(fil), "utf-8");
			BufferedReader br = new BufferedReader(is);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ICD.txt"), "UTF-8"));

			while(br.ready()){
				ArrayList<String> ord = new ArrayList<String>();
				String label = " ";
				String kodeFormatert = "";
				String kodeKomprimert = "";
				boolean formatert = false;
				String linje = br.readLine();
				if(linje.contains("<owl:Class rdf:about=\"&icd10")){
					do{
						linje = br.readLine();
						if(linje.contains("<rdfs:label")){
							label = linje.substring(linje.indexOf(">") + 1, linje.indexOf("</rdfs:label>"));
						}

						else if(linje.contains("<code_formatted")){
							kodeFormatert = linje.substring(linje.indexOf(">") + 1, linje.indexOf("</code_formatted>"));
						}else if(linje.contains("<synonym")){
							ord.add(linje.substring(linje.indexOf(">") + 1, linje.indexOf("</synonym>")));
						}else if(linje.contains("<code_compacted") && !formatert){
							kodeKomprimert = linje.substring(linje.indexOf(">") + 1, linje.indexOf("</code_compacted>"));
						}
					}while(br.ready() && !linje.contains("</owl:Class>"));
					bw.write(label + "|" + (kodeFormatert.equals("") ? kodeKomprimert : kodeFormatert));
					bw.write("\n");

					for(String s :ord){
						bw.write(s + "|" + (kodeFormatert.equals("") ? kodeKomprimert : kodeFormatert));
						bw.write("\n");

					}
					ord.clear();
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}


}
