package org.ocular.utils

import android.os.Looper

object MainThreadExecutor extends LooperThreadExecutor(Looper.getMainLooper)