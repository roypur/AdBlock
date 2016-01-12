#!/usr/bin/env bash
mount -o remount,rw /system

if [ -r $(dirname $0)/hosts ]
then
    cp $(dirname $0)/hosts /system/etc/hosts
fi

# sets the app-user to owner
chown -R ${APP_USER} $(dirname $0)
chgrp -R ${APP_GROUP} $(dirname $0)

mount -o remount,ro /system