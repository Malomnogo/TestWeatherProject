package com.malomnogo.data

import com.malomnogo.data.core.HandleError

private const val HANDLE_ERROR_HANDLE_ERROR = "HandleError#handleError"

class FakeHandleError(
    private val order: Order
) : HandleError {

    var result: String = ""

    override fun handleError(exception: Exception): String {
        order.add(HANDLE_ERROR_HANDLE_ERROR)
        return result
    }
}

