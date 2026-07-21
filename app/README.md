# Mobile Attendance App

A modern Android attendance management application built with
**Kotlin**, **Jetpack Compose**, **Clean Architecture**, and
**WebSocket-based communication**.

## Features

-   Admin Authentication
-   Employee Management
-   Add Employee
-   Face Registration
-   Attendance Verification
-   Profile Management
-   WebSocket Communication
-   Room Offline Storage
-   Offline Synchronization

## Architecture

``` text
Presentation
├── View
├── ViewModel
Domain
├── UseCases
├── Repository Interfaces
Data
├── Repository Implementations
├── Room Database
├── WebSocket Service
└── API Models
```

## Tech Stack

-   Kotlin
-   Jetpack Compose
-   MVVM
-   Clean Architecture
-   Hilt
-   Room
-   CameraX
-   MediaPipe
-   Gson
-   Coroutines
-   StateFlow
-   WebSocket

## Application Flow

``` text
Login
  ↓
Dashboard
├── Attendance
├── Employee
│   └── Add Employee
└── Settings
    └── Profile
        └── Edit Profile
```

## Companion Backend

https://github.com/ary3141/API_KPMobile

## Installation

``` bash
git clone https://github.com/ary3141/MobileAttendanceApp.git
cd MobileAttendanceApp
```

Open in Android Studio and run.

## Author

**M Dwiva Arya Erlangga**
