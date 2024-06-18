# SM Share (👷🔧️WIP👷⛏)

![kotlin-version](https://img.shields.io/badge/kotlin-2.0.0-blue?logo=kotlin)

[<img src="https://upload.wikimedia.org/wikipedia/commons/7/78/Google_Play_Store_badge_EN.svg"
alt="Get it on Google Play Store"
height="80">](#)
[<img src="https://developer.apple.com/assets/elements/badges/download-on-the-app-store.svg"
alt="Get it on App Store"
height="80">](#)

SMShare is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop. The UI of SMSahre is
written completely in Jetpack Compose and shareed accross iOS, Android and Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.

* `/iosApp` contains iOS applications.

## Building

You will require the following rools to build and run SMSahre

- Android SAndroid Studio Jellyfish | 2023.3.1 or higher
- Java 17 installed
- Xcode Version >> 15.4

<details><summary><h4>Android</h4></summary>

#### Using Android Studio

Open the KMP project using Android Studio, select the `composeApp` run configuration and click on
run.

#### Using Fleet

Fleet uses Smart Mode to detect and configure the Android run configuration for you.You can modify
in the [Run Json file](.fleet/run.json)

#### Using Command line

To run the Android app, use the command below or select the `composeApp` configuration on Android
Studio and click on run.

``bash

``
 </details>




<details>
  <summary>Desktop</summary>
#### Using Fleet
Fleet uses Smart Mode to detect and configure the Android run configuration for you.You can modify in the [Run Json file](.fleet/run.json)
#### Using Command line
```bash
./gradlew desktopRun -DmainClass=com.jerryokafor.smshare.MainKt --quiet
```
 </details>

<details>
  <summary>iOS</summary>
#### Using XCode
Open `iosApp/iosApp.xcworkspace` from Xcode and run.
Note: You need to have run `pod install` from the `iOSApp` directory app
#### Using Fleet
Fleet uses Smart mode to configure iOS run configuration for you. You can modify in the [Run Json file](.fleet/run.json)
 </details>

<details>
  <summary>Server</summary>
#### Using Fleet
Fleet uses Smart mode to detect and configure the various run configurations for you for iOS, Android, Desktop and Server. Ensure the You have Xcode, Android Studio, iOS Simulator, Android Emulator all installed or Physical iphone and Android Devices plugged in.

#### From Command Line

To run the server code, you can run the gradle command below:

```bash
./gradlew :server:run
```

Open: `http://0.0.0.0:8080/` to see the response from the server.

#### Testing Locally from your phone, run the following command:

##### Android

Ensure yuor android phone is connected and adb is running, then run

```bash
adb reverse tcp:8080 tcp:8080
```

Then open `http://localhost:8080` from your android phone to test. You can now also use this in the
base url of
your api requests for Android.

#### iOS

For iOS, you can follow this
guide : [How to open a localhost website on iPhone / iOS](https://maxschmitt.me/posts/localhost-iphone-ios)
 </details>

## Code Style (Todo)

## Todo

- [ ] Add KtLint and Detekt
- [ ] Add Detekt
- [ ] Add Kover
- [ ] Add Codecov
- [ ] Add Code style to readme
- [ ] Add Contributors section
- [ ] Add License section
- [X] Add Room KMP database
- [X] Add Datastore

## Reference

- [Jetpack Room KMP](https://johnoreilly.dev/posts/jetpack_room_kmp/)
- [Advanced work with the Snackbar in the Jetpack Compose](https://proandroiddev.com/advanced-work-with-the-snackbar-in-the-jetpack-compose-9bb7b7a30d60)
- [FantasyPremierLeague](https://github.com/joreilly/FantasyPremierLeague/tree/main)
- [Tivi](https://github.com/chrisbanes/tivi)
- [Compose Custom Window frame](https://github.com/amir1376/compose-custom-window-frame)
- [Targeting Android in KMP](https://medium.com/kodein-koders/targeting-android-in-a-kotlin-multiplatform-mobile-library-b6ab75469287)
- [Understanding and Configuring your Kotlin Multiplatform Mobile Test Suite](https://touchlab.co/understanding-and-configuring-your-kmm-test-suite)
- [KMM-PicSplash](https://github.com/cvivek07/KMM-PicSplash)

## Contributions

If you've found an error in this sample, please file an issue.

Patches are encouraged and may be submitted by forking this project and
submitting a pull request.Since this project is still in its very early stages,
if your change is substantial, please raise an issue first to discuss it.

## License

[MIT License](/LICENSE.tx)
