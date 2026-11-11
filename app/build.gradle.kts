plugins {
    id("com.android.application")
}

android {
    namespace = "ro.pub.cs.systems.eim.practicaltest01var07"
    compileSdk = 37

    defaultConfig {
        applicationId = "ro.pub.cs.systems.eim.practicaltest01var07"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
