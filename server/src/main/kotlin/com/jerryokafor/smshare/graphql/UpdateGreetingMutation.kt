package com.jerryokafor.smshare.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation

class UpdateGreetingMutation : Mutation {
    @GraphQLDescription("Modify Greetings, and returns it")
    fun updateGreeting(greeting: String) = "Hello update greetings $greeting"
}
