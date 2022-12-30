# Post Partum

Yes it's not a post mortem because this project is just released! It's just the beginning of it's
life.

So here some thoughts.

## Developing today is more than a plumber's job than being an algorithm genius

There is only **TWO** `for` loops in this application. And they are trivial.

I haven't had any optimisation like bisection or what so ever.

**But** I spent many time to understand how Flow works through the layers of the application and how
to plug dependencies with Gradle.

## This simple app took more than two months!

* Many times were not invested in pure production:
    * Master Flow from data source to Compose was easier than I thought but took some time anyway
    * It took almost a week to learn and apply the new DataSource with protobuff cache system.
    * I lost so much time to correctly use the dependencies with Gradle (WHAT A FUCKIN' MESS 🤬)
    * I lost few days figuring I didn't correctly fetch and save data like
      explained [here](https://developer.android.com/topic/architecture/data-layer/offline-first)
    * Figuring how I will organize documentation took some time too
* I worked part time (2/3 hours by day typically)
* So yes doing same kind of app would be faster Now at full time: I roughly say two weeks.
* If I'm doing this exercise "from scratch" in one year I will have to investigate more than 2/3
  weeks. Because tech will change And I will have tile to investigate to learn and master new
  things (and pretty sure to battle with Gradle again 🤬)

## I don't think that you could do this app in two days with the same philosophy

* Because some tasks needed to think and sometimes the best way to figure it out was to have a nice
  night!
* In the 2010's new technologies in the android world were introduced at an insane rate. It's not
  the case but there is always a new feature. For this project for example I invested (not lost,
  invested) almost one week on DataSource (that tool was just ONE month older for the 1.0! 🤯)

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

So I have to say that [Flow](https://developer.android.com/kotlin/flow) works like a charm, evn in
the most complicated situations: the unit tests.