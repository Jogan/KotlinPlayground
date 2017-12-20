# Kotlin Playground (work-in-progress)

## Android development

App which attempts to use the latest cutting edge libraries and tools. As a summary:

 * Entirely written in [Kotlin](https://kotlinlang.org/)
 * Unidirectional data flow (fully reactive architecture)
 * Uses [RxJava](https://github.com/ReactiveX/RxJava) 2
 * Uses [RxAndroid](https://github.com/ReactiveX/RxAndroid) and [RxKotlin](https://github.com/ReactiveX/RxKotlin) Extensions 
 * Uses all of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/): Room, LiveData and Lifecycle-components
 * Uses [dagger-android](https://google.github.io/dagger/android.html) for dependency injection
 * Uses [Glide](https://github.com/bumptech/glide) for image loading
 * Uses [Epoxy](https://github.com/airbnb/epoxy) for RecyclerView layouts with Kotlin support

## Development setup

First off, you require the latest Android Studio 3.0 (or newer) to be able to build the app.

### Code style

This project uses [ktlint](https://github.com/shyiko/ktlint), provided via
the [spotless](https://github.com/diffplug/spotless) gradle plugin, and the bundled project IntelliJ codestyle.

If you find that one of your pull reviews does not pass the CI server check due to a code style conflict, you can
easily fix it by running: `./gradlew spotlessApply`, or running IntelliJ/Android Studio's code formatter.