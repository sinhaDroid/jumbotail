# Mausam

## Preparing the project
Let's put some bullets on what good architectures should be:
1) Independent
2) Testable
3) Re-usable
4) Extendable

To help us with all that, I have used some famous libraries.

* Butterknife - I came across Butterknife a few weeks ago when I started looking for dependency injection libraries for Android. First let me be clear, BUTTERKNIFE IS NOT A DEPENDENCY INJECTION LIBRARY. It happened to find Butterknife while experimenting with Dagger. Butterknife injects code at compile time. It is very similar to the work done by Android Annotations. Essentially Butterknife allows you to annotate the inflation of your views and OnClickListeners  so that you can get back to writing the code that really matters.

* Glide - Glide is a fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface. Glide supports fetching, decoding, and displaying video stills, images, and animated GIFs. Glide includes a flexible API that allows developers to plug in to almost any network stack.

* Google Play Services Location API - The Google Play services location APIs are preferred over the Android framework location APIs (android.location) as a way of adding location awareness to your app. This class shows you how to use the Google Play services location APIs in your app to get the current location, get periodic location updates, and look up addresses.

* MPAndroidChart - MPAndroidChart :zap: is a powerful & easy to use chart library for Android. It runs on API level 8 and upwards.

* There are also some Android-specific libraries:
    * AppCompat
    * RecyclerView
    * SupportLibrary
    
* SinhaLibrary - This is my personal customized library which set up all custom widgets, jackson converter, crashlytics and various other things that requires for every common app. It still has lot to add like dagger features and realm setup.

## Good Android app architecture
On Android we have many things — activities, fragments, services, notifications, databases and others. With a general approach, everything is quite simple in the beginning, but as things grow more complex — activities and fragments become larger and larger, they incorporate lots of logic, talk to different web services and in the end it becomes very difficult to understand what’s going on by just looking at the code.

Following clean architecture principles, let’s imagine how could we structure things and assign responsibilities.

* View - Activity & Fragment — these two would be only doing UI and glue different parts of the app together, after all these are screens that user will see, Views. It would be tricky to unit test — it depends on things being displayed on the screen.
* Presenter - Somebody needs to do the logic for screens, like handling button clicks, doing analytics tracking, talking to network services. We could make a Controller, but on Android screens are created by the system, so we can’t really control them. That’s why need a Presenter. It’s job is to present things to the View. Presenter, on the other hand, only does the logic needed for the screen to work, and View always passes all it’s events to the Presenter by calling some method. And all this can be covered by unit tests.
* Model - The MVP’s Model in our architecture could be represented by Entities — these would some simple objects that we receive from our database or server. In Java they are called POJO(Plain Old Java Objects), because they don’t contain any logic, only fields.

## Building with Gradle
It’s also a good idea to put all links to maven and all global dependencies into buildscript right here, and remove it from all internal build.gradle files so we would have a single place to edit it later on.
Another nice addition to a build system I like to do is moving all dependencies into a separate file dependencies.gradle. Now, I can use this script in any projects. No need to declare libraries again and again.

## Activity and Fragments
I always use single activity or fragment that extends to AppCompatActivity or android.v4.support.Fragment.
Each activity or fragment has oncreate method which is use to initialize views and set layout. So, I have added abstract method, which takes the layout Ids to set with UI.
In, my case MausamActivity and MausamFragment does these jobs. They have common toast message and progressbar.

## Web services
I have used retrofit and jackson coverter as Gson does not support case insensitivity for key names, while jackson does. And Jackson is faster to read large response than Gson, while Gson is faster to for small response length.

## Offline
To save data locally, we have sqlite, preferences and realm. Realm works on no sql concept and it's way faster than sqlite. Here, I have used shared preferences which stores data on key pair basis. Here, I have converted my data into single object and converted it into string and store it in preferences.
To retrieve data, i have converted string back to object and using. I don't prefer to work in sql and it's too slow query process. If apk size doesn't matter for you then, you should use realm.

## Services
Calling api in every single hour using services in background.

## Points to be taken 
1) First time, in splash screen, checking three cases
    * if app comes from links then it will take cityname
    * will ask permission from gps and get city name
    * if above condition fails, then bengaluru will be default city. (This can have better approach than this.)
    
2) Calling weather api in splash screen itself and storing in preferences.

3) when application open it will fetch data from local db

4) if we refresh, it will call the api, if api fails or network is not connected, it will show local data

5) in every hour api will get called and data will get store locally.

