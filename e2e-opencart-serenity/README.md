# Ejercicio 1 - Prueba E2E de compra en OpenCart (Serenity BDD + Screenplay)

Prueba funcional automatizada del flujo de compra como invitado en http://opencart.abstracta.us/

## Flujo automatizado
1. Abrir la tienda.
2. Agregar dos productos al carrito (MacBook e iPhone).
3. Visualizar el carrito y verificar que ambos productos estén.
4. Completar el Checkout como invitado ("Guest Checkout") con datos de facturación.
5. Aceptar método de envío y términos del método de pago.
6. Confirmar el pedido y validar el mensaje **"Your order has been placed!"**.

## Tecnologías y versiones
| Herramienta | Versión |
|---|---|
| Serenity BDD (core, cucumber, screenplay, ensure) | 5.3.2 |
| Cucumber JUnit Platform Engine | 7.34.2 |
| JUnit Platform / Jupiter | 6.0.3 |
| Gradle (vía Gradle Wrapper) | 9.2.1 |
| Java | 21 (OpenJDK Temurin 21.0.12.1) |
| Navegador | Google Chrome 154.0.8037.98 (el driver se descarga solo con Selenium Manager) |
| Sistema operativo | Windows 11 |

## Requisitos previos
- Java 17 o superior (probado con 21): `java -version`
- Google Chrome instalado
- Conexión a internet (descarga de dependencias y acceso a la tienda)

No hace falta instalar Gradle: el proyecto incluye el Gradle Wrapper.

## Estructura
```
e2e-opencart-serenity/
├── build.gradle, gradlew, gradlew.bat, gradle/wrapper/
├── serenity.properties
├── src/test/resources/
│   ├── features/purchase/guest_checkout.feature   # Escenario en Gherkin (BDD)
│   └── serenity.conf                              # Navegador, esperas, capturas
└── src/test/java/opencart/
    ├── CucumberTestSuite.java                     # Ejecutor
    ├── stepdefinitions/                           # Pasos de Cucumber
    ├── tasks/                                     # Tareas Screenplay (qué hace el actor)
    └── ui/                                        # Páginas y localizadores (Targets)
```
Patrón **Screenplay**: el actor (Norma) ejecuta *Tasks* (OpenTheStore, AddToCart, ViewTheCart, CheckoutAsGuest, ConfirmTheOrder) sobre *Targets* definidos en `ui/`.

## Ejecución paso a paso
1. Abrir una terminal dentro de la carpeta `e2e-opencart-serenity`.
2. Ejecutar la prueba:
   - Windows (PowerShell): `.\gradlew.bat test`
   - Linux / macOS: `chmod +x gradlew` y luego `./gradlew test`
3. La primera vez Gradle descarga sus dependencias (puede tardar varios minutos). Se abrirá Chrome y se verá el flujo de compra.
4. Para ejecutar sin ventana del navegador: `.\gradlew.bat test -Dheadless.mode=true`
5. Reporte: se genera en `target/site/serenity/` (abrir `index.html` o `serenity-summary.html` en el navegador). Incluye capturas de pantalla de cada paso.

## Notas
- La tienda es un sitio de demostración público; puede ser lenta o estar caída. Las esperas son explícitas (hasta 15-30 s por paso).
- Los datos del cliente están en el escenario `.feature` y se pueden modificar sin tocar el código.
- Los pedidos de la demo son ficticios y no generan cobros.
