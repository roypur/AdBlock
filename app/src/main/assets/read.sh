#!/usr/bin/env bash

if [ -r /system/etc/hosts ]
then
    cp /system/etc/hosts $(dirname $0)/old-hosts
else
    echo "127.0.0.1 localhost\n::1 localhost" > $(dirname $0)/old-hosts
fi

# sets the app-user to owner
chown -R ${APP_USER} $(dirname $0)
chgrp -R ${APP_GROUP} $(dirname $0)