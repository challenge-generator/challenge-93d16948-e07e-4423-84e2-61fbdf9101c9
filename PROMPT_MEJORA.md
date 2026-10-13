# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Superficie de practica — NO resuelvas

Estos archivos SON el ejercicio de la persona. No los implementes; deja stubs.

- `src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java` — El topic pide resiliencia: este archivo es el ejercicio.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/Application.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java` — `org.bson`: El import org.bson.Document pertenece a org.bson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java` — `org.bson.types`: El import org.bson.types.ObjectId pertenece a org.bson.types, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java` — `io.smallrye.faulttolerance`: El import io.smallrye.faulttolerance.Fallback pertenece a io.smallrye.faulttolerance, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.config.inject.ConfigProperty pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/exception/GlobalExceptionHandler.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `org.jboss.logging`: El import org.jboss.logging.Logger pertenece a org.jboss.logging, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `org.eclipse.microprofile`: El import org.eclipse.microprofile.faulttolerance.Retry pertenece a org.eclipse.microprofile, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/Application.java` — `RiskBureauService.checkConnectivity`: Se invoca `checkConnectivity` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/Application.java` — `CoreBankingService.checkConnectivity`: Se invoca `checkConnectivity` sobre `CoreBankingService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java` — `IdempotencyKey.value`: Se invoca `value` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `IdempotencyKey.value`: Se invoca `value` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.sourceAccount`: Se invoca `sourceAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.destinationAccount`: Se invoca `destinationAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.description`: Se invoca `description` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `PaymentRequest.id`: Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/application/service/PaymentService.java` — `RiskBureauService.evaluateRisk`: Se invoca `evaluateRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.id`: Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.sourceAccount`: Se invoca `sourceAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java` — `PaymentRequest.destinationAccount`: Se invoca `destinationAccount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `PaymentRequest.id`: Se invoca `id` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `PaymentRequest.amount`: Se invoca `amount` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java` — `PaymentRequest.currency`: Se invoca `currency` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `PaymentService.submitPaymentRequest`: Se invoca `submitPaymentRequest` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `IdempotencyKey.value`: Se invoca `value` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java` — `IdempotencyException.getExistingRequestId`: Se invoca `getExistingRequestId` sobre `IdempotencyException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `RiskBureauService.evaluateRisk`: Se invoca `evaluateRisk` sobre `RiskBureauService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentService.processPayment`: Se invoca `processPayment` sobre `PaymentService`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentRequest.status`: Se invoca `status` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentRequest.riskBureauTransactionId`: Se invoca `riskBureauTransactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java` — `PaymentRequest.coreBankingTransactionId`: Se invoca `coreBankingTransactionId` sobre `PaymentRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `CoreBankingAdapter.processPaymentWithRetry`: Se invoca `processPaymentWithRetry` sobre `CoreBankingAdapter`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `CoreBankingException.getMessage`: Se invoca `getMessage` sobre `CoreBankingException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java` — `CoreBankingException.getStatusCode`: Se invoca `getStatusCode` sobre `CoreBankingException`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollo de software, Tecnología Java, Senior L2

### Brecha de conocimiento
Comprueba manejo de errores y reintentos.

### Reto
- Tema: E2E prod technologiesWork 202610131810
- Seniority: senior-l2
- Tipo: practical
- Título: Manejo de Errores y Reintentos en Sistema de Pagos
- Tiempo estimado: 4 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Registro de Solicitudes con Idempotencia — objetivo: Implementar el registro de solicitudes de pago con garantía de idempotencia. — entregable (NO resolver): Sistema que registra solicitudes de pago con idempotencia.
- Fase 2: Manejo de Errores del Buró de Riesgos — objetivo: Implementar el manejo de errores del buró de riesgos. — entregable (NO resolver): Sistema que maneja errores del buró de riesgos y continúa operando.
- Fase 3: Reintentos Automáticos ante Fallos del Core Bancario — objetivo: Implementar reintentos automáticos ante fallos temporales del core bancario. — entregable (NO resolver): Sistema que realiza reintentos automáticos ante fallos del core bancario.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: src/main/java/Application.java ===
package com.fintech.payments;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.fintech.payments.infrastructure.config.ResilienceConfig;
import com.fintech.payments.application.service.PaymentService;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.domain.port.CoreBankingService;

@QuarkusMain
public class Application {
    @Inject
    PaymentService paymentService;

    @Inject
    RiskBureauService riskBureauService;

    @Inject
    CoreBankingService coreBankingService;

    @ConfigProperty(name = "quarkus.mongodb.database")
    String mongoDatabase;

    @ConfigProperty(name = "quarkus.http.port")
    int httpPort;

    @ConfigProperty(name = "payment.resilience.circuit-breaker.failure-ratio")
    double circuitBreakerFailureRatio;

    public static void main(String... args) {
        Quarkus.run(Application.class, args);
    }

    public void run(String... args) {
        Log.info("Starting Payment Processing System");
        Log.infof("MongoDB database: %s", mongoDatabase);
        Log.infof("HTTP Server running on port: %d", httpPort);
        Log.infof("Circuit Breaker failure ratio configured: %.2f", circuitBreakerFailureRatio);

        try {
            // Validación de configuración inicial
            validateConfiguration();

            // Inicialización de componentes de resiliencia
            ResilienceConfig.initialize();

            // Verificación de conectividad con servicios externos
            verifyExternalServices();

            // Inicio del procesamiento de solicitudes
            Quarkus.waitForExit();
        } catch (Exception e) {
            Log.error("Failed to start application", e);
            Quarkus.asyncExit(1);
        }
    }

    private void validateConfiguration() {
        if (mongoDatabase == null || mongoDatabase.isBlank()) {
            throw new IllegalStateException("MongoDB database name is not configured");
        }

        if (circuitBreakerFailureRatio <= 0 || circuitBreakerFailureRatio > 1) {
            throw new IllegalStateException("Circuit breaker failure ratio must be between 0 and 1");
        }

        Log.info("All configurations validated successfully");
    }

    private void verifyExternalServices() {
        try {
            // Verificación asíncrona de servicios externos
            riskBureauService.checkConnectivity()
                .onItem().transform(connected -> {
                    if (!connected) {
                        Log.warn("Risk Bureau service connectivity check failed");
                    }
                    return connected;
                })
                .onFailure().recoverWithItem(false)
                .await().indefinitely();

            coreBankingService.checkConnectivity()
                .onItem().transform(connected -> {
                    if (!connected) {
                        Log.warn("Core Banking service connectivity check failed");
                    }
                    return connected;
                })
                .onFailure().recoverWithItem(false)
                .await().indefinitely();

            Log.info("External services connectivity verified");
        } catch (Exception e) {
            Log.error("Failed to verify external services connectivity", e);
            throw new RuntimeException("External services verification failed", e);
        }
    }

    public static class Startup {
        public static void main(String[] args) {
            Quarkus.run(Application.class, args);
        }
    }
}

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.fintech.payments</groupId>
    <artifactId>payment-processing-system</artifactId>
    <version>1.0.0</version>
    <properties>
        <compiler-plugin.version>3.13.0</compiler-plugin.version>
        <maven.compiler.parameters>true</maven.compiler.parameters>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <quarkus.platform.artifact-id>quarkus-universe-bom</quarkus.platform.artifact-id>
        <quarkus.platform.group-id>io.quarkus.platform</quarkus.platform.group-id>
        <quarkus.platform.version>3.14.3</quarkus.platform.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>${quarkus.platform.group-id}</groupId>
                <artifactId>${quarkus.platform.artifact-id}</artifactId>
                <version>${quarkus.platform.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <!-- Quarkus Core -->
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-resteasy-reactive-jackson</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-arc</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-context-propagation</artifactId>
        </dependency>

        <!-- MongoDB -->
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-mongodb-panache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mongodb</groupId>
            <artifactId>mongodb-driver-reactivestreams</artifactId>
            <version>5.1.0</version>
        </dependency>
        <dependency>
            <groupId>io.smallrye.reactive</groupId>
            <artifactId>smallrye-mutiny-vertx-mongodb-client</artifactId>
        </dependency>

        <!-- Resilience -->
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-fault-tolerance</artifactId>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-smallrye-reactive-messaging</artifactId>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-junit5</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.quarkus</groupId>
            <artifactId>quarkus-test-mongodb</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.12.0</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>${quarkus.platform.group-id}</groupId>
                <artifactId>quarkus-maven-plugin</artifactId>
                <version>${quarkus.platform.version}</version>
                <extensions>true</extensions>
                <executions>
                    <execution>
                        <goals>
                            <goal>build</goal>
                            <goal>generate-code</goal>
                            <goal>generate-code-tests</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>${compiler-plugin.version}</version>
                <configuration>
                    <parameters>${maven.compiler.parameters}</parameters>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <profiles>
        <profile>
            <id>native</id>
            <activation>
                <property>
                    <name>native</name>
                </property>
            </activation>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.graalvm.buildtools</groupId>
                        <artifactId>native-maven-plugin</artifactId>
                        <version>0.10.3</version>
                        <extensions>true</extensions>
                        <executions>
                            <execution>
                                <goals>
                                    <goal>build</goal>
                                </goals>
                                <phase>package</phase>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
</project>

// === ARCHIVO: src/main/resources/application.yml ===
quarkus:
  http:
    port: 8080
    host: 0.0.0.0
    cors:
      ~: true
      origins: /.*/
      headers: accept, authorization, content-type, x-requested-with
      methods: GET, POST, PUT, DELETE, OPTIONS

  mongodb:
    database: payment_db
    connection-string: mongodb://localhost:27017
    max-pool-size: 100
    min-pool-size: 10
    max-connection-idle-time: 30M
    max-connection-life-time: 1H

  log:
    category:
      "com.fintech.payments":
        level: INFO
    console:
      enable: true
      format: "%d{yyyy-MM-dd HH:mm:ss} %-5p [%c{3.}] (%t) %s%e%n"
    file:
      enable: true
      path: ./logs/payment-processing.log
      format: "%d{yyyy-MM-dd HH:mm:ss} %-5p [%c{3.}] (%t) %s%e%n"
      rotation:
        max-backup-index: 5
        max-file-size: 10M

  smallrye-fault-tolerance:
    circuit-breaker:
      delay: 30S
      failure-ratio: 0.6
      success-threshold: 2
      request-volume-threshold: 4
    retry:
      max-retries: 3
      delay: 200MS
      jitter: 100MS
      abort-on: com.fintech.payments.infrastructure.exception.IdempotencyException

payment:
  resilience:
    risk-bureau:
      timeout: 2S
      circuit-breaker:
        delay: 1M
        failure-ratio: 0.7
        success-threshold: 3
        request-volume-threshold: 10
    core-banking:
      retry:
        max-retries: 5
        delay: 500MS
        jitter: 200MS
      circuit-breaker:
        delay: 2M
        failure-ratio: 0.5
        success-threshold: 2
        request-volume-threshold: 20
  idempotency:
    key-prefix: "payment_request:"
    ttl: 24H

// === ARCHIVO: src/main/java/com/fintech/payments/domain/model/PaymentRequest.java ===
package com.fintech.payments.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa una solicitud de pago con garantía de idempotencia.
 * Contiene la información necesaria para procesar un pago y su estado actual.
 */
public record PaymentRequest(
    UUID id,
    IdempotencyKey idempotencyKey,
    String accountId,
    String recipientAccount,
    BigDecimal amount,
    String currency,
    PaymentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    int retryCount,
    String riskBureauTransactionId,
    String coreBankingTransactionId) {

    /**
     * Constructor que inicializa una solicitud de pago con los valores requeridos.
     *
     * @param idempotencyKey Clave de idempotencia para garantizar que la solicitud no se procese duplicada
     * @param accountId ID de la cuenta origen
     * @param recipientAccount ID de la cuenta destino
     * @param amount Monto a transferir
     * @param currency Moneda de la transferencia
     */
    public PaymentRequest {
        Objects.requireNonNull(idempotencyKey, "La clave de idempotencia no puede ser nula");
        Objects.requireNonNull(accountId, "El ID de cuenta origen no puede ser nulo");
        Objects.requireNonNull(recipientAccount, "El ID de cuenta destino no puede ser nulo");
        Objects.requireNonNull(amount, "El monto no puede ser nulo");
        Objects.requireNonNull(currency, "La moneda no puede ser nula");
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        
        if (currency.length() != 3) {
            throw new IllegalArgumentException("La moneda debe ser un código de 3 letras");
        }
    }

    /**
     * Crea una nueva solicitud de pago con estado PENDING.
     *
     * @param idempotencyKey Clave de idempotencia
     * @param accountId ID de la cuenta origen
     * @param recipientAccount ID de la cuenta destino
     * @param amount Monto a transferir
     * @param currency Moneda de la transferencia
     * @return Nueva instancia de PaymentRequest
     */
    public static PaymentRequest createNew(
            IdempotencyKey idempotencyKey,
            String accountId,
            String recipientAccount,
            BigDecimal amount,
            String currency) {
        return new PaymentRequest(
            UUID.randomUUID(),
            idempotencyKey,
            accountId,
            recipientAccount,
            amount,
            currency,
            PaymentStatus.PENDING,
            LocalDateTime.now(),
            LocalDateTime.now(),
            0,
            null,
            null
        );
    }

    /**
     * Actualiza el estado de la solicitud de pago.
     *
     * @param newStatus Nuevo estado
     * @return Nueva instancia con el estado actualizado
     */
    public PaymentRequest withStatus(PaymentStatus newStatus) {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            newStatus,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount,
            this.riskBureauTransactionId,
            this.coreBankingTransactionId
        );
    }

    /**
     * Incrementa el contador de reintentos.
     *
     * @return Nueva instancia con el contador incrementado
     */
    public PaymentRequest incrementRetryCount() {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount + 1,
            this.riskBureauTransactionId,
            this.coreBankingTransactionId
        );
    }

    /**
     * Actualiza el ID de transacción del buró de riesgos.
     *
     * @param transactionId ID de transacción
     * @return Nueva instancia con el ID actualizado
     */
    public PaymentRequest withRiskBureauTransactionId(String transactionId) {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount,
            transactionId,
            this.coreBankingTransactionId
        );
    }

    /**
     * Actualiza el ID de transacción del core bancario.
     *
     * @param transactionId ID de transacción
     * @return Nueva instancia con el ID actualizado
     */
    public PaymentRequest withCoreBankingTransactionId(String transactionId) {
        return new PaymentRequest(
            this.id,
            this.idempotencyKey,
            this.accountId,
            this.recipientAccount,
            this.amount,
            this.currency,
            this.status,
            this.createdAt,
            LocalDateTime.now(),
            this.retryCount,
            this.riskBureauTransactionId,
            transactionId
        );
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/domain/model/IdempotencyKey.java ===
package com.fintech.payments.domain.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Clase de valor que representa una clave de idempotencia.
 * Garantiza que las solicitudes de pago no se procesen duplicadas.
 */
public record IdempotencyKey(String value) {

    /**
     * Constructor que valida el formato de la clave de idempotencia.
     *
     * @param value Valor de la clave
     */
    public IdempotencyKey {
        Objects.requireNonNull(value, "La clave de idempotencia no puede ser nula");
        
        if (value.isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia no puede estar vacía");
        }
        
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La clave de idempotencia debe ser un UUID válido", e);
        }
    }

    /**
     * Genera una nueva clave de idempotencia usando UUID v7.
     *
     * @return Nueva instancia de IdempotencyKey
     */
    public static IdempotencyKey generate() {
        return new IdempotencyKey(UUID.randomUUID().toString());
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/domain/model/PaymentStatus.java ===
package com.fintech.payments.domain.model;

/**
 * Estados posibles de una solicitud de pago.
 */
public enum PaymentStatus {
    PENDING,
    RISK_CHECK_IN_PROGRESS,
    RISK_CHECK_COMPLETED,
    CORE_BANKING_IN_PROGRESS,
    COMPLETED,
    FAILED,
    REJECTED_BY_RISK_BUREAU
}

// === ARCHIVO: src/main/java/com/fintech/payments/domain/port/PaymentRequestRepository.java ===
package com.fintech.payments.domain.port;


import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import io.smallrye.mutiny.Uni;
import java.util.UUID;

/**
 * Puerto para persistencia de solicitudes de pago.
 * Define las operaciones necesarias para registrar y consultar solicitudes.
 */
public interface PaymentRequestRepository {

    /**
     * Guarda una solicitud de pago.
     *
     * @param paymentRequest Solicitud a guardar
     * @return Uni que emite la solicitud guardada
     */
    Uni<PaymentRequest> save(PaymentRequest paymentRequest);

    /**
     * Busca una solicitud de pago por su ID.
     *
     * @param id ID de la solicitud
     * @return Uni que emite la solicitud encontrada o null si no existe
     */
    Uni<PaymentRequest> findById(UUID id);

    /**
     * Busca una solicitud de pago por su clave de idempotencia.
     *
     * @param idempotencyKey Clave de idempotencia
     * @return Uni que emite la solicitud encontrada o null si no existe
     */
    Uni<PaymentRequest> findByIdempotencyKey(IdempotencyKey idempotencyKey);

    /**
     * Actualiza el estado de una solicitud de pago.
     *
     * @param id ID de la solicitud
     * @param newStatus Nuevo estado
     * @return Uni que emite la solicitud actualizada
     */
    Uni<PaymentRequest> updateStatus(UUID id, PaymentStatus newStatus);
}

// === ARCHIVO: src/main/java/com/fintech/payments/domain/port/RiskBureauService.java ===
package com.fintech.payments.domain.port;

import com.fintech.payments.domain.model.PaymentRequest;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el buró de riesgos.
 * Define el contrato que la infraestructura debe implementar para consultar
 * el historial crediticio y evaluar el riesgo del solicitante.
 */
public interface RiskBureauService {

    /**
     * Consulta el buró de riesgos para validar la situación crediticia del originador.
     * @param paymentRequest la solicitud de pago a evaluar
     * @return Uni con la solicitud actualizada incluyendo el ID de transacción del bureau
     */
    Uni<PaymentRequest> checkRisk(PaymentRequest paymentRequest);

    /**
     * Verifica si el originador tiene restricciones o alertas activas.
     * @param originatorId identificador del originador de crédito
     * @return Uni con true si el originador está en lista de restricciones
     */
    Uni<Boolean> hasActiveRestrictions(String originatorId);
}

// === ARCHIVO: src/main/java/com/fintech/payments/domain/port/CoreBankingService.java ===
package com.fintech.payments.domain.port;

import com.fintech.payments.domain.model.PaymentRequest;
import io.smallrye.mutiny.Uni;

/**
 * Puerto para interactuar con el core bancario.
 * Define el contrato que la infraestructura debe implementar para procesar
 * transacciones financieras a través del sistema bancario central.
 */
public interface CoreBankingService {

    /**
     * Procesa un pago a través del core bancario.
     * @param paymentRequest la solicitud de pago a procesar
     * @return Uni con la solicitud actualizada incluyendo el ID de transacción bancaria
     */
    Uni<PaymentRequest> processPayment(PaymentRequest paymentRequest);

    /**
     * Consulta el saldo disponible del originador.
     * @param originatorId identificador del originador
     * @param amount monto a verificar
     * @return Uni con true si hay saldo suficiente
     */
    Uni<Boolean> verifyAvailableFunds(String originatorId, java.math.BigDecimal amount);

    /**
     * Revierte una transacción previamente procesada.
     * @param transactionId ID de la transacción a revertir
     * @return Uni void que completa cuando la reversión es exitosa
     */
    Uni<Void> reverseTransaction(String transactionId);
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/adapter/MongoPaymentRequestRepository.java ===
package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.port.PaymentRequestRepository;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.UUID;

/**
 * Adaptador para la persistencia de solicitudes de pago en MongoDB.
 * Implementa el puerto PaymentRequestRepository usando MongoDB con Panache
 * para operaciones reactivas.
 */
@ApplicationScoped
public class MongoPaymentRequestRepository implements PaymentRequestRepository, PanacheMongoRepositoryBase<PaymentRequest, ObjectId> {

    @Inject
    io.quarkus.mongodb.reactive.ReactiveMongoClient mongoClient;

    @Inject
    jakarta.inject.Provider<com.mongodb.reactivestreams.client.MongoDatabase> databaseProvider;

    @Override
    public Uni<PaymentRequest> save(PaymentRequest paymentRequest) {
        return Uni.createFrom().item(() -> {
            persist(paymentRequest);
            return paymentRequest;
        });
    }

    @Override
    public Uni<PaymentRequest> findById(UUID id) {
        return Uni.createFrom().completionStage(() -> 
            find("id", id).first().executeInsert()
                .thenCompose(result -> find("id", id).first())
        ).onItem().ifNull().continueWith(() -> null);
    }

    @Override
    public Uni<PaymentRequest> findByIdempotencyKey(IdempotencyKey idempotencyKey) {
        return Uni.createFrom().completionStage(() -> 
            find("idempotencyKey", idempotencyKey.value()).first()
        ).onItem().ifNull().continueWith(() -> null);
    }

    @Override
    public Uni<PaymentRequest> updateStatus(UUID id, PaymentStatus newStatus) {
        return findById(id).onItem().ifNotNull().transformToUni(existing -> {
            PaymentRequest updated = existing.withStatus(newStatus);
            return Uni.createFrom().item(() -> {
                update(updated);
                return updated;
            });
        });
    }

    /**
     * Busca solicitudes por status específico.
     * @param status el estado de las solicitudes a buscar
     * @return Uni con lista de solicitudes en el estado indicado
     */
    public Uni<java.util.List<PaymentRequest>> findByStatus(PaymentStatus status) {
        return Uni.createFrom().completionStage(() -> 
            find("status", status).list()
        );
    }

    /**
     * Cuenta el total de solicitudes en un estado dado.
     * @param status el estado a contar
     * @return Uni con el número de solicitudes
     */
    public Uni<Long> countByStatus(PaymentStatus status) {
        return Uni.createFrom().completionStage(() -> 
            count("status", status)
        );
    }

    /**
     * Elimina una solicitud por su ID.
     * @param id el identificador único de la solicitud
     * @return Uni que completa cuando la eliminación es exitosa
     */
    public Uni<Void> deleteById(UUID id) {
        return Uni.createFrom().completionStage(() -> 
            delete("id", id).executeInsert().thenApply(v -> (Void) null)
        );
    }

    /**
     * Actualiza los datos de riesgo de una solicitud.
     * @param id identificador de la solicitud
     * @param riskTransactionId ID de transacción del bureau
     * @return Uni con la solicitud actualizada
     */
    public Uni<PaymentRequest> updateRiskData(UUID id, String riskTransactionId) {
        return findById(id).onItem().ifNotNull().transformToUni(existing -> {
            PaymentRequest updated = existing.withRiskBureauTransactionId(riskTransactionId);
            return Uni.createFrom().item(() -> {
                update(updated);
                return updated;
            });
        });
    }

    /**
     * Actualiza el ID de transacción del core bancario.
     * @param id identificador de la solicitud
     * @param bankingTransactionId ID de transacción bancaria
     * @return Uni con la solicitud actualizada
     */
    public Uni<PaymentRequest> updateBankingData(UUID id, String bankingTransactionId) {
        return findById(id).onItem().ifNotNull().transformToUni(existing -> {
            PaymentRequest updated = existing.withCoreBankingTransactionId(bankingTransactionId);
            return Uni.createFrom().item(() -> {
                update(updated);
                return updated;
            });
        });
    }

    /**
     * Obtiene la base de datos configurada para este repositorio.
     * @return el nombre de la base de datos MongoDB
     */
    public String getDatabaseName() {
        return mongoClient.getDatabase("payment_requests").getName();
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/application/service/PaymentService.java ===
package com.fintech.payments.application.service;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.domain.port.PaymentRequestRepository;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.infrastructure.exception.IdempotencyException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class PaymentService {

    private static final Logger LOG = Logger.getLogger(PaymentService.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;

    private final PaymentRequestRepository paymentRequestRepository;
    private final RiskBureauService riskBureauService;
    private final CoreBankingService coreBankingService;

    @Inject
    public PaymentService(
            PaymentRequestRepository paymentRequestRepository,
            RiskBureauService riskBureauService,
            CoreBankingService coreBankingService) {
        this.paymentRequestRepository = paymentRequestRepository;
        this.riskBureauService = riskBureauService;
        this.coreBankingService = coreBankingService;
    }

    public Uni<PaymentRequest> registerPaymentRequest(PaymentRequest request, IdempotencyKey idempotencyKey) {
        LOG.infof("Iniciando registro de solicitud de pago con idempotencyKey: %s", idempotencyKey.value());

        return paymentRequestRepository.findByIdempotencyKey(idempotencyKey)
                .onItem().ifNotNull().failWith(existing -> {
                    LOG.warnf("Solicitud duplicada detectada para idempotencyKey: %s", idempotencyKey.value());
                    return new IdempotencyException("Ya existe una solicitud con esta clave de idempotencia");
                })
                .onItem().ifNull().continueWith(() -> {
                    PaymentRequest newRequest = PaymentRequest.createNew(
                            request.amount(),
                            request.currency(),
                            request.sourceAccount(),
                            request.destinationAccount(),
                            request.description(),
                            idempotencyKey
                    );
                    return paymentRequestRepository.save(newRequest);
                })
                .chain(savedRequest -> processRiskAssessment(savedRequest));
    }

    private Uni<PaymentRequest> processRiskAssessment(PaymentRequest request) {
        LOG.infof("Procesando evaluación de riesgo para solicitud: %s", request.id());

        return riskBureauService.evaluateRisk(request)
                .onItem().transformToUni(riskResponse -> {
                    if (riskResponse.approved()) {
                        PaymentRequest updatedRequest = request.withRiskBureauTransactionId(riskResponse.transactionId());
                        LOG.infof("Evaluación de riesgo aprobada para solicitud: %s", request.id());
                        return processCoreBanking(updatedRequest);
                    } else {
                        LOG.warnf("Evaluación de riesgo rechazada para solicitud: %s - Razón: %s",
                                request.id(), riskResponse.rejectionReason());
                        return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.RISK_REJECTED)
                                .replaceWith(request);
                    }
                })
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf(throwable, "Error en evaluación de riesgo para solicitud: %s", request.id());
                    return handleRiskBureauFailure(request, throwable);
                });
    }

    private Uni<PaymentRequest> handleRiskBureauFailure(PaymentRequest request, Throwable throwable) {
        LOG.warnf("Manejando fallo del buró de riesgos para solicitud: %s - %s",
                request.id(), throwable.getMessage());
        return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.RISK_ASSESSMENT_FAILED)
                .replaceWith(request);
    }

    private Uni<PaymentRequest> processCoreBanking(PaymentRequest request) {
        LOG.infof("Enviando solicitud al core bancario: %s", request.id());

        return executeWithRetry(request, 1);
    }

    private Uni<PaymentRequest> executeWithRetry(PaymentRequest request, int attemptNumber) {
        LOG.infof("Intento %d de %d para solicitud: %s", attemptNumber, MAX_RETRY_ATTEMPTS, request.id());

        return coreBankingService.processPayment(request)
                .onItem().transformToUni(response -> {
                    if (response.success()) {
                        PaymentRequest updatedRequest = request.withCoreBankingTransactionId(response.transactionId());
                        return paymentRequestRepository.updateStatus(updatedRequest.id(), PaymentStatus.COMPLETED)
                                .replaceWith(updatedRequest);
                    } else {
                        LOG.warnf("Core bancario respondió con error para solicitud: %s - %s",
                                request.id(), response.errorMessage());
                        return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.FAILED)
                                .replaceWith(request);
                    }
                })
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf(throwable, "Error en intento %d para solicitud: %s", attemptNumber, request.id());
                    return handleCoreBankingFailure(request, attemptNumber, throwable);
                });
    }

    private Uni<PaymentRequest> handleCoreBankingFailure(PaymentRequest request, int attemptNumber, Throwable throwable) {
        if (attemptNumber >= MAX_RETRY_ATTEMPTS) {
            LOG.errorf("Máximo de reintentos alcanzado para solicitud: %s", request.id());
            return paymentRequestRepository.updateStatus(request.id(), PaymentStatus.MAX_RETRIES_EXCEEDED)
                    .replaceWith(request);
        }

        PaymentRequest retryRequest = request.incrementRetryCount();
        return paymentRequestRepository.save(retryRequest)
                .chain(saved -> {
                    long delayMillis = calculateBackoffDelay(attemptNumber);
                    LOG.infof("Esperando %d ms antes del reintento %d", delayMillis, attemptNumber + 1);
                    return Uni.createFrom().voidItem()
                            .onItem().delayIt().by(delayMillis)
                            .chain(() -> executeWithRetry(saved, attemptNumber + 1));
                });
    }

    private long calculateBackoffDelay(int attemptNumber) {
        return (long) Math.pow(2, attemptNumber) * 1000L;
    }

    public Uni<PaymentRequest> getPaymentRequest(UUID id) {
        LOG.debugf("Consultando solicitud de pago: %s", id);
        return paymentRequestRepository.findById(id);
    }

    public Uni<PaymentRequest> getPaymentRequestByIdempotencyKey(IdempotencyKey idempotencyKey) {
        LOG.debugf("Consultando solicitud por clave de idempotencia: %s", idempotencyKey.value());
        return paymentRequestRepository.findByIdempotencyKey(idempotencyKey);
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/adapter/RiskBureauAdapter.java ===
package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.infrastructure.exception.RiskBureauTimeoutException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.UUID;
import java.util.random.RandomGenerator;

@ApplicationScoped
public class RiskBureauAdapter implements RiskBureauService {

    private static final Logger LOG = Logger.getLogger(RiskBureauAdapter.class);
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(2);
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();

    @ConfigProperty(name = "risk-bureau.base-url", defaultValue = "https://risk-bureau.fintech.internal")
    String baseUrl;

    @ConfigProperty(name = "risk-bureau.timeout-ms", defaultValue = "2000")
    long timeoutMs;

    @Override
    public Uni<RiskEvaluationResponse> evaluateRisk(PaymentRequest request) {
        LOG.infof("Evaluando riesgo para solicitud: %s - Monto: %s %s",
                request.id(), request.amount(), request.currency());

        return Uni.createFrom().item(() -> simulateRiskEvaluation(request))
                .onItem().ifNotNull().transform(response -> {
                    LOG.infof("Evaluación de riesgo completada - Aprobada: %s, TransactionId: %s",
                            response.approved(), response.transactionId());
                    return response;
                })
                .ifNull().failWith(() -> new RiskBureauTimeoutException(
                        "El buró de riesgos no respondió dentro del tiempo esperado"));
    }

    private RiskEvaluationResponse simulateRiskEvaluation(PaymentRequest request) {
        try {
            long processingTime = RANDOM.nextLong(500, 3000);
            Thread.sleep(processingTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOG.warn("Hilo interrumpido durante simulación de evaluación de riesgo");
        }

        boolean approved = evaluateRiskRules(request);
        String transactionId = UUID.randomUUID().toString();

        if (processingTimeExceeded()) {
            LOG.errorf("Timeout detectado en el buró de riesgos para solicitud: %s", request.id());
            throw new RiskBureauTimeoutException(
                    "El buró de riesgos excedió el tiempo máximo de respuesta");
        }

        return new RiskEvaluationResponse(approved, transactionId, approved ? null : "Regla de riesgo triggered");
    }

    private boolean evaluateRiskRules(PaymentRequest request) {
        if (request.amount() != null && request.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return false;
        }

        if (request.sourceAccount() != null && request.sourceAccount().isBlank()) {
            return false;
        }

        if (request.destinationAccount() != null && request.destinationAccount().isBlank()) {
            return false;
        }

        return true;
    }

    private boolean processingTimeExceeded() {
        return RANDOM.nextInt(100) < 5;
    }

    private long getProcessingTime() {
        return RANDOM.nextLong(500, 2500);
    }

    public record RiskEvaluationResponse(
            boolean approved,
            String transactionId,
            String rejectionReason
    ) {}

    @Override
    public String getServiceName() {
        return "RiskBureauAdapter";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapter.java ===
package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.time.Duration;
import java.util.UUID;
import java.util.random.RandomGenerator;

@ApplicationScoped
public class CoreBankingAdapter implements CoreBankingService {

    private static final Logger LOG = Logger.getLogger(CoreBankingAdapter.class);
    private static final RandomGenerator RANDOM = RandomGenerator.getDefault();
    private static final int MAX_RETRY_ATTEMPTS = 5;
    private static final Duration INITIAL_BACKOFF = Duration.ofMillis(500);

    @ConfigProperty(name = "core-banking.base-url", defaultValue = "https://core-banking.fintech.internal")
    String baseUrl;

    @ConfigProperty(name = "core-banking.max-retries", defaultValue = "5")
    int maxRetries;

    @ConfigProperty(name = "core-banking.initial-backoff-ms", defaultValue = "500")
    long initialBackoffMs;

    @Override
    public Uni<CoreBankingResponse> processPayment(PaymentRequest request) {
        LOG.infof("Procesando pago en core bancario: %s - Monto: %s %s",
                request.id(), request.amount(), request.currency());

        return executeWithAutomaticRetry(request, 1);
    }

    private Uni<CoreBankingResponse> executeWithAutomaticRetry(PaymentRequest request, int attemptNumber) {
        LOG.infof("Ejecutando intento %d de %d para solicitud: %s",
                attemptNumber, maxRetries, request.id());

        return Uni.createFrom().item(() -> simulateCoreBankingCall(request))
                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
                .onFailure().recoverWithUni(throwable -> {
                    LOG.errorf(throwable, "Error en intento %d para solicitud: %s", attemptNumber, request.id());
                    return handleFailureWithRetry(request, attemptNumber, throwable);
                });
    }

    private CoreBankingResponse simulateCoreBankingCall(PaymentRequest request) {
        int scenario = RANDOM.nextInt(100);

        if (scenario < 10) {
            LOG.warnf("Simulando error 500 del core bancario para solicitud: %s", request.id());
            throw new CoreBankingException("Error interno del servidor (500)");
        }

        if (scenario < 15) {
            LOG.warnf("Simulando error 503 del core bancario para solicitud: %s", request.id());
            throw new CoreBankingException("Servicio no disponible (503)");
        }

        if (scenario < 20) {
            LOG.warnf("Simulando error de timeout del core bancario para solicitud: %s", request.id());
            throw new CoreBankingException("Timeout en la comunicación con el core bancario");
        }

        boolean success = RANDOM.nextInt(100) < 85;
        String transactionId = UUID.randomUUID().toString();

        if (success) {
            LOG.infof("Pago procesado exitosamente en core bancario: %s, TransactionId: %s",
                    request.id(), transactionId);
            return new CoreBankingResponse(true, transactionId, null);
        } else {
            LOG.warnf("Core bancario rechazó el pago para solicitud: %s", request.id());
            return new CoreBankingResponse(false, null, "Pago rechazado por el core bancario");
        }
    }

    private Uni<CoreBankingResponse> handleFailureWithRetry(PaymentRequest request, int attemptNumber, Throwable throwable) {
        if (attemptNumber >= maxRetries) {
            LOG.errorf("Máximo de reintentos automáticos alcanzado para solicitud: %s", request.id());
            return Uni.createFrom().failure(
                    new CoreBankingException("Máximo de reintentos alcanzado: " + throwable.getMessage()));
        }

        long backoffDelay = calculateExponentialBackoff(attemptNumber);
        LOG.infof("Programando reintento automático en %d ms (intento %d)", backoffDelay, attemptNumber + 1);

        return Uni.createFrom().voidItem()
                .onItem().delayIt().by(Duration.ofMillis(backoffDelay))
                .chain(() -> executeWithAutomaticRetry(request, attemptNumber + 1));
    }

    private long calculateExponentialBackoff(int attemptNumber) {
        double exponentialDelay = initialBackoffMs * Math.pow(2, attemptNumber - 1);
        double jitter = RANDOM.nextDouble() * 0.3 * exponentialDelay;
        return (long) (exponentialDelay + jitter);
    }

    @Override
    public Uni<CoreBankingResponse> reversePayment(String transactionId) {
        LOG.infof("Iniciando reversión de pago: %s", transactionId);

        return Uni.createFrom().item(() -> {
            boolean success = RANDOM.nextInt(100) < 90;
            if (success) {
                String reversalId = UUID.randomUUID().toString();
                LOG.infof("Reversión exitosa para transacción: %s, ReversalId: %s", transactionId, reversalId);
                return new CoreBankingResponse(true, reversalId, null);
            } else {
                LOG.errorf("Reversión fallida para transacción: %s", transactionId);
                return new CoreBankingResponse(false, null, "Reversión fallida");
            }
        });
    }

    @Override
    public String getServiceName() {
        return "CoreBankingAdapter";
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    public record CoreBankingResponse(
            boolean success,
            String transactionId,
            String errorMessage
    ) {}
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/exception/IdempotencyException.java ===
package com.fintech.payments.infrastructure.exception;

public class IdempotencyException extends RuntimeException {

    private final String idempotencyKey;

    public IdempotencyException(String message) {
        super(message);
        this.idempotencyKey = null;
    }

    public IdempotencyException(String message, String idempotencyKey) {
        super(message);
        this.idempotencyKey = idempotencyKey;
    }

    public IdempotencyException(String message, Throwable cause) {
        super(message, cause);
        this.idempotencyKey = null;
    }

    public IdempotencyException(String message, String idempotencyKey, Throwable cause) {
        super(message, cause);
        this.idempotencyKey = idempotencyKey;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public boolean hasIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isBlank();
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/exception/RiskBureauTimeoutException.java ===
package com.fintech.payments.infrastructure.exception;

public class RiskBureauTimeoutException extends RuntimeException {

    private static final long DEFAULT_TIMEOUT_MS = 2000L;

    private final long timeoutMs;
    private final String transactionId;

    public RiskBureauTimeoutException(String message) {
        super(message);
        this.timeoutMs = DEFAULT_TIMEOUT_MS;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, long timeoutMs) {
        super(message);
        this.timeoutMs = timeoutMs;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, Throwable cause) {
        super(message, cause);
        this.timeoutMs = DEFAULT_TIMEOUT_MS;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, long timeoutMs, Throwable cause) {
        super(message, cause);
        this.timeoutMs = timeoutMs;
        this.transactionId = null;
    }

    public RiskBureauTimeoutException(String message, String transactionId, long timeoutMs) {
        super(message);
        this.timeoutMs = timeoutMs;
        this.transactionId = transactionId;
    }

    public RiskBureauTimeoutException(String message, String transactionId, long timeoutMs, Throwable cause) {
        super(message, cause);
        this.timeoutMs = timeoutMs;
        this.transactionId = transactionId;
    }

    public long getTimeoutMs() {
        return timeoutMs;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public boolean hasTransactionId() {
        return transactionId != null && !transactionId.isBlank();
    }

    public static RiskBureauTimeoutException forTransaction(String transactionId) {
        return new RiskBureauTimeoutException(
            "Timeout del buró de riesgos al procesar transacción: " + transactionId,
            transactionId,
            DEFAULT_TIMEOUT_MS
        );
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/config/ResilienceConfig.java ===
package com.fintech.payments.infrastructure.config;

import io.smallrye.faulttolerance.Fallback;
import io.smallrye.faulttolerance.CircuitBreaker;
import io.smallrye.faulttolerance.Retry;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ResilienceConfig {

    @ConfigProperty(name = "payment.circuit-breaker.failure-ratio", defaultValue = "0.5")
    double failureRatio;

    @ConfigProperty(name = "payment.circuit-breaker.request-volume", defaultValue = "10")
    int requestVolume;

    @ConfigProperty(name = "payment.circuit-breaker.delay", defaultValue = "30000")
    long circuitBreakerDelay;

    @ConfigProperty(name = "payment.retry.max-retries", defaultValue = "3")
    int maxRetries;

    @ConfigProperty(name = "payment.retry.delay", defaultValue = "1000")
    long retryDelay;

    @ConfigProperty(name = "payment.retry.jitter", defaultValue = "200")
    long retryJitter;

    public double getFailureRatio() {
        return failureRatio;
    }

    public int getRequestVolume() {
        return requestVolume;
    }

    public long getCircuitBreakerDelay() {
        return circuitBreakerDelay;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public long getRetryJitter() {
        return retryJitter;
    }

    public long calculateRetryDelay(int attemptNumber) {
        return retryDelay * attemptNumber + (long)(Math.random() * retryJitter);
    }

    public boolean shouldAttemptRetry(int currentAttempt) {
        return currentAttempt < maxRetries;
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/exception/CoreBankingException.java ===
package com.fintech.payments.infrastructure.exception;

public class CoreBankingException extends RuntimeException {
    private final String transactionId;
    private final int httpStatusCode;
    private final boolean retryable;

    public CoreBankingException(String message) {
        super(message);
        this.transactionId = null;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public CoreBankingException(String message, String transactionId) {
        super(message);
        this.transactionId = transactionId;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public CoreBankingException(String message, String transactionId, int httpStatusCode, boolean retryable) {
        super(message);
        this.transactionId = transactionId;
        this.httpStatusCode = httpStatusCode;
        this.retryable = retryable;
    }

    public CoreBankingException(String message, Throwable cause) {
        super(message, cause);
        this.transactionId = null;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public CoreBankingException(String message, String transactionId, Throwable cause) {
        super(message, cause);
        this.transactionId = transactionId;
        this.httpStatusCode = 500;
        this.retryable = true;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public boolean isRetryable() {
        return retryable;
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/exception/GlobalExceptionHandler.java ===
package com.fintech.payments.infrastructure.exception;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Inject
    public GlobalExceptionHandler() {
    }

    @Override
    public Response toResponse(Exception exception) {
        LOG.error("Unhandled exception in payment processing", exception);

        if (exception instanceof IdempotencyException idempotencyException) {
            return handleIdempotencyException(idempotencyException);
        }

        if (exception instanceof RiskBureauTimeoutException riskTimeoutException) {
            return handleRiskBureauTimeoutException(riskTimeoutException);
        }

        if (exception instanceof CoreBankingException coreBankingException) {
            return handleCoreBankingException(coreBankingException);
        }

        return handleGenericException(exception);
    }

    private Response handleIdempotencyException(IdempotencyException ex) {
        LOG.warn("Idempotency violation: " + ex.getMessage());
        return Response.status(Response.Status.CONFLICT)
                .entity(Map.of(
                        "error", "CONFLICT",
                        "message", ex.getMessage(),
                        "existingRequestId", ex.getExistingRequestId() != null ? ex.getExistingRequestId().toString() : null
                ))
                .build();
    }

    private Response handleRiskBureauTimeoutException(RiskBureauTimeoutException ex) {
        LOG.error("Risk bureau timeout for transaction: " + ex.getTransactionId(), ex);
        return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                .entity(Map.of(
                        "error", "RISK_BUREAU_TIMEOUT",
                        "message", ex.getMessage(),
                        "transactionId", ex.getTransactionId() != null ? ex.getTransactionId() : "unknown",
                        "retryable", true
                ))
                .build();
    }

    private Response handleCoreBankingException(CoreBankingException ex) {
        LOG.error("Core banking error for transaction: " + ex.getTransactionId(), ex);
        int statusCode = ex.getHttpStatusCode();
        if (statusCode < 400 || statusCode >= 600) {
            statusCode = 500;
        }

        return Response.status(statusCode)
                .entity(Map.of(
                        "error", "CORE_BANKING_ERROR",
                        "message", ex.getMessage(),
                        "transactionId", ex.getTransactionId() != null ? ex.getTransactionId() : "unknown",
                        "retryable", ex.isRetryable()
                ))
                .build();
    }

    private Response handleGenericException(Exception ex) {
        LOG.error("Unexpected error in payment processing", ex);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Map.of(
                        "error", "INTERNAL_ERROR",
                        "message", "An unexpected error occurred while processing the payment"
                ))
                .build();
    }
}

// === ARCHIVO: src/main/java/com/fintech/payments/infrastructure/adapter/rest/PaymentResource.java ===
package com.fintech.payments.infrastructure.adapter.rest;

import com.fintech.payments.application.service.PaymentService;
import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.infrastructure.exception.IdempotencyException;
import com.fintech.payments.infrastructure.exception.RiskBureauTimeoutException;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.util.Map;

@Path("/api/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private static final Logger LOG = Logger.getLogger(PaymentResource.class);
    private static final String IDEMPOTENCY_KEY_HEADER = "X-Idempotency-Key";

    @Inject
    PaymentService paymentService;

    @POST
    @Path("/submit")
    public Uni<Response> submitPaymentRequest(
            @HeaderParam(IDEMPOTENCY_KEY_HEADER) String idempotencyKeyHeader,
            PaymentRequest paymentRequest) {

        LOG.infof("Received payment request submission, idempotencyKey: %s", idempotencyKeyHeader);

        if (idempotencyKeyHeader == null || idempotencyKeyHeader.isBlank()) {
            return Uni.createFrom().item(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of(
                                    "error", "MISSING_IDEMPOTENCY_KEY",
                                    "message", "X-Idempotency-Key header is required"
                            ))
                            .build()
            );
        }

        IdempotencyKey idempotencyKey;
        try {
            idempotencyKey = new IdempotencyKey(idempotencyKeyHeader);
        } catch (IllegalArgumentException e) {
            return Uni.createFrom().item(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(Map.of(
                                    "error", "INVALID_IDEMPOTENCY_KEY",
                                    "message", "Idempotency key must be a valid UUID"
                            ))
                            .build()
            );
        }

        return paymentService.submitPaymentRequest(paymentRequest, idempotencyKey)
                .onItem().transform(paymentReq -> {
                    LOG.infof("Payment request processed successfully, id: %s, status: %s",
                            paymentReq.id(), paymentReq.status());
                    return Response.status(Response.Status.CREATED)
                            .entity(Map.of(
                                    "id", paymentReq.id().toString(),
                                    "status", paymentReq.status().name(),
                                    "idempotencyKey", idempotencyKey.value(),
                                    "createdAt", paymentReq.createdAt().toString()
                            ))
                            .build();
                })
                .onFailure(IdempotencyException.class).recoverWithItem(ex -> {
                    LOG.warn("Idempotency conflict: " + ex.getMessage());
                    IdempotencyException ide = (IdempotencyException) ex;
                    return Response.status(Response.Status.CONFLICT)
                            .entity(Map.of(
                                    "error", "CONFLICT",
                                    "message", ex.getMessage(),
                                    "existingRequestId", ide.getExistingRequestId() != null ?
                                            ide.getExistingRequestId().toString() : "unknown"
                            ))
                            .build();
                })
                .onFailure(RiskBureauTimeoutException.class).recoverWithItem(ex -> {
                    LOG.error("Risk bureau timeout: " + ex.getMessage());
                    return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                            .entity(Map.of(
                                    "error", "RISK_BUREAU_UNAVAILABLE",
                                    "message", "Risk bureau service is temporarily unavailable",
                                    "retryable", true
                            ))
                            .build();
                })
                .onFailure(CoreBankingException.class).recoverWithItem(ex -> {
                    LOG.error("Core banking error: " + ex.getMessage());
                    CoreBankingException cbe = (CoreBankingException) ex;
                    int statusCode = cbe.getHttpStatusCode();
                    if (statusCode < 400 || statusCode >= 600) {
                        statusCode = cbe.isRetryable() ? 503 : 500;
                    }
                    return Response.status(statusCode)
                            .entity(Map.of(
                                    "error", "CORE_BANKING_ERROR",
                                    "message", ex.getMessage(),
                                    "retryable", cbe.isRetryable()
                            ))
                            .build();
                })
                .onFailure().recoverWithItem(ex -> {
                    LOG.error("Unexpected error processing payment request", ex);
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(Map.of(
                                    "error", "INTERNAL_ERROR",
                                    "message", "An unexpected error occurred"
                            ))
                            .build();
                });
    }

    @GET
    @Path("/health")
    public Uni<Response> health() {
        return Uni.createFrom().item(
                Response.ok(Map.of(
                        "status", "UP",
                        "service", "payment-processing"
                )).build()
        );
    }
}

// === ARCHIVO: src/test/java/com/fintech/payments/application/service/PaymentServiceTest.java ===
package com.fintech.payments.application.service;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.model.PaymentStatus;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.domain.port.PaymentRequestRepository;
import com.fintech.payments.domain.port.RiskBureauService;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import com.fintech.payments.infrastructure.exception.IdempotencyException;
import com.fintech.payments.infrastructure.exception.RiskBureauTimeoutException;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class PaymentServiceTest {

    @Inject
    PaymentService paymentService;

    @InjectMock
    PaymentRequestRepository paymentRequestRepository;

    @InjectMock
    RiskBureauService riskBureauService;

    @InjectMock
    CoreBankingService coreBankingService;

    private PaymentRequest sampleRequest;
    private IdempotencyKey sampleKey;

    @BeforeEach
    void setUp() {
        sampleKey = IdempotencyKey.generate();
        sampleRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-123",
            "beneficiary-456",
            new BigDecimal("1000.00"),
            "USD",
            sampleKey
        );
    }

    @Test
    void testProcessPayment_NewRequest_CreatesSuccessfully() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("risk-token-123"));
        when(coreBankingService.processPayment(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("core-txn-789"));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.status());
        assertNotNull(result.riskBureauTransactionId());
        assertNotNull(result.coreBankingTransactionId());
        verify(paymentRequestRepository).save(any(PaymentRequest.class));
    }

    @Test
    void testProcessPayment_DuplicateIdempotencyKey_ThrowsException() {
        PaymentRequest existingRequest = sampleRequest.withStatus(PaymentStatus.COMPLETED);
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().item(existingRequest));

        assertThrows(IdempotencyException.class, () ->
            paymentService.processPayment(sampleRequest).await().indefinitely()
        );
        verify(paymentRequestRepository, never()).save(any(PaymentRequest.class));
    }

    @Test
    void testProcessPayment_RiskBureauTimeout_HandlesGracefully() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().failure(new RiskBureauTimeoutException("Risk bureau timeout")));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.RISK_CHECK_FAILED, result.status());
        assertNull(result.riskBureauTransactionId());
    }

    @Test
    void testProcessPayment_CoreBankingFailure_RetriesAndEventuallyFails() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(paymentRequestRepository.updateStatus(any(UUID.class), any(PaymentStatus.class)))
            .thenAnswer(inv -> Uni.createFrom().item(true));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("risk-token-123"));
        when(coreBankingService.processPayment(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().failure(
                new CoreBankingException("Core banking unavailable", 503)
            ));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .onFailure().recoverWithItem(throwable -> {
                PaymentRequest recovered = sampleRequest.withStatus(PaymentStatus.FAILED);
                return recovered;
            })
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.FAILED, result.status());
        verify(coreBankingService, atLeast(1)).processPayment(any(PaymentRequest.class));
    }

    @Test
    void testProcessPayment_MultipleRetriesSucceeds() {
        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());
        when(paymentRequestRepository.save(any(PaymentRequest.class)))
            .thenAnswer(inv -> Uni.createFrom().item(inv.getArgument(0)));
        when(paymentRequestRepository.updateStatus(any(UUID.class), any(PaymentStatus.class)))
            .thenReturn(Uni.createFrom().item(true));
        when(riskBureauService.evaluateRisk(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().item("risk-token-123"));

        Mockito.when(coreBankingService.processPayment(any(PaymentRequest.class)))
            .thenReturn(Uni.createFrom().failure(new CoreBankingException("Service unavailable", 503)))
            .thenReturn(Uni.createFrom().failure(new CoreBankingException("Service unavailable", 503)))
            .thenReturn(Uni.createFrom().item("core-success-789"));

        PaymentRequest result = paymentService.processPayment(sampleRequest)
            .await().indefinitely();

        assertNotNull(result);
        assertEquals(PaymentStatus.COMPLETED, result.status());
        assertEquals("core-success-789", result.coreBankingTransactionId());
    }

    @Test
    void testProcessPayment_InvalidAmount_ValidationFails() {
        PaymentRequest invalidRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-123",
            "beneficiary-456",
            new BigDecimal("-100.00"),
            "USD",
            IdempotencyKey.generate()
        );

        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());

        assertThrows(IllegalArgumentException.class, () ->
            paymentService.processPayment(invalidRequest).await().indefinitely()
        );
    }

    @Test
    void testProcessPayment_EmptyOriginator_ValidationFails() {
        PaymentRequest invalidRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "",
            "beneficiary-456",
            new BigDecimal("1000.00"),
            "USD",
            IdempotencyKey.generate()
        );

        when(paymentRequestRepository.findByIdempotencyKey(any(IdempotencyKey.class)))
            .thenReturn(Uni.createFrom().nullItem());

        assertThrows(IllegalArgumentException.class, () ->
            paymentService.processPayment(invalidRequest).await().indefinitely()
        );
    }
}

// === ARCHIVO: src/test/java/com/fintech/payments/infrastructure/adapter/CoreBankingAdapterTest.java ===
package com.fintech.payments.infrastructure.adapter;

import com.fintech.payments.domain.model.IdempotencyKey;
import com.fintech.payments.domain.model.PaymentRequest;
import com.fintech.payments.domain.port.CoreBankingService;
import com.fintech.payments.infrastructure.exception.CoreBankingException;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CoreBankingAdapterTest implements CoreBankingService {

    @Inject
    CoreBankingAdapter coreBankingAdapter;

    private final AtomicInteger callCounter = new AtomicInteger(0);

    @Test
    void testProcessPayment_SuccessOnFirstAttempt() {
        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-001",
            "beneficiary-002",
            new BigDecimal("500.00"),
            "USD",
            IdempotencyKey.generate()
        );

        String result = coreBankingAdapter.processPayment(request).await().indefinitely();

        assertNotNull(result);
        assertTrue(result.startsWith("CORE-"));
    }

    @Test
    void testProcessPayment_AutomaticRetryOnServerError() {
        callCounter.set(0);

        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-003",
            "beneficiary-004",
            new BigDecimal("1500.00"),
            "EUR",
            IdempotencyKey.generate()
        );

        Uni<String> result = attemptWithFailures(request, 2);

        String transactionId = result.await().indefinitely();

        assertNotNull(transactionId);
        assertTrue(transactionId.startsWith("CORE-"));
    }

    private Uni<String> attemptWithFailures(PaymentRequest request, int failureCount) {
        AtomicInteger attempt = new AtomicInteger(0);

        return Uni.createFrom().deferred(() -> {
            int currentAttempt = attempt.incrementAndGet();
            if (currentAttempt <= failureCount) {
                return Uni.createFrom().failure(
                    new CoreBankingException("Service temporarily unavailable", 503)
                );
            }
            return Uni.createFrom().item("CORE-" + UUID.randomUUID().toString());
        });
    }

    @Test
    void testProcessPayment_MaxRetriesExceeded_ThrowsException() {
        callCounter.set(0);

        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-005",
            "beneficiary-006",
            new BigDecimal("2000.00"),
            "GBP",
            IdempotencyKey.generate()
        );

        CoreBankingException exception = assertThrows(CoreBankingException.class, () -> {
            coreBankingAdapter.processPaymentWithRetry(request).await().indefinitely();
        });

        assertTrue(exception.getMessage().contains("Max retries exceeded"));
    }

    @Test
    void testProcessPayment_CircuitBreakerBlocksOnRepeatedFailures() {
        for (int i = 0; i < 5; i++) {
            try {
                PaymentRequest request = PaymentRequest.createNew(
                    UUID.randomUUID(),
                    "originator-cb-" + i,
                    "beneficiary-cb-" + i,
                    new BigDecimal("100.00"),
                    "USD",
                    IdempotencyKey.generate()
                );
                coreBankingAdapter.processPaymentWithRetry(request).await().indefinitely();
            } catch (Exception e) {
            }
        }

        PaymentRequest newRequest = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-cb-final",
            "beneficiary-cb-final",
            new BigDecimal("100.00"),
            "USD",
            IdempotencyKey.generate()
        );

        assertThrows(CoreBankingException.class, () ->
            coreBankingAdapter.processPaymentWithRetry(newRequest).await().indefinitely()
        );
    }

    @Test
    void testProcessPayment_ClientErrorDoesNotRetry() {
        PaymentRequest request = PaymentRequest.createNew(
            UUID.randomUUID(),
            "originator-invalid",
            "beneficiary-007",
            new BigDecimal("100.00"),
            "USD",
            IdempotencyKey.generate()
        );

        CoreBankingException exception = assertThrows(CoreBankingException.class, () ->
            coreBankingAdapter.processPayment(request).await().indefinitely()
        );

        assertEquals(400, exception.getStatusCode());
    }

    @Override
    public Uni<String> processPayment(PaymentRequest paymentRequest) {
        return Uni.createFrom().item("CORE-" + UUID.randomUUID().toString());
    }
}

// === ARCHIVO: README.md ===
# Payment Processing System

Sistema de procesamiento de pagos de fintech con soporte para idempotencia, manejo de errores y reintentos automáticos.

## Requisitos Previos

- Java 21
- Maven 3.9+
- MongoDB 6.0+ (local o Docker)
- Quarkus CLI (opcional)

## Configuración del Entorno

### Variables de Entorno

```bash
export MONGO_DATABASE=payments_db
export MONGO_CONNECTION_STRING=mongodb://localhost:27017
export HTTP_PORT=8080
```

### application.yml

El archivo de configuración se encuentra en `src/main/resources/application.yml` y contiene la configuración de:
- Conexión a MongoDB
- Puerto HTTP
- Configuración de Circuit Breaker
- Timeouts para servicios externos

## Ejecución

### Desarrollo

```bash
mvn quarkus:dev
```

El servidor arrancará en `http://localhost:8080`

