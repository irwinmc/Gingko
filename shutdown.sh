#!/bin/bash
pid=`ps aux | grep gingko | awk '{if($11=="java") print $2}'`

if { $pid }
then
	echo "Stop gingko server, pid: $pid."
	kill $pid
else
	echo "No gingko server process found, may be killed already."
fi