# Manejo de Errores y Reintentos en Sistema de Pagos

El sistema de pagos de una fintech necesita registrar transacciones con idempotencia, manejar errores y realizar reintentos automáticos. Los actores involucrados son el originador de créditos, el motor antifraude, el buró de riesgos, el core bancario y el gateway de pagos. El sistema debe registrar cada solicitud de pago con una clave de idempotencia, manejar los errores del buró de riesgos (timeout >2s) y realizar reintentos automáticos ante fallos temporales del core bancario (respuesta 5xx). La tasa de transacciones es de 1 500 solicitudes/segundo en hora pico.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | E2E prod technologiesWork 202610131810 |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 4 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Registro de Solicitudes con Idempotencia

**Objetivo:** Implementar el registro de solicitudes de pago con garantía de idempotencia.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- El sistema debe registrar cada solicitud de pago con una clave de idempotencia para asegurar que dos invocaciones con la misma clave produzcan un solo registro y devuelvan la misma respuesta dentro de 24 horas.

**Entregable:** Sistema que registra solicitudes de pago con idempotencia.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo generar y usar una clave de idempotencia.
- Piensa en cómo asegurar que la respuesta sea consistente ante reintentos.

</details>

### Fase 2: Manejo de Errores del Buró de Riesgos

**Objetivo:** Implementar el manejo de errores del buró de riesgos.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- El sistema debe manejar los errores del buró de riesgos cuando el timeout supera los 2 segundos. Debe registrar el error y continuar operando.

**Entregable:** Sistema que maneja errores del buró de riesgos y continúa operando.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cómo detectar y registrar los errores del buró de riesgos.
- Piensa en cómo asegurar que el sistema continúe operando ante estos errores.

</details>

### Fase 3: Reintentos Automáticos ante Fallos del Core Bancario

**Objetivo:** Implementar reintentos automáticos ante fallos temporales del core bancario.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- El sistema debe realizar reintentos automáticos ante fallos temporales del core bancario (respuesta 5xx). Debe decidir cuántos reintentos realizar y con qué intervalo.

**Entregable:** Sistema que realiza reintentos automáticos ante fallos del core bancario.

<details>
<summary>Pistas de conocimiento</summary>

- Considera cuántos reintentos realizar y con qué intervalo.
- Piensa en cómo manejar los casos en los que los reintentos no tienen éxito.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la idempotencia en el contexto del registro de solicitudes de pago?
- **paraQueSirve**: ¿Para qué sirve manejar los errores del buró de riesgos en el sistema de pagos?
- **comoSeUsa**: ¿Cómo se usa la clave de idempotencia para garantizar la consistencia en las respuestas?
- **erroresComunes**: ¿Cuáles son los errores comunes que pueden ocurrir al interactuar con el buró de riesgos y cómo se manejan?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de reintentos automáticos ante fallos del core bancario?

## Criterios de Evaluacion

- Implementación del registro de solicitudes con idempotencia.
- Manejo efectivo de errores del buró de riesgos.
- Implementación de reintentos automáticos ante fallos del core bancario.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
