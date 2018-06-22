# Niton's Media API
## Description
Nitons Media API is an ***very*** big and **high level Java API**
It provides it provides many Classes utilitys to do I/O operations.
The API also includes many 3rd Party APIs like JCodec, JSoup and the Java Zoom MP3 Parser.
But we dont simply importet them, we also made them easy to use and make some abstract classes to use it in a simple way and added some really cool features.
But the main content is completely self created.
## Overview
 1. Functions
 2. Sub APIs
 3. Where to use
 4. Examples
 5. Rights and Usage
## Functions
 1. File System and IO Operations
 2. Utilitys
 3. Audio
 4. Cryptography
	 1. AES / RSA
	 2. Cluster
 5. Visuals 
 6. JSON Parser / Serializer
### File System and IO Operations
The package for all the features is `com.niton.media.filesystem` and `com.niton.media.streaming`
The most importat  classes and these are the base for the whole API and appear everywhere, are  `NFile` and `Directory`
They have many simple to use methods like move() setText(String) addText(String) delete() write and read and a lot more.
The `ByteParser` class provides many methods to convert everything to bytes using streaming and reverse it and read things out of an stream.
At moment we only have one additional Stream called the CounterIn/Outputstream which simply counts all bytes which pass it. There will be many others soon.
### Utilitys
The main classes are `RessurceLoader` which contains a lot of static methods and `SoundManager` also contais many static methods for playing music.
All these methods are well documented and very easy to use, if you wish to have more controll about what happens use the API classes
### Server Client Modell
The Server Client modell is the most important part of the API. It consist of many and big classes.
There are 3 Ways to use the given structure. The package is `com.niton.tele.network` and the
most interesting classes there are `client.NetworkClient` and `server.Server`

 - Basic Use
	 1. Basic uses only a minimal part of the API and **only use one Stream**/Socket. It is for sending data over an single Stream for only one time. Its not recommended as it doesn't supports encryption multithreading or data handling and connection seccurity
	 2. Examples are at the end of the document 
 - Package Use
	 1. Package Use allows you additional to the Basic use to pack the data to send in packages.
	 2. Because of this you have the advantage that you are able to **easy send full Objects** and big data constructs, the disadvantage you have in comparison to the Basic Use is that you are not able to stream the content so its not recommended for Big downloads as long as you dont write your own PackageSplitter
	 3. Encrypting also doesnt works here
 - Full stack
	 1. Here you have a very very hight amount of good services automisations and other cool stuff.
	 2. You can use this very easy as its very beautiful designed 
	 3. Some cool features
		 1. Automated Session Handling
		 2. Automated (controllable) Encryption
		 3. Automated Multitherading
		 4. ping
		 5. and much more
	 4. Additional we can use the Streaming feature from the Simple Use
## Where to use
You can use this API great for :
 - Data providing Services
 - Making an API for your Web Service
 - Live Data transmission
 - Online Based Games
 - Chat Services
 - Custom Servers
 - Something like Samba (sharing files/dirs in local networks) 
## Examples
Cryptography:
 - RSA or AES en/decrypt a byte array 

    `byte[] dataToEncrypt = "Some ****** very ****** bad text nobody is allowed to see".getBytes("UTF-8");
    SecretKey key        = SimpleAES.generateKey(128);
    byte[] encryptedData = SimpleAES.encrypt(key, dataToEncrypt);
    //Some Time in between
    byte[] decryptedData = SimpleAES.decrypt(key, encryptedData);`

 - Cluster en/decrypt a byte array 

    `byte[] dataToEncrypt = "Some ****** very ****** bad text nobody is allowed to see".getBytes("UTF-8");
    byte[] key           = SimpleAES.generateRandom(1024);
    byte[] encryptedData = SimpleCluster.encrypt(key, dataToEncrypt);
    //Some Time in between
    byte[] decryptedData = SimpleCluster.decrypt(key, encryptedData);`
    

 - Encrypt an `Serializeable` Object

    `Rectangle secretRectangle = new Rectangle(123, 448, 625, 326);
		SecretKey key = SimpleAES.generateKey(128);
		SealedObject encrypted = SimpleAES.encryptObject(secretRectangle, key);
		//You can do whatever you want here. I save it into an File
		File f = new File("C:/myfile.obj.enc");
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(encrypted);`
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
This is one of the parts i am the most proud of, because it seems to be the only simple to use working and well designed. The package is as you can predict `com.niton.media.json` .
Another very very cool feature is an VERY easy Serialisation where you can generate JSON out of Java Objects.
For this purpose you can use `new JsonObject(instanceToSerialize)`

 


