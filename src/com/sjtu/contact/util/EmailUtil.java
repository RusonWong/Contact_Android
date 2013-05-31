package com.sjtu.contact.util;

import android.app.Activity;
import android.content.Intent;

public class EmailUtil {
	
	public static void sendEmail(Activity context, String to, String content)
	{
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
         
        String[] tos = new String[]{to};
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, tos); //设置收件人
        intent.putExtra(android.content.Intent.EXTRA_TEXT, content); //设置内容
        context.startActivity(Intent.createChooser(intent,"请选择邮件发送"));
	}
}
