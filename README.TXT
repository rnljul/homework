Application downloads data from multiple sources and protocols to local disk.
Application gives list of sources input in the form of urls
(e.g. http://my.file.com/file, ftp://other.file.com/other, sftp://and.also.this/ending etc)

If source is file, then file will be downloaded in configurable location.
If source is directory then downloaded file will contain list of names directory content.


Project structure
*****************
./
 |
 test
 |  |___ 1gb.txt - file for test download data which bigger than memory
 |
 config
 |  |___ app.properties - configuration file
 |
 src - directory with source code
 |
 build.gradle - build script
 |
 gradlew - build script (when gradle not installed)
 |
 gradle.bat - build script for windows (when gradle not installed)
 |
 settings.gradle - build script settings
 |
 README.TXT - this file



Supported protocols
*******************
1. http
2. https
3. ftp
4. sftp
5. file


Application can be extended for support other protocols.
********************************************************
To extend application we need:
1. implement 'agoda.homework.streamers.InStreamer' interface,
   implementation examples located in 'agoda.homework.streamers.impl.in' package.


2. modify factory method 'createInStreamerByUri' in 'agoda.homework.streamers.Streamers' class.
   This method creates concrete instances of InStreamer for supported protocols.


Configuration
*************
Applicaction needs to be configured. Config must be located in <app_directory/config/app.properties>.
Properties:
1. buffer_size = 4096 # how many bytes will be read from InputStream for one time
2. download_location = ./download
3. thread_pool_size = 2 # ThreadPoolExecutor size


Requirements
************
for run application needs
java version "1.8.+

for build application needs
gradle version 4.8

if you don't want to install gradle you can
use 'gradlew' script


How to build
************
if gradle installed use follow command
'gradle clean build'

if not installed you can use
'./gradlew clean build'

test will be automatically executed before build

jar file will be written to 'build/libs'

if you want to build without tests use comand
'gradle clean build -x test -x junitHtmlReport'


How to test
***********
use command
'gradle clean test'
or
'./gradlew clean test'

test will be run with limited memory size
minHeapSize = "20m"
maxHeapSize = "200m"

reports will be written to 'build/reports/tests/junit-platform/index.html'

Some tests get data in public sftp server demo.wftpserver.com,
if tests have been run behind proxy server they could failed/


How to run
**********
use command
java -jar url-downloader-0.1.0.jar <url list>

example
java -jar url-downloader-0.1.0.jar http://my.file.com/file ftp://other.file.com/other