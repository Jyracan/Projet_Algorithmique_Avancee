#!/bin/bash

for j in `seq 1 20`
do
	for i in `seq 1 $j`	
	do
		echo $i $((RANDOM%$j)) >> ./test/$j.txt
	done
done

exit 0

