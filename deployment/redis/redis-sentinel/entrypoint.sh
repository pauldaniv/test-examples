#!/bin/bash

CONF_DIR=/etc/redis/conf

if [[ $1 == "standalone" ]]; then
    redis-server
elif [[ $1 == "cluster" ]]; then
    redis-server $CONF_DIR/redis.conf & redis-server $CONF_DIR/sentinel.conf --sentinel
else
    echo "Illegal argument. Try to run with 'standalone' or 'cluster'"
    exit 1;
fi
