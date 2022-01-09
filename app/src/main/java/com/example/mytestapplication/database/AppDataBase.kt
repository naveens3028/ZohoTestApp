package com.example.mytestapplication.database

import android.content.Context
import androidx.room.*
import com.example.mytestapplication.database.dao.DataModelDao
import com.example.mytestapplication.database.model.*


@Database(
    entities = [DataModel::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(ResultConvertor::class, PicturesConvertor::class, InfoConvertor::class,
    NameConvertor::class, RegisteredConvertor::class, CoordinatesConvertor::class, DobConvertor::class, IdConvertor::class,
    LocationConvertor::class, LoginConvertor::class, TimezoneConvertor::class, ListConverters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract val dataModelDao: DataModelDao

    companion object {

        val DB_NAME = "mydatatest"
        private var INSTANCE: AppDataBase? = null
        private val sLock = Any()

        fun getInstance(context: Context): AppDataBase? {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java, DB_NAME
                    )
                        .addMigrations()
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE
            }
        }
    }

}