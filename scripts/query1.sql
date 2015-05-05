--set mapred.reduce.tasks=50;
--set hive.execution.engine=tez;

--top tweets based on language
select u_lang,count(*) as count from TEST_poc group by(u_lang) order by count desc LIMIT 10;

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_languages ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select u_lang,count(*) as count from TEST_poc group by(u_lang) order by count desc LIMIT 20;
--saving in hadoop
hadoop fs -cat /biginsights/hive/warehouse/top_languages/* > top_languages.csv


--top tweets based on user_location
select u_location,count(*) as count from TEST_poc group by(u_location) order by count desc LIMIT 10;

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_languages ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select u_location,count(*) as count from TEST_poc group by(u_location) order by count desc LIMIT 20;

--saving in hadoop
hadoop fs -cat /biginsights/hive/warehouse/top_locations/* > top_locations.csv


---top users based on tweets--
select u_name,message,count(*) as count from TEST_poc 
group by u_name,message order by count desc LIMIT 10;


set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_tweet_user ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select u_name,message,count(*) as count from TEST_poc 
group by u_name,message order by count desc LIMIT 20;

--saving in hadoop
hadoop fs -cat /biginsights/hive/warehouse/top_tweet_user/* > top_tweet_user.csv



---top hashtags ---
select u_hashtags,count(*) as count from TEST_poc group by(u_hashtags) order by count desc LIMIT 10;

---top tweets from places---
select place,count(*) as count from TEST_poc group by place order by count desc limit 10;

---seaarchinnng hash tag with topic---
select hashtags,count from 
(select hashtags,count(*) as count from TEST_poc group by(hashtags)) a 
where a.hashtags LIKE '%love' order by a.count desc LIMIT 10;


----getting most tweeted user for given <hash tag>

select u_name,hashtags,count(*) as count from TEST_poc where hashtags like 'followmeskip' 
group by u_name,hashtags order by count desc LIMIT 10;

 

select u_name,hashtags,count(*) as count from TEST_poc 
where hashtags like '%followmeskip%' group by u_name,hashtags order by count desc LIMIT 10
UNION
select u_name,hashtags,count(*) as count from TEST_poc 
where hashtags like '%starwar%' group by u_name,hashtags order by count desc LIMIT 10; 


--top tweets regarding topic %topic% 
select u_name,u_id,retweets from TEST_poc where message LIKE '%IPL%' order by retweets desc LIMIT 10;
--(as our tweet data has retweeted as false for all tweets,we cannot so analysis on retweets on data)
--treading tweet topics

select count(*) as count from TEST_poc where message LIKE '%Nepal earth quake|nepalearthquake%' 

create table Results(topic string,score int)

INSERT OVERWRITE TABLE Results
SELECT count(*) FROM TEST_poc b where message LIKE '%IPL%'
UNION ALL
SELECT c.var1 FROM tmp_table2 c
UNION ALL
SELECT d.var1 FROM tmp_table3 d
UNION ALL
SELECT e.var1 FROM tmp_table4 e
UNION ALL
SELECT f.var1 FROM tmp_table5 f
UNION ALL
SELECT g.var1 FROM tmp_table6 g
UNION ALL
SELECT h.var1 FROM tmp_table7 h;


--user with maximum number of tweets

SELECT a.u_id,a.u_name FROM TEST_poc a left semi join 
(SELECT CAST(MAX(u_status_count) AS FLOAT) u_status_count FROM TEST_poc)b on (a.u_status_count=b.u_status_count);


--best tweets 

SELECT a.u_id,a.u_name FROM TEST_poc a left semi join 
(SELECT CAST(MAX(u_status_count) AS FLOAT) u_status_count FROM TEST_poc)b on (a.u_status_count=b.u_status_count);


--75.4 sec --68 records---
--hashtags= followmeskip




