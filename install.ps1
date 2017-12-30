
$INSTALL_DIR = $HOME + "\.osrs-maven\"

mvn clean package

# Create directory for client's files
if (!(Test-Path $INSTALL_DIR)) {
    New-Item -ItemType Directory -Path $INSTALL_DIR
}

# Copy the client to the location
Copy-Item -Path ".\target\osrsclient-maven-1.0-SNAPSHOT.jar" -Destination $INSTALL_DIR
Copy-Item -Path ".\src\main\resources\icon_128.ico" -Destination $INSTALL_DIR

# Create an entry in Start Menu
$objShell = New-Object -ComObject ("WScript.Shell")
$objShortCut = $objShell.CreateShortcut($env:USERPROFILE + '\AppData\Roaming\Microsoft\Windows\Start Menu\Programs' + '\RuneScape' + '\Old School RuneScape.lnk')
$jarPath = $INSTALL_DIR + 'osrsclient-maven-1.0-SNAPSHOT.jar'
$objShortCut.TargetPath = $jarPath
$objShortCut.IconLocation = $INSTALL_DIR + 'icon_128.ico'
$objShortCut.Save()