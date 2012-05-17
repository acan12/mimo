package com.mimo.app.view.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mimo.app.R;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;

public class ListDialogAdapter extends ArrayAdapter {
	private Context mContext;
	private int position;
	private ArrayList<ActivityEvent> acEvents;

	ViewHolder holder;
	Drawable icon;
	Icons icons = new Icons();

	class ViewHolder {
		ImageView icon;
		TextView title;
		TextView hiddenid;
		TextView hiddenloc;
		TextView hiddenicon;
	}

	public ListDialogAdapter(Context mContext, ArrayList<ActivityEvent> acEvents) {
		super(mContext, R.layout.list_row_dialog, acEvents.toArray());
		this.mContext = mContext;
		this.acEvents = acEvents;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.position = position;

		ActivityEvent acEvent = (ActivityEvent) getItemObject(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_row_dialog, null);

			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.icon_dialog);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.hiddenid = (TextView) convertView
					.findViewById(R.id.hidden_value_id);
			holder.hiddenloc = (TextView) convertView
					.findViewById(R.id.hidden_value_loc);
			holder.hiddenicon = (TextView) convertView
					.findViewById(R.id.hidden_value_iconlabel);
			convertView.setTag(holder);
		} else {
			// view already defined, retrieve view holder
			holder = (ViewHolder) convertView.getTag();
		}

		Drawable markIcon = mContext.getResources().getDrawable(
				icons.getIconFromLabel(acEvent.getIcon()));

		String title = acEvent.getIcon().toUpperCase() + " "
				+ acEvent.getName() + ", " + acEvent.getDescription();
		String loc = acEvent.getLat() + "," + acEvent.getLng();

		holder.title.setText(title);
		holder.icon.setImageDrawable(markIcon);
		holder.hiddenid.setText(acEvent.getId() + "");
		holder.hiddenloc.setText(loc);
		holder.hiddenicon.setText(acEvent.getIcon());

		return convertView;
	};

	public Object getItemObject() {
		return acEvents.get(position);
	}

	public Object getItemObject(int position) {
		return acEvents.get(position);
	}
}
