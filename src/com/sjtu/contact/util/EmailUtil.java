package com.sjtu.contact.util;

import android.app.Activity;
import android.content.Intent;

public class EmailUtil {
	
	public static void sendEmail(Activity context, String to, String content)
	{
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("plain/text");
         
        String[] tos = new String[]{to};
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, tos); //�����ռ���
        intent.putExtra(android.content.Intent.EXTRA_TEXT, content); //��������
        context.startActivity(Intent.createChooser(intent,"��ѡ���ʼ�����"));
	}
}
