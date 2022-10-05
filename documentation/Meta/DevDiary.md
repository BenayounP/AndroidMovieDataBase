# Dev Diary

## August 2022

### 16/08/22
First day. Configured project and already some questions (Will I make many gradle modules?)

### 17/08/22
Began from data source. Some retrofit. Copy pasted some old code that worked with...coroutines, not flow!

### 18/08/22
Worked on retrofit and flow. Found some nice medium article. Created Bibliography and almost overengineered the app with a Flow to monitor internet connection status

### 22/08/22
Added Hilt and...project cannot compile. Seems to have some incompatibility between Compose Compiler and/or gradle and/or kotlin version. Tried "random" values (and look at how it was made on an recent project) to make it work!

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
(no code yesterday) Fully implement timestamp for last successful connection ! No I have to display it on homeScreen   

### 08/09/22
Worked yesterday and today on displaying data on the screen with compose. Miss `dimens.xml` badly

### 12/09/22
Worked on documentation. So many "where do I put this information"?

### 13/09/22
Created gradle module. I feel like I'm lost in the middle of a move....

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
Ok, Turbine wasn't needed (for now). Finalized to test the main data class `DefaultTmdbRepository`. Did some QA tests using fake datasource and fixed some bugs in...DataStore(🤷). The app is almost ready to be presented to the world!

### 30/09/22
Oops, forgot to write on previous days.

I worked on flavors. Surprisingly I wasn't worked many time with this concept. 

Used it to do a `fake` flavor to handle a FakeDatasource to easier some QA. 

### 03/10/22
Worked on documentation and....noticed that there was in my architecture. 
A proof that documentation can be useful and not just for help others understand what you did 

### 04/10/22
I reorganized the classes in data layer to be cleaner: Feeled like a electrician trying to wire some strange material.
Also did some documentation. OMG it's complicated to work with video in Markdown!