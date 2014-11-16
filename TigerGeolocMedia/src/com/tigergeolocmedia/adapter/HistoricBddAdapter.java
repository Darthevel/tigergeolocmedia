package com.tigergeolocmedia.adapter;

import java.util.List;

import com.tigergeolocmedia.Media;
import com.tigergeolocmedia.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HistoricBddAdapter extends BaseAdapter {
	
	private List<Media> mediaList;
	private Context context;
	private LayoutInflater mInflater;
	

	public HistoricBddAdapter(Context context, List<Media> mediaList) {
		super();
		this.context = context;
		this.mediaList = mediaList;
		mInflater = LayoutInflater.from(this.context);
	}

	@Override
	public int getCount() {
		return this.mediaList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mediaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout layoutItem;
		
		if(convertView == null){
			layoutItem = (RelativeLayout) mInflater.inflate(R.layout.list_historic_bdd, parent, false);
		}else {
			layoutItem = (RelativeLayout) convertView;
		}
		
		ImageView iv_Image = (ImageView)layoutItem.findViewById(R.id.IV_Image);
		TextView tv_Name = (TextView)layoutItem.findViewById(R.id.TV_Name);
		TextView tv_Type = (TextView)layoutItem.findViewById(R.id.TV_Type);
		TextView tv_Description = (TextView)layoutItem.findViewById(R.id.TV_Description);
		
		iv_Image.setImageBitmap(BitmapFactory.decodeFile(mediaList.get(position).getPath()));;
		tv_Name.setText(mediaList.get(position).getName());
		tv_Type.setText(mediaList.get(position).getType().toString());
		tv_Description.setText(mediaList.get(position).getDescription());
		
		return layoutItem;
	}

}
