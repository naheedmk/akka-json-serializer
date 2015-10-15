Akka JSON Serializer 
====================

This project demonstrates an akka persistence serializer that uses json strings as the marshaled object format.  The json library used is [spray-json](https://github.com/spray/spray-json).  The purpose of this serializer is for use in a quick prototype app that uses [akka-persistence](http://doc.akka.io/docs/akka/2.4.0/scala/persistence.html) and [akka-http](http://doc.akka.io/docs/akka-stream-and-http-experimental/1.0/scala/http/index.html), both using the same json protocol.  I realized json performance isn't as good as protobufs but it is great for rapid development and debugging. 

### Run From Command Line

1. Install [JDK 8u40](https://jdk8.java.net/download.html)
2. Install [Scala version  2.11.5](http://www.scala-lang.org/download/)
3. Install [SBT version 0.13.7](http://www.scala-sbt.org/download.html)
4. Verify your JAVA_HOME environment variable is set to your JDK home
5. From the project directory run command:

```
sbt test
```
