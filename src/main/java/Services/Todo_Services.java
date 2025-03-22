package Services;

import MyModel.Todo;
import Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Todo_Services {

    @Autowired
    private TodoRepository todoRepository;


    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(String id) {
        return todoRepository.findById(id).orElse(null);
    }

    public Todo createTodo(Todo user) {
        return todoRepository.save(user);
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }

    public Todo updateTodo(String id, Todo updatedTodo) {
        Optional<Todo> existingTodo = todoRepository.findById(id);

        if (existingTodo.isPresent()) {
            Todo todo = existingTodo.get();

            if (updatedTodo.getTodo_title() != null) {
                todo.setTodo_title(updatedTodo.getTodo_title());
            }
            if (updatedTodo.getTodo_desc() != null) {
                todo.setTodo_desc(updatedTodo.getTodo_desc());
            }

            return todoRepository.save(todo);
        } else {
            return null;
        }
    }
}
