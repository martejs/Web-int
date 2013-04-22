package codeICD;

import java.util.ArrayList;

public class Icd10 {
	private String id;
	private String label;
	private String compactId;
	private ArrayList<String> synonymer;
	

	public Icd10(String id, String label){
		
		this.id = id;
		this.label = label;
		synonymer = new ArrayList<String>();
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}
	
	public void addSynonym(String synonym){
		synonymer.add(synonym);
	}


	public String getCompactId() {
		return compactId;
	}


	public void setCompactId(String compactId) {
		this.compactId = compactId;
	}
	
}
