# WakeMe 🔔

> **Stay connected. Even when they're asleep.**

WakeMe é um aplicativo Android de comunicação e alertas remotos entre pessoas que autorizaram uma conexão. A proposta combina alertas sonoros, mensagens personalizadas e uma experiência visual personalizável.

## Objetivos

- Permitir que pessoas conectadas enviem alertas remotos umas às outras.
- Oferecer mensagens personalizadas associadas aos alertas.
- Registrar entrega e confirmação por dispositivo destinatário.
- Permitir personalização de temas e wallpapers.
- Manter contas individuais, conexões autorizadas e múltiplos dispositivos por conta.

## Funcionalidades planejadas

| Área | Escopo |
|---|---|
| Alarmes remotos | Envio bidirecional, som configurável, confirmação e cancelamento quando possível |
| Mensagens | Criação e uso de mensagens personalizadas |
| Wallpapers | Seleção de imagens para a experiência visual do aplicativo e dos alertas |
| Histórico | Consulta de alertas, destinatários e estados por dispositivo |
| Temas | Temas claros/escuros, paletas e preferências individuais |
| Contas e conexões | Contas individuais, convites por código ou e-mail e conexões aceitas |

## Stack planejada

- **Linguagem:** Kotlin
- **Interface:** Jetpack Compose + Material 3
- **IDE:** Android Studio
- **Backend gerenciado:** Firebase
- **Autenticação:** Firebase Authentication
- **Dados:** Cloud Firestore
- **Entrega de notificações:** Firebase Cloud Messaging (FCM)
- **Lógica confiável no servidor:** Cloud Functions
- **Arquivos de mídia:** Firebase Storage, quando necessário

A stack e os serviços poderão ser ajustados durante a implementação, com as decisões registradas em `docs/decisions/`.

## Compatibilidade

- Android mínimo planejado: API 26 (Android 8.0), sujeito à validação das bibliotecas e funcionalidades adotadas.
- Interface planejada para celulares e tablets.
- Os recursos disponíveis podem variar conforme versão do Android, fabricante e configurações do dispositivo.

## Estado atual

**Fase:** planejamento concluído; preparação do ambiente e início da implementação pendentes.

Consulte:
- [Especificação do produto](docs/PRODUCT_SPECIFICATION.md)
- [Requisitos funcionais](docs/FUNCTIONAL_REQUIREMENTS.md)
- [Requisitos não funcionais](docs/NON_FUNCTIONAL_REQUIREMENTS.md)
- [Arquitetura do sistema](docs/SYSTEM_ARCHITECTURE.md)
- [Modelo de dados](docs/DATABASE_MODEL.md)
- [Roadmap](docs/DEVELOPMENT_ROADMAP.md)
- [Configuração do ambiente](docs/SETUP.md)
- [Decisões arquiteturais](docs/decisions/)

## Limitações importantes do Android

WakeMe não pode garantir a reprodução de um alerta em um dispositivo desligado, sem conectividade, com notificações bloqueadas ou sujeito a restrições severas de segundo plano. O comportamento de áudio também depende das permissões, do modo Não Perturbe, das configurações do usuário, da versão do Android e das políticas do fabricante.

O aplicativo deve explicar suas permissões, oferecer configurações claras e nunca prometer que consegue ignorar todas as proteções do sistema.

## Segurança e privacidade

- Somente usuários autenticados e conexões aceitas podem trocar alertas.
- Operações privilegiadas devem ser validadas por uma camada confiável no backend.
- Tokens FCM são dados de dispositivos e devem ter acesso restrito.
- Credenciais administrativas e chaves privadas nunca devem ser versionadas.
- Regras de segurança do Firebase precisam ser configuradas antes de armazenar dados reais.

## Desenvolvimento

O projeto será implementado por etapas. Veja [DEVELOPMENT_ROADMAP.md](docs/DEVELOPMENT_ROADMAP.md).

## Licença

A licença ainda não foi definida. Até que uma licença seja adicionada, todos os direitos permanecem reservados aos titulares do projeto.
