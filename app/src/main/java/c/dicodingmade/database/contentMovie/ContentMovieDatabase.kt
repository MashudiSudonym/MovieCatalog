package c.dicodingmade.database.contentMovie

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContentMovieEntity::class], version = 1, exportSchema = false)
abstract class ContentMovieDatabase : RoomDatabase() {
    abstract fun contentMovieDao(): ContentMovieDao

    companion object {
        @Volatile
        private var INSTANCE: ContentMovieDatabase? = null

        fun getDatabase(context: Context): ContentMovieDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContentMovieDatabase::class.java,
                    "ContentMovieEntity Database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}