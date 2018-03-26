#!/bin/bash


echo "================== publish  jar ==================="

project_dir=`pwd`
echo "***** current project dir : ${project_dir}"

work_dir="bin/"

echo "***** make work dir if not exists :  ${work_dir}"
mkdir -p $work_dir

echo "***** enter work dir : ${work_dir}"
cd $work_dir

echo "***** clear the work dir"

rm -rf *

cp -r ../build/classes/java/main/com ./

echo "***** remove unused classes"
rm -rf com/ly/freemarker/gen/

echo "***** recreate  jar "

jar cvf fm-v0.1.jar -C . .

rm -rf com

echo "================== create  jar complete ==================="



