package com.example.foodapp.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

//custom scope -> means will be found in activity scope
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}