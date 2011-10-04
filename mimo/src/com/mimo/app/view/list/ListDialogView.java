package com.mimo.app.view.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mimo.app.R;
import com.mimo.app.model.pojo.ActivityEvent;
import com.mimo.app.model.pojo.Icons;

public class ListDialogView extends BaseAdapter{
	private Context mContext;
	private int[] mIcons = new int[20];
	private int mLayout;
	
	public ListDialogView(Context context, int[] icons, int layout ){
		this.mContext = context;
		this.mIcons = icons;
		this.mLayout = layout;
	}
	
	public ListAdapter getDialogAdapter(ArrayList<ActivityEvent> eventItems) {
		String icon = null; 
		final String iconPicture ;
		final String[] items = new String[eventItems.size()];
		final String[] locs = new String[eventItems.size()];
		
		Iterator<ActivityEvent> it= eventItems.iterator(); 
		int i = 0;
		
		Log.d("---x-------------------debug: ", "eventItems="+eventItems.size()      );
		
		while(it.hasNext()){
			ActivityEvent a = (ActivityEvent)it.next();
			icon = a.getIcon();
			items[i] = a.getIcon().toUpperCase()+" "+a.getName()+", "+a.getDescription();
			locs[i++]= a.getLat()+","+a.getLng();
		}
		
		// TODO Auto-generated method stub
		iconPicture = icon;
		ListAdapter adapter = new ArrayAdapter<String>(
		  mContext, R.layout.list_row_dialog,items) {
               
	        ViewHolder holder;
	        Drawable icon;
	        Icons icons = new Icons();
	 
	        class ViewHolder {
	                ImageView icon;
	                TextView title;
	                TextView hiddenloc;
	                TextView hiddenicon;
	        }
	 
	        public View getView(int position, View convertView,
	          ViewGroup parent) {
	                final LayoutInflater inflater = (LayoutInflater) mContext
	                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
	                if (convertView == null) {
	                        convertView = inflater.inflate(
	                                        R.layout.list_row_dialog, null);
	 
	                        holder = new ViewHolder();
	                        holder.icon   = (ImageView) convertView
	                                      	.findViewById(R.id.icon_dialog);
	                        holder.title  =	(TextView) convertView
	                                        .findViewById(R.id.title);
	                        holder.hiddenloc = (TextView) convertView
	                        				.findViewById(R.id.hidden_value_loc);
	                        holder.hiddenicon = (TextView) convertView
            								.findViewById(R.id.hidden_value_iconlabel);
	                        convertView.setTag(holder);
	                } else {
	                        // view already defined, retrieve view holder
	                        holder = (ViewHolder) convertView.getTag();
	                }              
	 
	                
	                Drawable tile = mContext.getResources().getDrawable(icons.getIconFromLabel(iconPicture)); //this is an image from the drawables folder
	               
	                holder.title.setText(items[position]);
	                holder.icon.setImageDrawable(tile);
	                holder.hiddenloc.setText(locs[position]);
	                holder.hiddenicon.setText(iconPicture);
//	                LinearLayout layout_row = (LinearLayout)convertView.findViewById(R.layout.list_row_dialog);
//	                layout_row.setOnClickListener(new LinearLayout.OnClickListener() {
//						
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
////							Toast.makeText(this, "dodolipret", Toast.LENGTH_LONG);
//							Log.d("---x-------------------debug: ", "**************");
//						}
//					});
	                
            	
	                return convertView;
	        };
		
		};
		return adapter;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	};
}
