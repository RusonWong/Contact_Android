package com.sjtu.contact.UI.Util;

import com.sjtu.contact.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ShowLoadingUtil {
	
	public static void maskWithLoading(RelativeLayout viewToOverlay)
	{
		View loadingOverlay = viewToOverlay.findViewById(R.id.ruson_loading_main);
		
		if( loadingOverlay == null )
		{
			LayoutInflater inflater = LayoutInflater.from(viewToOverlay.getContext());
			View loadingView = inflater.inflate(R.layout.overlay_loading_layout, null);
			
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			
			viewToOverlay.addView(loadingView , params);
			
			loadingOverlay = loadingView.findViewById(R.id.ruson_loading_main);
		}
		loadingOverlay.setVisibility(View.VISIBLE);
		loadingOverlay.setClickable(true);
	}

	    
	public static void unMaskLoading(RelativeLayout viewToOverlay)
	{
		View loadingOverlay = viewToOverlay.findViewById(R.id.ruson_loading_main);
		
		loadingOverlay.setVisibility(View.INVISIBLE);
		loadingOverlay.setClickable(false);
	}
	
	private static View getRootView(Activity context)
	{
		return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
	}
	
	public static void showLoading( Activity context )
	{
		View rootView = getRootView( context );
		
		if( rootView instanceof RelativeLayout )
		{
			maskWithLoading((RelativeLayout)rootView);
		}
		else
		{
			Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public static void hideLoading( Activity context )
	{
		View rootView = getRootView( context );
		
		if( rootView instanceof RelativeLayout )
		{
			unMaskLoading((RelativeLayout)rootView);
		}
		
	}

}
