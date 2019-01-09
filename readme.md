# BlackMUD Recipe Manager
This application is used to manage crafting recipes for BlackMUD.  It depends 
on a direct connection to the MySQL database, however the port is not open on the new
server.

It is possible to tunnel to the database.  The following command can be used to forward local
port 3306 to remote port 3306:

```
ssh -N -L localhost:3306:localhost:3306 vylar@blackmud.com
```