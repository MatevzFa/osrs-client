#!/bin/bash

INSTALL_DIR="$HOME/.osrs-client"

mvn clean package

if [[ ! -d $INSTALL_DIR ]]; then
    mkdir $INSTALL_DIR
fi

cp target/osrs-client-*.jar $INSTALL_DIR/osrsclient.jar
chmod +x $INSTALL_DIR/osrsclient.jar

cp src/main/resources/icon_128.png $INSTALL_DIR/icon.png

cat << EOF > $HOME/.local/share/applications/osrs.desktop
[Desktop Entry]
Comment=Old School RuneScape
Terminal=false
Name=Old School RuneScape
Exec=java -jar $HOME/.osrs-maven/osrsclient.jar
Type=Application
Icon=$HOME/.osrs-maven/icon.png
Keywords=old school;oldschool;runescape;osrs;
EOF