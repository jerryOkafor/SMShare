package com.jerryokafor.smshare.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query

class HelloWorldQuery : Query {
    @GraphQLDescription("Returns Greeting - Hello World : ")
    @Suppress("FunctionOnlyReturningConstant")
    fun hello(): String = "Hello World!"
}
