#!/bin/bash

echo "************************************************************"
echo "Setting up users..."
echo "************************************************************"

# create root user
nohup gosu mongodb mongosh opencdx --eval "db.createUser({user: 'admin', pwd: 'admin', roles:[{ role: 'root', db: 'admin' }, { role: 'read', db: 'opencdx' }]});"

# create app user/database
nohup gosu mongodb mongosh opencdx --eval "db.createUser({ user: 'appuser', pwd: 'appuser', roles: [{ role: 'readWrite', db: 'opencdx' }, { role: 'read', db: 'local' }]});"

echo "************************************************************"
echo "Shutting down"
echo "************************************************************"
nohup gosu mongodb mongosh admin --eval "db.shutdownServer();"