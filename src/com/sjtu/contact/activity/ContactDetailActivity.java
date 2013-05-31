package com.sjtu.contact.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sjtu.contact.R;
import com.sjtu.contact.UI.Util.DialogUtil;
import com.sjtu.contact.pojo.Contact;
import com.sjtu.contact.util.EmailUtil;
import com.sjtu.contact.util.StringUtil;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
		
		//call_mobile_btn
//		TextView mobileText = (TextView) findViewById(R.id.contact_mobilephone);
//		String mobileNumber = mobileText.getText().toString().trim();
//		ImageButton call_mobile_btn = (ImageButton) findViewById(R.id.call_mobile_btn);
//		if (mobileNumber != null && !mobileNumber.equals("") && StringUtil.isPhone(mobileNumber)) {
//			call_mobile_btn.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					TextView mobileText = (TextView) findViewById(R.id.contact_mobilephone);
//					String mobileNumber = mobileText.getText().toString().trim();
//					
//					onCallPhone(mobileNumber);
//				}
//
//			});
//		}
//		else
//		{
//			call_mobile_btn.setVisibility(View.INVISIBLE);
//		}

		// call_subphone_btn
		TextView subPhoneText = (TextView) findViewById(R.id.contact_subphone);
		String mobileNumber = subPhoneText.getText().toString().trim();
		ImageButton call_subphone_btn = (ImageButton) findViewById(R.id.call_subphone_btn);
		if (mobileNumber != null && !mobileNumber.equals("")
				&& StringUtil.isPhone(mobileNumber)) {
			call_subphone_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TextView mobileText = (TextView) findViewById(R.id.contact_subphone);
					String mobileNumber = mobileText.getText().toString()
							.trim();

					onCallPhone(mobileNumber);
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

}
