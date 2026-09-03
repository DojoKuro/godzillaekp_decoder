# WebShellDecoder

A Java-based decoder for analyzing encrypted Godzilla ekp webshell communication.

This tool supports decoding encrypted payloads using:

- URL Decode
- Base64 Decode
- AES/ECB/PKCS5Padding
- GZIP Decompression
- Java Class Detection
- CFR Java Decompilation


> This project is intended for authorized security research, malware analysis, penetration testing, and CTF environments only.

---

## Features

- Decode encrypted HTTP request payloads
- Decode encrypted HTTP response payloads
- AES decryption support
- Automatic GZIP decompression
- Java bytecode detection
- Automatic CFR decompilation
- Export decrypted results


---

## Supported Encryption Flow


### Request Payload

```
HTTP Parameter
        |
        v
URL Decode
        |
        v
Base64 Decode
        |
        v
Base64 Decode
        |
        v
AES/ECB/PKCS5Padding Decrypt
        |
        v
GZIP Decompress
        |
        v
Java Class / Text
```


### Response Payload

```
HTTP Response
        |
        v
Base64 Decode
        |
        v
AES/ECB/PKCS5Padding Decrypt
        |
        v
GZIP Decompress
        |
        v
Plain Text
```


---

# Requirements


## Java

Required:

```
Java 21+
```


Tested with:

```
OpenJDK 21.0.12
```


## Maven

Required:

```
Maven 3.9+
```


---

# Build


Clone repository:


```bash
git clone https://github.com/<username>/WebShellDecoder.git

cd WebShellDecoder
```


Build:


```bash
mvn clean package
```


The executable JAR will be generated:

```
target/WebShellDecoder-1.0.jar
```


---

# Run


Run directly:


```bash
java -jar target/WebShellDecoder-1.0.jar
```


---

# Usage


Start the program:


```
pass:
```


Enter the WebShell password parameter:

Example:

```
mypass
```


Enter AES key:

Example:

```
9adbe0b3033881f8
```


Menu:

```
=========================
 Java WebShell Decoder
=========================

1. Decode Request Data
2. Decode Response Data
```


---

# Decode Request


Select:

```
1
```


Input the encrypted HTTP parameter value.


Output:


```
request_decrypted.txt
```


If the result is a Java Class:


Generated files:


```
payload.class

decompile/
└── payload.java
```


---

# Decode Response


Select:

```
2
```


Input the encrypted response string.


Output:


```
response_decrypted.txt
```


---

# Project Structure


```
WebShellDecoder

├── pom.xml

├── src
│   └── main
│       └── java
│           └── Main.java

├── LICENSE

├── README.md

└── .gitignore
```


---

# Dependencies


## CFR Java Decompiler


Project:

https://github.com/leibnitz27/cfr


Used for automatic Java bytecode decompilation.


---

# Security Notice


This tool does not exploit vulnerabilities or provide unauthorized access.

Use only against systems where you have explicit permission.


---

# License


MIT License
