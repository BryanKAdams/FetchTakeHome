# FetchTakeHome

example list:
[{"id": 684, "listId": 1, "name": "Item 684"},
{"id": 276, "listId": 1, "name": "Item 276"}]

Display this list of items to the user based on the following requirements:

Display all the items grouped by "listId"
Sort the results first by "listId" then by "name" when displaying.
Filter out any items where "name" is blank or null.
The final result should be displayed to the user in an easy-to-read list.

Please make the project buildable on the latest (non-pre release) tools and supporting the current
release mobile OS.


Developer Notes:
This project is a Jetpack Compose project using Jetpack Navigation, Ktor(w/OkHttp engine), Hilt for
Dependency Injection, Kotlin Coroutines (suspend functions and Flows), Material3 for U.I.
KotlinX for serialization.

Alternatives I'm familiar with:

Ktor -> Retrofit, I decided to use Ktor in order to grow my familiarity with it and because I like
using it for its Multiplatform support.

Hilt -> Koin, I decided to use Hilt instead of Koin, this is the one dependency that breaks KMP
support for this project, I would most likely refactor this later in the future. But I have a strong
familiarity with Hilt and it's also a more common D.I. library.

KotlinX Serialization -> Moshi. The production applications I work with at Fishbowl use Moshi, but
I personally prefer KotlinX Serialization, especially when sometimes it's required anyway, such is 
the case with Type Safe Navigation with Jetpack Navigation with Compose.

Jetpack Navigation -> No navigation, this app is so simple, it could have been built without a nav 
library and been just fine. If I needed to mimic navigation, I could have used state within a viewModel
to change the screen, started a new activity or fragment that had it's own compose contents, etc.
I prefer Single Activity architecture with Jetpack Navigation though, it's clean to me.

Material3 -> Material2, very similar, and would have been fine implementing Compose components in either

Jetpack Compose -> I could have written this with Views, but I much prefer the way Compose is,
and it's the future in my opinion.