### Producción

```bash
mvn package
java -jar target/quarkus-app/quarkus-run.jar
```

### Docker

```bash
docker build -f src/main/docker/Dockerfile.jvm -t fintech/payment-processing:1.0.0 .
docker run -p 8080:8080 fintech/payment-processing:1.0.0
```

## Endpoints REST

| Método | Path | Descripción |
|--------|------|-------------|
| POST | /api/v1/payments | Procesar nuevo pago |
| GET | /api/v1/payments/{id} | Consultar estado de pago |
| GET | /health | Health check |

### Ejemplo de Request

```bash
curl -X POST http://localhost:8080/api/v1/payments \
  -H "Content-Type: application/json" \
  -H "X-Idempotency-Key: $(uuidgen)" \
  -d '{
    "originatorAccount": "ACC-123456",
    "beneficiaryAccount": "ACC-789012",
    "amount": 1000.00,
    "currency": "USD"
  }'
```

## Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│              (REST API - PaymentResource)                    │
└─────────────────────────────┬───────────────────────────────┘
                              │
┌─────────────────────────────▼───────────────────────────────┐
│                    Application Layer                         │
│                (PaymentService - Casos de uso)               │
└─────────────────────────────┬───────────────────────────────┘
                              │
┌─────────────────────────────▼───────────────────────────────┐
│                      Domain Layer                            │
│         (PaymentRequest, IdempotencyKey, Ports)              │
└─────────────────────────────┬───────────────────────────────┘
                              │
┌─────────────────────────────▼───────────────────────────────┐
│                  Infrastructure Layer                        │
│  (MongoDB Adapter, RiskBureau Adapter, CoreBanking Adapter)  │
└─────────────────────────────────────────────────────────────┘
```

## Patrones Implementados

- **Hexagonal Architecture**: Separación estricta entre dominio, aplicación e infraestructura
- **Idempotency**: Claves UUID v7 con garantía de unicidad
- **Circuit Breaker**: Protección contra fallos en cascada
- **Retry**: Reintentos automáticos con backoff exponencial

## Testing

```bash
mvn test
```

Para ejecutar solo pruebas unitarias:

```bash
mvn test -Dtest=PaymentServiceTest
```

Para ejecutar pruebas de integración:

```bash
mvn test -Dtest=CoreBankingAdapterTest
```

## Dependencias Externas

- **Risk Bureau**: Servicio de evaluación de riesgo (timeout: 2s)
- **Core Banking**: Sistema bancario principal (reintentos: 3, timeout: 5s)

## Métricas

El sistema soporta hasta 1,500 solicitudes/segundo en hora pico con las siguientes características:
- Latencia p99: < 200ms
- Disponibilidad: 99.9%
- Rate limiting: 2000 req/min por cliente
```
