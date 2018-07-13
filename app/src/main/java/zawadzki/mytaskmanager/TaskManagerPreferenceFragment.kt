package zawadzki.mytaskmanager

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

class TaskManagerPreferenceFragment() : PreferenceFragmentCompat() {
    override fun onCreatePreferences(bundle: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }
}