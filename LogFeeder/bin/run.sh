$JAVA_HOME/bin/java -classpath activation.jar:mail.jar:jdbcappender.jar:log4j.jar:ojdbc5.jar:. LineFeeder ./myJobs.sh -Dname.LogFeeder $1 $2 $3

