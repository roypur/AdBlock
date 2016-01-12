#!/usr/bin/env bash
mount -o remount,rw /system

if [ -r $(dirname $0)/hosts ]
then
    cp $(dirname $0)/hosts /system/etc/hosts
fi

# sets the app-user to owner
chown -R $1 $(dirname $0)
chgrp -R $2 $(dirname $0)

mount -o remount,ro /system