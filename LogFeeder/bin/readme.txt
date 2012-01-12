##### convert to unix #####

dos2unix log4j.xml log4j.xml 
dos2unix run.sh run.sh 
dos2unix compile.sh compile.sh 
dos2unix myJobs.sh myJobs.sh 

##### compile onetime  #####

sh compile.sh 

##### specify your command in myJobs.sh whose output you want to parse#####

vi myJobs.sh

##### give executable rights #####

chomd +x  myJobs.sh
chomd +x  run.sh

##### Edit log4j.xml to apply DBappander or Email Appender  #####
#####     for "processOutputLogger" category applay your appender #####

vi log4j.xml

##### run your logfeeder #####

sh run.sh

#####<< you can use "run.sh" to start just with your application >>#####
#####<< you put "run.sh" in cron job for failover safe execution >>#####
#####<< check *.log if any trouble >>############################