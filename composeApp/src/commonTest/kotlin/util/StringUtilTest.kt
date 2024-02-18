package util

import kotlin.test.Test
import kotlin.test.assertEquals


class StringUtilTest {
    @Test
    fun testUrlEncode() {
        val value = "Hello, World!"
        val expected = "Hello%2C+World%21"
        val actual = urlEncode(value)
        assertEquals(expected, actual)
    }

    @Test
    fun testUrlDecode() {
        val value = "Hello%2C+World%21"
        val expected = "Hello, World!"
        val actual = urlDecode(value)
        assertEquals(expected, actual)
    }
}