#!/usr/bin/env python3

import os
import subprocess
from subprocess import call

APK_NAME = "../apks/app-prod-release.apk"
DEST_NAME = "apk/semar-new.apk"
DROPBOX = "~/dotfiles/dropbox/uploader.sh"

dir_path = os.path.dirname(os.path.realpath(__file__))

call("cd " + dir_path, shell=True)

print("Currently in " + dir_path)

call("../gradlew clean assembleProdRelease -p ..", shell=True)
call(DROPBOX + " -p upload " + APK_NAME + " " + DEST_NAME, shell=True)
result = subprocess.check_output(DROPBOX + " share " + DEST_NAME, shell=True)

call('echo ' + str(result) + '| pbcopy', shell=True)

print(result)
print("🎉 All Done!")


