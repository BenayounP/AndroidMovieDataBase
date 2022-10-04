#Technical Presentation

I recommend you to try the app or at least to read the [Quick User Manual](../manual/QuickUserManual.md) before reading this documentation!

### From user Journey to Implementation
* The application basically fetches data from [TMDB API](https://developers.themoviedb.org/3) with retrofit. 
  * Interface: [TmdbDataSource](../../data/src/main/java/eu/benayoun/androidmoviedatabase/data/source/network/TmdbDataSource.kt)
  * default implementation: [RetrofitTmdbDataSource](../../data/src/main/java/eu/benayoun/androidmoviedatabase/data/source/network/retrofit/RetrofitTmdbDataSource.kt)
* It saves this in a dataBase with metadata. Metadata are about last internet update and eventual errors
  * Global Interface: [TmdbDataSource](../../data/src/main/java/eu/benayoun/androidmoviedatabase/data/source/network/TmdbDataSource.kt)
  * Movie List
    * Interface: 