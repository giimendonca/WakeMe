# Requisitos Funcionais

Os requisitos abaixo representam o escopo funcional inicial do WakeMe. A implementação e os critérios de aceite poderão ser detalhados em issues.

| Código | Requisito |
|---|---|
| RF01 | O sistema deve permitir criar conta e autenticar usuários. |
| RF02 | O sistema deve permitir cadastrar múltiplos dispositivos por conta. |
| RF03 | O sistema deve permitir enviar convites de conexão por código e por e-mail. |
| RF04 | O sistema deve permitir aceitar, recusar e encerrar conexões. |
| RF05 | Usuários conectados devem poder enviar alertas uns aos outros. |
| RF06 | O aplicativo deve reproduzir alertas sonoros configuráveis, respeitando permissões e restrições do Android. |
| RF07 | O usuário deve poder criar, editar, excluir e selecionar mensagens personalizadas. |
| RF08 | O usuário deve poder selecionar wallpapers disponibilizados pelo aplicativo ou pela galeria. |
| RF09 | O usuário deve poder personalizar temas e cores da interface. |
| RF10 | O sistema deve manter histórico de alertas e seus estados. |
| RF11 | O destinatário deve poder confirmar explicitamente o recebimento/percepção do alerta. |
| RF12 | O remetente deve poder selecionar os dispositivos destinatários de um alerta. |
| RF13 | O aplicativo deve permitir gerenciar permissões, notificações e dispositivos registrados. |
| RF14 | O remetente deve poder solicitar cancelamento de um alerta ativo, quando suportado pelo estado e pela plataforma. |

## Critérios de aceite iniciais

### RF01 — Conta e autenticação
- Uma pessoa consegue criar uma conta.
- Uma pessoa consegue entrar e sair da conta.
- Erros de autenticação são apresentados sem expor informações sensíveis.

### RF02 — Dispositivos
- Cada instalação é registrada associada à conta autenticada.
- Um usuário consegue visualizar e remover seus próprios dispositivos.
- Tokens de notificação são armazenados com acesso restrito.

### RF03 e RF04 — Conexões
- Convites possuem identificador e estado.
- Um convite não cria conexão ativa antes da aceitação.
- Somente participantes autorizados podem consultar ou encerrar a conexão.

### RF05 e RF06 — Alertas
- O usuário seleciona uma conexão e ao menos um dispositivo destinatário.
- O backend valida a autorização antes de encaminhar o alerta.
- O aplicativo receptor apresenta o alerta conforme as permissões e restrições do sistema.
- Falhas de entrega são registradas sem serem confundidas com confirmação humana.

### RF07 a RF09 — Personalização
- Preferências são salvas e restauradas.
- Alterar o tema não altera dados de outros usuários.
- Imagens selecionadas são tratadas conforme permissões e políticas de armazenamento.

### RF10 e RF11 — Histórico e confirmação
- Cada destinatário possui estado individual.
- O histórico diferencia estados técnicos e confirmação explícita.
- O usuário consegue consultar eventos anteriores.

### RF12 a RF14 — Controles
- O usuário visualiza os dispositivos selecionados antes do envio.
- Permissões ausentes são explicadas.
- Remoção de dispositivo revoga sua associação e impede novos envios a ele.
