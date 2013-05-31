package com.sjtu.contact.activity;

import java.util.ArrayList;
import java.util.List;

import com.sjtu.contact.R;
import com.sjtu.contact.UI.Util.DialogUtil;
import com.sjtu.contact.UI.Util.ShowLoadingUtil;
import com.sjtu.contact.model.ContactData;
import com.sjtu.contact.pojo.Contact;
import com.sjtu.contact.pojo.Department;
import com.sjtu.contact.pojo.Institution;
import com.sjtu.contact.pojo.Organization;
import com.sjtu.contact.service.ContactService;
import com.sjtu.contact.service.QueryFilter;
import com.sjtu.contact.util.Logger;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchConfigActivity extends Activity{

	private TextView titile;
	
	private List<Organization> orgs;
	private List<Institution> insts;
	private List<Department> depts;
	
	private Spinner  orgSpinner;
	private OrgSpinnerAdapter orgAdapter;
	
	private Spinner  instsSpinner;
	private InstsSpinnerAdapter instsAdapter;

	private Spinner deptSpinner;
	private DeptsSpinnerAdapter deptAdapter;
	
	private ContactData      contactData;
	
	private int selectedOrg;
	private int selectedInst;
	private int selectedDept;
	
	private Button orgSelectBtn;
	private Button instSelectBtn;
	private Button deptSelectBtn;
	
	
	private Button nextButton;
	private Button backButton;
	private EditText searchNameText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search_filter);
		
		orgSpinner = (Spinner) findViewById(R.id.org_spinner);
		instsSpinner = (Spinner) findViewById(R.id.inst_spinner); 
		deptSpinner = (Spinner) findViewById(R.id.dept_spinner); 
		
		orgs = new ArrayList<Organization>();
		insts = new ArrayList<Institution>();
		depts = new ArrayList<Department>();
		contactData = ContactData.getInstance();
		
		titile = (TextView) findViewById(R.id.title_text);
		titile.setText("ËÑË÷ÉèÖÃ");
		
		nextButton = (Button)findViewById(R.id.btn_fw);
		backButton = (Button)findViewById(R.id.btn_back);
		nextButton.setText("ËÑË÷");
		backButton.setText("ÍË³ö");
		searchNameText = (EditText) findViewById(R.id.search_name_text);
		
		selectedOrg = -1;
		selectedInst = -1;
		selectedDept = -1;
		
		new InitOrgsSpinnerTask().execute(null);
		
		initButtons();
	}
	
	
	private void initButtons()
	{
		initSearchButton();
		initBackButton();
		initSelectButton();
	}
	
	private void initSelectButton()
	{
		orgSelectBtn = (Button)findViewById(R.id.org_select_btn);
		orgSelectBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				orgSpinner.performClick();
			}
			
		});
		
		instSelectBtn = (Button)findViewById(R.id.inst_select_btn);
		instSelectBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				instsSpinner.performClick();
			}
			
		});
		
		deptSelectBtn = (Button)findViewById(R.id.dept_select_btn);
		deptSelectBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				deptSpinner.performClick();
			}
			
		});
	}
	
	private void initBackButton()
	{
		OnClickListener ocl = new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				onQuit();
				
			}
			
		};
		
		backButton.setOnClickListener(ocl);
	}
	
	private void initSearchButton()
	{
		OnClickListener ocl = new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				
				String searchName = searchNameText.getText().toString().trim();

				Intent intent = new Intent();
		        intent.setClass(SearchConfigActivity.this, SearchResultActivity.class);
		        Bundle bundle = new Bundle();
		        bundle.putInt("selectedOrg", selectedOrg);
		        bundle.putInt("selectedInst", selectedInst);
		        bundle.putInt("selectedDept", selectedDept);
		        bundle.putString("searchName", searchName);
		        intent.putExtra("searchConfig", bundle);
		        startActivity(intent);
			}
			
		};
		
		nextButton.setOnClickListener(ocl);
	}
	
	
	
	
	/////////////////////////////////orgs spinner START/////////////////////////////
	
	private class InitOrgsSpinnerTask extends AsyncTask <Void, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			try{
				orgs = contactData.getOrganizations();
				Logger.log("orgs count:"+orgs.size());
				
				boolean isToAddNull = true;
				if(!orgs.isEmpty())
				{
					Organization orgone = orgs.get(0);
					if(orgone.getId() == -1)
					{
						isToAddNull = false;
					}
				}
				
				if(isToAddNull)
				{
					String tip = orgs.isEmpty()?"¿Õ":"ÇëÑ¡Ôñ";
					//add null item
					Organization orgNull =new Organization();
					orgNull.setId(-1);
					orgNull.setName(tip);
					
					orgs.add(0,orgNull);
				}
				return 1;
			} catch(Exception e)
			{
				e.printStackTrace();
				return 0;
			}
		}
		
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if(result == 1 )
			{
				initOrgSpinner();
			}
			else
			{
				hideLoading();
				DialogUtil.showDialog(SearchConfigActivity.this, "²éÕÒ´óÀà³ö´í!");
			}
		}
	}
	
	
	
	private void initOrgSpinner()
	{
		orgAdapter = new OrgSpinnerAdapter(this , orgs);
		orgSpinner.setAdapter(orgAdapter);	

		orgSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Object itemSelected = arg0.getItemAtPosition(arg2);
				
				selectedOrg = ((Organization)itemSelected).getId();
				TextView text = (TextView) arg1.findViewById(R.id.org_name_text);
				//text.setTextColor(SearchConfigActivity.this.getResources().getColor(R.color.white));
				Logger.log("org " +selectedOrg+ " seleced...");
				
				new InitInstsSpinnerTask().execute(selectedOrg);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
	}
	
	
	private class OrgSpinnerAdapter extends BaseAdapter
	{
		private List<Organization> orgs;
		
		private class OrgViewHolder
		{
			public TextView nameText;
		}
		
		private LayoutInflater mInflater;
		
		public OrgSpinnerAdapter(Context context, List<Organization> orgsSrc)
		{
			mInflater = LayoutInflater.from(context);
			this.orgs = orgsSrc;
		}
		
		@Override
		public int getCount() {
			return this.orgs.size();
		}

		@Override
		public Object getItem(int arg0) {
			return this.orgs.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return this.orgs.get(arg0).getId();
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			OrgViewHolder viewHolder = null;
			if( arg1 == null )
			{
				arg1 = mInflater.inflate(R.layout.scope_org_item, null);
				viewHolder = new OrgViewHolder();
				viewHolder.nameText = (TextView) arg1.findViewById(R.id.org_name_text);
				arg1.setTag(viewHolder);
			}
			else
			{
				viewHolder = (OrgViewHolder) arg1.getTag();
			}
			
			Organization org = this.orgs.get(arg0);
			viewHolder.nameText.setText(org.getName());
			//viewHolder.nameText.setTextColor(SearchConfigActivity.this.getResources().getColor(R.color.black));
			return arg1;
		}
	
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			
			return getView(position, convertView, parent);
		}
	}
	///////////////////////////////orgs spinner START/////////////////////////////
	
	////////////////////////////////insts spinner START/////////////////////////////
	private class InitInstsSpinnerTask extends AsyncTask <Integer, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}
		@Override
		protected Integer doInBackground(Integer... arg0) {
			try{
				int selectedOrg = arg0[0];
				Logger.log("selected org:"+selectedOrg);
				if(selectedOrg != -1)
					insts = contactData.getInstitutions(selectedOrg);
				else
					insts = new ArrayList<Institution>();
				
				Logger.log("insts count:"+insts.size());
				
				boolean isToAddNull = true;
				if(!insts.isEmpty())
				{
					Institution inst = insts.get(0);
					
					if(inst.getId() == -1)
					{
						isToAddNull = false;
					}
				}
				
				if(isToAddNull){
					String tip = insts.isEmpty()?"¿Õ":"ÇëÑ¡Ôñ";
					//first
					Institution instNull = new Institution();
					instNull.setId(-1);
					instNull.setName(tip);
					insts.add(0, instNull);
				}
				return 1;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return 0;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if( result ==1 )
			{
				initInstsSpinner();
			}
			else
			{
				hideLoading();
				DialogUtil.showDialog(SearchConfigActivity.this, "Çë¼ì²éÍøÂç!");
			}
		}
	}
	
	private void initInstsSpinner() {
		instsAdapter = new InstsSpinnerAdapter(this, insts);
		
		this.instsSpinner.setAdapter(instsAdapter);
		
		OnItemSelectedListener oisl = new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
					
					Object inst = arg0.getItemAtPosition(arg2);
					
					selectedInst = ((Institution)inst).getId();
					
					TextView text = (TextView)arg1.findViewById(R.id.inst_name_text);
					//text.setTextColor(SearchConfigActivity.this.getResources().getColor(R.color.white));
					
					new InitDeptsSpinnerTask().execute(selectedInst);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		instsSpinner.setOnItemSelectedListener(oisl);
		
	}
	
	private class InstsSpinnerAdapter extends BaseAdapter
	{
		private List<Institution> insts;
		
		private class ViewHolder
		{
			public TextView nameText;
		}
		
		private LayoutInflater mInflater;
		
		public InstsSpinnerAdapter(Context context, List<Institution> instsSrc)
		{
			mInflater = LayoutInflater.from(context);
			this.insts = instsSrc;
		}
		
		@Override
		public int getCount() {
			return insts.size();
		}

		@Override
		public Object getItem(int position) {
			return insts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return insts.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if( convertView == null )
			{
				convertView = mInflater.inflate(R.layout.scope_inst_item, null);
				viewHolder = new ViewHolder();
				viewHolder.nameText = (TextView) convertView.findViewById(R.id.inst_name_text);
				convertView.setTag(viewHolder);
			}
			else
			{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			Institution inst = this.insts.get(position);
			viewHolder.nameText.setText(inst.getName());
			//viewHolder.nameText.setTextColor(SearchConfigActivity.this.getResources().getColor(R.color.black));
			
			return convertView;
		}
		
	}
	//////////////////////////////insts spinner END////////////////////////////
	
	
	
	//////////////////////////insts spinner START///////////////////////////
	
	private class InitDeptsSpinnerTask extends AsyncTask <Integer, Void, Integer>
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoading();
		}
		@Override
		protected Integer doInBackground(Integer... arg0) {
			try{
				int selectedInst = arg0[0];
				
				if( selectedInst != -1)
					depts = contactData.getDepartments(selectedInst);
				else
					depts = new ArrayList<Department>();
				
				Logger.log("depts count:"+depts.size());
				
				boolean isToAdd  = true;
				if(!depts.isEmpty())
				{
					Department dept = depts.get(0);
					if(dept.getId() == -1 )
					{
						isToAdd  = false;
					}
				}
				
				if(isToAdd)
				{
					String tip = depts.isEmpty()?"¿Õ":"ÇëÑ¡Ôñ";
					//first
					Department deptNull = new Department();
					deptNull.setId(-1);
					deptNull.setName(tip);
					depts.add(0, deptNull);
				}
				
				return 1;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return 0;
			}
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if(result == 1 )
			{
				initDeptsSpinner();
			}
			else
			{
				hideLoading();
				DialogUtil.showDialog(SearchConfigActivity.this, "Çë¼ì²éÍøÂçÁ¬½Ó!");
			}
			
			hideLoading();
			
		}
	}
	
	
	private void initDeptsSpinner() {
		deptAdapter = new DeptsSpinnerAdapter(this, depts);
		
		this.deptSpinner.setAdapter(deptAdapter);
		
		OnItemSelectedListener oisl = new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
					
					Object inst = arg0.getItemAtPosition(arg2);
					TextView text = (TextView) arg1.findViewById(R.id.dept_name_text);
					//text.setTextColor(SearchConfigActivity.this.getResources().getColor(R.color.white));
					selectedDept = ((Department)inst).getId();
					
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		};
		
		deptSpinner.setOnItemSelectedListener(oisl);
	}
	
	private class DeptsSpinnerAdapter extends BaseAdapter
	{
		private List<Department> depts;
		
		private class ViewHolder
		{
			public TextView nameText;
		}
		private LayoutInflater mInflater;
		
		public DeptsSpinnerAdapter(Context context, List<Department> deptsSrc)
		{
			mInflater = LayoutInflater.from(context);
			this.depts = deptsSrc;
		}
		
		@Override
		public int getCount() {
			return this.depts.size();
		}

		@Override
		public Object getItem(int position) {
			return this.depts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return this.depts.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if( convertView == null )
			{
				convertView = mInflater.inflate(R.layout.scope_dept_item, null);
				viewHolder = new ViewHolder();
				viewHolder.nameText = (TextView) convertView.findViewById(R.id.dept_name_text);
				convertView.setTag(viewHolder);
			}
			else
			{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			Department inst = this.depts.get(position);
			viewHolder.nameText.setText(inst.getName());
			//viewHolder.nameText.setTextColor(SearchConfigActivity.this.getResources().getColor(R.color.black));
			return convertView;
		}
		
	}
	
	////////////////////////insts spinner END////////////////////////////
	
	private boolean isLoadingShowing = false;
    private void showLoading()
    {
    	if( !isLoadingShowing )
    		ShowLoadingUtil.showLoading(this);
    	isLoadingShowing = true;
    }
    
    private void hideLoading()
    {
    	ShowLoadingUtil.hideLoading(this);
    	isLoadingShowing = false;
    }
    
    @Override 
    public void onBackPressed(){
    	onQuit();
    }
    
    private void onQuit()
    {
    	DialogInterface.OnClickListener ocl_OK = new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();
				PageData.clear();
				contactData.clear();//Çå³þ»º´æ
				finish();
			}
			
		};
		
		DialogInterface.OnClickListener ocl_Cancel = new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();
			}
			
		};
		
		DialogUtil.showOptionDialog(SearchConfigActivity.this,"È·¶¨ÍË³ö£¿",ocl_OK, ocl_Cancel);
    }

}
