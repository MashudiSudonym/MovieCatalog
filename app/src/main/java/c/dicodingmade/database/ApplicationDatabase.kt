package c.dicodingmade.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import c.dicodingmade.database.contentmovie.ContentMovieDao
import c.dicodingmade.database.contentmovie.ContentMovieEntity
import c.dicodingmade.database.contentmoviesearch.ContentMovieSearchDao
import c.dicodingmade.database.contentmoviesearch.ContentMovieSearchEntity
import c.dicodingmade.database.contentmovieupcoming.ContentMovieUpcomingDao
import c.dicodingmade.database.contentmovieupcoming.ContentMovieUpcomingEntity
import c.dicodingmade.database.contentmovieupcoming.ContentUpcomingByDateEntity
import c.dicodingmade.database.contenttvshow.ContentTvShowDao
import c.dicodingmade.database.contenttvshow.ContentTvShowEntity
import c.dicodingmade.database.contenttvshowsearch.ContentTvShowSearchDao
import c.dicodingmade.database.contenttvshowsearch.ContentTvShowSearchEntity
import c.dicodingmade.database.favorite.FavoriteDao
import c.dicodingmade.database.favorite.FavoriteEntity

@Database(
    entities = [
        ContentMovieEntity::class,
        ContentTvShowEntity::class,
        FavoriteEntity::class,
        ContentMovieUpcomingEntity::class,
        ContentUpcomingByDateEntity::class,
        ContentMovieSearchEntity::class,
        ContentTvShowSearchEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun contentMovieDao(): ContentMovieDao
    abstract fun contentTvShowDao(): ContentTvShowDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun contentMovieUpcomingDao(): ContentMovieUpcomingDao
    abstract fun contentMovieSearchDao(): ContentMovieSearchDao
    abstract fun contentTvShowSearchDao(): ContentTvShowSearchDao

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