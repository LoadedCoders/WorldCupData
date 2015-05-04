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

import java.io.IOException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * <p>
 * This is a code example of Twitter4J Streaming API - sample method support.<br>
 * Usage: java twitter4j.examples.PrintSampleStream<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class TwitterStreamService {
	static String CONSUMER_KEY = "XmuCJg6wqok0kM4atoBWyzX70";
	static String CONSUMER_SECRET = "M791X1Py0jy52DG2f18EsxS0CYaMJhOfEZykO8H3mOLmfMXOBD";
	static String ACCESS_KEY = "66398818-wqoEXxQRTtb5GS24eqvn4DS5yQHIfay0NkgN3YDed";
	static String ACCESS_SECRET = "xP3IHuIaGJAuDES88Mt6TuxVEz3oSDz5AlYOgtZ7MEZD1";

	/**
	 * Main entry of this application.
	 *
	 * @param args
	 *            arguments doesn't take effect with this example
	 * @throws IOException
	 */

	public static void main(String[] args) throws TwitterException, IOException {
		final long count = 10;

		// Twitter Conf.
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY)
				.setOAuthConsumerSecret(CONSUMER_SECRET)
				.setOAuthAccessToken(ACCESS_KEY)
				.setOAuthAccessTokenSecret(ACCESS_SECRET);

		final TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
				.getInstance();

		StatusListener listener = new StatusListener() {
			private long counter = 0;

			@Override
			public void onStatus(Status status) {
//				System.out.println(status.getUser().getName());
//				writer.println(TwitterObjectFactory.getRawJSON(status)+",");
				Tweet tweet = new Tweet(status);
				try {
					HBaseHelper.put(tweet);
				} catch (IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				counter++;
				System.out.println(counter);
//				if (counter >= count) {
//					twitterStream.shutdown();
//				}
			}

			@Override
			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
				// System.out.println(statusDeletionNotice.toString());
				// System.out.println("Got a status deletion notice id:" +
				// statusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:"
						+ numberOfLimitedStatuses);
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId
						+ " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning:" + warning);
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		};
		twitterStream.addListener(listener);
		FilterQuery fq = new FilterQuery();
		String keywords[] = { "#nosql" };
		fq.track(keywords);

		twitterStream.sample();
	}
}
