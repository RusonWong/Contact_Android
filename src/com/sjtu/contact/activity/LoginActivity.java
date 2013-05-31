package com.sjtu.contact.activity;

import com.sjtu.contact.R;
import com.sjtu.contact.UI.Util.DialogUtil;
import com.sjtu.contact.UI.Util.ShowLoadingUtil;
import com.sjtu.contact.pojo.Response;
import com.sjtu.contact.pojo.User;
import com.sjtu.contact.service.GlobalVar;
import com.sjtu.contact.service.UserService;
import com.sjtu.contact.util.Logger;

import android.os.AsyncTask;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText userNameEdit;
	private EditText passwordEdit;
	private Button loginBtn;
	private CheckBox savePasswdCB;
	
	//private View loadingOverlay;
	private UserService userService;
	private View RootView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userService = UserService.getInstance();
		
		
		RootView = getLayoutInflater().inflate(R.layout.layout_login, null);
		
		setContentView(RootView);
		
		loginBtn = (Button)findViewById(R.id.login_btn);
		userNameEdit = (EditText)findViewById(R.id.input_username);
		passwordEdit = (EditText)findViewById(R.id.input_password);
		savePasswdCB = (CheckBox) findViewById(R.id.save_passwd_btn);
		
		initInputText();
		initLoginBtn();
		
	}
	
	private void initInputText()
	{
	
		boolean isToSavePasswd = getChoice();
		Logger.log("saved passwd:"+isToSavePasswd);
		savePasswdCB.setChecked(isToSavePasswd);
		
		if (isToSavePasswd) {
			String userName = this.getSavedUsername();
			Logger.log("userName saved is:"+userName);
			String passwd = "";
			if (userName != null && !userName.equals("")) {
				passwd = this.getSavedPasswd();
				
				userNameEdit.setText(userName);
				passwordEdit.setText(passwd);
			}
		}
	}
	
	private void initLoginBtn()
	{
		OnClickListener ocl = new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String userName = userNameEdit.getText().toString().trim();
				String password = passwordEdit.getText().toString().trim();
				
				if(userName == null||userName.equals(""))
				{
					Toast.makeText(LoginActivity.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
					userNameEdit.requestFocus();
					return;
				}
				
				if(password == null||password.equals(""))
				{
					Toast.makeText(LoginActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
					passwordEdit.requestFocus();
					return;
				}
				
				loginBtn.setEnabled(false);
				showLoading();
				
				//do login
				new LoginTask(userName, password).execute();
				
			}
			
		};
		
		loginBtn.setOnClickListener(ocl);
	}
	
    private class LoginTask extends AsyncTask <String, Void, String> {
    	private String userName;
    	private String passWord;
    	
    	public LoginTask(String _userName, String _passwd)
    	{
    		this.userName = _userName;
    		this.passWord = _passwd;
    	}
		@Override
		protected String doInBackground(String... params) {
			
			try{
							
				Logger.log("username:["+userName+"]");
				Logger.log("password:["+passWord+"]");
				
				Response r = userService.doLogin(userName , passWord, "android");
				User u = (User) r.getObj();
				
				if (u == null) {
					return r.getErrorMsg();
					
				} else {
					GlobalVar.setCurrentUser(u);
					return "success";
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return "failed";
			}
			
		}

		@Override
		protected void onPostExecute(String rslt) {
			Logger.log("execute reuslt is:"+rslt);
			
            if (rslt.equals("success")) {
            	Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            	//save user
            	
            	if( savePasswdCB.isChecked() )
            	{
            		Logger.log("start save passwd:"+userName+":"+passWord);
            		saveChoice(true);
            		savePasswd(userName, passWord);
            	}
            	else
            	{
            		saveChoice(false);
            	}
            	//清楚缓存
            	PageData.clear();
            	//跳转
            	Intent intent = new Intent();
            	intent.setClass(LoginActivity.this, SearchConfigActivity.class);
            	
            	startActivity(intent);
            	finish();
                
            } else if(rslt.equals("failed")) {
            	DialogUtil.showDialog(LoginActivity.this, "登录失败，请检查网络！");
            }
            else
            {
            	DialogUtil.showDialog(LoginActivity.this, "用户名或密码不正确！");
            }
            
            loginBtn.setEnabled(true);
            hideLoading();
		}
    }
    
    private void showLoading()
    {
    	ShowLoadingUtil.showLoading(this);
    }
    
    private void hideLoading()
    {
    	ShowLoadingUtil.hideLoading(this);
    }
    
    /////////save passwd///////////////
    private void savePasswd(String username, String passwd)
	{
		SharedPreferences sp;
		SharedPreferences.Editor editor;
		
		sp = this.getSharedPreferences("saved_passwd", 0);
		editor = sp.edit();
		editor.putString("username", username);
		editor.putString("passwd", passwd);
		editor.commit();
	}
	
	private String getSavedUsername()
	{
		SharedPreferences sp;
		sp = this.getSharedPreferences("saved_passwd", 0);
		return sp.getString("username", null);
	}
	
	private String getSavedPasswd()
	{
		SharedPreferences sp;
		sp = this.getSharedPreferences("saved_passwd", 0);
		return sp.getString("passwd", null);
	}
	
	private void saveChoice(Boolean isSavePasswd)
	{
		SharedPreferences sp;
		SharedPreferences.Editor editor;
		
		sp = this.getSharedPreferences("saved_passwd", 0);
		editor = sp.edit();
		editor.putBoolean("choice", isSavePasswd);
		editor.commit();
	}
	
	private boolean getChoice()
	{
		SharedPreferences sp;
		sp = this.getSharedPreferences("saved_passwd", 0);
		return (boolean) sp.getBoolean("choice", false);
	}
	
	@Override 
    public void onBackPressed(){
		if( !savePasswdCB.isChecked() )
    	{
    		saveChoice(false);
    	}
		
		finish();
    }

}
