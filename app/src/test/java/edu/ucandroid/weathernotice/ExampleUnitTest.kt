package edu.ucandroid.weathernotice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.ucandroid.weathernotice.dto.Weather
import edu.ucandroid.weathernotice.ui.main.MainViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import java.net.HttpURLConnection
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    lateinit var mvm: MainViewModel

    @Before
    fun populateCountries() {
        mvm = MainViewModel()

    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun countryDTO_maintainsState() {
        var weather = Weather("US", "cincinnati")
        assertTrue(weather.countryCode.equals("US"))
        assertTrue(weather.city.equals("cincinnati"))
    }
    @Test
    fun weatherDTO_containsCincinnati() {
        givenViewModelIsInitialized()
        whenJSONDataAreReadAndParsed()
        thenResultsShouldContainBelize()
    }

    @Test
    private fun givenViewModelIsInitialized() {

    }

    @Test
    private fun whenJSONDataAreReadAndParsed() {
        mvm.fetchWeatherLocations()
    }

    @Test
    private fun getUserCurrentGpsLocation() {
        //ToDO
    }

    @Test
    private fun printEachUserLocation() {
        //ToDO
        println("")
    }

    @Test
    private fun thenResultsShouldContainBelize() {
        var containsCincinnati:Boolean = false
        mvm.weatherLocations.observeForever {
            it.forEach {
                if (it.city.equals("Cincinnati")) {
                    containsCincinnati = true
                }
            }
            assertTrue(containsCincinnati)
        }
    }

}