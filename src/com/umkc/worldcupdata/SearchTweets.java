package com.umkc.worldcupdata;

/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class SearchTweets {
	static String CONSUMER_KEY = "XmuCJg6wqok0kM4atoBWyzX70";
	static String CONSUMER_SECRET = "M791X1Py0jy52DG2f18EsxS0CYaMJhOfEZykO8H3mOLmfMXOBD";
	static String ACCESS_KEY = "66398818-wqoEXxQRTtb5GS24eqvn4DS5yQHIfay0NkgN3YDed";
	static String ACCESS_SECRET = "xP3IHuIaGJAuDES88Mt6TuxVEz3oSDz5AlYOgtZ7MEZD1";

	/**
	 * Usage: java twitter4j.examples.search.SearchTweets [query]
	 * 
	 * @param args
	 *            search query
	 * @throws IOException
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws IOException, JSONException {

		String path = AppConstants.HDFS_URI + AppConstants.HDFS_DIR
				+ "tweets.json";

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);

		Path outFile = new Path(path);

		if (fs.exists(outFile)) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			Date date = new Date();
			path = AppConstants.HDFS_URI + AppConstants.HDFS_DIR + "tweets"
					+ dateFormat.format(date) + ".json";
			outFile = new Path(path);

			System.out.println(dateFormat.format(date)); // 2014/08/06-15:59:48

			System.out.println("Output already exists");
			fs.delete(outFile, true);
		}

		final FSDataOutputStream out = fs.create(outFile);

		// Twitter Conf.
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(ACCESS_KEY)
				.setOAuthAccessTokenSecret(ACCESS_SECRET);

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();

		Query query = new Query("#cwc15 OR #WorldCup15 OR @ICC OR @cricketworldcup OR @ICCLive");
		query.setSince("2015-01-10");
		
		int numberOfTweets = 3;
		long lastID = Long.MAX_VALUE;
		ArrayList<Status> tweets = new ArrayList<Status>();
		
		int lastTweetCount = numberOfTweets;
		
		while (tweets.size() < numberOfTweets) {
			if (numberOfTweets - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(numberOfTweets - tweets.size());
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets");
				
				if (lastTweetCount == tweets.size()) {
					break;
				}
				lastTweetCount = tweets.size();
				
				for (Status t : tweets)
					if (t.getId() < lastID)
						lastID = t.getId();
			}
			catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			query.setMaxId(lastID - 1);
		}

		JSONArray tweetsJsonArray = new JSONArray();
		
		for (int i = 0; i < tweets.size(); i++) {
			Status t = (Status) tweets.get(i);
			Tweet tweet = new Tweet(t);
			JSONObject object = tweet.getJSONObject();
			HBaseHelper.put(tweet);
			
			tweetsJsonArray.put(object);
			
			System.out.println(object.toString());
			
		}
		out.writeUTF(tweetsJsonArray.toString().trim());	
		out.close();
	}
}
