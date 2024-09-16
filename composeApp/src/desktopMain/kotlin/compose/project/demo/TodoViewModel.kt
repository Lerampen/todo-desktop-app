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
    val editingTodo = mutableStateOf<TodoItem?>(null)

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            todos.value = repository.getAllTodos()
        }
    }

    fun startEditingTodo(todoItem: TodoItem) {
        editingTodo.value = todoItem
    }
     fun cancelEditing() {
         editingTodo.value = null
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

    fun editTodo(id: Long, newTitle : String) {
        viewModelScope.launch {
            repository.updateTodo(id = id, title = newTitle, isCompleted = editingTodo.value?.isCompleted ?: false)
            loadTodos()
        }
    }


    fun toggleTodo(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            val todo = todos.value.find { it.id == id }
            todo?.let {
                repository.updateTodo(id = id, title = it.title, isCompleted = isCompleted)
            }
//            repository.toggleTodo(id, isCompleted)
            loadTodos()
        }
    }
}

