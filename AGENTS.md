# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Manejo de Errores y Reintentos en Sistema de Pagos**.

| | |
|---|---|
| Tema | E2E prod technologiesWork 202610131810 |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Quarkus 3.14 |
| Patron arquitectonico | hexagonal/clean con patrones reactivos |
| Tiempo estimado | 4 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- io.quarkus.platform:quarkus-bom 3.14.3
- io.quarkus:quarkus-resteasy-reactive-jackson n/a
- io.quarkus:quarkus-mongodb-panache n/a
- io.quarkus:quarkus-smallrye-fault-tolerance n/a
- io.quarkus:quarkus-smallrye-reactive-messaging n/a
- io.smallrye.reactive:smallrye-mutiny-vertx-mongodb-client n/a
- io.quarkus:quarkus-test-mongodb n/a
- io.quarkus:quarkus-junit5 n/a
- org.mockito:mockito-core 5.12.0
- org.mongodb:mongodb-driver-reactivestreams 5.1.0

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Registro de Solicitudes con Idempotencia**: Sistema que registra solicitudes de pago con idempotencia.
- **Fase 2 — Manejo de Errores del Buró de Riesgos**: Sistema que maneja errores del buró de riesgos y continúa operando.
- **Fase 3 — Reintentos Automáticos ante Fallos del Core Bancario**: Sistema que realiza reintentos automáticos ante fallos del core bancario.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Superficie de practica (NO completes)

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs. No toques la logica que el reto pide completar.

- [ ] `src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que falta y tenes que completar

### 1. Referencias colgando (43)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/Application.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java` — `org.bson`
      El import org.bson.Document pertenece a org.bson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java` — `org.bson.types`
      El import org.bson.types.ObjectId pertenece a org.bson.types, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java` — `io.smallrye.faulttolerance`
      El import io.smallrye.faulttolerance.Fallback pertenece a io.smallrye.faulttolerance, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/exception/GlobalExceptionHandler.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `org.jboss.logging`
      El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `org.eclipse.microprofile`
      El import org.eclipse.microprofile.faulttolerance.Retry pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/Application.java` — `RiskBureauService.checkConnectivity`
      Se invoca `checkConnectivity` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/Application.java` — `CoreBankingService.checkConnectivity`
      Se invoca `checkConnectivity` sobre `CoreBankingService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java` — `IdempotencyKey.value`
      Se invoca `value` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `IdempotencyKey.value`
      Se invoca `value` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.sourceAccount`
      Se invoca `sourceAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.destinationAccount`
      Se invoca `destinationAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.description`
      Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.id`
      Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `RiskBureauService.evaluateRisk`
      Se invoca `evaluateRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.id`
      Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.sourceAccount`
      Se invoca `sourceAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.destinationAccount`
      Se invoca `destinationAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `PaymentRequest.id`
      Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `PaymentRequest.amount`
      Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `PaymentRequest.currency`
      Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `PaymentService.submitPaymentRequest`
      Se invoca `submitPaymentRequest` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `IdempotencyKey.value`
      Se invoca `value` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `IdempotencyException.getExistingRequestId`
      Se invoca `getExistingRequestId` sobre `IdempotencyException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `RiskBureauService.evaluateRisk`
      Se invoca `evaluateRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentService.processPayment`
      Se invoca `processPayment` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentRequest.status`
      Se invoca `status` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentRequest.riskBureauTransactionId`
      Se invoca `riskBureauTransactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentRequest.coreBankingTransactionId`
      Se invoca `coreBankingTransactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `CoreBankingAdapter.processPaymentWithRetry`
      Se invoca `processPaymentWithRetry` sobre `CoreBankingAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `CoreBankingException.getMessage`
      Se invoca `getMessage` sobre `CoreBankingException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `CoreBankingException.getStatusCode`
      Se invoca `getStatusCode` sobre `CoreBankingException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (22)

- `src/main/java/Application.java`
- `pom.xml`
- `src/main/resources/application.yml`
- `src/main/java/com/fintech/payments/domain/model/PaymentRequest.java`
- `src/main/java/com/fintech/payments/domain/model/IdempotencyKey.java`
- `src/main/java/com/fintech/payments/domain/model/PaymentStatus.java`
- `src/main/java/com/fintech/payments/domain/port/PaymentRequestRepository.java`
- `src/main/java/com/fintech/payments/domain/port/RiskBureauService.java`
- `src/main/java/com/fintech/payments/domain/port/CoreBankingService.java`
- `src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java`
- `src/main/java/com/fintech/payments/application/service/PaymentService.java`
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java`
- `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java`
- `src/main/java/com/fintech/payments/infrastructure/exception/IdempotencyException.java`
- `src/main/java/com/fintech/payments/infrastructure/exception/RiskBureauTimeoutException.java`
- `src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java`
- `src/main/java/com/fintech/payments/infrastructure/exception/CoreBankingException.java`
- `src/main/java/com/fintech/payments/infrastructure/exception/GlobalExceptionHandler.java`
- `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java`
- `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java`
- `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java`
- `README.md`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/fintech/payments`
- `src/main/java/com/fintech/payments/domain`
- `src/main/java/com/fintech/payments/domain/model`
- `src/main/java/com/fintech/payments/domain/port`
- `src/main/java/com/fintech/payments/application`
- `src/main/java/com/fintech/payments/application/service`
- `src/main/java/com/fintech/payments/infrastructure`
- `src/main/java/com/fintech/payments/infrastructure/adapter`
- `src/main/java/com/fintech/payments/infrastructure/config`
- `src/main/java/com/fintech/payments/infrastructure/exception`
- `src/main/resources`
- `src/test/java/com/fintech/payments`

## Verificacion

```bash
mvn clean compile
```

El comando tiene que pasar SIN implementar los archivos de la superficie de practica: solo andamiaje.

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con patrones reactivos**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2
- Brecha que el reto ataca: Comprueba manejo de errores y reintentos.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
