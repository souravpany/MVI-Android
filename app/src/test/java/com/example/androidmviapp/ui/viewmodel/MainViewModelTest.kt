package com.example.androidmviapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidmviapp.data.repository.GetUserRepository
import com.example.androidmviapp.ui.viewstate.MainViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@Suppress("OPT_IN_IS_NOT_ENABLED")
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    /**
     *
     * Adding InstantTaskExecutorRule which overrides isMainThread method which will call to get the main looper.
     * It also changes the background executor for Architecture Components, in order to execute them synchronously in the tests.
     *
     * */
    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @InjectMocks // is used for creating class objects
    private lateinit var getUserRepository: GetUserRepository

    @Mock //annotation is used for creating mocks
    private lateinit var mainViewState: MainViewState

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(getUserRepository)
    }


    @Test
    fun testApiSucceeds() {
        runTest {
            val newsFlow =
                getUserRepository.getUsersApiDetails()

            val jobNew = launch {
                newsFlow.collect {
                    // Check whether the first value is loading
                  //  assert(mainViewModel.state.value == mainViewState.Loading())

                    // Check whether the response is successful
                   // assert(mainViewModel.state.value is mainViewState.Success<*>)
                }
            }
            jobNew.cancel()
        }
    }
}