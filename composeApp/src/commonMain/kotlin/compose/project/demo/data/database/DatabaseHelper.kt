package compose.project.demo.data.database

import compose.project.demo.data.database.AppDatabase

expect class DatabaseHelper {
    fun createDatabase(): AppDatabase
}


