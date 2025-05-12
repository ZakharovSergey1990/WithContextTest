This is a sample Android application built with Jetpack Compose, showcasing why withContext(Dispatchers.Default) is still important — even when using libraries like Retrofit, Room, or Ktor that handle threading internally.

Features
Jetpack Compose UI with user-friendly controls

Input number of users to generate and save to Room database

Retrieve the oldest user from the database

Frame time measurement with Choreographer to detect skipped frames

Optional usage of withContext(Dispatchers.Default) in UseCases

Logs for dispatcher tracing (Thread.currentThread())

Tech Stack

Kotlin

Jetpack Compose

Room (SQLite)

MVVM architecture

Kotlin Coroutines

Choreographer for measuring frame drops
