package com.gra_dus.uioschildsampleerror.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val mainDispatcher:CoroutineDispatcher = Dispatchers.Main

actual val ioDispatcher:CoroutineDispatcher = Dispatchers.IO