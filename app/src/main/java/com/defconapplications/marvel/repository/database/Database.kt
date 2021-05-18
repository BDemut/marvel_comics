package com.defconapplications.marvel.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.defconapplications.marvel.repository.database.RoomComic

@Dao
interface ComicDao {
    @Query("select * from RoomComic")
    fun getComics(): LiveData<List<Comic>>
    @Query("select * from RoomComic where id = :id")
    fun getComic(id : Int): LiveData<Comic>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComics(vararg comics : RoomComic)

    @Query("delete from RoomComic")
    fun deleteAll()
    @Transaction
    fun replace(vararg comics : RoomComic) {
        deleteAll()
        insertComics(*comics)
    }
}

@Database(entities = [RoomComic::class], version = 1)
@TypeConverters(Converters::class)
abstract class ComicDatabase : RoomDatabase() {
    abstract val comicDao: ComicDao
}



