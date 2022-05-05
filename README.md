# Baby Meal Tracker

# Smart UX!
![ezgif-3-ca9850ebeb](https://user-images.githubusercontent.com/79411811/166949209-5e022957-88ee-43d3-80b8-ff1a9129f079.gif) 

# Supports Light and Dark Mode!
![ezgif-3-c287479d52](https://user-images.githubusercontent.com/79411811/166951845-8d903f51-6247-41f5-90ab-61b649892e64.gif)



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
