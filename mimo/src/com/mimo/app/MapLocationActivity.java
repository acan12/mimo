package com.mimo.app;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.mimo.app.interfaces.IApp;
import com.mimo.app.view.MapOverlays;

//Test
public class MapLocationActivity extends MapActivity implements IApp {
	double lat = -6.198254;
	double lng = 106.841086;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getIntent().getExtras();
		int paramid = bundle.getInt(PARAMS_KEY);
		setContentView(R.layout.layout_maplocation);
		GeoPoint point = getPoint(lat, lng);

		final EditText name = (EditText) findViewById(R.id.placename);
		final TextView hidden = (TextView) findViewById(R.id.hidden_value);
		final Geocoder coder = new Geocoder(getApplicationContext());
		final ImageButton mapLoc = (ImageButton) findViewById(R.id.next_map);
		final MapView mv = (MapView) findViewById(R.id.mapview);

		mv.setBuiltInZoomControls(true);
		mv.getController().setCenter(point);
		mv.getController().setZoom(13);

		List<Overlay> mapOverlays = mv.getOverlays();
		Drawable drawable = getResources().getDrawable(R.drawable.ic_map_marker);
		MapOverlays itemizedoverlay = new MapOverlays(drawable, this, false,
		false, true, paramid);
		OverlayItem overlayitem = new OverlayItem(point, "", "");
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
		

		ImageButton geocode = (ImageButton) findViewById(R.id.geocode);
		geocode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final double[] lat = new double[10];
				final double[] lon = new double[10];
				String locInfo = "Results:\n";
				int i = 0;

				String placeName = name.getText().toString();
				try {
					List<Address> geocodeResults = coder.getFromLocationName(
							placeName, 3);
					Iterator<Address> locations = geocodeResults.iterator();

					while (locations.hasNext()) {
						if (i > 0) {
							mapLoc.setVisibility(View.VISIBLE);
						} else {
							mapLoc.setVisibility(View.GONE);
						}
						Address loc = locations.next();
						locInfo += String.format("Location: %f, %f \n",
								loc.getLatitude(), loc.getLongitude());
						lat[i] = loc.getLatitude();
						lon[i] = loc.getLongitude();
						i++;
					}

					GeoPoint newPoint = new GeoPoint((int) (lat[0] * 1E6),
							(int) (lon[0] * 1E6));
					mv.getController().animateTo(newPoint);
					setMarkerOnMapLocation(mv, newPoint);
					hidden.setText("" + (i - 1));

					final int resultSize = i;
					mapLoc.setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							int nextSize = Integer.parseInt((String) hidden
									.getText());

							GeoPoint newPoint = new GeoPoint(
									(int) (lat[nextSize] * 1E6),
									(int) (lon[nextSize] * 1E6));
							mv.getController().animateTo(newPoint);
							setMarkerOnMapLocation(mv, newPoint);
							if (nextSize == 0) {
								nextSize = resultSize;
							}
							hidden.setText("" + (--nextSize));
						}

					});

				} catch (IOException e) {
					Log.e("Mapping", "Failed to get location info", e);
				}

			}

			private void setMarkerOnMapLocation(final MapView mv,
					GeoPoint newPoint) {
				MapView.LayoutParams mapMarkerParams = new MapView.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
						newPoint, MapView.LayoutParams.TOP_LEFT);
				ImageView mapMarker = new ImageView(getApplicationContext());
				mv.addView(mapMarker, mapMarkerParams);
			}

		});
	}
	
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		
		getMenuInflater().inflate(R.menu.map_location_menu, menu);
		menu.setHeaderIcon(android.R.drawable.ic_input_add).setHeaderTitle("Timer Controls");
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.map_location_menu, menu);
	    return true;
	}

	private GeoPoint getPoint(double lat, double lng) {
		return new GeoPoint((int) (lat * 1000000.0), (int) (lng * 1000000.0));
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
