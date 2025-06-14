package com.example.currencyconverter.di

import android.content.Context
import com.bor96dev.data.dataSource.RatesRepositoryImpl
import com.bor96dev.data.dataSource.remote.RemoteRatesServiceImpl
import com.bor96dev.data.dataSource.room.ConverterDatabase
import com.bor96dev.data.dataSource.room.RatesService
import com.bor96dev.data.dataSource.room.account.dao.AccountDao
import com.bor96dev.data.dataSource.room.transaction.dao.TransactionDao
import com.bor96dev.domain.repository.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourcesModule {

    @Provides
    @Singleton
    fun provideRemoteRatesService(): RatesService {
        return RemoteRatesServiceImpl()
    }

    @Provides
    @Singleton
    fun provideRatesRepository(remoteRatesService: RatesService): RatesRepository{
        return RatesRepositoryImpl(remoteRatesService)
    }

    @Provides
    @Singleton
    fun provideConverterDatabase(@ApplicationContext context: Context): ConverterDatabase {
        return androidx.room.Room.databaseBuilder(
            context.applicationContext,
            ConverterDatabase::class.java,
            "converter_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountDao(database: ConverterDatabase): AccountDao {
        return database.accountDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: ConverterDatabase): TransactionDao {
        return database.transactionDao()
    }
}