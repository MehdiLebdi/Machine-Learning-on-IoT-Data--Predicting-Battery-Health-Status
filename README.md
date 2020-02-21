
STEPS FOR CREATING CLUSTER:

Structure of cluster follows architecture mentioned in report. Please follow below steps for environment creation.

(1) KAFKA CLUSTER:
	Kafka cluster is setup on machine say instance-1.
	For Kafka Cluster:
	
	In $KAFKA_HOME/config/server.properties update values of "zookeeper.connect" and "listeners" to have appropriate values of ip and port numbers.

	set SPARK_HOME=D:\Softwares\Anaconda3\envs\tensorflow\Lib\site-packages\pyspark
	set HADOOP_HOME=D:\Softwares\Anaconda3\envs\tensorflow\Lib\site-packages\pyspark
	
	NOTE:- If spark cluster is running on windows, winutils.exe needs to be copied under $HADOOP_HOME/bin directory.
	
	Start Zookeeper:
	===================
	$KAFKA_HOME\bin\windows\zookeeper-server-start.bat config/zookeeper.properties

	Start Kafka Server:
	======================
	$KAFKA_HOME\bin\windows\kafka-server-start.bat config\server.properties

(2) PRODUCER:
	Producers can be started at n different machines(n=3 in this case), sourcing data from 3 different users. Before starting producers, Installation.ipynb needs to be ran for creating colab instances ready with all relevant packages.

(3) CONSUMER:
	Consumer(Spark-streaming) can be started at another local machine say instance-2. This instance should have spark 2.4 version. 


(4) BACKEND:
	For persisting data at backend we are using free tier of mongodb atlas and chose AWS as cloud provided. Security related settings needs to be changed for this to whitelist ip addresses from where we connect to database.
	
	DASHBOARD URL:
	URL: https://cloud.mongodb.com/v2/5ca4eea5cf09a2d1b1a4b7fd#metrics/replicaSet/5ca4efe2fd4cba101aeb8cb3/explorer/iot_prediction/battery_1/find
	username: mbaig@sfu.ca
	password: Burnaby1$
	
	API URL: mongodb+srv://falcon:vancouver@cmpt733-stzkw.mongodb.net/test?retryWrites=true 
	
(5) VISUALIZATION:
	Beta version of mongo chart is used for basic visualization, data-source(db at atlas) is mentioned at mongo chart and frequency of update is at 1 min.
	currently this version of product is having limitation as it is at earlier stage and does not support complex visualization therefore tableau dashboard is used for full breadth of plots and charts.
	
	Since trial versions of product is used, there is no support of continous integration. As a workaround daemon process(shell script at background) is ran to continously update data in file which is imported at tableau. This provides end to end connected layer without any manual intervention of updating data.
	
	TABLEAU URL: https://public.tableau.com/profile/mehdi.lebdi#!/vizhome/tableau_eda/Userbehaviorshowinghighmedlowactivity
	
	MONGODB CHART URL: https://charts.mongodb.com/charts-project-0-sltax/dashboards/fe20a563-fc84-4f07-b5ae-e7166756a013