Application for download data from multiple sources and protocols to local disk.
Application gives list of sources input in the form of urls
(e.g. http://my.file.com/file, ftp://other.file.com/other, sftp://and.also.this/ending etc)

If source is file, then file will be downloaded in configurable location.
If source is directory then downloaded file will contain list of names directory content.

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
   It should open InputStream for source Url.

2. modify factory method 'createInStreamerByUri' in 'agoda.homework.streamers.Streamers' class.
   This method creates concrete instances of InStreamer for supported protocols.


Download process
****************
Download process basically implemented by 'agoda.homework.downloader.Downloader' class.
Downloader class is abstract but it has default download implementation.
I considered that downloaded data might be bigger than memory, to solve that problem
i use buffered reading from InputStream.
There is concrete downloader class 'agoda.homework.downloader.impl.FileDownloader' it implements
'cleanup' method to remove partial downloaded data from local disk when download process failed.

Downloading executes in parallel mode, application creates download task for each source Url
and submit it to ThreadPoolExecutor. Then application wait for all sources downloading completed.

Downloaded files will be located in directory specified in configuration file.


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
'gradlew clean build'

test will be automatically executed before build

jar file will be written to 'build/libs'

if you want to build without tests use comand
'gradle clean build -x test -x junitHtmlReport'


How to test
***********
use command
'gradle clean test'
or
'gradlew clean test'

test will be run with limited memory size
minHeapSize = "20m"
maxHeapSize = "200m"

reports will be written to 'build/reports/tests/junit-platform/index.html'



How to run
**********
use command
java -jar url-downloader-0.1.0.jar <url list>

example
java -jar url-downloader-0.1.0.jar http://my.file.com/file ftp://other.file.com/other