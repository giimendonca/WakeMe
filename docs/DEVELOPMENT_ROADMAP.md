# Roadmap de Desenvolvimento

O roadmap organiza o desenvolvimento por entregas. As etapas podem ser ajustadas conforme riscos técnicos e resultados dos testes.

## Fase 0 — Preparação
- [ ] Instalar Android Studio.
- [ ] Instalar Android SDK e ferramentas necessárias.
- [ ] Configurar emulador Android.
- [ ] Criar projeto Kotlin com Jetpack Compose.
- [ ] Executar a aplicação inicial.
- [ ] Configurar Git local e fluxo de commits.

## Fase 1 — Fundação do aplicativo
- [x] Definir arquitetura de pacotes.
- [x] Configurar tema Material 3.
- [ ] Implementar navegação principal.
- [x] Criar componentes reutilizáveis.
- [x] Preparar suporte a diferentes tamanhos de tela.

## Fase 2 — Personalização local
- [x] Implementar temas claro e escuro.
- [ ] Criar paletas e preferências.
- [ ] Implementar seleção de wallpaper.
- [x] Persistir preferências de tema localmente.

## Fase 3 — Mensagens
- [ ] Criar tela de mensagens.
- [ ] Criar, editar e excluir mensagens.
- [ ] Implementar mensagens favoritas.
- [ ] Associar mensagens a alertas.

## Fase 4 — Áudio e notificações locais
- [ ] Criar canais de notificação.
- [ ] Implementar reprodução de áudio de alerta.
- [ ] Avaliar execução em segundo plano.
- [ ] Implementar controles e explicações de permissões.
- [ ] Testar tela bloqueada, Não Perturbe e economia de bateria.
- [ ] Documentar limitações por versão/fabricante.

## Fase 5 — Firebase e contas
- [ ] Criar projeto Firebase.
- [ ] Configurar Authentication.
- [ ] Configurar Firestore.
- [ ] Escrever e testar regras de segurança.
- [ ] Configurar Cloud Functions.
- [ ] Configurar FCM.
- [ ] Avaliar custos e plano de faturamento antes de habilitar serviços pagos.

## Fase 6 — Dispositivos e conexões
- [ ] Registrar dispositivos.
- [ ] Implementar convites por código.
- [ ] Implementar convites por e-mail.
- [ ] Implementar aceitação e recusa.
- [ ] Implementar encerramento de conexão.
- [ ] Implementar gerenciamento de dispositivos.

## Fase 7 — Alertas remotos
- [ ] Implementar solicitação autenticada de alerta.
- [ ] Validar conexão e destinatários no backend.
- [ ] Encaminhar alertas por FCM.
- [ ] Processar alertas no aplicativo receptor.
- [ ] Implementar confirmação explícita.
- [ ] Implementar estados individuais por dispositivo.
- [ ] Implementar histórico.

## Fase 8 — Qualidade e distribuição
- [ ] Testar no Samsung Galaxy M23.
- [ ] Testar no Motorola (modelo a confirmar).
- [ ] Testar no tablet Samsung (modelo a confirmar).
- [ ] Testar conectividade instável e dispositivos offline.
- [ ] Testar permissões e restrições de bateria.
- [ ] Revisar acessibilidade e privacidade.
- [ ] Preparar versão de distribuição.

## Critério de conclusão do MVP

O MVP estará pronto quando duas contas distintas puderem se conectar, registrar dispositivos, enviar alertas autorizados, receber uma notificação/experiência sonora compatível com as permissões do Android, confirmar explicitamente o alerta e consultar o histórico por dispositivo.

A entrega não será considerada confiável apenas porque o backend aceitou uma solicitação FCM.
