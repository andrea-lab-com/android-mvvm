# android-mvvm

Demo Android app that consume [3 endpoints]:
- https://jsonplaceholder.typicode.com/posts
- https://jsonplaceholder.typicode.com/users
- https://jsonplaceholder.typicode.com/comments

The app have two screens:
- A first screen to present a list of posts.
- A second screen presenting post details.

The first time the app will get all data from API, and store that in a local DB, 
and register the timestamp of the last call per endpoint.

The data in local DB will be active for 5 minutes, during this time if the user 
open one screen the app doesn't call to API.

After the expiration of data(more of 5 minutes), if the user open one screen, the 
app will call to API again and update local DB.

The App use single source of truth principle, all of data present to the user will be 
from local DB.

It has been built with clean architecture principles, Repository Pattern and MVVM
pattern as well as Architecture Components.

Min Api Level : 24 (https://developer.android.com/about/dashboards)

Build System : [Gradle](https://gradle.org/)

## Table of Contents

- [Architecture](#architecture)
- [Testing](#testing)
- [Libraries](#libraries)
- [Demo](#demo)

## Architecture

The Application is split into a three layer architecture:
- Presentation
- Domain
- Data

![Architecture Flow Diagram](imgs/arch_flow.png)

The 3 layered architectural approach is majorly guided by clean architecture which provides
a clear separation of concerns with its Abstraction Principle.

Koin is used for dependency injection.

#### Presentation

```app``` contains the UI files and handles binding of DI components from other modules.
Binding of data is facilitated by jetpacks data binding by serving data from the viewmodel
to the UI.The data being received is part of a viewstate class that has properties contained in the
relevant state.

#### Domain

The ```domain``` module contains domain model classes which represent the
data we will be handling across presentation and data layer.

Use cases are also provided in the domain layer and orchestrate the flow 
of data from the data layer onto the presentation layer and a split into
modular pieces serving one particular purpose.

The UseCases use a ```BaseUseCase``` interface that defines the parameters its taking in and 
output this helps in creating fakes using in testing.

#### Data

- ```data```

Handles data interacting with the network and is later serverd up to the presentation layer through 
domain object

Handles persistence of object with Room ORM from.This module is responsible for handling all local related
logic and serves up data to and from the presentation layer through domain objects.

## Testing

Types of Test:
- Unit test
- UI Test (Espresso)

Each module has its own tests(Unit Test) except for the ```domain``` module which is catered for since its
part of the behavior under test.

All server responses in the tests are served by mock web server by appending relative urls to
the localhost and the connected port as the base url.

In the ``data`` module the responses are mocked using the mockwebserver and verified that they
are what we expect. And in this module an memory database is being used to run the tests,this 
makes it a little faster compared to an actual db.
In this module the repositories are been tested.

In the ```app``` module there are:
- Unit tests for the Viewmodels
- UI tests for the UI Screens.

The test instrumentation app uses modules that have been swaped(using Koin) with fakes for:

- The network module so as to run requests on localhost with mockwebserver, this removes flakiness
compared to relying on actual  data from the real server aspects such as internet connection or
network service might bring up issues
- An in memory database for local data that also allows main thread queries since tests
should also be fast and we are just asserting stuff works.

View models testing on live data were guided by this [article](https://proandroiddev.com/how-to-easily-test-a-viewmodel-with-livedata-and-coroutines-230c74416047)
 
## Libraries

Libraries used in the whole application are:

- [Jetpack](https://developer.android.com/jetpack)🚀
  - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way 
  and act as a channel between use cases and ui
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - support library that allows binding of UI components in  layouts to data sources,binds character details and search results to UI
  - [Room](https://developer.android.com/training/data-storage/room) - Provides abstraction layer over SQLite
- [Retrofit](https://square.github.io/retrofit/) - type safe http client 
and supports coroutines out of the box.  
- [Moshi](https://github.com/square/moshi) - JSON Parser,used to parse 
requests on the data layer for Entities and understands Kotlin non-nullable 
and default parameters
- [okhttp-logging-interceptor](https://github.com/square/okhttp/blob/master/okhttp-logging-interceptor/README.md) - logs HTTP request and response data.
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines,provides `runBlocking` coroutine builder used in tests
- [Truth](https://truth.dev/) - Assertions Library,provides readability as far as assertions are concerned
- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - web server for testing HTTP clients ,verify requests and responses on the star wars api with the retrofit client.
- [Leak Canary](https://square.github.io/leakcanary/) - Leak Detection Library
- [Material Design](https://material.io/develop/android/docs/getting-started/) - build awesome beautiful UIs.🔥🔥
- [Firebase](https://firebase.google.com/) - Backend As A Service for faster mobile development.
  - [Crashylitics](https://firebase.google.com/docs/crashlytics) - Provide Realtime crash reports from users end.
- [Koin](https://github.com/InsertKoinIO/koin) - A pragmatic lightweight dependency injection framework for Kotlin
- [Robolectric](http://robolectric.org/) - Unit test on android framework.
- [Espresso](https://developer.android.com/training/testing/espresso) - Test framework to write UI Tests
- [recyclerview-animators](https://github.com/wasabeef/recyclerview-animators) - Recycler View Animations
- [AboutLibraries](https://github.com/mikepenz/AboutLibraries) -provide info on used open source libraries.
- [Stetho](https://github.com/facebook/stetho) - debug bridge
- [Kiel](https://github.com/ibrahimyilmaz/kiel) - Kiel RecyclerView Adapter Builders

## Demo

|<img src="imgs/sh11.png" width=200/>|<img src="imgs/sh12.png" width=200/>|<img src="imgs/sh13.png" width=200/>|


## License

 ```
   Copyright 2021 Cesar Sosa
   
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 ```
