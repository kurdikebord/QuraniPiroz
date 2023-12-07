package com.goran.quran.karim.dang.utils.receivers

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.goran.quran.karim.dang.utils.votd.VOTDUtils

class AlarmPermissionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED == intent?.action && VOTDUtils.isVOTDTrulyEnabled(
                context
            )
        ) {
            VOTDUtils.enableVOTDReminder(context)
        }
    }
}
