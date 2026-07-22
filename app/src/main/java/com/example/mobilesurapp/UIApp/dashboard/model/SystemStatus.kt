package com.example.mobilesurapp.UIApp.dashboard.model

data class SystemStatus(

    val camera: ConnectionState = ConnectionState.CONNECTING,

    val server: ConnectionState = ConnectionState.CONNECTING,

    val firebase: ConnectionState = ConnectionState.CONNECTING,

    val lastSync: String = "--",

    val pendingUploads: Int = 0

)