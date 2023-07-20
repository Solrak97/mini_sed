# Mini_sed

Its called mini_sed coz it remembers me to the sed program in linux.


To avoid using one of the multiple weird forms of importing files I found on Stack Overflow and some more obscure forums I used the flag '-cp'.

Every time the program runs, it'll create a file with the list of modified files, u can change the path of the file on the 4th optional parameter.

I was thinking about backup methods, my initial approach was creating a second file called filename.bak, basically a copy with bak extension, I pivoted from that idea and now the program will create a 'Files_Backup' directory and save a backup of the modified files on it.

The logging is super simple, it goes directly to standard output and it's not a lot of info but should be enough to have an idea of whats going on.

I run the code using this command:
```
groovy -cp .\Src\ .\Src\main.groovy <path> <pattern> <replacement string> <optional: output>
``` 

Also I asked a friend for some random files to test it, so I'll send the files too.