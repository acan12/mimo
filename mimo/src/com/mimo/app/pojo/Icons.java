package com.mimo.app.pojo;

import com.mimo.app.R;

public class Icons {
	String[] labels = {"Entertainment", 
			"Food/Fun", 
			"News",
			"Meeting",
			"Flavour",
			"Sport",
			"Art",
			"Junk Food",
			"Space",
			"Walking",
			"Fitness",
			"Holiday",
			"Market",
			"Community",
			"Office"};
	
	final int[] icons = {
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
	
	

	public String[] getLabels() {
		return labels;
	}

	public int[] getIcons() {
		return icons;
	}
}
