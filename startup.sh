#!/bin/sh
files=`ls -r | grep 'gingko*\.jar'`

for var in files; do
	file=$var
	break;
done

echo file
nohup java -jar $file > server.log &