package compose.project.demo.data.database

import app.cash.sqldelight.db.SqlDriver
import compose.project.demo.data.database.AppDatabase

class AppDatabaseHelper(private val databaseDriver: SqlDriver) {
    fun getDatabase(): AppDatabase = AppDatabase(databaseDriver)

}
