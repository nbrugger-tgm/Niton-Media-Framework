> ## This projectis currently under mainstance and being reworked. Version **3.0.0rc0** will be the next stable version


# Niton's Media API (name is subject to change)
## Description
Nitons Media API is an big and high level Java API.

It provides it provides many classes and utilitys to do I/O operations.
~~The API also includes many 3rd Party APIs like JCodec, JSoup and the Java Zoom MP3 Parser.~~
## Overview
 1. Installation
 2. Functions
 3. Sub APIs
 4. Where to use
 5. Examples
## Installation

> Todo: Add maven/gradle repo / artifact

## Functions
 1. File System and IO Operations
 2. Utilitys
 3. Audio (MP3)
 4. Cryptography
	 1. AES / RSA
	 2. Cluster
 5. Visuals 
 6. ~~JSON Parser / Serializer~~ (moved to an independent project called `json-io`
### File System and IO Operations
The package for all the features is `com.niton.media.filesystem` and `com.niton.media.streaming`
The most importat  classes and these are the base for the whole API and appear everywhere, are  `NFile` and `Directory`
They have many simple to use methods like move() setText(String) addText(String) delete() write and read and a lot more.
The `ByteParser` class provides many methods to convert everything to bytes using streaming and reverse it and read things out of an stream.
At moment we only have one additional Stream called the CounterIn/Outputstream which simply counts all bytes which pass it. There will be many others soon.
### Utilitys
The main classes are `RessurceLoader` which contains a lot of static methods and `SoundManager` also contais many static methods for playing music.
All these methods are well documented and very easy to use, if you wish to have more controll about what happens use the API classes.
You can do things like read/parse/write XML, read out of the JAR, read and scale Images
### Audio
Here i use JavaZooms MP3 Parser but it is a completely new thing. It not only provides the ability to play MP3 players it has some functions to stop pause get the current time mark and much more. And a thing which is way important is that it is extensible. I have many interfaces which you can use to extend the API in an easy way. 
The package obviously is `com.niton.media.audio` and if you simply play music (MP3) you only need the class SoundManager which also supports streaming for example over an network using my SaveNetwork API.
> By the way we also have to possibility to **record** Audio using the `AudioFileRecorder` class.

If you want to implement your own custom parser for any other format like OGG you should take a look at the class `Player` and if you don't know how to watch my `MP3Player` class.
### Cryptography
the package `com.niton.tele.crypto` is responsable for all Cryptographic tasks.
The Package consists of two parts.
The first part are the standardized Encryption Methods **RSA** and **AES**.  The classes
for them are called `SimpleAES` and `SimpleRSA`which contain hight level en/decrypt
methods.
The seccond part is my custom Encryption called **CLUSTER**. The technice is a bit like a
rubics Cube, but you can see the source. The advantage of Clustering is that the key has **only
one rule**: it needs at least 1  Byte and should / can not exceed the size of `Integer.MAX_VALUE`.
Obviously the top Level class is called `SimpleCluster`

> **The use of the encryptions is at your own risk! 
> *I am NOT responsible for any loss of data or a loss of security***


But i can tell you they are all well tested. For RSA and AES I use the default JavaSE API which is
incredible difficult! So if RSA or AES has an error its not my fault.
All the methods in the class are well documented or self describing.
### Visual
I have some Swing Components they are also documented so take a look at `com.niton.media.visual`.
I am not done and there will be much more in the future.
### JSON
This part became a seperate project : [JsonIO](https://github.com/nbrugger-tgm/JsonIO)<br>

## Where to use
You can use this API great for :
 - Reading and Writing Files
 - Store Objects in Files
 - Encrypting Objects and Streams
 - Live Data transmission
 - Store Objects in JSON
 - Read and write JSON
 - Custom Audio Decoders/Endcoders
 - Play Audio
 - Play Video (soon)
 - Do things with files: easy moving and so on
## Examples

> Add short and eyecatching examples
 
More Examples are found in the `example` package
