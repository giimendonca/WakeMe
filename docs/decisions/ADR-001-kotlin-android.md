# ADR-001 — Aplicativo Android nativo com Kotlin

- **Status:** aceita
- **Data:** 26/11/2026

## Contexto

WakeMe será lançado inicialmente para Android e precisa interagir com recursos da plataforma, como notificações, áudio, permissões e execução em segundo plano.

## Decisão

Desenvolver o cliente como aplicativo Android nativo utilizando Kotlin.

## Alternativas consideradas

- React Native.
- Flutter.
- Aplicativo web/PWA.

## Consequências

- Acesso direto às APIs Android.
- Integração mais natural com recursos de áudio e notificações.
- O desenvolvimento inicial ficará limitado ao Android.
- Será necessário aprender e manter código Kotlin.
