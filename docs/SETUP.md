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

## 8. Observações

A instalação do Android Studio não exige criar imediatamente um projeto Firebase. Primeiro validaremos o ambiente Android local. Firebase será configurado quando a fundação do aplicativo estiver pronta.
