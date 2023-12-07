/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 5/4/2022.
 * All rights reserved.
 */
package com.goran.quran.karim.dang.interfaceUtils

import android.content.Intent
import androidx.core.app.ActivityOptionsCompat

interface ActivityResultStarter {
    fun startActivity4Result(intent: Intent, options: ActivityOptionsCompat?)
}