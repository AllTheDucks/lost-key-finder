# Lost Key Finder

Find unused language pack keys in Blackboard building blocks.

## Building

This project uses [gradle](https://gradle.org) for building from source.

 - GNU/Linux & MacOSX `./gradlew installdist`
 - Windows `gradlew.bat installdist`
 
The resulting files will be located in `build/install/lkf/bin/`.

## Running

Run `lkf` inside the root directory of a building block project. The 
application will scan the project for language pack bundles, and then
proceed to find unused keys in those language pack bundles. 
Alternatively, a directory can be passed in as the first argument, which
`lkf` will proceed to scan.

The application will ignore the build directory if a `build.gradle` file 
is present. Binary files of type jar, war, zip, png, jpeg and gif are 
also ignored. Finally, files and directories that begin with a `.` are
also ignored.

Language bundle files are identified by the combination of the file 
having a `.properties` extension and where the parent folder structure 
is `WEB-INF/bundles/`.