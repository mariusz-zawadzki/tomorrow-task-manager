package zawadzki.mytaskmanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.preference.PreferenceManager
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.widget.*
import android.widget.Toast




class NoteCreation : AppCompatActivity() {
    private lateinit var txtSpeechInput: EditText
    private lateinit var btnSpeak: ImageButton
    private lateinit var saveNote: Button
    private val REQ_CODE_SPEECH_INPUT = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_creation)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        txtSpeechInput = findViewById(R.id.txtSpeechInput)
        btnSpeak = findViewById(R.id.btnSpeak)
        saveNote = findViewById(R.id.save_note)
        // hide the action bar
//        actionBar?.hide()

        txtSpeechInput.clearFocus()
        btnSpeak.setOnClickListener({ promptSpeechInput() })
        saveNote.setOnClickListener { saveCurrentNote() }
    }

    private fun saveCurrentNote() {

        val defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val email = defaultSharedPreferences.getString(getString(R.string.pref_target_email), "")

        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        i.putExtra(Intent.EXTRA_SUBJECT, "Pomysł.")
        i.putExtra(Intent.EXTRA_TEXT, txtSpeechInput.text.toString())
        try {
            startActivity(Intent.createChooser(i, "Wyślij mailem."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
        }
        if (id == R.id.action_settings) {
            val startSettingsActivity = Intent(this, SettingsActivity::class.java)
            startActivity(startSettingsActivity)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Showing google speech input dialog
     */
    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt))
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext,
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Receiving speech input
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    txtSpeechInput.append(result[0])
                }
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}