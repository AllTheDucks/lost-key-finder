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
