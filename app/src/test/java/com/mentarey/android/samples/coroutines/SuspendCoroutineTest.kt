package com.mentarey.android.samples.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SuspendCoroutineTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun `coroutines test`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        withContext(coroutinesTestRule.testDispatcherProvider.io) {}
    }
}