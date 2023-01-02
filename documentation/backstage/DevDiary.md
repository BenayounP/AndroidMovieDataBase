# Dev Diary

## August 2022

### 16/08/22
First day. Configured project and already some questions (Will I make many Gradle modules?)

### 17/08/22
Began from data source. Some Retrofit. Copy pasted some old code that worked with...coroutines, not Flow!

### 18/08/22
Worked on retrofit and Flow. Found some nice medium article. Created Bibliography and almost over engineered the app with a Flow to monitor internet connection status

### 22/08/22
Added Hilt and...project cannot compile. Seems to have some incompatibility between Compose Compiler and/or Gradle and/or kotlin version. Tried "random" values (and look at how it was made on an recent project) to make it work!

### 23/08/22
Repository created and "ok" to create the Flooooooow (so much preparation before the fun)

### 24/08/22
Flow works (with logs). Preparing Room for caching

### 25/08/22
Caching works with Room. Next Step: Compose!

### 26/08/22
And Compose basically works (displaying title). Having the feeling that once bases are here (a good repository for example), things go faster

### 29/08/22
Included Coil (so we can display movie posters) in few lines and....what? 10 minutes? 😯. And fought with colors for 1 hour...🤷

### 30/08/22
What have I done? I wasn't satisfied by the way I processed the TMDB API response. It was the movie list if ok and an empty list if not. Now I have a complex system of sealed class to  process success or failure and trace the reason (no internet, exception etc...).

### 31/08/22
What have I done? (bis) Ok I had to save some meta data (the current source data for movies: Internet or cache for example). And If I try Proto dataSource?

## September 2022

### 02/09/22
Yes no log from yesterday. What have I done? (ter) There is so boiler plates code for using proto Datasource. So much work for "just" save some data...

### 05/09/22
And the cache with both room and datasource works (basically)?. Just forgot to fully implement timestamp for last successful connection  

### 07/09/22
(No work yesterday) Fully implement timestamp for last successful connection ! No I have to display it on homeScreen   

### 08/09/22
Worked yesterday and today on displaying data on the screen with compose. Miss `dimens.xml` badly

### 12/09/22
Worked on documentation. So many "where do I put this information"?

### 13/09/22
Created Gradle modules. I feel like I'm lost in the middle of a move....

### 14/09/22
* The data layer module works fine. Just exposing the repository interface is sooooo rewarding.
* Did some cleaning and I came across this official Google article: [Build an offline-first app](https://developer.android.com/topic/architecture/data-layer/offline-first)
-> 🤯. Ok the process I used to fetch and save data was "totally" wrong...

### 15/09/22
Now that I have the skeleton of the app it's easier (and funnier) to change/add things. Now we have a proper _offline first_ app system. Playing with a refresh status now. 

### 19/09/22
Did some important work on the app update display and to implements an update when user ~~overscroll(you down scroll when movie grid is already at the first element)~~ pull to refresh\
It's quite fascinating that when you have the good foundation the functionality implementation is so faaaast.

### 20/09/22
My own pull to refresh system bugs and I fought against the `Modifier.pointerInput` ... and I lost...for today!

### 21/09/2022
Battled almost all day with the pull to refresh system and I won! 🍷

### 23/09/2022
Found a nice medium post about life cycle and allow app to refresh screen onResume. Began to work on tests and fake classes in data Layer

### 26/09/22
And I tried to test my data classes so I needed to test some Flow methods. Not easy at all....(maybe [Turbine](https://github.com/cashapp/turbine) can help?)

### 27/09/22

Ok, Turbine wasn't needed (for now). Finalized to test the main data class `DefaultTmdbRepository`.
Did some QA tests using fake datasource and fixed some bugs in...DataStore(🤷). The app is almost
ready to be presented to the world!

### 30/09/22

Oops, forgot to write on previous days.

I worked on flavors. Surprisingly I wasn't worked many time with this concept.

Used it to do a `fake` flavor to handle a FakeDatasource to easier some QA.

## October 2022

### 03/10/22

Worked on documentation and....noticed that there was in my architecture. A proof that documentation
can be useful and not just for help others understand what you did

### 04/10/22

I reorganized the classes in data layer to be cleaner: Felt like a electrician trying to wire some
strange material. Also did some documentation. OMG it's complicated to work with video in Markdown!

### 05/10/22
Worked on documentation. It begins to looks like a good one. By the way, I realised that I had never worked on projects with good documentation. From another perspective, it is very time consuming. 

### 06/10/22
Worked on Gradle files (in order to simplify them).   

Worked also on dependencies. To be honest I do not totally understand how the plugins and libraries work together. So i was fail and try before manage to make all of this work together.   
And begin to writing tests for Compose. Didn't worked...

### 07/10/22
Worked on Compose test. Well...it's complicated.  

In order to make work these tests, I battled again with dependencies in `build.gradle`. 

### 08/10/22
Yes it's saturday. BUT THESE FUCKING TESTS WEREN'T WORKING. And now it's working. And my week end is saved.

### 09/10/22
Continue working on Compose test. It's a shame that you can't easily identify a composable without adding a testTag.

### 10/10/22
Finished Compose tests. 

Did you know that by default the method `assertTextContains` check if the two texts are equals? And that you have to add `substring = true` to effectively check that the text **contains** the other? 🤦‍️

Also wrote many documentation. 

And did the local test for Room database with Robolectric. Did this in 30 minutes. Why was it so fast? because it took me 3 days on another side project 🤦‍️🤦‍️🤦‍️🤦‍️🤦‍️

### 11/10/22
Coding in the train. Got an strange exception while trying to do an instrumented test of the DataStore ...🤷

### 15/10/22
(Yes I'm coding the week end, bad habit). 

Using Robolectric to test the DataStore (to avoid the annoying exception).

Works like a charm...until I try to save two data in a row...Have a strange problem that could be linked to...[Windows](https://issuetracker.google.com/issues/203087070#comment3) ?

And...I recoded many thing to bypass this problem. Change the way the DataStore (it's nicier now), and redone test (with some redesign of some tests with `TmdbSourceStatusTester`)

### 16/10/22
(Yes I'm coding the week end, again. Bad habit again).

Finalized at last DataStore tests. 

Fought with the windows bug (see above) but it works now!

### 31/10/22

Long time, no see! Google has made its traditional Android Developer Summit last week and there was
some [important news for android and especially Compose](https://android-developers.googleblog.com/2022/10/modern-android-development-at-ads-22.html)
.

So I dealt with them today. I'm at the same time glad and concerned about the fact that there is
always some improvements in Android development.

Glad that the Google's dev team improve regularly the tools wit this time
the [Gradle BOM for Compose](https://developer.android.com/jetpack/compose/setup#using-the-bom) that
simplify the mess with gradle management of dependencies.

Concerned that in less than 3 months of development I have to develop some functionality (
the `pull to refresh` one) to see just after that...Google propose it as
an [experimental version](https://developer.android.com/reference/kotlin/androidx/compose/material/pullrefresh/package-summary#(androidx.compose.ui.Modifier).pullRefresh(androidx.compose.material.pullrefresh.PullRefreshState,kotlin.Boolean))

## November 2022

Ain't nobody got time for that

## December 22

### 23/12/22

"Just" upgraded dependencies version and fought with tests that weren't works anymore. But found a
bug that had gone under the radar.

### 30/12/22

"Finished" documentation. I'm sure there is many typo but it's doooone.

## January 2023

### 02/01/23

Public Release date!
