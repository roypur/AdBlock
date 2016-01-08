#!/usr/bin/env bash
mount -o remount,rw /system

if [ -r /system/etc/hosts ]
then
    cp /system/etc/hosts $(dirname $0)/old-hosts
else
    echo "127.0.0.1 localhost\n::1 localhost" > $(dirname $0)/old-hosts
fi

if [ -r $(dirname $0)/hosts ]
then
    cp $(dirname $0)/hosts /system/etc/hosts
fi

# sets the app-user to owner
chown -R $1 $(dirname $0)
chgrp -R $2 $(dirname $0)