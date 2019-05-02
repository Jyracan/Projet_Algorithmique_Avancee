#!/bin/bash

#for j in `seq 1 20`	
#do
	for i in `seq 1 2350`	
	do
		echo $i $((RANDOM%2350)) >> ./test/2350.txt
	done
#done

exit 0

