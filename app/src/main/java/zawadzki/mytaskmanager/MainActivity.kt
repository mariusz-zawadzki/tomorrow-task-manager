package zawadzki.mytaskmanager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var showTaskList: Button
    lateinit var createTask: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showTaskList = findViewById(R.id.show_task_list)
        createTask = findViewById(R.id.create_task)

        showTaskList.setOnClickListener {
            Toast.makeText(this, "Show task list", Toast.LENGTH_SHORT).show()
        }
        createTask.setOnClickListener {
            val intent = Intent(this, NoteCreation::class.java).apply {
            }
            startActivity(intent)
        }
    }
}
