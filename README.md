# Compose Directions

A navigation library for compose built op top of Jetpack Compose Navigation.

## Features

- Type safe navigation
- passing an arbitrary result back from composable which can survive process death.

Feel free to experiment and add your own features.

## Installation
1. Add 'https://jitpack.io' to the ```settings.gradle``` file of your project. If you have configured your project such that all project level repositories are defined in the **project level ```build.gradle```** file, then, instead of adding it to the settings.gradle file, add it to the **project level build.gradle** file.

```groovy
// settings.gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' } // add this
    }
}
```

2. Add the jitpack repository to your root gradle file and the dependency to your `build.gradle` file:

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}

dependencies {
    implementation 'com.github.vgupta98:compose-directions:1.0.0'
}
```

Bug reports and pull requests are welcome. There is a lot room for optimizations and features!

## License

    MIT License

    Copyright (c) 2024 Vishal Gupta
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.