#!/usr/bin/env bash


echo ${APP_USER} > $(dirname $0)/test

if [ -r /system/etc/hosts ]
then
    cp /system/etc/hosts ${APP_CACHE}/old-hosts
else
    echo "127.0.0.1 localhost\n::1 localhost" > ${APP_CACHE}/old-hosts
fi

# sets the owner to app-user
chown -R ${APP_USER} ${APP_CACHE}
chgrp -R ${APP_GROUP} ${APP_CACHE}