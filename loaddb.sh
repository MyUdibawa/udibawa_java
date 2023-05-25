#!/bin/bash
kill $(cat db.pid)
git pull
mvn spring-boot:run -Drun.profiles=aws &
echo $! > db.pid