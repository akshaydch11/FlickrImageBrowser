package com.akshay.adapters;

import java.util.ArrayList;


import com.akshay.flickrimages.R;
import com.akshay.util.AppConstants;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FSImageAdapter extends PagerAdapter {

	static final String DEBUG_TAG = "FSImageAdapter";
	Activity activity;
	ArrayList<Parcelable> m_images;
	private LayoutInflater inflater;
	
	public FSImageAdapter(Activity act, ArrayList<Parcelable> arrayList) {
		activity = act;
		m_images = arrayList;
	}
	@Override
	public int getCount() {
		//Log.e(DEBUG_TAG, "size " + m_images.size());
		return m_images.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return (view == ((ImageView) obj));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		//Log.e(DEBUG_TAG, "instantiateItem " + position + " sz " + m_images.size());
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.fs_image_view, container,
                false);
 
        ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.fsImage);
        
        if (position < m_images.size()) 
        	imgDisplay.setImageBitmap((Bitmap)m_images.get(position));
        
        ((ViewPager) container).addView(viewLayout);
        
		return viewLayout;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
 
    }

}
