# WakeMe — Especificação do Produto

- **Versão do documento:** 1.0
- **Data de consolidação:** 26/11/2026
- **Status:** especificação inicial aprovada

## 1. Visão do produto

WakeMe é um aplicativo Android para comunicação e alertas remotos entre pessoas que estabeleceram uma conexão autorizada. Seu objetivo principal é permitir que uma pessoa chame a atenção de outra por meio de um alerta sonoro e visual, acompanhado opcionalmente de uma mensagem personalizada.

O produto também oferece histórico, wallpapers e temas personalizáveis.

**Frase do produto:** *Stay connected. Even when they're asleep.*

## 2. Público e cenário inicial

O primeiro cenário de uso envolve duas pessoas, cada uma com sua própria conta e um ou mais dispositivos Android. A arquitetura não deve limitar o produto a casais: qualquer pessoa poderá estabelecer conexões autorizadas com outras.

Dispositivos iniciais previstos para testes:
- Samsung Galaxy M23;
- smartphone Motorola (modelo ainda a confirmar);
- tablet Samsung (modelo ainda a confirmar).

## 3. Decisões de produto aprovadas

| Tema | Decisão |
|---|---|
| Contas | Individuais |
| Formas de conexão | Convite por código e por e-mail |
| Comunicação | Bidirecional |
| Envio de alertas | Ambos os participantes podem enviar |
| Envio de mensagens | Ambos podem enviar |
| Dispositivos | Vários dispositivos por conta |
| Personalização | Preferências independentes por usuário |
| Plataforma inicial | Android |

Uma conexão só se torna ativa depois que o convite é aceito. Qualquer participante pode encerrar a conexão.

## 4. Funcionalidades principais

### 4.1 Alarmes remotos
- Enviar alertas para uma pessoa conectada.
- Selecionar os dispositivos destinatários.
- Reproduzir um som configurável no dispositivo receptor.
- Associar uma mensagem ao alerta.
- Registrar estados de envio, entrega e confirmação por dispositivo.
- Permitir cancelamento quando tecnicamente possível.

### 4.2 Mensagens personalizadas
- Criar, editar e excluir mensagens próprias.
- Salvar mensagens favoritas.
- Associar uma mensagem a um alerta.
- Exibir o conteúdo na experiência de recebimento, respeitando as permissões do Android.

### 4.3 Wallpapers
- Selecionar imagens disponíveis no aplicativo.
- Selecionar imagens da galeria por meio dos mecanismos de seleção de mídia do Android.
- Definir imagens para áreas compatíveis da interface e da experiência de alerta.
- Armazenar preferências individualmente.

### 4.4 Histórico
- Registrar remetente, destinatário, data e horário.
- Exibir estado por dispositivo destinatário.
- Diferenciar envio solicitado, entrega confirmada pelo serviço, recebimento processado pelo aplicativo e confirmação explícita do usuário.
- Permitir consulta de alertas anteriores.

### 4.5 Temas
- Disponibilizar temas claro e escuro.
- Permitir paletas personalizadas.
- Personalizar cores e elementos da interface.
- Manter preferências independentes por conta e, quando apropriado, por dispositivo.

## 5. Navegação inicial

Áreas previstas:
1. Início — painel e envio de alertas.
2. Conexões — pessoas conectadas e convites.
3. Mensagens — mensagens personalizadas e favoritas.
4. Histórico — alertas anteriores e seus estados.
5. Personalização — temas e wallpapers.
6. Configurações — conta, dispositivos, notificações e permissões.

A estrutura poderá ser refinada durante prototipação e testes de usabilidade.

## 6. Princípios de experiência

- Personalização deve ser opcional e não prejudicar a clareza dos alertas.
- O botão principal de alerta deve ser fácil de localizar e utilizar.
- O aplicativo deve informar claramente quando um dispositivo estiver offline ou sem permissões necessárias.
- Entrega técnica não deve ser apresentada como confirmação de que a pessoa percebeu o alerta.
- O usuário deve controlar suas conexões, dispositivos e permissões.

## 7. Limitações da plataforma

O Android controla notificações, execução em segundo plano, áudio, tela de bloqueio e modo Não Perturbe. O comportamento varia conforme versão, fabricante e configurações do usuário.

WakeMe não poderá garantir alertas em dispositivos desligados, sem rede, com notificações desativadas ou com restrições de execução. Não deve tentar contornar proteções do sistema sem consentimento. Recursos especiais de alarme e tela bloqueada serão avaliados com as APIs e permissões oficiais do Android.

## 8. Fora do escopo inicial

- Versão iOS.
- Chamadas telefônicas ou de vídeo.
- Rede social pública.
- Mensagens instantâneas completas em tempo real.
- Garantia de sobreposição irrestrita sobre qualquer tela.
- Controle remoto irrestrito de todas as configurações do dispositivo.
