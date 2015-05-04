CREATE EXTERNAL TABLE TEST_MAP(
TWEET_ID STRING,   
CREATED_AT STRING,  ---
FAVORITED STRING,   ---
FAVS STRING,
GEO_LATITUDE STRING,
GEO_LONGITUDE STRING,
ID STRING,    ----
lang STRING,
mediaURL STRING,
message STRING,   ---
retweeted STRING,   --
retweets STRING,   ---
source STRING,     ---
u_description STRING,
u_followers_count,
u_handle STRING,
u_id BIGINT,
u_lang STRING,
u_location STRING,
u_profile_image_url STRING,
u_status_count BIGINT,
U_timezone STRING,


) 
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler' 
WITH SERDEPROPERTIES (
"hbase.columns.mapping" = 
":key,
Status:message,
Status:favs,
Status:retweets,
Status:source,
Status:lang,
User:id,
User:handle,
User:name,
User:location,
Team:country_mentioned,
Team:match_mentioned") 
TBLPROPERTIES(
"hbase.table.name" = "wc15");