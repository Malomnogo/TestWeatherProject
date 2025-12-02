package com.malomnogo.testweatherproject

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

class FakeRunAsync(private val order: Order) : RunAsync {

    private var cachedUiBlock: (Any) -> Unit = {}
    private var cachedResult: Any = ""

    fun returnResult() {
        order.add(RUN_ASYNC_UI)
        cachedUiBlock.invoke(cachedResult)
    }

    override fun <T : Any> start(
        coroutineScope: CoroutineScope,
        background: suspend () -> T,
        uiBlock: (T) -> Unit,
    ) = runBlocking {
        order.add(RUN_ASYNC_BACKGROUND)
        val result = background.invoke()
        cachedResult = result
        cachedUiBlock = uiBlock as (Any) -> Unit
    }
}