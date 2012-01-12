#!/bin/bash

#Note that there must not be any whitespace at the beginning of the line.
#Then give your script executable permissions:
#
#chmod a+x myJobs.sh 

########### ADD your commands here ##########3

tail -f $JBOSS_HOME_DELSIM/server/default/log/jboss.log 
