package com.maryang.hiltsample.hilt

import com.maryang.hiltsample.data.repository.GithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class GithubRepositoryModule {

    @Singleton
    @Provides
    fun bindGithubRepository(): GithubRepository {
        return GithubRepository()
    }
}
