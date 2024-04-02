
# Mvvm Setup

This is a simple app in Android with Integrated API. In this project we have covered everything that is required to create a fully functional app.

## Features

### MVVM Setup
#### What is MVVM?
MVVM stands for Model-View-ViewModel. It is a software design pattern that separates the user interface (UI) from the underlying data. This makes it easier to develop and maintain complex applications.

1. Model:
The Model represents the data and business logic of the application. It can consist of data classes, repositories, or network service layers that handle data retrieval and manipulation. The Model is responsible for interacting with the data sources and providing the necessary methods to access and update the data.

2. View:
The View represents the user interface (UI) components of the application, such as activities, fragments, or custom views. It is responsible for rendering the UI and capturing user interactions. In MVVM, the View observes changes in the ViewModel and updates its state accordingly. The View should be kept as lightweight as possible, focusing primarily on UI rendering and event handling.

3. ViewModel:
The ViewModel acts as an intermediary between the View and the Model. It exposes data and state required by the View and provides methods to handle user interactions. The ViewModel retrieves data from the Model and exposes it to the View through observable properties or LiveData objects. It also communicates with the Model to update data based on user actions. The ViewModel should be decoupled from the View, ensuring that it does not have any direct references to UI components.

References
- https://medium.com/@jecky999/mvvm-architecture-in-android-using-kotlin-a-practical-guide-73f8de1d9c58


### HILT
Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project

#### Setup

The current version of the Hilt can be found here.

For the project-level build.gradle put this inside:

plugins {
  id 'com.google.dagger.hilt.android' version '2.46.1' apply false
}
For the app-level build.gradle put this inside respectively to sections:

plugins {
  // other plugins - order is important
  id 'kotlin-kapt'
  id 'com.google.dagger.hilt.android'
}

android {
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}

dependencies {
  // For hilt Implementation
  implementation 'com.google.dagger:hilt-android:2.46.1'
  kapt 'com.google.dagger:hilt-compiler:2.46.1'

  // For instrumentation tests
  androidTestImplementation "com.google.dagger:hilt-android-testing:2.46.1"
  kaptAndroidTest "com.google.dagger:hilt-android-compiler:2.46.1"

  // For local unit tests
  testImplementation 'com.google.dagger:hilt-android-testing:2.46.1'
  kaptTest 'com.google.dagger:hilt-compiler:2.46.1'
}

kapt {
  correctErrorTypes true
}

For more details of implementation refer below link
- https://tomas-repcik.medium.com/dependency-injection-with-hilt-in-android-development-e23fc636d65c

#### Coroutines

A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.

###### Setup


dependencies {
def coroutines_version = "1.6.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
}

###### Launch
launch is used to fire and forget coroutine. It's perfect for cases where you don't need to compute any result. Here's a simple example:

import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch { 
        delay(1000L) 
        println("Hello from Coroutine!")
    }
    println("Hello from Main Thread!")
    Thread.sleep(2000L)
}

In this code, we’re launching a new coroutine using GlobalScope.launch. Inside this coroutine, we're delaying for one second (1000 milliseconds), and then printing a message.

##### Async
async is used when you need a result computed in a coroutine. It starts a new coroutine and returns a Deferred<T>, which is a non-blocking future that represents a promise to provide a result later. Here's an example:

import kotlinx.coroutines.*

fun main() {
    GlobalScope.launch {
        val result = async { 
            computeResult() 
        }
        println("Computed result: ${result.await()}")
    }
    Thread.sleep(2000L)
}

suspend fun computeResult(): Int {
    delay(1000L)
    return 42
}

In this code, we’re launching a new coroutine and starting a computation inside it using async. This computation is a suspend function computeResult, which delays for one second and then returns the number 42. After the computation, we print the result using await.

##### RunBlocking

runBlocking is a bridge between non-coroutine world and coroutine world. It's a way to start top-level main coroutine. Here's how to use it:

import kotlinx.coroutines.*

fun main() = runBlocking { 
    launch { 
        delay(1000L) 
        println("Hello from Coroutine!")
    }
    println("Hello from Main Thread!")
}

In this code, we’re starting a main coroutine using runBlocking, and inside this coroutine, we're launching a new coroutine.

References
- https://medium.com/hprog99/mastering-kotlin-coroutines-with-practical-examples-1544e0bdbd64


#### API Integration using Retrofit

##### Setup

Navigate to your build.gradle (Module: app) file and add the following dependencies:

dependencies {
   def retrofit_version = "2.9.0"
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
    implementation 'com.squareup.okhttp3:okhttp:4.9.3'
}

Sync your project to ensure the new dependencies are added.

##### Creating Retrofit and ApiService Singletons:

In order to ensure that we have a single instance of Retrofit and ApiService throughout the application, we can implement the singleton pattern. Create a new Kotlin file, e.g., ApiClient.kt, and implement the following:

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitClient.retrofit.create(ApiService::class.java)
    }
}

With this implementation, you have a single instance of Retrofit and ApiService that can be accessed throughout your application.

##### Defining the ApiService Interface:

Now that we have our singleton pattern in place for the Retrofit instance, let’s define the ApiService interface that outlines the API endpoints and their respective HTTP methods. Create a new Kotlin file, e.g., ApiService.kt, and implement the following:

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts/{id}")
    fun getPostById(@Path("id") postId: Int): Call<Post>
}

References
- https://medium.com/@imkuldeepsinghrai/api-calls-with-retrofit-in-android-kotlin-a-comprehensive-guide-e049e19deba9

