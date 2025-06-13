//package com.jerryokafor.smshare
//
//import io.ktor.client.request.*
//import io.ktor.client.statement.*
//import io.ktor.http.*
//import io.ktor.server.testing.*
//import kotlin.test.*
//
//
//class ApplicationTest {
//    @Test
//    fun testRoot() = testApplication {
//        val response = client.get("/")
//        assertEquals(HttpStatusCode.OK, response.status)
//        assertEquals(
//            "Hello from SMS Share App Ktor Service (GraphQL + RPC + HTTP)",
//            response.bodyAsText()
//        )
//    }
//}
