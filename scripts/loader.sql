CREATE EXTERNAL TABLE TEST_MAP(
tweet_id STRING,   
created_at STRING,
favorited BOOLEAN,
favs INT,
sentiment STRING,
geo_latitude DOUBLE,
geo_longitude DOUBLE,
id STRING,    
lang STRING,
hashtags STRING,
mediaURL STRING,
place STRING,
message STRING,  
retweeted BOOLEAN,
retweets BIGINT,   
source STRING,     
u_description STRING,
u_followers_count BIGINT,
u_handle STRING,
u_id BIGINT,
u_name STRING,
u_lang STRING,
u_location STRING,
u_profile_image_url STRING,
u_status_count BIGINT,
u_timezone STRING,
u_createdat TIMESTAMP) 
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' 
WITH SERDEPROPERTIES (
"hbase.columns.mapping" = 
":key,
Status:created_at,
Status:favorited,
Status:favs,
Status:sentiment,
Status:geo_latitude,
Status:geo_longitude,
Status:id,
Status:lang,
Status:hashtags,
Status:mediaURL,
Status:place,
Status:message,
Status:retweeted,
Status:retweets,
Status:source,
User:description,
User:followers_count,
User:handle,
User:id,
User:name,
User:lang,
User:location,
User:profile_image_url,
User:status_count,
User:timezone STRING,
User:created_at") 
TBLPROPERTIES("hbase.table.name" = "tweets_poc");