package cz.eman.kaalsample.feature.login.infrastructure.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cz.eman.kaalsample.feature.login.infrastructure.db.dao.UserDao
import cz.eman.kaalsample.feature.login.infrastructure.db.entity.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Room database for the User process which providing tables and DAOs
 * @author eMan a.s.
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        private const val DB_NAME = "user.db"
        const val DB_KEY_NAME = "DB_KEY"

        /**
         * Returns Singleton instance of this AppDatabase.
         * AppDatabase is created if needed.
         *
         * @param context Context of application
         * @param factory SafeRoom
         * @return Singleton instance of AppDatabase
         */
        fun getInstance(
            context: Context,
            factory: SupportSQLiteOpenHelper.Factory?
        ): UserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, factory).also {
                    INSTANCE = it
                }
            }

        /**
         * Creates Singleton instance of this AppDatabase if needed.
         *
         * @param context Context of application
         * @param openHelperFactory SupportOpenHelper factory
         * @return Singleton instance of this AppDatabase
         */
        private fun buildDatabase(
            context: Context,
            openHelperFactory: SupportSQLiteOpenHelper.Factory? = null
        ): UserDatabase {

            return Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java, DB_NAME
            )
                .addCallback(object : Callback() {

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch {
                            val appDb = getInstance(context, openHelperFactory)
                            appDb.userDao.insert(populateDefaultUserData())
                        }
                    }
                })
                .openHelperFactory(openHelperFactory)
                .build()
        }

        private fun populateDefaultUserData() = UserEntity("john", "travolta")

    }

}
