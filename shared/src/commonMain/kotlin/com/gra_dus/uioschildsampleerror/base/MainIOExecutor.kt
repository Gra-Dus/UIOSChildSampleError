package com.gra_dus.uioschildsampleerror.base

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class MainIOExecutor : IExecutorScope, CoroutineScope {

    private val  job: CompletableJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = job + mainDispatcher

    override fun cancel() {
        job.cancel()
    }

    protected fun <T> collect(
        flow: Flow<T>, collect: (T) -> Unit
    ) {
        launch {
            flow
                .flowOn(ioDispatcher)
                .collect {
                    collect(it)
                }
        }
    }
}