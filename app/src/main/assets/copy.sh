#!/bin/bash
mount -o remount,rw /system
cp $(dirname $0)/hosts /system/etc/hosts