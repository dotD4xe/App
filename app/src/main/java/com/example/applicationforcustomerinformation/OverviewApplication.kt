package com.example.applicationforcustomerinformation

import android.app.Application
import com.example.applicationforcustomerinformation.data.ItemRoomDatabase

class OverviewApplication : Application(){
    val database: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
}