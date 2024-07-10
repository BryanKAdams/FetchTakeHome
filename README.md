# FetchTakeHome

## Product Requirements

Display this list of items to the user based on the following requirements:

- Display all the items grouped by "listId"
- Sort the results first by "listId" then by "name" when displaying.
- Filter out any items where "name" is blank or null.
- The final result should be displayed to the user in an easy-to-read list.

Please make the project buildable on the latest (non-pre release) tools and supporting the current
release mobile OS.

## Example DataSet

```json
[
  {
    "id": 684,
    "listId": 1,
    "name": "Item 684"
  },
  {
    "id": 276,
    "listId": 1,
    "name": "Item 276"
  },
  {
    "id": null,
    "listId": 1,
    "nme": null
  }
]
```

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for
development and testing purposes.

### Prerequisites

- Android Studio Jellyfish | 2023.3.1 Patch 1 or later
- Git
- JDK 11 or newer (recommended for development, although the project targets JVM 1.8)

### Cloning the Repository

1. Open a terminal.
2. Change the current working directory to the location where you want the cloned directory.
3. Type `git clone git@github.com:BryanKAdams/FetchTakeHome.git` and press **Enter** to create your
   local clone.

### Building the Project

1. Open Android Studio.
2. Select `File` > `Open...` from the menu bar.
3. Navigate to the directory where you cloned the project, select it, and click `OK`.
4. Android Studio will import the project and index its contents.
5. Once the indexing and Gradle sync are complete, select `Build` > `Make Project` to build the app.
6. To run the app, select `Run` > `Run 'app'` and choose an emulator or connected device.

### Running Tests

1. To run unit tests, right-click on the `test` folder in the Project view and
   select `Run 'Tests in 'com.bryankeltonadams.fetchtakehometest...''`.
2. To run Android Instrumentation tests, right-click on the `androidTest` folder and
   select `Run 'Tests in 'com.bryankeltonadams.fetchtakehometest...''`.

### Generating APK

1. Select `Build` > `Build Bundle(s) / APK(s)` > `Build APK(s)`.
2. Once the build is complete, Android Studio will notify you. The APK can be found
   in `projectname/app/build/outputs/apk/debug/app-debug.apk`.

### Installing APK on a Device

1. Enable USB debugging on your Android device.
2. Connect your device to your development machine via USB.
3. Open a terminal and navigate to the project's root directory.
4. Run `./gradlew installDebug` to install the debug APK on your device.

### Running Unit Tests from the Command Line

After installing the APK on a device, you can also run the unit tests from the command line to
verify the correctness of your application logic. This method is useful for automated testing
environments or when you prefer not to use the Android Studio UI.

1. Open a terminal.
2. Navigate to the root directory of your project.
3. Run the following command:
   `./gradlew test`

## Developer Notes:

This project is a Jetpack Compose project using Jetpack Navigation, Ktor(w/OkHttp engine), Hilt for
Dependency Injection, Kotlin Coroutines (suspend functions and Flows), Material3 for U.I., and
KotlinX for serialization.

## Stretch Goals

### Considered

- **Advanced Filtering and Sorting**: Allow users to dynamically customize views with more
  sophisticated filtering and sorting options.
- **Search Functionality**: Implement a search bar for querying items by name or other relevant
  properties.
- **Offline Support**: Enable the app to function without an internet connection by fetching and
  storing data for offline use.
- **Detail View**: Enhance user experience on tablets and desktops by introducing a detail view
  through navigation or using `ListDetailPaneScaffold`.

### Implemented

- **Pull to Refresh**: Users can refresh content by pulling down, with the ability to retry after a
  failure.
- **Error Messaging**: A snackbar system has been implemented to display error messages to users.
- **Navigation**: A navigation system has been set up, facilitating the addition of a detail view
  and other navigational elements like a hamburger menu or bottom action bar.
- **Dedicated Database/API**: Future enhancements could include using Firestore for persistent data
  storage and feature expansion, such as adding new items. Additionally, developing a REST API in
  Kotlin could further extend the app's capabilities.

## Other Considerations:

- I considered keeping the dataset as a `List<Item>` instead of a `Map<Int, Item>` for the U.I.
- I could have sorted by name, groupedBy listId, and then flattened back into a list in order,
- I then could have checked the first occurrence of every listId within the list in order to attach
  the header a singular time, this would have prevented me from doing a `forEach` inside the u.i.
  but other methods would be required instead.

## Alternative Libraries I'm familiar with:

- **Ktor -> Retrofit**: I decided to use Ktor in order to grow my familiarity with it and because I
  like using it for its Multiplatform support.

- **Hilt -> Koin**: I decided to use Hilt instead of Koin, this is the one dependency that breaks
  KMP support for this project, I would most likely refactor this later in the future. But I have a
  strong familiarity with Hilt and it's also a more common D.I. library for Android.

- **KotlinX Serialization -> Moshi**: The production applications I work with at Fishbowl use Moshi,
  but I personally prefer KotlinX Serialization, especially when sometimes it's required anyway,
  such is the case with Type Safe Navigation with Jetpack Navigation with Compose.

- **Jetpack Navigation -> No navigation**: This app is so simple, it could have been built without a
  nav library and been just fine. If I needed to mimic navigation, I could have used state within a
  viewModel to change the screen, started a new activity or fragment that had it's own compose
  contents, etc. I prefer Single Activity architecture with Jetpack Navigation though, it's clean to
  me.

- **Material3 -> Material2**: Very similar, and would have been fine implementing Compose components
  in either.

- **Jetpack Compose**: I could have written this with Views, but I much prefer the way Compose is,
  and it's the future in my opinion.

![Screenshot 1](screenshots/Loaded%20List.png "Loaded Success")