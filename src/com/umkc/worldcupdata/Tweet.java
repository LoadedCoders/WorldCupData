package com.umkc.worldcupdata;

import twitter4j.GeoLocation;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class Tweet {

	long id;
	String message;
	String sentiment;
	int favs;
	int retweets;
	String created_at;
	String source;
	String lang;
	String mediaURL;
	String location;
	
	String userId;
	String userName;
	String userHandle;
	String userLocation;
	
	String teamCountry;
	String teamMatch;
	
	String playerName;
	String playerCountry;
	
	public Tweet(Status status) {
		id = status.getId();
		message = status.getText();
		favs = status.getFavoriteCount();
		retweets = status.getRetweetCount();
		lang = status.getLang();
		source = status.getSource();
		MediaEntity[]entities = status.getMediaEntities();
		if (entities.length > 0) {
			mediaURL = entities[0].getMediaURL();
		}
		GeoLocation geoLocation = status.getGeoLocation();
		if (location != null) {
			location = geoLocation.getLatitude()+","+geoLocation.getLongitude();
		}
		
		userHandle = status.getUser().getScreenName();
		userName = status.getUser().getName();
		userLocation = status.getUser().getLocation();
		
		//Team & Player details
		
	}
	
	public JSONObject getJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		
		JSONObject statusJsonObject = new JSONObject();
		statusJsonObject.put("id", id);
		statusJsonObject.put("message", message);
		statusJsonObject.put("sentiment", sentiment);
		statusJsonObject.put("favs", favs);
		statusJsonObject.put("retweets", retweets);
		statusJsonObject.put("lang", lang);	
		statusJsonObject.put("location", location);
		statusJsonObject.put("source", source);
		statusJsonObject.put("mediaURL", mediaURL);
		object.put("Status", statusJsonObject);
		
		JSONObject userJsonObject = new JSONObject();
		userJsonObject.put("name", userName);
		userJsonObject.put("handle", userHandle);
		userJsonObject.put("location", userLocation);
		object.put("User", userJsonObject);
		
		return object;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
