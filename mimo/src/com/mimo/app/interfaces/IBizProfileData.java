package com.mimo.app.interfaces;

public interface IBizProfileData {
	static final int CARREFOUR = 0;
	static final int SEVEN_ELEVEN = 1;
	
	static final double[][][] biz = 
	{
		{  // CARREFOUR
			{-6.28869009017944, 106.777000427246 },
			{-6.278, 106.73 },
			{-6.32117, 106.74711}
		},
		{  // SEVEN_ELEVEN
			{-6.2439532914955, 106.796808242798},
			{ -6.25003241489425, 106.797902584076 },
			{-6.20003219877074, 106.854164600372 },
			{-6.24798, 106.82652}
		}
	};
}
