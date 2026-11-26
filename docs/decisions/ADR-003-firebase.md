# ADR-003 — Firebase como backend inicial

- **Status:** aceita, sujeita a revisão de custos e requisitos
- **Data:** 26/11/2026

## Contexto

WakeMe precisa de autenticação, armazenamento de dados, notificações remotas e lógica de servidor para validar conexões e encaminhar alertas.

## Decisão

Utilizar inicialmente Firebase Authentication, Cloud Firestore, Firebase Cloud Messaging, Cloud Functions e, se necessário, Firebase Storage.

## Consequências

- A equipe poderá começar sem manter um servidor próprio.
- Regras de segurança do Firestore e Storage serão essenciais.
- Cloud Functions e determinados recursos podem exigir plano Blaze e faturamento habilitado.
- Custos, limites e dependência de fornecedor deverão ser revisados antes de uso em produção.
- O cliente não poderá decidir sozinho quais tokens de outros usuários receberão alertas; o backend deverá validar as permissões.
