package edu.ucandroid.weathernotice

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import edu.ucandroid.weathernotice.dto.LocationInfo
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

    //@Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    /*@Test
    fun countryDTO_maintainsState() {
        var locationInfo = LocationInfo("US", "cincinnati","",null)
        assertTrue(locationInfo.countryCode.equals("US"))
        assertTrue(locationInfo.city.equals("cincinnati"))
    }*/
    @Test
    fun weatherDTO_containsCincinnati() {
        givenViewModelIsInitialized()
        whenJSONDataAreReadAndParsed()
        thenResultsShouldContainCincinnati()
    }
    private fun givenViewModelIsInitialized() {

    }

    private fun whenJSONDataAreReadAndParsed() {
        mvm.fetchLocations()
    }

    private fun getUserCurrentGpsLocation() {
        //ToDO
    }

    private fun printEachUserLocation() {
        //ToDO
        println("")
    }

    private fun thenResultsShouldContainCincinnati() {
        var containsCincinnati:Boolean = false
        mvm.locationinfos.observeForever {
            it.forEach {
                if (it.city.equals("Cincinnati")) {
                    containsCincinnati = true
                }
            }
            //assertTrue(containsCincinnati)
        }
    }

}