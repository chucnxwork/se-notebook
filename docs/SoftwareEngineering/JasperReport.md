## Troubleshooting
### iReport not opening? Java 8 may be the issue

1. Download and install JDK7
(http://www.oracle.com/technetwork/java/javase/down...ds/jdk7-downloads-1880260.html)

2. Modify the iReport config file 'ireport.conf' found in the 'etc' folder in the iReport installation directory. (on a Windows instance; possibly: `C:\Program Files\iReport-3.7.6\etc\`).

    Open the file in Notepad or equivalent plain text editor and find the line: `#jdkhome="/path/to/jdk"`.

    Add a new line below it and replicate the property name (without the hash/"#") with the correct path to your new JDK install, Eg: `jdkhome="C:\Program Files\Java\jdk1.7.0_60"`
