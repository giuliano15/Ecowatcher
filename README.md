<div align="center">

<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white" />
<img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
<img src="https://img.shields.io/badge/Clean_Architecture-000000?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0xMiAyQTEwIDEwIDAgMCAwIDIgMTJhMTAgMTAgMCAwIDAgMTAgMTAgMTAgMTAgMCAwIDAgMTAtMTBBMTAgMTAgMCAwIDAgMTIgMm0wIDJhOCA4IDAgMCAxIDggOCA4IDggMCAwIDEtOCA4IDggOCAwIDAgMS04LTggOCA4IDAgMCAxIDgtOG0wIDJhNiA2IDAgMCAwLTYgNiA2IDYgMCAwIDAgNiA2IDYgNiAwIDAgMCA2LTYgNiA2IDAgMCAwLTYtNm0wIDJhNCA0IDAgMCAxIDQgNCA0IDQgMCAwIDEtNCA0IDQgNCAwIDAgMS00LTQgNCA0IDAgMCAxIDQtNCIvPjwvc3ZnPg==&logoColor=white" />
<img src="https://img.shields.io/badge/Hilt-FF6F00?style=for-the-badge&logo=google&logoColor=white" />
<img src="https://img.shields.io/badge/Room-0288D1?style=for-the-badge&logo=sqlite&logoColor=white" />
<img src="https://img.shields.io/badge/Retrofit-48B983?style=for-the-badge&logo=square&logoColor=white" />
<img src="https://img.shields.io/badge/Coil-FF6242?style=for-the-badge&logo=coil&logoColor=white" />
<img src="https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge" />
<img src="https://img.shields.io/badge/API-24%2B-blue?style=for-the-badge" />
<img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" />

<br/><br/>

# 📈 EcoWatcher

### Monitor de Ativos Financeiros (Crypto & Stocks)
**Aplicativo Android de portfólio técnico demonstrando Clean Architecture, MVVM e as melhores práticas do ecossistema Android moderno.**

