package com.sjtu.contact.activity;

import com.sjtu.contact.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView titile;
	private ListView contentList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		titile = (TextView) findViewById(R.id.title_text);
		titile.setText("ึ๗าณ");
		
		contentList = (ListView) findViewById(R.id.content_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
