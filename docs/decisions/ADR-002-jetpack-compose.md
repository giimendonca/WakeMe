# ADR-002 — Jetpack Compose para a interface

- **Status:** aceita
- **Data:** 26/11/2026

## Contexto

WakeMe terá temas, paletas, componentes reutilizáveis e telas adaptáveis. A interface precisa permitir evolução visual sem acoplar a lógica de negócio às telas.

## Decisão

Utilizar Jetpack Compose com Material 3 para a interface Android.

## Alternativas consideradas

- XML Views.
- Jetpack Compose.
- Abordagem híbrida.

## Consequências

- A interface será declarativa e escrita em Kotlin.
- Estados podem atualizar a interface de forma reativa.
- Conceitos de componentes e estado serão familiares a quem conhece React.
- Será necessário aprender Compose e suas práticas de estado, navegação e acessibilidade.
