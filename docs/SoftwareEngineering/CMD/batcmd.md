[toc]

Window bat file

# Batch rename

```bash
ren "D:\folder\filename*.jpg" filename*.png
```

# Comment

```bash
rem echo "abc"
```

# Timeout and interval cmd windows

Create bat file

```bat
:loop
curl -d "account=6666" -X POST http://192.168.11.82/iras/api/login/1/search
timeout /t 600
goto loop
```

# Powershell loop and sleep

```bat
while ($true){
       $wsh = New-Object -ComObject WScript.Shell
       $wsh.SendKeys('{NUMLOCK}')
       Write-Host "Numlock switched"
       Start-Sleep -Seconds 10
}
```