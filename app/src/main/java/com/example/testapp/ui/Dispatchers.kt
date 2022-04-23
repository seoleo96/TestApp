package com.example.testapp.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface Dispatchers {

    fun launchUI(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit)
    fun launchIO(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit)

    class Base : Dispatchers {

        override fun launchIO(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit) {
            scope.launch(kotlinx.coroutines.Dispatchers.IO, block = block)
        }

        override fun launchUI(scope: CoroutineScope, block: suspend CoroutineScope.() -> Unit) {
            scope.launch(kotlinx.coroutines.Dispatchers.Main, block = block)
        }
    }
}