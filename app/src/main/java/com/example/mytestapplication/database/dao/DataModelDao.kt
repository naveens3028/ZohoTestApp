package com.example.mytestapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytestapplication.database.model.DataModel

@Dao
interface DataModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAvg(sectionItem: DataModel)

    @Query("SELECT * FROM MydataTable")
    fun getAll(): DataModel

}

