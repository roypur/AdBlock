#!/bin/bash
mount -o remount,rw /system

if [ -r /system/etc/hosts ]
then
    cp /system/etc/hosts $(dirname $0)/old-hosts
    chmod 777 $(dirname $0)/old-hosts
else
    echo "127.0.0.1 localhost\n::1 localhost" > $(dirname $0)/old-hosts
    chmod 777 $(dirname $0)/old-hosts
fi

if [ -r $(dirname $0)/hosts ]
then
    cp $(dirname $0)/hosts /system/etc/hosts
fi

