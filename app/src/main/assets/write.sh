#!/usr/bin/env bash
mount -o remount,rw /system

if [ -r ${APP_CACHE}/hosts ]
then
    cp ${APP_CACHE}/hosts /system/etc/hosts
fi

# sets the owner to app-user
chown -R ${APP_USER} ${APP_CACHE}
chgrp -R ${APP_GROUP} ${APP_CACHE}

mount -o remount,ro /system