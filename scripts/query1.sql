--set mapred.reduce.tasks=50;
--set hive.execution.engine=tez;

--top tweets based on language
select u_lang,count(*) as count from TEST_poc group by(u_lang) order by count desc LIMIT 10;


--top tweets based on location
select u_location,count(*) as count from TEST_poc group by(u_location) order by count desc LIMIT 10;

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

