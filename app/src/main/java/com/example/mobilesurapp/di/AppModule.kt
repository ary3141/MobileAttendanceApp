package com.example.mobilesurapp.di

import android.content.Context
import android.util.Base64
import androidx.room.Room
import com.example.mobilesurapp.UIApp.dashboard.data.repository.DashboardRepositoryImpl
import com.example.mobilesurapp.UIApp.dashboard.domain.repository.DashboardRepository
import com.example.mobilesurapp.UIApp.dashboard.domain.usecase.GetDashboardSummaryUseCase
import com.example.mobilesurapp.UIApp.login.LoginStateViewModel
import com.example.mobilesurapp.api.WebSocketService
import com.example.mobilesurapp.database.AppDatabase
import com.example.mobilesurapp.database.dao.EmployeeDao
import com.example.mobilesurapp.database.dao.PendingSyncDao
import com.example.mobilesurapp.domain.usecase.GetUserProfileUseCase
import com.example.mobilesurapp.domain.usecase.LoginUseCase
import com.example.mobilesurapp.domain.usecase.RegisterUserWithFaceUseCase
import com.example.mobilesurapp.domain.usecase.SyncOfflineFacesUseCase
import com.example.mobilesurapp.domain.usecase.UpdateUserProfileUseCase
import com.example.mobilesurapp.domain.usecase.VerifyFaceUseCase
import com.example.mobilesurapp.domain.utils.CryptoManager
import com.example.mobilesurapp.domain.utils.NetworkUtils
import com.example.mobilesurapp.face.FaceEmbedder
import com.example.mobilesurapp.face.FaceNetModel
import com.example.mobilesurapp.modelload.AddFaceDetector
import com.example.mobilesurapp.repository.FaceRepository
import com.example.mobilesurapp.repository.FaceRepositoryImpl
import com.example.mobilesurapp.repository.LoginRepository
import com.example.mobilesurapp.repository.LoginRepositoryImpl
import com.example.mobilesurapp.repository.UserProfileRepository
import com.example.mobilesurapp.repository.UserProfileRepositoryImpl
import com.example.mobilesurapp.domain.usecase.RegisterUseCase
import com.example.mobilesurapp.repository.RegisterRepository
import com.example.mobilesurapp.repository.RegisterRepositoryImpl
import com.example.mobilesurapp.repository.EmployeeRepository
import com.example.mobilesurapp.repository.EmployeeRepositoryImpl
import com.example.mobilesurapp.domain.usecase.GetEmployeesUseCase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import java.security.SecureRandom
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager {
        return CryptoManager()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideWebSocketService(
        webSocketClient: com.example.mobilesurapp.api.WebSocketClient,
        gson: Gson
    ): WebSocketService {
        return WebSocketService(webSocketClient, gson)
    }

    @Provides
    @Singleton
    fun provideLoginStateViewModel(): LoginStateViewModel {
        return LoginStateViewModel()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        cryptoManager: CryptoManager
    ): AppDatabase {

        val prefs =
            context.getSharedPreferences(
                "secure_app_prefs",
                Context.MODE_PRIVATE
            )

        var encryptedKeyBase64 =
            prefs.getString(
                "encrypted_db_key",
                null
            )

        val dbPassphrase: ByteArray

        if (encryptedKeyBase64 == null) {

            val random = SecureRandom()
            val key = ByteArray(32)
            random.nextBytes(key)

            val encryptedKey =
                cryptoManager.encrypt(key)

            encryptedKeyBase64 =
                Base64.encodeToString(
                    encryptedKey,
                    Base64.DEFAULT
                )

            prefs.edit()
                .putString(
                    "encrypted_db_key",
                    encryptedKeyBase64
                )
                .apply()

            dbPassphrase = key

        } else {

            val encryptedKey =
                Base64.decode(
                    encryptedKeyBase64,
                    Base64.DEFAULT
                )

            dbPassphrase =
                cryptoManager.decrypt(
                    encryptedKey
                )
        }

        val factory = SupportFactory(dbPassphrase)

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "faceRecogntionDB"
        )
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    @Singleton
    fun provideEmployeeDao(
        database: AppDatabase
    ): EmployeeDao {
        return database.employeeDao()
    }

    @Provides
    @Singleton
    fun provideEmployeeRepository(
        webSocketService: WebSocketService
    ): EmployeeRepository {

        return EmployeeRepositoryImpl(webSocketService)

    }

    @Provides
    fun provideDashboardRepository(
        webSocketService: WebSocketService
    ): DashboardRepository =
        DashboardRepositoryImpl(webSocketService)
    @Provides

    fun provideDashboardSummaryUseCase(
        repository: DashboardRepository
    ): GetDashboardSummaryUseCase =
        GetDashboardSummaryUseCase(repository)

    @Provides
    @Singleton
    fun provideGetEmployeesUseCase(
        repository: EmployeeRepository
    ): GetEmployeesUseCase {

        return GetEmployeesUseCase(repository)

    }
    @Provides
    @Singleton
    fun providePendingSyncDao(
        database: AppDatabase
    ): PendingSyncDao {
        return database.pendingSyncDao()
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(
        @ApplicationContext context: Context
    ): NetworkUtils {
        return NetworkUtils(context)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        webSocketService: WebSocketService
    ): LoginRepository {
        return LoginRepositoryImpl(webSocketService)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(
        webSocketService: WebSocketService
    ): RegisterRepository {
        return RegisterRepositoryImpl(webSocketService)
    }

    @Provides
    @Singleton
    fun provideFaceRepository(
        employeeDao: EmployeeDao,
        pendingSyncDao: PendingSyncDao,
        webSocketService: WebSocketService,
        networkUtils: NetworkUtils
    ): FaceRepository {

        return FaceRepositoryImpl(
            employeeDao,
            pendingSyncDao,
            webSocketService,
            networkUtils
        )
    }

    @Provides
    @Singleton
    fun provideUserProfileRepository(
        webSocketService: WebSocketService
    ): UserProfileRepository {

        return UserProfileRepositoryImpl(
            webSocketService
        )
    }
    @Provides
    @Singleton
    fun provideLoginUseCase(
        repository: LoginRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        repository: RegisterRepository
    ): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUserWithFaceUseCase(
        repository: FaceRepository
    ): RegisterUserWithFaceUseCase {
        return RegisterUserWithFaceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVerifyFaceUseCase(
        repository: FaceRepository
    ): VerifyFaceUseCase {
        return VerifyFaceUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSyncOfflineFacesUseCase(
        repository: FaceRepository,
        networkUtils: NetworkUtils
    ): SyncOfflineFacesUseCase {
        return SyncOfflineFacesUseCase(
            repository,
            networkUtils
        )
    }

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(
        repository: UserProfileRepository
    ): GetUserProfileUseCase {
        return GetUserProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateUserProfileUseCase(
        repository: UserProfileRepository
    ): UpdateUserProfileUseCase {
        return UpdateUserProfileUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFaceNetModel(
        @ApplicationContext context: Context
    ): FaceNetModel {
        return FaceNetModel(context)
    }

    @Provides
    @Singleton
    fun provideFaceEmbedder(
        faceNetModel: FaceNetModel
    ): FaceEmbedder {
        return FaceEmbedder(faceNetModel)
    }

    @Provides
    @Singleton
    fun provideAddFaceDetector(
        @ApplicationContext context: Context
    ): AddFaceDetector {
        return AddFaceDetector(context)
    }
}