package io.atreydos.route4me.data.net.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toApiFormat(): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)