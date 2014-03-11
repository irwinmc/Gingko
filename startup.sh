#!/bin/sh
files=`ls -r | grep 'gingko-server.*\.jar'`

for var in files; do
	file=$var
	break;
done

echo file
nohup java -jar $file > server.log &