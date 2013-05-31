package com.sjtu.contact.activity;

import java.util.ArrayList;
import java.util.List;

import com.sjtu.contact.R;
import com.sjtu.contact.UI.Util.DialogUtil;
import com.sjtu.contact.UI.Util.ShowLoadingUtil;
import com.sjtu.contact.pojo.Contact;
import com.sjtu.contact.service.ContactService;
import com.sjtu.contact.service.QueryFilter;
import com.sjtu.contact.util.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultActivity extends Activity{

	
	private Button nextButton;
	private Button backButton;
	private TextView titile;
	
	private ViewGroup contactBriefList;
	private Button getMoreBtn;
	
	//---search config---
	private int selectedOrg;
	private int selectedInst;
	private int selectedDept;
	private String searchName;
	
	private List<Contact> newGotContacts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result_layout);
		
		titile = (TextView) findViewById(R.id.title_text);
		titile.setText("搜索结果列表");
		
		nextButton = (Button)findViewById(R.id.btn_fw);
		backButton = (Button)findViewById(R.id.btn_back);
		
		nextButton.setVisibility(View.GONE);
		
		getMoreBtn = (Button)findViewById(R.id.get_more_btn);
		contactBriefList = (LinearLayout) findViewById(R.id.contact_brief_layout);
		
		newGotContacts = new ArrayList<Contact>();
		
		initSearchConfigs();
		initButtons();
		//retrive data
		retriveContactsData();
	}
	
	private void retriveContactsData()
	{
		new DoSearchContactTask(selectedOrg, selectedInst, selectedDept, searchName ).execute(null);
	}
	
	private void initSearchConfigs()
	{
		Bundle bundle = this.getIntent().getBundleExtra("searchConfig");
		selectedOrg = bundle.getInt("selectedOrg");
		selectedInst = bundle.getInt("selectedInst");
		selectedDept = bundle.getInt("selectedDept");
		searchName = bundle.getString("searchName");
	}
	
	private void initButtons()
	{
		//init back btn
		OnClickListener ocl = new OnClickListener(){

			@Override
			public void onClick(View v) {
				quit();
			}
		};
		
		backButton.setOnClickListener(ocl);
		
		//init get more buttton
		
		OnClickListener ocl2 = new OnClickListener(){

			@Override
			public void onClick(View v) {
				retriveContactsData();
			}
		};
		getMoreBtn.setOnClickListener(ocl2);
	}
	
	
	private void onGotNewContacts(List<Contact> newContacts)
	{
		LayoutInflater mInflater = LayoutInflater.from(this);
		//update UI
		for(int i =0; i < newContacts.size(); i++)
		{
			Contact contact = newContacts.get(i); 
			View vitem = mInflater.inflate(R.layout.contact_brief_item, null);
			vitem.setTag(contact);
			contactBriefList.addView(vitem);
			
			TextView nameText = (TextView) vitem.findViewById(R.id.name_text);
			TextView deptText = (TextView) vitem.findViewById(R.id.phone_text);
			
			nameText.setText(contact.getName());
			deptText.setText(contact.getInst());
			initViewItem(vitem);
		}
		
		
		//set get_more_button button
		
		if (newContacts.size() == PAGE_SIZE)// 获得了整页,可以认为还有下一页
		{
			getMoreBtn.setClickable(true);
		} else {
			//getMoreBtn.setText("没有更多联系人");
			getMoreBtn.setClickable(false);
			getMoreBtn.setVisibility(View.INVISIBLE);
		}
	}
	
	
	private View selectedViewItem = null;
	private void setSelectionTip(View v)
	{
		if(selectedViewItem != null )
		{
			View selectTip = selectedViewItem.findViewById(R.id.select_tip);
			selectTip.setBackgroundResource(R.color.white);
		}
		
		
		selectedViewItem = v;
		View selectTip = selectedViewItem.findViewById(R.id.select_tip);
		selectTip.setBackgroundResource(R.color.darkblue);
		
		
		
	}
	
	private void initViewItem(final View v)
	{
		v.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// Object obj = arg0.getItemAtPosition(arg2);
				
				Contact selectedContact = (Contact)(v.getTag());
				
				if(selectedContact!=null)
				{
					PageData.setSelectedContact(selectedContact);
					
					setSelectionTip(v);
					
					//跳转
					Intent intent = new Intent();
	            	intent.setClass(SearchResultActivity.this, ContactDetailActivity.class);
	            	
	            	startActivity(intent);
				}
				
			}
			
		});
	}
	
	private final int PAGE_SIZE = PageData.PAGE_SIZE;
	private int offset = 0;
	private int getNextOffset()
	{	
		int reslt = offset;
		offset += PAGE_SIZE;
		return reslt;
	}
	
	
	boolean isListInited = false;
	/////////////////////////////search START////////////////////////
	private class DoSearchContactTask extends AsyncTask<Void, Void, Integer> {
		private int oid;
		private int iid;
		private int did;
		private String filterName;

		public DoSearchContactTask(int _oid, int _iid, int _did,
				String _filterName) {
			oid = _oid;
			iid = _iid;
			did = _did;
			filterName = _filterName;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			QueryFilter filter = new QueryFilter();

			filter.AddFilter("offset", getNextOffset()+"");
			filter.AddFilter("limit", PAGE_SIZE+"");
			
			if (did != -1) {
				filter.AddFilter(ContactService.FILTER_DEPTID, did + "");
			} else if (iid != -1) {
				filter.AddFilter(ContactService.FILTER_INSTID, iid + "");
			} else if (oid != -1) {
				filter.AddFilter(ContactService.FILTER_ORGID, oid + "");
			}

			if (filterName != null && !filterName.equals("")) {
				filter.AddFilter(ContactService.FILTER_WD, filterName);
			}

			try {
				List<Contact> contacts = (List<Contact>) ContactService
						.getInstance().queryContacts(filter).getObj();

				newGotContacts = contacts;
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				PageData.contacts = null;
				return 0;
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			hideLoading();

			if (result == 0) {
				DialogUtil.showDialog(SearchResultActivity.this,
						"搜索出错...");
			} else if (result > 0) {
				
				if(newGotContacts.size()==0 && !isListInited){
					DialogUtil.showDialog(SearchResultActivity.this,
							"没有找到符合条件的联系人");
				}
				
				//set UI
				if (newGotContacts.size() >= 0) {
					//data got
					Logger.log("data GOt:" + newGotContacts.size());
					onGotNewContacts( newGotContacts );
					isListInited = true;
				} 
				
				
			}
		}
	}

	// ///////////////////////////search END/////////////////////////

	private boolean isLoadingShowing = false;

	private void showLoading() {
		if (!isLoadingShowing)
			ShowLoadingUtil.showLoading(this);
		isLoadingShowing = true;
	}

	private void hideLoading() {
		ShowLoadingUtil.hideLoading(this);
		isLoadingShowing = false;
	}
	
	@Override
	public void onBackPressed() {
		quit();
	}
	 
	private void quit()
	{
		Logger.log("cleaning。。。");
		PageData.clear();
		finish();
	}

}
