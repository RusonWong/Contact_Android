package com.sjtu.contact.activity;

import com.sjtu.contact.R;
import com.sjtu.contact.UI.Util.DialogUtil;
import com.sjtu.contact.pojo.Contact;
import com.sjtu.contact.util.EmailUtil;
import com.sjtu.contact.util.Logger;
import com.sjtu.contact.util.StringUtil;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.RawContacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailActivity extends Activity{
	
	private Contact contact;
	
	
	private Button nextButton;
	private Button backButton;
	private TextView titile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.contact_detail_item);
		
		nextButton = (Button)findViewById(R.id.btn_fw);
		backButton = (Button)findViewById(R.id.btn_back);
		nextButton.setVisibility(View.GONE);
		titile = (TextView) findViewById(R.id.title_text);
		titile.setText("详细信息");
		
		
		contact = PageData.getSelectedContact();
		if (contact != null)
			initView();
		
		initButtons();
	}
	
	private void initView()
	{
		titile.setText(contact.getName());
		
		TextView Textitem;
		Textitem = (TextView) findViewById(R.id.contact_name);
		Textitem.setText(contact.getName());
		
		Textitem = (TextView) findViewById(R.id.contact_inst);
		Textitem.setText(contact.getInst());
		
		Textitem = (TextView) findViewById(R.id.contact_dept);
		Textitem.setText(contact.getDept());

		Textitem = (TextView) findViewById(R.id.contact_position);
		Textitem.setText(contact.getPosition());
		
		Textitem = (TextView) findViewById(R.id.contact_officephone);
		Textitem.setText(contact.getOfficePhone());
		
		Textitem = (TextView) findViewById(R.id.contact_mobilephone);
		Textitem.setText(contact.getMobilePhone());
		
		Textitem = (TextView) findViewById(R.id.contact_subphone);
		Textitem.setText(contact.getSubPhone());
		
		Textitem = (TextView) findViewById(R.id.contact_switchboard);
		Textitem.setText(contact.getSwitchBoard());
		
		Textitem = (TextView) findViewById(R.id.contact_fax);
		Textitem.setText(contact.getFax());
		
		Textitem = (TextView) findViewById(R.id.contact_email);
		Textitem.setText(contact.getEmail());
		
		Textitem = (TextView) findViewById(R.id.contact_remark);
		Textitem.setText(contact.getRemark());
	}
	
	private void initButtons()
	{
		//bind save contact btn
		boolean isContactSaveable = true;
		if(contact.getOfficePhone().equals("")
				&&contact.getMobilePhone().equals("")
				&&contact.getSubPhone().equals(""))
		{
			isContactSaveable = false;
		}
		

		View saveContactBtn = findViewById(R.id.save_contact_btn);
		if(!isContactSaveable)
		{
			saveContactBtn.setVisibility(View.INVISIBLE);
		}
		else
		{
			saveContactBtn.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					OnSaveContact(contact);
				}
			});
		}
		
		//config back button
		OnClickListener ocl = new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		};
		
		backButton.setOnClickListener(ocl);
		
		//call_office_btn
		String phoneText = ((TextView) findViewById(R.id.contact_officephone)).getText().toString();
		ImageButton call_office_btn = (ImageButton) findViewById(R.id.call_office_btn);
		
		if (phoneText != null && !phoneText.equals("") && StringUtil.isPhone(phoneText)) {
			
			call_office_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TextView phoneText = (TextView) findViewById(R.id.contact_officephone);
					String phoneNumber = phoneText.getText().toString().trim();
					onCallPhone(phoneNumber);
				}

			});
		}
		else
		{
			call_office_btn.setVisibility(View.INVISIBLE);
		}
		
		//view_operator_btn
		TextView mobileText = (TextView) findViewById(R.id.contact_mobilephone);
		String mobileNumber = mobileText.getText().toString().trim();
		final ImageButton view_operator_btn = (ImageButton) findViewById(R.id.view_operator_btn);
		if (mobileNumber != null && !mobileNumber.equals("") && StringUtil.isPhone(mobileNumber)) {
			
			//bind view operator btn
			view_operator_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					final View operatorLine = findViewById(R.id.contact_operator_line);
					
					if(operatorLine.getVisibility() == View.GONE)
					{
						operatorLine.setVisibility(View.VISIBLE);
						view_operator_btn.setImageResource(R.drawable.up_arrow);
					}
					else
					{
						operatorLine.setVisibility(View.GONE);
						view_operator_btn.setImageResource(R.drawable.down_arrow);
					}
				}
			});
			
			//bind call phone 
			View callPhoneBtn = findViewById(R.id.call_mobile_btn);
			callPhoneBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					TextView mobileText = (TextView) findViewById(R.id.contact_mobilephone);
					String mobileNumber = mobileText.getText().toString().trim();
					
					onCallPhone(mobileNumber);
				}
			});
			
			//bind send email btn
			View sendSmsBtn = findViewById(R.id.send_sms_btn);
			sendSmsBtn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					TextView mobileText = (TextView) findViewById(R.id.contact_mobilephone);
					String mobileNumber = mobileText.getText().toString().trim();
					
					onSendSMS(mobileNumber);
				}
			});			
		}
		else
		{
			view_operator_btn.setVisibility(View.INVISIBLE);
		}

		// call_subphone_btn
		TextView subPhoneText = (TextView) findViewById(R.id.contact_subphone);
		String subPhoneNumber = subPhoneText.getText().toString().trim();
		ImageButton call_subphone_btn = (ImageButton) findViewById(R.id.call_subphone_btn);
		if (subPhoneNumber != null && !subPhoneNumber.equals("")
				&& StringUtil.isPhone(subPhoneNumber)) {
			call_subphone_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TextView subPhoneText = (TextView) findViewById(R.id.contact_subphone);
					String subPhoneNumber = subPhoneText.getText().toString()
							.trim();

					onCallPhone(subPhoneNumber);
				}

			});
		} else {
			call_subphone_btn.setVisibility(View.INVISIBLE);
		}

		//send email btn
		TextView emailText = (TextView) findViewById(R.id.contact_email);
		String emailAddr = emailText.getText().toString().trim();
		ImageButton send_email_btn = (ImageButton) findViewById(R.id.send_email_btn);
		
		if(emailAddr != null && !emailAddr.equals("") && StringUtil.isEmail(emailAddr))
		{
			send_email_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TextView emailText = (TextView) findViewById(R.id.contact_email);
					String emailAddr = emailText.getText().toString().trim();
					
					String recName = ((TextView) findViewById(R.id.contact_name)).getText().toString().trim();
					
					
					onSendEmail(emailAddr, recName);
				}
			});
		}else
		{
			send_email_btn.setVisibility(View.INVISIBLE);
		}
	}
	
	private void OnSaveContact(final Contact contact) {
		
		DialogInterface.OnClickListener ocl_OK = new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doAddContact(contact);
			}
		};
		
		DialogInterface.OnClickListener ocl_Cancel = new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
		};
		DialogUtil.showOptionDialog(ContactDetailActivity.this, "确定要保存该联系人么？", ocl_OK , ocl_Cancel );
		
	}
	
	private void doAddContact(Contact contact)
	{
		ContentValues values = new ContentValues();  
        /* 
         * 首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获得系统返回的rawContactId 
         */  
        Uri rawContactUri = getContentResolver().insert(RawContacts.CONTENT_URI, values);  
        long rawContactId = ContentUris.parseId(rawContactUri);  
        
        //往data表里写入姓名数据  
        values.clear();  
        values.put(Data.RAW_CONTACT_ID, rawContactId);  
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); //内容类型  
        values.put(StructuredName.GIVEN_NAME, contact.getName());  
        getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);  
        
        //添加单位数据
        String inst = contact.getInst();
        if(!(inst.equals("")))
        {
	        values.clear();
	        values.put(Data.RAW_CONTACT_ID, rawContactId);  
	        values.put(Data.MIMETYPE, Organization.CONTENT_ITEM_TYPE);
	        values.put(Organization.COMPANY, inst);  
	        
	        String pos = contact.getPosition();
	        if(!(pos.equals("")))
	        {
	        	values.put(Organization.TITLE, pos);  
	        }
	        values.put(Organization.TYPE, Organization.TYPE_WORK);
	        getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
        
        //往data表里写入手机号码  
        String mobilePhone = contact.getMobilePhone();
        if(!(StringUtil.isPhone(mobilePhone)))
        {
        	mobilePhone = contact.getSubPhone();
        }
        if(StringUtil.isPhone(mobilePhone))
        {
	        values.clear();  
	        values.put(Data.RAW_CONTACT_ID, rawContactId);  
	        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);  
	        values.put(Phone.NUMBER, mobilePhone);  
	        values.put(Phone.TYPE, Phone.TYPE_MOBILE);  
	        getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);  
        }
        
        String officePhone = contact.getOfficePhone();
        if(StringUtil.isPhone(officePhone))
        {
	        values.clear();  
	        values.put(Data.RAW_CONTACT_ID, rawContactId);  
	        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);  
	        values.put(Phone.NUMBER, officePhone);  
	        values.put(Phone.TYPE, Phone.TYPE_WORK);  
	        getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);  
        }
        
        //往data表里写入Email的数据  
        String email = contact.getEmail();
        if(StringUtil.isEmail(email))
        {
	        values.clear();  
	        values.put(Data.RAW_CONTACT_ID, rawContactId);  
	        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);  
	        values.put(Email.DATA, email);  
	        values.put(Email.TYPE, Email.TYPE_WORK);  
	        getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        }
       
        
        Toast.makeText(this, "保存联系人成功", Toast.LENGTH_SHORT).show();
	}

	private void onSendEmail(String emailAddr, String receiverName) {
		
		try{
			EmailUtil.sendEmail(this, emailAddr, "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			DialogUtil.showDialog(this, "无法使用邮件服务...");
		}
		
	}
	

	private void onCallPhone(final String phoneNumber)
	{
		DialogInterface.OnClickListener ocl_OK = new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try{
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
					startActivity(intent);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					DialogUtil.showDialog(ContactDetailActivity.this, "无法使用电话服务!");
				}
			}
		};
		
		DialogInterface.OnClickListener ocl_Cancel = new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
		};
		DialogUtil.showOptionDialog(ContactDetailActivity.this, "确定要拨打电话吗？", ocl_OK , ocl_Cancel );
	}
	
	
	private void onSendSMS(String phoneNumber)
	{
		Uri uri = Uri.parse("smsto:" + phoneNumber);          
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);          
		it.putExtra("sms_body", "");          
		startActivity(it);
	}

}
