package com.gra_dus.uioschildsampleerror.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext

object MainDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run()
            } catch (err: Throwable) {
                throw err
            }
        }
    }
}

object IODispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(
            dispatch_get_global_queue(
                DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(),
                0.toULong()
            )
        ) {
            try {
                block.run()
            } catch (err: Throwable) {
                throw err
            }
        }
    }

}

actual val mainDispatcher: CoroutineDispatcher = MainDispatcher

actual val ioDispatcher: CoroutineDispatcher = IODispatcher