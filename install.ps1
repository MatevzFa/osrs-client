<# 
    Builds and installs Old School RuneScape client to a Windows machine.
    The client is installed to %HOME%\.osrs-client\ folder by default.
#>


$INSTALL_DIR = $HOME + "\.osrs-maven\"

mvn clean package

# Create directory for client's files
if (!(Test-Path $INSTALL_DIR)) {
    New-Item -ItemType Directory -Path $INSTALL_DIR
}

$jarName = Get-ChildItem -Path .\target\osrsclient-maven-*.jar -Name
$iconName = 'osrs.ico'

# Copy the client to the location
Copy-Item -Path ".\target\$jarName" -Destination $INSTALL_DIR
Copy-Item -Path ".\src\main\resources\$iconname" -Destination $INSTALL_DIR

# Create an entry in Start Menu
$objShell = New-Object -ComObject ("WScript.Shell")
$objShortCut = $objShell.CreateShortcut($env:USERPROFILE + '\AppData\Roaming\Microsoft\Windows\Start Menu\Programs' + '\Old School RuneScape.lnk')

$objShortCut.TargetPath = $INSTALL_DIR + $jarName
$objShortCut.IconLocation = $INSTALL_DIR + $iconName

$objShortCut.Save()
