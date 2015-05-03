package com.umkc.worldcupdata;

import java.util.HashMap;

public abstract class WCSchema {
	public static String TABLE_NAME = "wctweets";
	
	public static final String[] StatusColumns = {
		"id",
		"message",
		"location",
		"favs",
		"retweets"
	};
	
	public static final String[] UserColumns = {
		"name",
		"handle",
		"location"
	};
	
	public static final String[] TeamColumns = {
		"country",
		"match"
	};
	
	public static final String[] PlayerColumns = {
		"name",
		"country",
		"twitter_id"
	};

	public static HashMap<String, String[]> ColumnFamilies = new HashMap<String, String[]>();
	
	static {
		ColumnFamilies.put("Status", StatusColumns);
		ColumnFamilies.put("User", UserColumns);
		ColumnFamilies.put("Team", TeamColumns);
		ColumnFamilies.put("Player", PlayerColumns);
	}
	
	public String[] FAMILIES = {
			"Status",
			"User",
			"Team",
			"Player"
			};
}
