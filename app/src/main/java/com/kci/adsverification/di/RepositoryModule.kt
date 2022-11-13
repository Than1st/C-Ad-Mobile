package com.kci.adsverification.di

import com.kci.adsverification.data.Repository
import com.kci.adsverification.data.api.ApiHelper
import com.kci.adsverification.data.datastore.UserPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @ViewModelScoped
    @Provides
    fun provideRepositoryy(
        apiHelper: ApiHelper,
        userPreferences: UserPreferences
    ) = Repository(apiHelper, userPreferences)
}