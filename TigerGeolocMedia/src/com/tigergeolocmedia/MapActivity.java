package com.tigergeolocmedia;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends ParentMenuActivity {

	int nbMarker = 0;

	private final static float ZOOM_DEFAULT = 18;
	private final static int MAX_ADDRESS = 1;

	private LocationManager locationManager;
	private GoogleMap googleMap;

	private Geocoder geoCoder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		init();
	}

	private void init() {
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = mapFragment.getMap();
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		googleMap.setMyLocationEnabled(true);

		Criteria criteria = new Criteria();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String bestProvider = locationManager.getBestProvider(criteria, false);

		geoCoder = new Geocoder(getApplication());

		initLocation();
	}

	private void initLocation() {
		// Acquire a reference to the system Location Manager
		// LocationManager locationManager = (LocationManager) this
		// .getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				makeUseOfNewLocation(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				int toto = 5;
				toto++;
			}

			public void onProviderEnabled(String provider) {
				int toto = 5;
				toto++;
			}

			public void onProviderDisabled(String provider) {
				int toto = 5;
				toto++;
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 5000, 20, locationListener);
		// locationManager
		// .requestLocationUpdates(provider, 0, 0, locationListener);
	}

	protected void makeUseOfNewLocation(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		LatLng coordinate = new LatLng(lat, lng);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate,
				ZOOM_DEFAULT));
		// if (firstZoom) {
		// firstZoom = false;
		// googleMap.animateCamera(CameraUpdateFactory.zoomBy(ZOOM_DEFAULT));
		// }

		List<Address> addressList = null;
		try {
			addressList = geoCoder.getFromLocation(lat, lng, MAX_ADDRESS);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int toto = 5;
		toto++;
		if (addressList != null) {
			if (!addressList.isEmpty()) {
				Address firstAddress = addressList.get(0);
				handleAddress(firstAddress, coordinate);
			}
		}
	}

	private void handleAddress(Address address, LatLng coordinate) {
		int maxAddressLineIndex = address.getMaxAddressLineIndex();

		String addressAsString = "";

		for (int index = 0; index <= maxAddressLineIndex; index++) {
			String line = address.getAddressLine(index);
			line = address.getAddressLine(index);
			if (index != 0) {
				addressAsString += "\n";
			}
			addressAsString = addressAsString + line;
		}
		int toto = 5;
		toto++;

		addMarker(coordinate, "Vous êtes ici", addressAsString);

	}

	private void addMarker(LatLng coordinate, String title, String snippet) {
		nbMarker++;
		Marker marker = googleMap.addMarker(new MarkerOptions()
				.position(coordinate).title(title).draggable(false));

		marker.setSnippet(snippet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
