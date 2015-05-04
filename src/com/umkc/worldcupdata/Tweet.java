package com.umkc.worldcupdata;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.MediaEntity;
import twitter4j.Status;

public class Tweet {

	String created_at;
	String source;
	boolean retweeted;
	int retweet_count;
	double geo_latitude;
	double geo_longitude;
	int favorite_count;	
	long id;
	String text;
	String place;
	String lang;
	String hashtags;
	boolean favorited;
	String sentiment;
	String mediaURL;
	
	long userId;
	String userDescription;
	String userCreatedAt;
	String userName;
	String userLang;
	String userProfileImageURL;
	String userTimeZone;
	long userStatusCount;
	long userFollowersCount;
	String userHandle;
	String userLocation;
	
	
	public Tweet(Status status) {
		created_at = status.getCreatedAt().toString();
		source = status.getSource();
		retweeted = status.isRetweeted();
		retweet_count = status.getRetweetCount();
		favorite_count = status.getFavoriteCount();
		id = status.getId();
		text = status.getText();
		place = status.getPlace().getName(); //Can take more data from this
		lang = status.getLang();
		
		HashtagEntity[]hashtagEntities = status.getHashtagEntities();
		if (hashtagEntities.length > 0) {
			hashtags = hashtagEntities[0].getText();
		}
		favorited = status.isFavorited();
		MediaEntity[]entities = status.getMediaEntities();
		if (entities.length > 0) {
			mediaURL = entities[0].getMediaURL();
		}
		GeoLocation geoLocation = status.getGeoLocation();
		if (geoLocation != null) {
			geo_latitude = geoLocation.getLatitude();
			geo_longitude = geoLocation.getLongitude();
		}
		
		userId = status.getUser().getId();
		userDescription = status.getUser().getDescription();
		userHandle = status.getUser().getScreenName();
		userLang = status.getUser().getLang();
		userProfileImageURL = status.getUser().getProfileImageURL();
		userTimeZone = status.getUser().getTimeZone();
		userStatusCount = status.getUser().getStatusesCount();
		userFollowersCount = status.getUser().getFollowersCount();
		userName = status.getUser().getName();
		userLocation = status.getUser().getLocation();			
	}
	
	public JSONObject getJSONObject() throws JSONException {
		JSONObject object = new JSONObject();
		
		JSONObject statusJsonObject = new JSONObject();
		statusJsonObject.put("created_at", created_at);
		statusJsonObject.put("source", source);
		statusJsonObject.put("retweeted", retweeted);
		statusJsonObject.put("id", id);
		statusJsonObject.put("message", text);
		statusJsonObject.put("place", place);
		statusJsonObject.put("hashtags", hashtags);
		statusJsonObject.put("favorited", favorited);
		statusJsonObject.put("sentiment", sentiment);
		statusJsonObject.put("favs", favorite_count);
		statusJsonObject.put("retweets", retweet_count);
		statusJsonObject.put("lang", lang);	
		statusJsonObject.put("geo_latitude", geo_latitude);
		statusJsonObject.put("geo_longitude", geo_longitude);
		statusJsonObject.put("mediaURL", mediaURL);
		object.put("Status", statusJsonObject);
		
		JSONObject userJsonObject = new JSONObject();
		userJsonObject.put("id", userId);
		userJsonObject.put("description", userDescription);
		userJsonObject.put("created_at", userCreatedAt);
		userJsonObject.put("name", userName);
		userJsonObject.put("lang", userLang);
		userJsonObject.put("profile_image_url", userProfileImageURL);
		userJsonObject.put("timezone", userTimeZone);
		userJsonObject.put("status_count", userStatusCount);
		userJsonObject.put("followers_count", userFollowersCount);
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
