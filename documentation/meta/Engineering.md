
# Engineering

## There is no simple app
Even with the simplest business need "get the popular movies and displays it" there is no simplest engineering. You ALWAYS have to make decisions. 
I explain here my choices. 

## Global philosophy

### Do it the Google way with latest tools

### Do it like a real project

## How I did it from scratch

### Some thought
This "simple" app needed many research, a lot of back and forth and so would have been very difficult to do in the classic way: "here's your Jira ticket, see you tomorrow for next step"

### Step 1: create architecture skeleton
* Create ui and data layers/folders

### Step 2 POC: Just a log with a movie title
* Concentrate on data layer. Create a repository without cache that "just" get data on the net
* for ui part: just logs

### Step 3: "just" complete layers walktrough with log
* Add cache mainly with Room (and begin to do some redesign when needed)

### Step 4: Display moviiiiies
* Create Composable, ViewModel. Use Coil to display posters!

### Step 5 : improve system
* Add metadata about movie update (and add DataSource)
* Add Gradle modules

### Step 6
* Do the tests! (and continue improve app)

## Some choices I had to take
### UI
#### Compose
* I had to create Dimensions class to handle global dimensions (padding1, Padding2)
* I had to create Color class to handle global colors with light and dark theme

## Some thought

#### Developing today looks like more than a job of a plumber than being an Algorithm genius
There is only **TWO** `for` loops in this application. And they are trivial. 

I haven't had any optimisation like bisection or what so ever. 

**But** I spent many time to understand how flow works through the layers of the application and how to plug dependencies with Gradle. 

### This simple app took more than two months!
#### What I did
* Many times were not invested in pure production:
  * Master Flow from data source to Compose was easier than I thought but took some time anyway
  * It took almost a week to learn and apply the new DataSource with protobuff cache system.
  * I lost so much time to correctly use the dependencies with Gradle (WHAT A FUCKIN' MESS 🤬)
  * I lost few days figuring I didn't correctly fetch and save data like explained [here](https://developer.android.com/topic/architecture/data-layer/offline-first)
  * Figuring how I will organize documentation took some time too
* I worked part time (2/3 hours by day typically)
* So yes doing same kind of app would be faster now at full time: I roughly say two weeks. But I know if I'm doing this exercise "from scratch" in one year I will have to investigate more than 2/3 weeks. Because tech will change And I will have tile to investigate to learn and master new things (and pretty sure to battle with Gradle again 🤬) 

#### I don't think that you could do this app in two days with the same philosophy
* Because some tasks needed to think and sometimes the best way to figure it out was to have a nice night!
* In the 2010's new technologies in the android world were introduced at an insane rate. It's not the case but there is always a new feature. For this project for example I invested (not lost, invested) almost one week on DataSource (that tool was just ONE month older for the 1.0! 🤯)

### Compose is great but the paint is still wet
Especially on tests!

### Flow works like a charm 

### My work was essentially to create ans uses classes
In another words I didn't do any low level algorithms/kotlin language. I didn't need to optimize some sorting algorithm but fight with the many variations of Flow. 

## How I implemented some principles

### DDD

### SOLID

## About comments
My policy is the following:
* I comment all interface 
* I comment all models linked to business
* I explain the tricky parts or the "illogical" hacks I had to do
* I give a link to useful online resources (like...stack overflow 😉)
* No more, no less