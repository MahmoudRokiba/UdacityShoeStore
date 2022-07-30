package com.rokiba.udacityshoestore.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rokiba.udacityshoestore.Env
import com.rokiba.udacityshoestore.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    //    /**
//     * Provides App instance of the application
//     *
//     * @return the instance of application
//     */
    @Provides
    @Singleton
    fun provideApplication() = MyApp()


    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.serializeNulls()
        gsonBuilder.setPrettyPrinting()
        return gsonBuilder.create()
    }


    /**
     * provide context to be used in data repository
     *
     * @return applicationContext
     */
    @Provides
    @Singleton
    fun provideContext(): Context = MyApp.applicationContext()


    /**
     * provide shared preference to store data
     *
     * @return shared preference instance
     */
    @Provides
    @Singleton
    fun provideSharedPreference() =
        provideContext().getSharedPreferences(Env.PREF_NAME, Context.MODE_PRIVATE)
}