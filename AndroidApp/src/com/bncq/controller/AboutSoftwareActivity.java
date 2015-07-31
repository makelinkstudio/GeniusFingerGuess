package com.bncq.controller;


import com.bncq.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AboutSoftwareActivity extends Activity {

	private ImageButton ibtn_about_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.about_software_layout);
		ibtn_about_back=(ImageButton) findViewById(R.id.ibtn_about_back);
		ibtn_about_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		super.onCreate(savedInstanceState);
	}
}
