package com.mimo.app.model.pojo;

import org.apache.commons.lang.ArrayUtils;

import com.mimo.app.R;

public class Icons {
	private static final String[] labels = {
			"Entertainment",  	//0
			"Food/Fun", 		//1
			"News",				//2
			"Meeting",			//3
			"Flavour",			//4
			"Sport",			//5
			"Art",				//6
			"Junk Food",		//7
			"Space",			//8
			"Walking",			//9
			"Fitness",			//10
			"Holiday",			//11
			"Market",			//12
			"Community",		//13
			"Office"};			//14
	
	private static final int[] icons = {
			R.drawable.alien, 
			R.drawable.bread, 
			R.drawable.butcher2, 
			R.drawable.candy,
			R.drawable.cheese,
			R.drawable.eggs,
			R.drawable.farmstand,
			R.drawable.fruits,
			R.drawable.japanese_sweet2,
			R.drawable.patisserie,
			R.drawable.sandwich2,
			R.drawable.alien, 
			R.drawable.bread, 
			R.drawable.butcher2, 
			R.drawable.candy};
	
	

	public static String[] getLabels() {
		return labels;
	}

	public static int[] getIcons() {
		return icons;
	}
	
	public int getIndexFromLabel(String label){
		return ArrayUtils.indexOf(getLabels(), label);
	}
	
	public int getIconFromLabel(String label){
		
		int index = ArrayUtils.indexOf(getLabels(), label);
		return icons[index];
	}
	
	public String getLabelFromIcon(int icon){
		
		int index = ArrayUtils.indexOf(getIcons(), icon);
		return labels[index];
	}
}
