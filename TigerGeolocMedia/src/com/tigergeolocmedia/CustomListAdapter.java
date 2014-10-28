package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Media> mediaList = new ArrayList<Media>();

	public CustomListAdapter(Activity activity, List<Media> mediaList) {
		this.activity = activity;
		this.mediaList = mediaList;
	}

	@Override
	public int getCount() {
		return mediaList.size();
	}

	@Override
	public Object getItem(int location) {
		return mediaList.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		ImageView image = (ImageView) convertView.findViewById(R.id.image);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView type = (TextView) convertView.findViewById(R.id.mediaType);
		TextView genre = (TextView) convertView.findViewById(R.id.genre);
		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

		// getting movie data for the row
		Media m = mediaList.get(position);
		
		// title
		title.setText(m.getName());
		
		// rating
		type.setText("Rating: " + String.valueOf(m.getType().toString()));
		
//		// genre
//		genre.setText(genreStr);
//		
//		// release year
//		year.setText(String.valueOf(m.getYear()));

		return convertView;
	}

}