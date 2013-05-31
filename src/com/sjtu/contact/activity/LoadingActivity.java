package com.sjtu.contact.activity;

import com.sjtu.contact.R;
import com.sjtu.contact.model.ContactData;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class LoadingActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.loading_page);
		
		new InitOrgsSpinnerTask().execute();
	}
	
	
	private class InitOrgsSpinnerTask extends AsyncTask <Void, Void, Void>{

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			//Ìø×ª
        	Intent intent = new Intent();
        	intent.setClass(LoadingActivity.this, SearchConfigActivity.class);
        	
        	startActivity(intent);
			finish();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			ContactData.getInstance().getOrganizations();
			return null;
		}
	}
	
}

