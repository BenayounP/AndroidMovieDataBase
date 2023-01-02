# Engineering choices

## There is no simple app

Even with the simplest business need: "get the popular movies and displays it" there is no simplest
engineering. You ALWAYS have to make decisions. I explain here my choices.

## Global philosophy

### Do it like a real project

That's the main point. I wanted to take the simplest project from a business point of view and to
treat it as a real, professional project, with the highest level of engineering rigor possible.

### Do it the Google way with latest tools

Android was developed by Google, and while the company used to allow developers a lot of freedom in
the early days of the OS, this is no longer the case.

Now, the company provides official tools and guidelines that are increasingly becoming the standard
way of doing things.

This is why I use these [Android Tools](../technical/AndroidTools.md).

## How I did it from scratch

### Disclaimer

This "simple" app needed many research, a lot of back and forth and so would have been very
difficult to do in the classic way: "here's your Jira ticket, see you tomorrow for next step"

### Step 1: create architecture skeleton

* Create ui and data layers/folders

### Step 2 POC: Just a log with a movie title

* Concentrate on data layer. Create a repository without cache that "just" get data on the net
* For UI part: just logs

### Step 3: "just" complete layers walktrough with log

* Add cache mainly with Room (and begin to do some redesign when needed)

### Step 4: Display movies (at last)

* Create Composable, ViewModel. Use Coil to display posters!

### Step 5 : improve system

* Add metadata about movie update (and add DataSource)
* Add Gradle modules

### Step 6

* Do the tests! (and continue improve app)

### Step 7

* Do the documentation (especially long because I never had the occasion to do this properly)

## Some choices I had to take

### UI

#### Compose

* I had to
  create [Dimensions class](../../app/src/main/java/eu/benayoun/androidmoviedatabase/ui/theme/Dimensions.kt)
  to handle global dimensions (padding1, padding2)
* I had to
  create [Color class](../../app/src/main/java/eu/benayoun/androidmoviedatabase/ui/theme/Color.kt)
  to handle global colors with light and dark theme

## About comments

My policy is the following:

* I comment all interface class
* I comment all models linked to business
* I explain the tricky parts or the "illogical" hacks I had to do
* I give a link to useful online resources (like...stack overflow 😉)
* No more, no less