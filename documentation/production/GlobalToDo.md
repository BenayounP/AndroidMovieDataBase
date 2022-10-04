# Global ToDo

## Architecture/Meta
* Add [Fastlane](https://docs.fastlane.tools/getting-started/android/setup/) for CI? 
* The FakeRepository class is not `internal` and we should change that (exposing just an interface ?)

## Code cleanness
* Change the "compose" package name?
* Compare code with [now in android](https://github.com/android/nowinandroid)
* Replace the ugly companion object used for dimensions on compose with...what? (that's the problem)

## Data
* Paging Data from TMDB API?
* Try to make "all" classes internal?

## DI
* Replace `@provides` by `@binds` when necessary like [here](https://www.valueof.io/blog/inject-provides-binds-dependencies-dagger-hilt)


## UI
* Add a config screen to change theme (light or dark)?

## Tests
* Use lib [Turbine](https://github.com/cashapp/turbine) to test Flow?
* Test Room DB and data store (with robolectric!)
* Test UI (especially the push to refresh) with espresso
* Complete test part in HowTo.md








 