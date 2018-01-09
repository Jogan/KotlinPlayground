package com.jogan.kotlinplayground.domain.executor

import java.util.concurrent.Executor

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute
 */
interface ThreadExecutor : Executor