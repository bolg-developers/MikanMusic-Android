package com.example.mikan.DB

import android.provider.BaseColumns

object DBContract {
    class TaskEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "tasks"
            const val TASK_NAME = "task_name"
            const val EMAIL = "email"
            const val PASSWORD = "password"
            const val DISPLAYNAME = "displayname"
        }
    }
}
