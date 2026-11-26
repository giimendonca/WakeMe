# Modelo de Dados — Proposta Inicial

Este documento descreve entidades conceituais. Os nomes, campos e índices finais serão definidos durante a implementação do Firestore.

## 1. Entidades

### Users
Representa uma conta individual.
- `uid` — identificador do Firebase Authentication.
- `displayName` — nome de exibição.
- `email` — e-mail, quando disponível.
- `createdAt` — data de criação.
- `preferences` — preferências gerais não sensíveis.

### Devices
Representa uma instalação/dispositivo associado a uma conta.
- `deviceId` — identificador da instalação.
- `ownerUid` — proprietário.
- `displayName` — nome amigável.
- `platform` — plataforma.
- `fcmToken` — token de notificação, com acesso restrito.
- `lastSeenAt` — último contato.
- `enabled` — indica se o dispositivo está ativo.

### Connections
Representa uma conexão aceita entre duas contas.
- `connectionId`
- `participantUids` — identificadores dos participantes.
- `status` — estado da conexão.
- `createdAt`
- `acceptedAt`
- `endedAt`

### Invitations
Representa um convite enviado.
- `invitationId`
- `senderUid`
- `recipientEmail` ou identificador de convite.
- `codeHash` — hash do código, quando aplicável.
- `status` — pendente, aceito, recusado, expirado ou cancelado.
- `expiresAt`
- `createdAt`

### Alerts
Representa a solicitação de alerta.
- `alertId`
- `senderUid`
- `connectionId`
- `messageId` ou conteúdo validado da mensagem.
- `soundId`
- `createdAt`
- `status`

### AlertRecipients
Representa o estado individual de cada dispositivo destinatário.
- `recipientId`
- `alertId`
- `recipientUid`
- `deviceId`
- `deliveryStatus`
- `processedAt`
- `acknowledgedAt`
- `failureReason`

### Messages
Representa mensagens personalizadas.
- `messageId`
- `ownerUid`
- `title`
- `content`
- `createdAt`
- `updatedAt`

### Themes
Representa preferências visuais.
- `ownerUid`
- `themeMode`
- `colorPalette`
- `customColors`
- `updatedAt`

### Wallpapers
Representa imagens e preferências de fundo.
- `wallpaperId`
- `ownerUid`
- `storagePath` ou referência local/fornecida pelo sistema.
- `source`
- `createdAt`

## 2. Relacionamentos

- Um usuário possui vários dispositivos.
- Um usuário pode participar de várias conexões.
- Uma conexão possui dois participantes no escopo inicial.
- Um usuário pode enviar e receber vários convites.
- Um alerta pertence a um remetente e a uma conexão.
- Um alerta pode possuir vários destinatários/dispositivos.
- Um usuário pode possuir várias mensagens e preferências de personalização.

## 3. Diagrama conceitual

```mermaid
erDiagram
    USERS ||--o{ DEVICES : owns
    USERS ||--o{ INVITATIONS : sends
    USERS }o--o{ CONNECTIONS : participates
    USERS ||--o{ ALERTS : sends
    CONNECTIONS ||--o{ ALERTS : authorizes
    ALERTS ||--o{ ALERT_RECIPIENTS : targets
    DEVICES ||--o{ ALERT_RECIPIENTS : receives
    USERS ||--o{ MESSAGES : creates
    USERS ||--o{ THEMES : configures
    USERS ||--o{ WALLPAPERS : owns
```

## 4. Regras de integridade

- Um dispositivo pertence a uma única conta ativa.
- Apenas participantes de uma conexão ativa podem trocar alertas por meio dela.
- O backend deve validar a propriedade de cada dispositivo destinatário.
- Cada estado de destinatário deve ser atualizado de maneira idempotente.
- Tokens FCM devem ser protegidos e removidos/atualizados quando inválidos.
- O usuário só pode consultar dados aos quais possui autorização.
- Exclusão de conta deve possuir política documentada para dados relacionados.

## 5. Observações

Este modelo é conceitual e não deve ser tratado como schema definitivo. Antes da implementação, serão definidos caminhos de coleções/documentos, índices compostos, regras de segurança e estratégia de exclusão/retention.
