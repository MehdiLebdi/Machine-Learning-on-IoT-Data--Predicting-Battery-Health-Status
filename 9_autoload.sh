#Tableau dashboard is used for full breadth of plots and charts. Since trial versions of product is used, there is no support of continous integration. As a workaround this shell script is running as a daemon process(shell script in background) that continuously updates data in file which is imported in tableau. This provides end to end connected layer without any manual intervention of updating data.


while true
do
	../Software/mongodb-osx-x86_64-4.0.4/bin/mongoexport --host cmpt733-shard-0/cmpt733-shard-00-00-stzkw.mongodb.net:27017,cmpt733-shard-00-01-stzkw.mongodb.net:27017,cmpt733-shard-00-02-stzkw.mongodb.net:27017 --ssl --db iot_prediction --collection battery --username falcon --password vancouver --authenticationDatabase admin --type=csv --fields="Battery_Type,Battery_Level,DateTime,User_Type,Latitude,Battery_Cycle_No,Location,u_id,Langitude,Battery_Status,full_name" --out ../Tableau_iot_data/final.csv
	sleep 5
done