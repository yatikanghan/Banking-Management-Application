package MyModel;


import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tbl_todo")
public class Todo {
    @Id
    private String id;
    private String todo_title;
    private String todo_desc;
    private String todo_date;
    private String todo_admin_id;


    public Todo(String id, String todo_title, String todo_desc, String todo_date, String todo_admin_id) {
        this.id = id;
        this.todo_title = todo_title;
        this.todo_desc = todo_desc;
        this.todo_date = todo_date;
        this.todo_admin_id = todo_admin_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTodo_title() {
        return todo_title;
    }

    public void setTodo_title(String todo_title) {
        this.todo_title = todo_title;
    }

    public String getTodo_desc() {
        return todo_desc;
    }

    public void setTodo_desc(String todo_desc) {
        this.todo_desc = todo_desc;
    }

    public String getTodo_date() {
        return todo_date;
    }

    public void setTodo_date(String todo_date) {
        this.todo_date = todo_date;
    }

    public String getTodo_admin_id() {
        return todo_admin_id;
    }

    public void setTodo_admin_id(String todo_admin_id) {
        this.todo_admin_id = todo_admin_id;
    }
}