[Funcionalidades](#-funcionalidades) · [Arquitetura](#-arquitetura) · [Stack Técnica](#-stack-técnica) · [Como rodar](#-como-rodar) · [Decisões Técnicas](#-decisões-técnicas)

</div>

---

## ✨ Funcionalidades

- 📡 **Dados em tempo real** via API CoinGecko (top 100 criptomoedas)
- 💾 **Offline-First** — funciona sem internet com cache Room
- 🔄 **Pull-to-Refresh** para atualização manual
- ⚡ **Shimmer Effect** durante o carregamento
- 📋 **Empty State** inteligente com botão "Tentar Novamente"
- 🟢🔴 **Variação colorida** — verde para alta, vermelho para queda
- 🪵 **API Monitor** — log completo de requests/responses no Logcat

---

## 🏗 Arquitetura

O projeto segue estritamente os princípios de **Clean Architecture** com separação em 4 camadas independentes:

```
┌─────────────────────────────────────────────────────┐
│                    UI / Presentation                 │
│  MainActivity · AssetViewModel · AssetAdapter        │
└───────────────────────┬─────────────────────────────┘
                        │ observa StateFlow
┌───────────────────────▼─────────────────────────────┐
│                      Domain                          │
│  GetMarketAssetsUseCase · AssetRepository (interface)│
│  Asset (model) · Resource<T> (wrapper)               │
└───────────────────────┬─────────────────────────────┘
                        │ implementa
┌───────────────────────▼─────────────────────────────┐
│                       Data                           │
│  AssetRepositoryImpl (Offline-First)                 │
│  CoinGeckoApi (Retrofit) · AssetDao (Room)           │
│  AssetResponse (DTO) · AssetEntity · Mappers         │
└─────────────────────────────────────────────────────┘
                        │ injetado por
┌─────────────────────────────────────────────────────┐
│                  DI (Hilt Modules)                   │
│  NetworkModule · DatabaseModule                      │
│  RepositoryModule · UseCaseModule                    │
└─────────────────────────────────────────────────────┘
```

### 🔄 Fluxo de Dados (Offline-First)

```
Abre o app
    │
    ▼
ViewModel.loadAssets()
    │
    ▼
UseCase.invoke() → Repository.getAssets()
    │
    ├── emit(Loading) ──────────────► [UI: Shimmer]
    │
    ├── API ok? ─── SIM ──► Salva no Room
    │                              │
    │                              └──► emit(Success) ─► [UI: Lista]
    │
    └── API falhou? ─── Room tem cache? ─── SIM ──► emit(Success) ─► [UI: Lista offline]
                                        │
                                        └── NÃO ──► emit(Error) ──► [UI: Empty State]
```

---

## 🛠 Stack Técnica

| Categoria | Tecnologia | Versão |
|---|---|---|
| Linguagem | Kotlin | 2.0.21 |
| Arquitetura | Clean Architecture + MVVM | — |
| Injeção de Dependência | Hilt (Dagger) | 2.51.1 |
| Assincronismo | Coroutines + StateFlow | 1.8.1 |
| Rede | Retrofit + OkHttp | 2.11.0 / 4.12.0 |
| Banco local | Room | 2.6.1 |
| Imagens | Coil | 2.6.0 |
| UI | Material Design 3 + ViewBinding | 1.13.0 |
| Loading | Facebook Shimmer | 0.5.0 |
| Processador | KSP (Kotlin Symbol Processing) | 2.0.21-1.0.25 |

---

## ▶️ Como rodar

### Pré-requisitos
- Android Studio **Hedgehog** (2023.1.1) ou superior
- JDK 11+
- Dispositivo ou emulador com **Android API 24+**

### Passo a passo

```bash
# 1. Clone o repositório
git clone https://github.com/giuliano15/Ecowatcher.git

# 2. Abra no Android Studio
# File → Open → selecione a pasta clonada

# 3. Aguarde o Gradle sincronizar automaticamente

# 4. Execute
# Run → Run 'app' (Shift + F10)
```

> **Nota:** O app utiliza a API pública gratuita da [CoinGecko](https://www.coingecko.com/api). Nenhuma chave de API é necessária.

---

## 🧠 Decisões Técnicas

### `StateFlow` em vez de `LiveData`
`StateFlow` é Kotlin-native, tem valor inicial obrigatório (sem estados nulos), suporte nativo a Coroutines e é lifecycle-safe com `repeatOnLifecycle`. Representa o futuro do gerenciamento de estado no Android.

### `ListAdapter + DiffUtil` em vez de `notifyDataSetChanged()`
O `DiffUtil` calcula diffs em background thread, gerando animações automáticas e performáticas. Crucial para listas longas sem travar a UI thread.

### `UseCase` com Interface (SOLID-D)
A `ViewModel` depende da abstração `GetMarketAssetsUseCase`, não da implementação concreta. Facilita testes unitários com mocks sem alterar a lógica existente.

### Estratégia Offline-First no Repository
O dado que chega ao usuário **sempre** vem do Room (fonte de verdade local). A API serve apenas para atualizar o cache. Padrão usado em apps de missão crítica.

### `Coil` em vez de Glide
Coil é escrito 100% em Kotlin, integra-se nativamente com Coroutines, gerencia lifecycle automaticamente e é ~40% mais leve que o Glide para casos de uso comuns.

### `@Binds` em vez de `@Provides` no Hilt
O `@Binds` é mais eficiente — o Hilt apenas registra o bind sem criar uma função de fábrica. Menor overhead de código gerado.

---

## 📁 Estrutura de Pacotes

```
com.giuldev.ecowatcher/
├── EcoWatcherApp.kt              # @HiltAndroidApp
│
├── di/
│   ├── NetworkModule.kt          # Retrofit, OkHttp, CoinGeckoApi
│   ├── DatabaseModule.kt         # Room, AssetDao
│   ├── RepositoryModule.kt       # AssetRepositoryImpl binding
│   └── UseCaseModule.kt          # UseCase binding via @Binds
│
├── data/
│   ├── local/
│   │   ├── EcoWatcherDatabase.kt
│   │   ├── dao/AssetDao.kt
│   │   └── entity/AssetEntity.kt
│   ├── remote/
│   │   ├── api/CoinGeckoApi.kt
│   │   ├── model/AssetResponse.kt
│   │   └── interceptor/ApiMonitorInterceptor.kt
│   ├── mapper/AssetMapper.kt
│   └── repository/AssetRepositoryImpl.kt
│
├── domain/
│   ├── model/
│   │   ├── Asset.kt              # Modelo de domínio puro
│   │   └── Resource.kt           # Sealed class: Success/Error/Loading
│   ├── repository/AssetRepository.kt
│   └── usecase/GetMarketAssetsUseCase.kt
│
└── ui/
    ├── MainActivity.kt
    ├── adapter/AssetAdapter.kt
    └── viewmodel/AssetViewModel.kt
```

---

## 🚀 Próximas Features Planejadas

- [ ] Gráfico de variação de preço (MPAndroidChart)
- [ ] Tela de detalhe do ativo
- [ ] Lista de favoritos (Room)
- [ ] Busca em tempo real
- [ ] Notificações de alerta de preço (WorkManager)
- [ ] Testes unitários (JUnit5 + MockK + Turbine)
- [ ] Testes de integração (Room in-memory database)

---

## 📄 Licença

```
MIT License — Copyright (c) 2024 Giuliano
```

---

<div align="center">

Feito com ❤️ por **Giuliano**

[![GitHub](https://img.shields.io/badge/GitHub-giuliano15-181717?style=flat-square&logo=github)](https://github.com/giuliano15)

</div>
