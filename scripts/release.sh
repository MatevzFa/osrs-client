#!/bin/bash

NAME_PREFIX="osrs-client"

WINDOWS_NAME="$NAME_PREFIX-windows"
UBUNTU_NAME="$NAME_PREFIX-ubuntu"

if [[ ! -z $TRAVIS_TAG ]]; then
	WINDOWS_NAME="$WINDOWS_NAME-$TRAVIS_TAG"
	UBUNTU_NAME="$UBUNTU_NAME-$TRAVIS_TAG"
fi

mkdir -p release/$WINDOWS_NAME
mkdir -p release/$UBUNTU_NAME

cp install.ps1 release/$WINDOWS_NAME
cp --parents target/$NAME_PREFIX-*.jar release/$WINDOWS_NAME
cp --parents src/main/resources/icon_128.ico release/$WINDOWS_NAME

cp install.sh release/$UBUNTU_NAME
cp --parents target/$NAME_PREFIX-*.jar release/$UBUNTU_NAME
cp --parents src/main/resources/icon_128.png release/$UBUNTU_NAME

cd release
zip -r $WINDOWS_NAME.zip $WINDOWS_NAME
zip -r $UBUNTU_NAME.zip $UBUNTU_NAME
