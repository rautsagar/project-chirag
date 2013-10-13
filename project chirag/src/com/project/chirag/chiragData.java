package com.project.chirag;

public class chiragData {
	
	private int id;
	private String data;
	
	chiragData(int inId,String InData){
		id=inId;
		data=InData;
	}
	
	int getId(){
		
		return this.id;
	}
	
	String getData(){
		
		return this.data;
	}

}
