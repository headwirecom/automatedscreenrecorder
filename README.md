# Automated Screen Recorder
## Purpose
The automated screen recorder project allows a user to write a script to record a tutorial video
for a web based application, complete with audio. 

## prerequisits

- ffmpeg
- selenium web driver for google chrome
- amazon AWS account for Amazon Polly (for text to speech conversion)
- Java8+
- Maven

## quickstart

clone the current repository
```
git clone https://github.com/headwirecom/automatedscreenrecorder.git
```

Modify the `asr.properties` file to point to the location of the chrome driver and ffmpeg to be used. 

Make sure the environment variables for your AWS account are set up - for windows you can use

```
set AWS_ACCESS_KEY_ID=<ID>
set AWS_SECRET_ACCESS_KEY=<ACCESS_KEY>
```

Compile and run the screen recorder with

```
mvn install exec:java
```

by default a script in `src/main/resources/test.script` will be executed. This script assumes you
have a local copy of the peregrine-cms installed. The script will result in a video like 
[https://youtu.be/_oOdR27F3kM](https://youtu.be/_oOdR27F3kM)

## script syntax

The script follows a simple syntax: 

```
Comments: 
# some comment

Single Line:
command modifier data

Multi Line:
command modifier `data
more lines`
```

## Commands

The following commands are implemented: 

### use
```
use <browser>
```
Chooses the browser to use. At present `chrome` or `firefox` are supported - make sure the `driver` 
property in the `asr.properties` file points to the location of the selenium web driver for the 
selected browser. 

### start
```
start <name>

example: 
start recording
```

Start a recording into a file called `final-<recording>.avi`. The file can be found in the `target` 
folder of the project after the recording is complete. An additional file called `<recording>.avi`
is created in the `target` folder without the audio track. 

### stop

```
stop
```

The stop command completes the recording and starts the post processing steps (merge audio into video).

### open

```open <url>

#example:
open https://github.com/headwirecom/automatedscreenrecorder
```

opens the given url in the browser

### audio

```
audio text <text to insert>

example: 
# single line audio
audio text hello world

# multi line audio
audio text `welcome to the automated screen recorder. 
We're excited to see you try our tool`
```
Insert audio at this location in the video given the `<text to insert>` text. This command requires 
your Amazon AWS credentials to be set up correctly to run Amazon Polly for the text to speech 
conversion. The resulting audio file will be stored in the `target` folder of your project. The
filename is an SHA256 hash of the text followed by `.mp3` file extension. To reduce cost the 
conversion is only performed if the file is not present yet. Amazon Polly has a free tier for a
limited amount of conversions. 

### wait
```
wait <time in ms or audio>

examples: 
# wait 1 second
wait 1000

# wait for audio to complete playing
wait audio
```
The wait command waits either for a given time in milli seconds or for the most recent audio file
to complete playing. 

### moveTo

```
moveTo <modifier> <data>

examples: 
# move the mouse to a location on the screen by id
moveTo id some-id

# move the mouse to a location on the screen by name
moveTo name some-name

# move the mouse to a location on the screen by xpath
moveTo xpath some-xpath

```
moves the mouse to the given location of an html page by id, name or xpath.

### click
```click <modifier> <data>```
same as moveTo but clicks the item.

### input
```input <text>```
types the given text

### enter
```enter```
types the `enter` key

### acceptDialog
```acceptDialog```
accepts a browser dialog


## Prerequisits Installation and Setup

- **Download browser driver**
   Download the driver for one of the supported browsers:

   [Selenium components](http://www.seleniumhq.org/download/) for Google Chrome and Mozilla Firefox


- **Install FFmpeg to merge audio & video:** 
   [FFmpeg](https://www.ffmpeg.org/)

- **AWS Polly**

    In order to get to know and use Amazon's web service [Polly](https://aws.amazon.com/polly/), visit the [AWS Documentation](http://docs.aws.amazon.com/polly/latest/dg/what-is.html). Setting up an [AWS Account](http://docs.aws.amazon.com/polly/latest/dg/setting-up.html) and creating a user is mandatory for you to use the service. After successfully signing up, look for your AWS AccessKeyID and your AWSSecretKey. You'll need the credentials to [connect the SDK to Amazons Services](http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html).
