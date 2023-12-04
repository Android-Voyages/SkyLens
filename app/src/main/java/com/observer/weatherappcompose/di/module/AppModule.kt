package com.observer.weatherappcompose.di.module

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideQueueVolley(@ApplicationContext context: Context): RequestQueue {
        return  Volley.newRequestQueue(context)
    }

    @Provides
    @Singleton
    fun provideCoroutineScope():CoroutineScope{
        return CoroutineScope(Dispatchers.IO)
    }
}
