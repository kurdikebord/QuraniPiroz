/*
 * Created by Faisal Khan on (c) 23/8/2021.
 */

package com.goran.quranipiroz.utils.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.goran.quranipiroz.utils.sharedPrefs.SPVerses;
import com.goran.quranipiroz.utils.votd.VOTDUtils;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) && VOTDUtils.isVOTDTrulyEnabled(context)) {
            VOTDUtils.enableVOTDReminder(context);
        }
    }
}
