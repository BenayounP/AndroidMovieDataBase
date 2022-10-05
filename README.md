# AMDB (Android Movie Data Base)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

<p align="center">
  <img alt="Light" src="./documentation/assets/app_demo.gif" width="60%">
</p>

### TLDR
AMDB is a movie data base based on [TMDB API](https://developers.themoviedb.org/3). 

It's a personal project to be up to date on Android latest "official" tools for a simple but realistic app

### Quick user manual
The app is minimal, you can find more details in the [user manual](./documentation/manual/QuickUserManual.md). 

### HowTo
You need to do some few manipulations to compile and use the app. More details in the [HowTo](./documentation/technical/HowTo.md) document. 

### Android official tools
This app uses:
* Global Architecture: [Android app architecture](https://developer.android.com/topic/architecture)
* Language: [Kotlin](https://developer.android.com/kotlin)
  * Data Stream: [Flow](https://developer.android.com/kotlin/flow)
* Data Layer
  * Fetching data on API: [Retrofit](https://square.github.io/retrofit/)
  * Repository
    * Data Base: [Room](https://developer.android.com/jetpack/androidx/releases/room)
    * Other cache for metadata:  [Proto DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
* UI:
  * Global system: [Compose](https://developer.android.com/jetpack/compose)
  * Image loading: [Coil](https://coil-kt.github.io/coil/compose/)
* Tests :
  * Simulate device for local tests: [Robolectric](http://robolectric.org/)
  * Assert tooling: [Google Truth](https://github.com/google/truth)

## Disclaimer
This is the first POC version for pair reviews

## Contributing
if you want to help in any way, just send me an [email](mailto:pierre@cabnum.fr)

## License
This project is licensed under the [Apache License 2.0](https://opensource.org/licenses/Apache-2.0) 


