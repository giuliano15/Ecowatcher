package com.giuldev.ecowatcher

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe base Application para o EcoWatcher.
 * Anotada com @HiltAndroidApp para acionar a geração de código do Hilt e
 * atuar como o contêiner de injeção de dependências em nível de aplicativo.
 */
@HiltAndroidApp
class EcoWatcherApp : Application()
