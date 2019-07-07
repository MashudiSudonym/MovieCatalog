package c.dicodingmade.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import c.dicodingmade.database.contentMovie.ContentMovieDao
import c.dicodingmade.database.contentMovie.ContentMovieEntity
import c.dicodingmade.database.contentTvShow.ContentTvShowDao
import c.dicodingmade.database.contentTvShow.ContentTvShowEntity
import c.dicodingmade.database.favorite.FavoriteDao
import c.dicodingmade.database.favorite.FavoriteEntity

@Database(
    entities = [ContentMovieEntity::class, ContentTvShowEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun contentMovieDao(): ContentMovieDao
    abstract fun contentTvShowDao(): ContentTvShowDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getDatabase(context: Context): ApplicationDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "Dicoding MADE ApplicationDatabase"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}