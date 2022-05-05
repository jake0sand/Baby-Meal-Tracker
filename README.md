# SimpleFeedingTracker

-------------- *Technologies Utilised* --------------

- MVVM Architecture

- Android Jetpack
  - Fragments
  - LiveData
  - ViewBinding
  - RecyclerView with Headings
  - Room
  - Navigation Component
     - SafeArgs
  - DataStore for saved preferences/settings

  
- Single Activity w/ Fragments
  
- Kotlin Coroutines
  
- CRUD
  
- 100% Kotlin


---------------------------------------------------------------------------------------------------------------------------

    // Navigation Component
    implementation 'androidx.navigation:navigation-fragment-ktx:2.4.1'
    implementation 'androidx.navigation:navigation-ui-ktx:2.4.1'

    // Room components
    implementation "androidx.room:room-runtime:$room_version"
    kapt "androidx.room:room-compiler:$room_version"
    implementation "androidx.room:room-ktx:$room_version"
    androidTestImplementation "androidx.room:room-testing:$room_version"


    // Preferences DataStore
    implementation "androidx.datastore:datastore-preferences:1.0.0"

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0'

    // Coroutine Lifecycle Scopes
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"

    // Kotlin components
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.10"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"
