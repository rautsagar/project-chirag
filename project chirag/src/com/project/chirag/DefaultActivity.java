package com.project.chirag;



import com.project.chirag.R;

import android.os.Bundle;
import android.view.Window;

public class DefaultActivity extends ParentActivity {
	
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_default);
		
	}

}
