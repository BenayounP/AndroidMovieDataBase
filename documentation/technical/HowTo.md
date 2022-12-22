# How to

## Build Variants
There is two flavors:
* `fakeServer`: That works with a **fake** server with static data that you can find [here](../../data/src/main/java/eu/benayoun/androidmoviedatabase/data/model/fake/FakeTmdbMovieListGenerator.kt). It gives you once in two refresh a server error
* `tmdbServer`: That works with the **real** TMDB API server

## Get real data from TMDB server
By default this app works with fake data from fake server.

### Step 1: Get a TMDB API key
1. Create an account [here](https://www.themoviedb.org/signup) (or [login](https://www.themoviedb.org/login)) on TMDB site
2. Follow these steps to get your API key [here](https://developers.themoviedb.org/3/getting-started/introduction)

### Step 2: Include API key in the project
Add your API key in `local.properties` file.
```
tmdb_api_key="[YOUR_API_KEY]"
```

Et voilà !

## Tests
 