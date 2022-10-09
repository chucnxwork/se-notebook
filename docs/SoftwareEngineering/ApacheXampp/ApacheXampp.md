# Make XAMPP/Apache serve file outside of htdocs

## Update document root

**Find and update DocumentRoot**

DocumentRoot "D:\1Projects\INPROGRESS\0127crminventoryms\ims\public"
<Directory "D:\1Projects\INPROGRESS\0127crminventoryms\ims\public ">
 

## Make an Alias

**In http.conf file, add**
```conf
<Directory "C:\xampp\htdocs">
    
    Options Indexes FollowSymLinks Includes ExecCGI

    AllowOverride All

    Require all granted
</Directory>
```
 
**Find the `<IfModule alias_module></IfModule>`**

Alias /directoryalias "C:\xampp\htdocs"

## Make virtual host

Add in file apache/conf/extra/httpd-vhosts.conf
```conf
<VirtualHost *:80>
DocumentRoot "E:\1Projects\04Iras\public"
ServerName localhost
<Directory "E:\1Projects\04Iras\public">
Options FollowSymLinks
	AllowOverride All
	DirectoryIndex index.php
	Require all granted
</Directory>
</VirtualHost>
```