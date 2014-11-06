package com.tigergeolocmedia;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter implements Observer<HistoricElement> {
	private Activity activity;
	private LayoutInflater inflater;
	private List<HistoricElement> elementList = new ArrayList<HistoricElement>();
	private Observable<HistoricElement> factory;

	public CustomListAdapter(Activity activity, Observable<HistoricElement> factory) {
		this.activity = activity;
		this.factory = factory;
		this.factory.subscribe(this);
	}

	@Override
	public int getCount() {
		return elementList.size();
	}

	@Override
	public Object getItem(int location) {
		return elementList.get(location);
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
		TextView description = (TextView) convertView.findViewById(R.id.description);
//		TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

		// getting historic data for the row
		HistoricElement element = elementList.get(position);
		
		//Image
		image.setImageBitmap(element.getImage());		

		// Title
		title.setText(element.getName());
		
		// MediaType
		type.setText("Type: " + String.valueOf(element.getType().toString()));
		
		// Description
		description.setText(element.getDescription());
				
//		// release year
//		year.setText(String.valueOf(m.getYear()));

		return convertView;
	}

	@Override
	public void onCompleted() {
		this.factory = null;
		// TODO toast to user
	}

	@Override
	public void onError(Throwable arg0) {
		this.factory = null;
		// TODO toast to user
	}

	@Override
	public void onNext(HistoricElement arg0) {
		// des qu'il y a un nouvel historicElement (ajout a la liste d'element) + notify listview
		this.elementList.add(arg0);
		notifyDataSetChanged();
	}



}