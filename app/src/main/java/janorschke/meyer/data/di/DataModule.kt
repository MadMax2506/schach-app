/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package janorschke.meyer.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import janorschke.meyer.data.ChessRepository
import janorschke.meyer.data.DefaultChessRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsChessRepository(
        chessRepository: DefaultChessRepository
    ): ChessRepository
}

class FakeChessRepository @Inject constructor() : ChessRepository {
    override val chesss: Flow<List<String>> = flowOf(fakeChesss)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}

val fakeChesss = listOf("One", "Two", "Three")
