# Post Partum

Yes it's not a post mortem because this project is just released! It's the beginning of it's life.

So here some thoughts.

## Developing today is more than a plumber's job than being an algorithm genius

There is only **TWO** `for` loops in this application. And they are trivial.

I haven't had to do any algorithm optimisation like bisection or what so ever.

**But** I spent many time to understand how Flow works through the layers of the application and how
to plug dependencies with Gradle.

## This simple app took more than two months (of real work)!

* Many times were not invested in pure production:
  * Master Flow from data source to Compose was easier than I thought but took some time anyway
  * It took almost a week to learn and apply the new DataSource with protobuff cache system.
  * I lost so much time to correctly use the dependencies with Gradle (**WHAT A FUCKIN' MESS** 🤬)
  * I lost few days figuring I didn't correctly fetch and save data like
    explained [here](https://developer.android.com/topic/architecture/data-layer/offline-first)
  * Figuring how I will organize documentation took some time too
* I worked part time (2/3 hours by day typically)
* So yes doing same kind of app would be faster now at full time: I roughly say two weeks.
* If I'm doing this exercise "from scratch" in one year I will have to investigate more than two
  weeks. Because tech will change and I will have tile to investigate to learn and master new
  things (and pretty sure to battle with Gradle again 🤬)

## I don't think that you could do this app in two days with the same philosophy

* Because some tasks needed to think and sometimes the best way to figure it out was to have a nice
  night!
* During the 2010's, the android world saw a rapid introduction of new technologies. While this pace
  has slowed down, there is still a constant stream of new features being released. For this project
  for example I invested (not lost, invested) almost one week on DataSource, which had only been
  released a month prior

## Compose is great but the paint is still wet

### What I loved:

* Coil integration in few lines/hours
* The grid system so easily done
* Integrated UI tests that already works well

### Where the paint is still wet

* It's easy to have so many parameter in a composable
* Tests

## Flow works like a charm

I used the antediluvian [Asynctask](https://developer.android.com/reference/android/os/AsyncTask),
tried [services](https://developer.android.com/guide/components/services), struggled
with [RxAndroid](https://github.com/ReactiveX/RxAndroid) and have some issues with the begining
of [coroutine](https://developer.android.com/kotlin/coroutines) tests.

[Flow](https://developer.android.com/kotlin/flow), need some work to be well integrated but after
that it works like a charm, even in the most complicated situations: the unit tests.