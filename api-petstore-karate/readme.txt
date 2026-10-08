# Ejercicio 2 - Pruebas de API REST con Karate (PetStore)

Automatización del ciclo de vida de una mascota en https://petstore.swagger.io/ (API `https://petstore.swagger.io/v2`).

## Casos cubiertos
| # | Paso | Endpoint | Validación |
|---|---|---|---|
| 1 | Añadir una mascota | `POST /pet` | status 200, id/nombre/status devueltos |
| 2 | Consultar por ID | `GET /pet/{petId}` | status 200 y cuerpo completo igual al enviado |
| 3 | Actualizar nombre y status a `sold` | `PUT /pet` | status 200, nombre y status actualizados |
| 4 | Consultar por status | `GET /pet/findByStatus?status=sold` | la mascota aparece con el nombre nuevo (con reintentos) |
| Extra | Mascota inexistente | `GET /pet/999999999999` | status 404 y mensaje `Pet not found` |

Entradas, variables y salidas: el `petId` se genera con la hora actual (único por ejecución), los nombres se arman a partir de ese id, y cada respuesta se valida con `match`. Las salidas se imprimen y quedan en el reporte HTML.

## Tecnologías y versiones
| Herramienta | Versión |
|---|---|
| Karate | 2.1.2 (jar independiente) |
| Java | 21 (probado con OpenJDK 21.0.10) |
| Sistema operativo | Windows 11 (PowerShell) |

Verificar Java: `java -version`

## Estructura
```
api-petstore-karate/
├── features/petstore.feature   # Escenarios de prueba
├── karate-config.js            # URL base configurable
├── README.md / readme.txt
├── conclusiones.txt
└── .gitignore
```

## Ejecución paso a paso
1. Instalar Java 21 o superior (por ejemplo, Temurin: https://adoptium.net).
2. Descargar `karate-2.1.2.jar` desde
   https://github.com/karatelabs/karate/releases/download/v2.1.2/karate-2.1.2.jar
   y guardarlo dentro de la carpeta `api-petstore-karate` (el jar no se sube al repositorio).
3. Abrir una terminal dentro de esa carpeta y ejecutar:
   `java -jar karate-2.1.2.jar run features`
4. Revisar resultados:
   - Consola: resumen de escenarios aprobados/fallidos.
   - Reporte HTML: `target/karate-reports/karate-summary.html` (abrir en el navegador).

Para cambiar la URL base: `java -DbaseUrl=https://otra-url/v2 -jar karate-2.1.2.jar run features`

## Notas
- PetStore es una API pública compartida y de demostración: puede ser lenta o fallar de forma intermitente. El paso 4 reintenta hasta 5 veces cada 2 segundos.
- Se requiere conexión a internet.
