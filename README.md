# SM Share

![kotlin-version](https://img.shields.io/badge/kotlin-2.0.0-blue?logo=kotlin)

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - `commonMain` is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the
      folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for
  your project.

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

**Note:** Compose/Web is Experimental and may be changed at any time. Use it only for evaluation
purposes.
We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack
channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them
on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

## Reference

[Jetpack Room KMP](https://johnoreilly.dev/posts/jetpack_room_kmp/)
[Advanced work with the Snackbar in the Jetpack Compose](https://proandroiddev.com/advanced-work-with-the-snackbar-in-the-jetpack-compose-9bb7b7a30d60)
[FantasyPremierLeague](https://github.com/joreilly/FantasyPremierLeague/tree/main)
[Tivi](https://github.com/chrisbanes/tivi)
[Compose Custom Window frame](https://github.com/amir1376/compose-custom-window-frame)
[Targeting Android in KMP](https://medium.com/kodein-koders/targeting-android-in-a-kotlin-multiplatform-mobile-library-b6ab75469287)
[Understanding and Configuring your Kotlin Multiplatform Mobile Test Suite](https://touchlab.co/understanding-and-configuring-your-kmm-test-suite)