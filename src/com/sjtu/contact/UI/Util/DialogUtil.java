package com.sjtu.contact.UI.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtil {
//	public static void showDialog(Context context, String title, String msg)
//	{
//		AlertDialog.Builder builder  = new AlertDialog.Builder(context);
//		builder.setCancelable(true)
//			   .setMessage(msg)
//			   .setTitle(title)
//			   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.cancel();
//					}
//			   }
//			   ).create().show();
//	}
	
	public static void showDialog(Context context, String msg)
	{
		CustomDialog.Builder builder  = new CustomDialog.Builder(context);
		builder.setMessage(msg)
			   .setPositiveButton("确定",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}})
			   .create()
			   .show();
	}
	
	
	public static void showOptionDialog(Context context, String msg, OnClickListener ocl_OK, OnClickListener ocl_Cancel)
	{
		CustomDialog.Builder builder  = new CustomDialog.Builder(context);
		builder.setMessage(msg)
			   .setPositiveButton("确定",ocl_OK)
			   .setNegativeButton("取消",ocl_Cancel)
			   .create()
			   .show();
	}
}
