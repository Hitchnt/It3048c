package edu.ucandroid.weathernotice

import org.junit.Test

import org.junit.Assert.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun subtract_isCorrect() {
        assertEquals(1, 3 - 2)
    }
    @Test
    fun callJson_gotData() {
        println("Getting json data")
        data class weather(val description:String)

        var url ="https://api.weatherbit.io/v2.0/current?&city=Cincinnati&country=USA&key=66af4499735e4bcc914957a0354de0b2"

       // val connection = URL(url[0]).openConnection() as HttpURLConnection

    }
}