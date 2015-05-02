-- TODO: Add Pig content here
A = LOAD '/user/biadmin/wc15/tweets2015_04_25_19_55_52.txt' USING JsonLoader('user:chararray, message:chararray');
