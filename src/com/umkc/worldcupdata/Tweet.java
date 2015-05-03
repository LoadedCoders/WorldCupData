package com.umkc.worldcupdata;

import twitter4j.GeoLocation;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class Tweet {

	long id;
	String message;
	
	String userName;
	String userHandle;
	String userLocation;
	
	int favs;
	int retweets;
	String lang;
	String mediaURL;
	String source;
	String location;
	
	
	public Tweet(Status status) {
		id = status.getId();
		message = status.getText();
		
		userHandle = status.getUser().getScreenName();
		userName = status.getUser().getName();
		userLocation = status.getUser().getLocation();
		
		favs = status.getFavoriteCount();
		retweets = status.getRetweetCount();
		lang = status.getLang();
		source = status.getSource();
		GeoLocation geoLocation = status.getGeoLocation();
		if (location != null) {
			location = geoLocation.getLatitude()+","+geoLocation.getLongitude();
		}
		
		MediaEntity[]entities = status.getMediaEntities();
		if (entities.length > 0) {
			mediaURL = entities[0].getMediaURL();
		}
	}
	
	public JSONObject getJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("id", id);
		object.put("message", message);
		
		object.put("user.name", userName);
		object.put("user.handle", userHandle);
		object.put("user.location", userLocation);
		
		object.put("favs", favs);
		object.put("retweets", retweets);
		object.put("lang", lang);	
		
		object.put("location", location);
		object.put("source", source);
		return object;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
