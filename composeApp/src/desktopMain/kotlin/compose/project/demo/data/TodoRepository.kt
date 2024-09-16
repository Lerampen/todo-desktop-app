package compose.project.demo.data



import compose.project.demo.data.database.AppDatabase
import compose.project.demo.data.database.TodoItem as DbTodoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepository(private val database: AppDatabase) {
    suspend fun getAllTodos() = withContext(Dispatchers.IO) {
        database.appDatabaseQueries.selectAll().executeAsList().map { it.toTodoItem() }
    }

    suspend fun addTodo(title: String) = withContext(Dispatchers.IO) {
        database.appDatabaseQueries.insertItem(title, isCompleted = 0L)
    }

    suspend fun updateTodo(id: Long,title: String, isCompleted: Boolean) = withContext(Dispatchers.IO){
        database.appDatabaseQueries.updateItem(
            id = id,
            title = title,
            isCompleted = if (isCompleted) 1L else 0L
        )
    }
    suspend fun toggleTodo(id: Long, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        val isCompletedLong = if (isCompleted) 1L else 0L
        val currentTodo = database.appDatabaseQueries.selectById(id).executeAsOne()

        database.appDatabaseQueries.updateItem( title = currentTodo.title , isCompleted = isCompletedLong, id = id)
    }
    private fun DbTodoItem.toTodoItem() = TodoItem(
        id = id,
        title = title,
        isCompleted = isCompleted != 0L
    )
}

data class TodoItem(val id: Long, val title: String, val isCompleted: Boolean)
