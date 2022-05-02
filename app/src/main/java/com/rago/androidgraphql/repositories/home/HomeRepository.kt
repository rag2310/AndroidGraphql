package com.rago.androidgraphql.repositories.home

import com.apollographql.apollo3.ApolloClient
import com.rago.LaunchListQuery
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {
    fun getLaunchList() = apolloClient.query(LaunchListQuery())
}