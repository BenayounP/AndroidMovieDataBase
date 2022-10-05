# How to

## Make it Compile

### Step 1: Get a TMDB API key
1. Create an account [here](https://www.themoviedb.org/signup) (or [login](https://www.themoviedb.org/login)) on TMDB site 
2. Follow these steps to get your API key [here](https://developers.themoviedb.org/3/getting-started/introduction)

### Step 2: Include API key in the project
Add your API key in `local.properties` file.
```
tmdb_api_key=[YOUR_API_KEY]
```

Et voilà ! 

## Build Variants
There is two flavors:
* `fake`: That works with a **fake** data source
* `prod`: That woks with a **real** data source 

## Tests
 