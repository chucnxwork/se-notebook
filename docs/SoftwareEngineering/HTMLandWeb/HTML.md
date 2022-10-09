# Avoid CSRF

```js
if (request.getHeader('X-Requested-With') == null) {
  // Without the `X-Requested-With` header, this request could be forged. Aborts.
}
```

# Redirect HTTP to HTTPS

You need to put a canonical link at the head of your page to tell search engines that HTTPS is the best way to get to your site.
Set `<link rel="canonical" href="https://…"/>` tags in your pages. This helps search engines determine the best way to get to your site.

# File .htaccess
## Cache control
```
    ### HEADER CACHING
    # https://www.askapache.com/htaccess/speed-up-sites-with-htaccess-caching/
    <FilesMatch "\.(flv|gif|jpg|jpeg|png|ico)$">
    Header set Cache-Control "max-age=2592000"
    </FilesMatch>
    <FilesMatch "\.(js|css|pdf|swf)$">
    Header set Cache-Control "max-age=604800"
    </FilesMatch>
    <FilesMatch "\.(html|htm|txt)$">
    Header set Cache-Control "max-age=600"
    </FilesMatch>
    <FilesMatch "\.(pl|php|cgi|spl|scgi|fcgi)$">
    Header unset Cache-Control
    </FilesMatch>
```

# Table scroll with fixed header (IE not supported)
```html
<html>

<head>
    <title>Test</title>
    <style>
        .table-fixed-header-container {
            overflow: auto;
        }

        .table-fixed-header-container table {
            text-align: left;
            position: relative;
            border-collapse: collapse;
            width: 100%;
        }

        .table-fixed-header-container th {
            position: sticky;
            top: 0;
            background-color: white;
        }


        th, td {
            padding: 0.25rem;
        }

        .table-fixed-header-container {
            width: 300px;
            height: 200px;
        }
    </style>
</head>

<body>

    <div class="table-fixed-header-container">
        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Address</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>0001</td>
                    <td>Nguyen Van A</td>
                    <td>nguyenvana@gmail.com</td>
                    <td>Ha Noi</td>
                </tr>
                <tr>
                    <td>0002</td>
                    <td>Nguyen Van A</td>
                    <td>nguyenvana@gmail.com</td>
                    <td>Ha Noi</td>
                </tr>
                <tr>
                    <td>0099</td>
                    <td>Nguyen Van A</td>
                    <td>nguyenvana@gmail.com</td>
                    <td>Ha Noi</td>
                </tr>
            </tbody>
        </table>
    </div>

</body>

</html>
```

