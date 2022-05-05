# Sample Scala Play Microservice 

__Description__

This application provides APIs to upload (and convert) plus download pictures.

__Technology__

Scala, SBT and Play fraemwork
(sbt 1.3.13 Scala 2.12.8, Play 2.8)

__How to run__
* you can compile and run it using sbt 
* in command prompt go to the root directory of the folder
* set/export the DATA_DIR folder path appropriately in your command prompt 
(or in the file start.sh in the same folder and save)
* issue the command `sbt run` (or run the start.sh script)
* be patient when you run for the first time (may take some time with downloads etc.)
* The application runs on localhost:9000 
* Unit test included in the project and can be run using sbt    


__End points__

It has endpoints as stated below through curl commands and description

curl -F image=@/Users/Malay/test/3M.png http://localhost:9000/picture
This is to upload a picture (the picture being in /Users/Malay/test folder named 3M.png)

curl http://localhost:9000/original?file=3M.png -o check3M.png
This is to download the original picture into an output file check3M.png

curl http://localhost:9000/converted?file=3M.png -o check-converted3M.png
This is to download the converted picture of 3M.png into an output file check-converted3M.png

curl http://localhost:9000/list
Lists the pictures available

__Assumptions and consideratios__
1. the openCV/javaCV (to be used to cartoonify) was asking for too many native dependencies at runtime.
So [considering cartoonification is not the main point of the exercise, and being conscious of time]
I have used ImageIO and greyscaled the image instead.
2. No security feature has been added [again being conscious of time]
3. File based saving and retrieval of images has been used. 
(Normally MongoDB could have been used, but the basic exercise as I understand is to provide upload, list and retrieval APIs, so I wish to avoid Mongo Container and driver dependency for quicker coding and simpler demonstration).
4. Unit tests has been provided at the service level (Actual APIs can be tested by running the application)

__Before production__

This is not production ready
* Actual cartoonification needs to implemented
* Security features for APIs (Basic or token based) could be added
* or there may be an https based initial reverse proxy or ingress (Kubernetes) like gateway may be added
* user based access may be implemented to ensure all images are not universally accessible
* MongoDB or similar database may be added in the stack for file storage, with possibly mountable volume
* API should be added for maintaining/deleting images
