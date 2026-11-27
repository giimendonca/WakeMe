# Configuração do Ambiente de Desenvolvimento

Este guia será atualizado conforme a versão do Android Studio e do projeto forem definidas.

## 1. Ferramentas

- Android Studio: https://developer.android.com/studio
- Documentação Kotlin: https://kotlinlang.org/docs/home.html
- Documentação Jetpack Compose: https://developer.android.com/develop/ui/compose
- Documentação Android: https://developer.android.com/docs

## 2. Instalar Android Studio no Windows

1. Acesse https://developer.android.com/studio.
2. Baixe a versão estável do Android Studio para Windows.
3. Execute o instalador.
4. Mantenha selecionados Android Studio e Android Virtual Device, se o instalador oferecer essas opções.
5. Abra o Android Studio.
6. No assistente inicial, escolha a instalação padrão (Standard).
7. Aceite as licenças e aguarde o download do Android SDK e das ferramentas recomendadas.
8. Na tela inicial, abra **More Actions > SDK Manager**.
9. Na aba **SDK Platforms**, instale uma plataforma Android estável compatível com o projeto.
10. Na aba **SDK Tools**, confirme a instalação de Android SDK Build-Tools, Android SDK Platform-Tools, Android SDK Command-line Tools e Android Emulator.

As versões exatas serão confirmadas quando criarmos o projeto. Não é necessário instalar Kotlin separadamente: o suporte Kotlin é integrado ao Android Studio e ao plugin do projeto.

## 3. Criar um emulador

1. Na tela inicial, abra **More Actions > Virtual Device Manager** (o nome pode variar conforme a versão).
2. Clique em **Create Device**.
3. Escolha um modelo de telefone, por exemplo Pixel.
4. Selecione uma imagem de sistema Android estável recomendada pelo Android Studio.
5. Baixe a imagem se necessário.
6. Finalize e inicie o emulador.

A virtualização por hardware deve estar habilitada no computador. Se o emulador não iniciar, verificaremos a configuração de virtualização e os requisitos do sistema.

## 4. Preparar os aparelhos físicos

Para instalar versões de teste:
1. Ative as Opções do desenvolvedor no aparelho.
2. Ative a Depuração USB.
3. Conecte o aparelho ao computador por USB.
4. Autorize a chave de depuração quando o aparelho perguntar.
5. Selecione o dispositivo no Android Studio e execute o projeto.

Os nomes dos menus variam entre Samsung, Motorola e versões do Android.

## 5. Configuração inicial do projeto

O projeto será criado com:
- Linguagem: Kotlin.
- Interface: Jetpack Compose.
- Design system: Material 3.
- Android mínimo planejado: API 26, sujeito à validação das dependências.

O Android Studio gerenciará Gradle, Android SDK e plugins do projeto. Não instale versões avulsas de Gradle ou Kotlin sem necessidade.

## 6. Git e GitHub

Se o Git ainda não estiver instalado:
1. Instale Git for Windows: https://git-scm.com/download/win.
2. Abra o terminal e execute `git --version`.
3. Configure nome e e-mail para commits com `git config --global user.name` e `git config --global user.email`.
4. Clone o repositório WakeMe e abra a pasta no Android Studio.

Não coloque senhas, tokens pessoais ou credenciais Firebase privadas em arquivos versionados.

## 7. Verificação do ambiente

Antes de iniciar o desenvolvimento, confirmar:
- [ ] Android Studio abre sem erros.
- [ ] Android SDK instalado.
- [ ] SDK Platform e Build-Tools disponíveis.
- [ ] Emulador inicia corretamente.
- [ ] Git funciona no terminal.
- [ ] Repositório WakeMe clonado localmente.
- [ ] Primeiro projeto Compose consegue compilar e executar.

## 8. Firebase (contas e perfis)

Esta etapa usa somente Firebase Authentication (e-mail/senha) e Cloud Firestore. O projeto Android já contém os SDKs e `firestore.rules`, mas não contém configuração específica de um projeto Firebase.

1. Crie um projeto Firebase no [Firebase Console](https://console.firebase.google.com/). Para esta etapa, use o plano Spark e não associe faturamento. Authentication por e-mail/senha não exige upgrade; o Firestore tem cotas sem custo (atualmente 1 GiB, 50 mil leituras/dia e 20 mil gravações/dia). No Spark, exceder a cota pausa o serviço até o próximo ciclo ou upgrade; no Blaze, uso excedente pode ser cobrado. Confira os limites atuais na [página de planos e preços](https://firebase.google.com/docs/projects/billing/firebase-pricing-plans) antes de habilitar cobrança.
2. Em **Configurações do projeto > Seus apps**, registre um app Android com o ID `com.wakeme.app`.
3. Baixe o `google-services.json` e coloque-o em `app/google-services.json`. O arquivo é ignorado pelo Git. Não use arquivos de service account nem compartilhe credenciais administrativas.
4. Em **Authentication > Sign-in method**, habilite **E-mail/senha**.
5. Crie o banco Cloud Firestore em modo de produção, escolha a região com cuidado (a região não pode ser trocada depois) e publique as regras do arquivo `firestore.rules` na aba **Regras**.
6. Sincronize o Gradle e execute o app. O plugin Google Services só é aplicado quando `app/google-services.json` existe. Sem ele, o app mostra uma instrução de configuração e não tenta abrir a Home.

As regras permitem a cada usuário ler, criar, atualizar ou remover apenas `/users/{seu UID}`. O perfil aceita apenas UID, nome, e-mail e datas; o e-mail precisa corresponder à conta autenticada e a data de criação não pode ser alterada. Coleções futuras permanecem fechadas por padrão.

Authentication e Firestore têm cotas gratuitas, mas uso acima delas ou recursos futuros (como Cloud Functions em produção e envio em escala) podem exigir Blaze e gerar custos. Esta implementação não ativa faturamento nem configura Functions, FCM ou Storage. Verifique os limites atuais no Console e na página oficial de preços antes de evoluir.

## 9. Observações

Nunca versione `google-services.json`, credenciais administrativas, chaves privadas, senhas ou tokens. O JSON de configuração de app Firebase não é uma credencial administrativa, mas identifica o projeto e fica fora do Git por prudência e para cada desenvolvedor usar seu ambiente.
