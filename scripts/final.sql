--total tweets--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_languages ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select count(*) as tweetcount from TEST_poc;

--word count--- not done
set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table total_words ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select sum(temptable2.count) from (select word,count(1) as count from (select explode(split(message,' ')) as word from (select message from TEST_poc)temptable1)temptable group by word)temptable2;

--total number of languages used in tweets--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table total_languages ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select count(*) from (select lang, count(lang) from TEST_poc GROUP BY lang)test;

--total number of users --

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table total_users ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select count(*) from (select u_id, count(u_id) from TEST_poc GROUP BY u_id)test;

--top 10 languages used in tweets--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_languages ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select lang,count(*) as count from TEST_poc group by(lang) order by count desc LIMIT 10;

--Top 10 places tweet count--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_places ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select place, count(*) as count from TEST_poc group by place order by count desc limit 10;



--top 10 hash tags--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_hashtags ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select hashtags, count(*) as count from TEST_poc group by(hashtags) order by count desc LIMIT 10;

--top 10 users--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_users ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' as
select DISTINCT u_handle, u_name, u_description, u_profile_image_url, u_followers_count, u_status_count from TEST_poc order by u_followers_count, u_status_count desc LIMIT 5;



--fetching top tweets about a keyword--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_hastags_retweet ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select u_name, message from TEST_poc where message like '%music%' order by favs, retweets DESC LIMIT 10;

--Top Sources
set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table top_sources ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select source, count(source) as count from TEST_poc group by source order by count DESC LIMIT 8;

--ngrams
set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table unigrams ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
SELECT explode(context_ngrams(sentences(lower(message)), array(null), 25)) AS word_map FROM TEST_poc;


set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table bigrams ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
SELECT explode(context_ngrams(sentences(lower(message)), array(null, null), 25)) AS word_map FROM TEST_poc;

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table trigrams ROW FORMAT DELIMITED FIELDS TERMINATED BY '\054' LINES TERMINATED BY '\n' as
SELECT explode(context_ngrams(sentences(lower(message)), array(null, null, null), 25)) AS word_map FROM TEST_poc;

-----2nd page----

--Popular tweets for a search keyword:
set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table topuser_keyword ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select message,u_name,u_profile_image_url from TEST_poc where hashtags like ‘%ipl%’ order by favs, retweets desc LIMIT 5;

SELECT test.a0 as b0, test.a1 as b1 
    FROM test
    ORDER BY b1 ASC, b0 DESC

--Countries talking about hashtag(keyword):

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table topplace_keyword ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select place,count(place) as count from TEST_poc where hashtags like ‘%keyword%’ group by place order by count desc LIMIT 10; 


--latitude and logitude--

set hive.exec.compress.output=false;
set hive.cli.print.header=true;
create table lat_long ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' as
select geo_latitude,geo_longitude from TEST_poc where geo_latutude =   and geo_longitude = ; 




