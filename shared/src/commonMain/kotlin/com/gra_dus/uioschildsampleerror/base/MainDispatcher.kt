package com.gra_dus.uioschildsampleerror.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

expect val mainDispatcher: CoroutineDispatcher

val mainCoroutineScope = object : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.Main

}

expect val ioDispatcher: CoroutineDispatcher
