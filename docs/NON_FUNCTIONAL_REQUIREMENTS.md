# Requisitos Não Funcionais

| Código | Requisito |
|---|---|
| RNF01 | O aplicativo terá Android API 26 (Android 8.0) como versão mínima inicialmente planejada, sujeita à validação da stack e bibliotecas. |
| RNF02 | A interface deverá se adaptar a celulares e tablets. |
| RNF03 | A comunicação com serviços remotos deverá utilizar HTTPS/TLS. |
| RNF04 | Operações protegidas deverão exigir autenticação e autorização. |
| RNF05 | Credenciais e dados sensíveis não deverão ser armazenados em texto simples. |
| RNF06 | O código deverá ser modular e organizado por responsabilidade. |
| RNF07 | Falhas de rede e dispositivos indisponíveis deverão ser tratadas de forma explícita. |
| RNF08 | Preferências de personalização deverão ser independentes por usuário. |
| RNF09 | A interface deverá considerar contraste, legibilidade, leitores de tela e áreas de toque adequadas. |
| RNF10 | A execução em segundo plano deverá respeitar as restrições e APIs oficiais do Android. |
| RNF11 | O sistema deverá diferenciar entrega técnica de confirmação humana. |
| RNF12 | Regras de segurança e validações deverão ser aplicadas no backend, não apenas no aplicativo. |
| RNF13 | O aplicativo deverá informar de forma compreensível as permissões necessárias e seus efeitos. |
| RNF14 | Dados pessoais deverão ser minimizados e ter finalidade de uso documentada. |

## Segurança

- Não versionar chaves privadas, arquivos de service account ou credenciais administrativas.
- Restringir acesso ao Firestore e Storage por regras de segurança.
- Validar no backend se remetente e destinatário possuem conexão ativa.
- Validar propriedade dos dispositivos e tokens FCM.
- Não aceitar do cliente comandos arbitrários destinados a outros usuários.
- Evitar registrar tokens, e-mails ou conteúdo privado em logs.
- Planejar remoção de conta, dispositivos e dados associados.

## Confiabilidade

- Considerar que FCM e sistemas operacionais não garantem entrega imediata.
- Tratar tokens expirados ou inválidos.
- Tratar aparelhos offline, sem bateria ou com restrições de rede.
- Evitar duplicação de alertas por meio de identificadores únicos e tratamento idempotente.
- Manter estados de alerta por destinatário/dispositivo.

## Restrições Android

O aplicativo deve seguir as políticas do Android para notificações, permissões de notificações, serviços em primeiro plano, alarmes exatos, reprodução de áudio e modo Não Perturbe. A disponibilidade de cada recurso depende da versão do sistema, do fabricante e da escolha do usuário.

Não se deve afirmar que o aplicativo consegue ignorar silencioso, Não Perturbe, bloqueios de notificação ou restrições de bateria em todos os aparelhos.
