package c.dicodingmade.database.contentTvShow

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContentTvShowEntity::class], version = 1, exportSchema = false)
abstract class ContentTvShowDatabase : RoomDatabase() {
    abstract fun contentTvShowDao(): ContentTvShowDao

    companion object {
        @Volatile
        private var INSTANCE: ContentTvShowDatabase? = null

        fun getDatabase(context: Context): ContentTvShowDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContentTvShowDatabase::class.java,
                    "ContentTvShowEntity Database"
                ).fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}