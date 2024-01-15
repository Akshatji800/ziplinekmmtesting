## ZiplineKMMTesting
This is zipline testing project that uses Kotlin Multiplatform Mobile (KMM) to share code between Android and iOS. The project is based on the [Zipline](https://github.com/cashapp/zipline).

### How to run
1. Clone the project
2. Open the project in Android Studio
3. Wait for the Gradle to finish syncing
4. Have cmake installed (on Mac, you can use brew install cmake)
5. Build QuickJS for your platform using ./.github/workflows/build-mac.sh (replace -mac with the appropriate script for your machine type). This is a one-time process.
6. Type the command ` ./gradlew android:installDebug ` in the android studio terminal and press cmd + return to run it (you are executing here a gradle task - keep in mind that two gradle tasks can't be executed parallelly so wait for other to finish if you have already one running for example: gradle sync). This will install the app on your emulator.
7. Type the command ` ./gradlew presenters:serveDevelopmentZipline --info --continuous ` in the terminal and press cmd + return to run it. This will compile Kotlin/JS and serve it at [[http://localhost:8080/manifest.zipline.json]]. The server will run until you CTRL+C the process.
8. Start the app on your emulator. You should see the app running with the JS bundle served from the local server. You can now make changes to the JS code (RealWorldClockPresenter [file path: presenters/src/jsMain/kotlin/com/hyperboot/ziplinekmmtesting/js.kt] example change: ` TimeFormatter().formatLocalTime(millis = true) ` to ` TimeFormatter().formatLocalTime(millis = false) `) and see them reflected in the app.

### Credits
What is [Zipline](https://github.com/cashapp/zipline)?

Zipline is a library designed to make it easy for Kotlin/JVM and Kotlin/Native programs to incorporate and use Kotlin/JS libraries. This allows for streamlined integration of JavaScript functionality into applications running on the JVM (Java Virtual Machine) or as native applications.

Here are some scenarios where Zipline can be beneficial:

- Continuous Deployment in Mobile Apps:

  Zipline facilitates continuous deployment within mobile apps, similar to what is commonly done for servers and web apps.
  The goal is to make the deployment process faster and more efficient than traditional app store updates, which can be slow and don't guarantee immediate user updates.

- User-Customizable Behavior and Plugin Systems:

  Zipline supports scenarios where users can customize the behavior of the application.
  It provides a framework for implementing plugin systems, allowing developers to extend and enhance the functionality of their applications easily.

- Updating Business Rules:

  Zipline can be used to update business rules dynamically, such as pricing or payment logic.
  Instead of waiting for a new version of the application, developers can update these rules independently and have them take effect without requiring a full app update.

- Fresh Content in Games:

  Zipline is suitable for scenarios where fresh content, such as updates in games, needs to be delivered quickly and seamlessly.
  The library enables embedding the QuickJS JavaScript engine, a lightweight and fast JavaScript engine, which is well-suited for integration into applications.

- How Zipline Works:

  Zipline achieves its functionality by embedding the QuickJS JavaScript engine in Kotlin/JVM or Kotlin/Native programs.
  QuickJS is known for its small size and fast performance, making it suitable for inclusion in applications.
  This embedded JavaScript engine allows seamless communication between Kotlin and JavaScript, enabling developers to fetch and use JavaScript code as easily as fetching data.
  In summary, Zipline aims to simplify the process of incorporating and updating JavaScript functionality in Kotlin-based applications, providing flexibility for various deployment scenarios and dynamic updates.

Thanks to [cash-app](https://github.com/cashapp) for zipline :)