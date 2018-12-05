# Java CardConnect API REST Client and Sample

Copyright 2014, CardConnect (http://www.cardconnect.com)

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
PERFORMANCE OF THIS SOFTWARE.
----

Example CardConnect REST Client library and example usage in Java.

Requires:
	json-simple 1.1 from: https://code.google.com/p/json-simple/
	HttpClient 4.3.3 from: https://hc.apache.org/downloads.cgi
		- httpclient-4.3.3.jar
		- httpcore-4.3.2.jar
		- commons-codec-1.6.jar
		- commons-logging-1.3.3.jar)

To run:

- Create a new Java Project using your IDE of choice
- Create the source folder directory structure:
    src/com/cardconnect/rest
- Place the three source files in directory above:
    CardConnectRestClientExample.java
    CardConnectRestClient.java
    HttpRequest.java
- Download and add the json_simple-1.1.jar file to your project and add it to
  your project's build path
- Download and add the listed HttpClient jar files to your project and add
  them to your project's build path
- Modify CardConnectRestClientExample.java and set:
	ENDPOINT - to your site's REST url
	USERNAME - your CardConnect username
	PASSWORD - your CardConnect password
- Save and Run CardConnectRestClientExample.java
