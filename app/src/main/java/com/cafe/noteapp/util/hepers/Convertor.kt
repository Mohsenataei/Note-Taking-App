package com.cafe.noteapp.util.hepers

import android.content.Context
import com.cafe.noteapp.R
import com.cafe.noteapp.util.hepers.DateHelper.getCurrentDateInMilli
import com.cafe.noteapp.util.provider.BaseResourceProvider
import javax.inject.Inject


/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class Convertor @Inject constructor(
    private val resourceProvider: BaseResourceProvider
) {

    companion object {
        private const val SECOND_MILLIS = 1000
        private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
        private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
        private const val DAY_MILLIS = 24 * HOUR_MILLIS
    }


    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now: Long = getCurrentDateInMilli()
        if (time > now || time <= 0) {
            return null
        }

        // TODO: localize
        var diff = now - time

        return when {
            diff < MINUTE_MILLIS -> {
                resourceProvider.getString(R.string.just_now)
            }
            diff < 2 * MINUTE_MILLIS -> {
                resourceProvider.getString(R.string.a_minute_ago)
            }
            diff < 50 * MINUTE_MILLIS -> {
                "${diff / MINUTE_MILLIS} ${resourceProvider.getString(R.string.minute_ago)}"
            }
            diff < 90 * MINUTE_MILLIS -> {
                resourceProvider.getString(R.string.an_hours_ago)
            }
            diff < 24 * HOUR_MILLIS -> {
                "${diff / HOUR_MILLIS} ${resourceProvider.getString(R.string.hours_ago)}"
            }
            diff < 48 * HOUR_MILLIS -> {
                resourceProvider.getString(R.string.yesterday)
            }
            else -> {
                "${diff / DAY_MILLIS}  ${resourceProvider.getString(R.string.day_ago)}"
            }
        }
    }
}