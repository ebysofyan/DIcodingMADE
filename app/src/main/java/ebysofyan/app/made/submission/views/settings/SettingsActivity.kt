package ebysofyan.app.made.submission.views.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.services.receiver.DailyReminderAlarmReceiver
import ebysofyan.app.made.submission.services.receiver.ReleaseTodayAlarmReceiver
import kotlinx.android.synthetic.main.toolbar.*


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initActionBar()
    }

    private fun initActionBar() {
        setSupportActionBar(_toolbar)
        supportActionBar?.apply {
            title = getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
        }
        _toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

        private val releaseTodayAlarmReceiver = ReleaseTodayAlarmReceiver()
        private val dailyReminderAlarmReceiver = DailyReminderAlarmReceiver()
        private var switchDailyReminder: SwitchPreference? = null
        private var switchReleaseToday: SwitchPreference? = null

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            switchDailyReminder = findPreference(getString(R.string.daily_reminder))
            switchDailyReminder?.onPreferenceChangeListener = this
            switchReleaseToday = findPreference(getString(R.string.release_today))
            switchReleaseToday?.onPreferenceChangeListener = this
        }

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            when (preference?.key) {
                getString(R.string.daily_reminder) -> {
                    if (newValue as Boolean) dailyReminderAlarmReceiver.scheduleAlarm(context)
                    else dailyReminderAlarmReceiver.cancelAlarm(context)
                }
                getString(R.string.release_today) -> {
                    if (newValue as Boolean) releaseTodayAlarmReceiver.scheduleAlarm(context)
                    else releaseTodayAlarmReceiver.cancelAlarm(context)
                }
            }
            return true
        }

    }
}