package com.akshay.adapters;

import java.util.ArrayList;

import com.akshay.flickrimages.ImageViewActicity;
import com.akshay.util.AppConstants;


import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GVImageAdapter extends BaseAdapter{

	private ImageViewActicity activity;
	private ArrayList<Parcelable> m_images;
	private int imageWidth;

	public GVImageAdapter(ImageViewActicity act, ArrayList<Parcelable> images,
			int imageWidth) {
		this.activity = act;
		this.m_images = images;
		this.imageWidth = imageWidth;
	}

	@Override
	public int getCount() {
		return m_images.size();
	}

	@Override
	public Object getItem(int posn) {
		return m_images.get(posn);
	}

	@Override
	public long getItemId(int posn) {
		return posn;
	}

	@Override
	public View getView(int posn, View v, ViewGroup viewGrp) {
		ImageView imageView;
		if (v == null) {
			imageView = new ImageView(activity);
		} else {
			imageView = (ImageView) v;
		}

		Bitmap image = Bitmap.createScaledBitmap((Bitmap)m_images.get(posn), 
				imageWidth, imageWidth, false);

		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
				imageWidth));
		imageView.setImageBitmap(image);

		imageView.setOnTouchListener(new OnImageTouchListener(posn));
		

		return imageView;
	}

	class OnImageTouchListener implements OnTouchListener {
		int position;
		public OnImageTouchListener(int posn) {
			position = posn;
		}
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			//Log.e("OnTouch", "event " + event.getAction() + "  msk " + event.getActionMasked() );
				AppConstants.GRID_IMAGE_POS = position;
				activity.getActionBar().setSelectedNavigationItem(1);
			return true;
		}
	}

}


