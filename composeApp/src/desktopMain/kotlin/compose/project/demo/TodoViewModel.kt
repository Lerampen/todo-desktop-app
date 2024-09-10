package compose.project.demo



import androidx.compose.runtime.mutableStateOf
import compose.project.demo.data.TodoItem
import compose.project.demo.data.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    val todos = mutableStateOf<List<TodoItem>>(emptyList())
    val newTodoTitle = mutableStateOf("")

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            todos.value = repository.getAllTodos()
        }
    }

    fun addTodo() {
        val title = newTodoTitle.value
        if (title.isNotBlank()) {
            viewModelScope.launch {
                repository.addTodo(title)
                newTodoTitle.value = ""
                loadTodos()
            }
        }
    }

    fun toggleTodo(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.toggleTodo(id, !isCompleted)
            loadTodos()
        }
    }
}

