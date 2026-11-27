# Changelog

Todas as alterações relevantes do WakeMe serão registradas neste arquivo.

O formato segue uma adaptação de Keep a Changelog. As versões ainda não foram publicadas.

## [Unreleased]

### Added
- Cadastro, login, logout e recuperação de senha por Firebase Authentication, com estado de sessão persistente e validação de formulário.
- Perfil `/users/{uid}` no Cloud Firestore com datas de servidor e regras por UID; configuração Firebase local documentada e ignorada pelo Git.
- Editor local de alertas com título, mensagem, seletor de ícones/emojis e opção sem ícone; os estilos predefinidos foram removidos.
- Fundos locais sólidos, padrões gráficos vetoriais e padrões de emojis configuráveis (emoji, tamanho e espaçamento), com prévia em proporção de tela cheia.
- Seleção local de imagem com Photo Picker, visualização na prévia, remoção e opções de preencher/ajustar; sem permissões amplas de armazenamento.
- Modelo de domínio para rascunho de alerta separado da interface, com validação de conteúdo e ação para limpar.
- Testes unitários para configuração, fundos, validação e limpeza do rascunho, e teste instrumentado do editor.
- Rótulos da navegação ajustados para “Alertas” e “Aparência”; estados selecionados agora seguem a cor de destaque.
- Prioridade do produto documentada para criação, reprodução e futura transmissão de alertas; aparência geral como configuração secundária.
- Seleção local de cor de destaque com cinco opções e persistência independente do tema.
- Prévia de componentes Material 3 atualizada em tempo real conforme tema e cor selecionados.
- Navegação Compose entre Início, Conexões, Alertas, Histórico, Aparência e Configurações.
- Telas introdutórias para áreas ainda sem operações funcionais e acesso às preferências de tema em Configurações.
- Teste instrumentado para navegação e continuidade da preferência de tema entre destinos.
- Home inicial do WakeMe com áreas informativas para conexões, alertas e personalização.
- Preferências de tema Sistema/Claro/Escuro persistidas localmente com Preferences DataStore.
- Esquemas Material 3 claro/escuro e gradientes reutilizáveis com a paleta WakeMe.
- Testes unitários para leitura de valores persistidos da preferência de tema.
- Definição inicial do produto WakeMe.
- Requisitos funcionais e não funcionais.
- Arquitetura técnica inicial.
- Modelo conceitual de dados.
- Roadmap de desenvolvimento.
- Guia de configuração do ambiente Android.
- Registros iniciais de decisões arquiteturais.
