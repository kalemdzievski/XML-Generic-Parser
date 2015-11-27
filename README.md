XML-Generic-Parser
==================
XML-Generic-Parser java library for parsing XML responces.
  
Download
==================
[Download the jar here](https://github.com/kalemdzievski/XML-Generic-Parser/blob/master/XMLGenericParser.jar?raw=true)

Description
==================

Java library for parsing xml response into java objects. This generic xml parser is written in java, using simple tree model and java reflection. Note that this is prototype and is not tested enough, except for RSS feed, for which is forking fine. Feel free to change whatever you like for your uses, or give me feedback for any errors and notes.

Usage
==================

Just import this library into your project and write the following code in your class.
```java
String yourResponse; // Set your response string here;
Class<?> yourResultClass; // Set your result class here;
XMLParser parser = new XMLParser();
Object result = parser.parseXML(yourResponse, yourResultClass);
```
Then just cast the result object into your result class.

You can use annotations like this:
```java
@XMLParserField(name = "lastBuildDate")
public String lastChange;
```
This will save the element with tag lastBuildDate into the string variable lastChange.


IMPORTANT! Note that your result class must be structured exactly the same as the xml response!<br>

Licence
==================
```
The MIT License (MIT)

Copyright (c) 2015 Nikola

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
