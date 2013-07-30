#!/bin/bash
mkdir -p c:/temp/data/rs1 c:/temp/data/rs2 c:/temp/data/rs3
mongod --replSet m101 --logpath "1.log" --dbpath c:/temp/data/rs1 --port 27017 --smallfiles &
mongod --replSet m101 --logpath "2.log" --dbpath c:/temp/data/rs2 --port 27018 --smallfiles &
mongod --replSet m101 --logpath "3.log" --dbpath c:/temp/data/rs3 --port 27019 --smallfiles &
