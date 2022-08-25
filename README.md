[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

# AMDB (Android Movie Data Base)

### TLDR
AMDB is a movie data base based on [TMDB API](https://developers.themoviedb.org/3). 

It's a personal project to be up to date on Android latest "official" tools for a simple but realistic app

### What it does
It get a list of movie on IMDB, cache data to be consulted offline and allow to give personal rate to movies.

## How to build on your environment
Add your API key in `local.properties` file.
```
tmdb_api_key=YOUR_API_KEY
```

### Android official tools
This app uses:
* Global Architecture: [Android app architecture](https://developer.android.com/topic/architecture)
* Language: [Kotlin](https://developer.android.com/kotlin)
  * Data Stream: [Flow](https://developer.android.com/kotlin/flow)
* Fetching data on API: [Retrofit](https://square.github.io/retrofit/)
* Data Base: [Room](https://developer.android.com/jetpack/androidx/releases/room)
* UI: [Compose](https://developer.android.com/jetpack/compose)
* Tests :
  * Simulate device for local tests: [Roboelectric](http://robolectric.org/)
  * Assert tooling: [Google Truth](https://github.com/google/truth)

## Disclaimer
This is the first POC version for pair reviews

## Contributing
if you want to help in any way, just send me an [email](mailto:pierre@cabnum.fr)

## License
This project is licensed under the [Apache License 2.0](https://opensource.org/licenses/Apache-2.0) 

## Dev Diary

#### 16/08/22
First day. Configured project and already some questions (Will i make many gradle modules?) 

#### 17/08/22
Began from data source. Some retrofit. Copy pasted some old code that worked with...coroutines, not flow!

#### 18/08/22
Worked on retrofit and flow. Found some nice medium article. Created Bibliography and almost overengineered the app with a Flow to monitor internet connection status

#### 22/08/22
Added hilt and...project cannot compile. Seems to have some incompatibility between Compose Compiler and/or gradle and/or kotlin version. Tried "random" values (and look at how it was made on an recent project) to make it work! 

#### 23/08/22
Ok the repository is created and "ok" to create the Flooooooow (so much preparation before the fun)

#### 24/08/22
Flow works (with logs). Preparing Room for caching  

#### 25/08/22
Caching works with Room. Next Step: Compose!