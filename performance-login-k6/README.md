# Ejercicio 1 - Prueba de carga del servicio de login (K6)

Prueba de carga a `POST https://fakestoreapi.com/auth/login` con credenciales parametrizadas desde CSV.

## Tecnologías y versiones
| Herramienta | Versión |
|---|---|
| k6 | v2.2.0 (go1.26.5, windows/amd64) |
| Sistema operativo | Windows 11 (PowerShell) |
| Conexión | Internet (requerida para librerías de reporte y para el API) |

Verificar versión instalada: `k6 version`

## Estructura
```
performance-login-k6/
├── login-test.js        # Script de la prueba
├── data/users.csv       # Credenciales (user,passwd)
├── reports/             # Reportes generados (html, json, txt)
├── README.md / readme.txt
└── conclusiones.txt
```

## Instalación de k6
- Windows: `winget install k6` (o `choco install k6`)
- macOS: `brew install k6`
- Linux: ver https://grafana.com/docs/k6/latest/set-up/install-k6/

## Ejecución paso a paso
1. Clonar el repositorio y entrar a la carpeta:
   `cd performance-login-k6`
2. Ejecutar la prueba (25 TPS durante 2 minutos, valores por defecto):
   `k6 run login-test.js`
3. Parámetros opcionales (la corrida de 20 TPS documentada usó este valor):
   `k6 run -e TPS=20 login-test.js`
   `k6 run -e TPS=25 -e DURATION=5m login-test.js`
4. Revisar resultados:
   - Consola: resumen con thresholds (✓ cumplido / ✗ incumplido)
   - `reports/report.html`: reporte visual (abrir en el navegador)
   - `reports/summary.json` y `reports/textSummary.txt`

> Nota: el script importa dos librerías remotas (reporte HTML y resumen), por lo que requiere conexión a internet.

## Escenario y validaciones
- Ejecutor `constant-arrival-rate`: 25 peticiones/s sostenidas por defecto (mínimo exigido: 20).
- Datos: 5 usuarios del CSV, usados de forma rotativa.
- Tiempo de respuesta máximo: 1,5 s (p95 < 1500 ms; máx. 3% de respuestas lentas).
- Tasa de error < 3% (`http_req_failed`).
- Se valida status 200/201 y presencia de `token` en la respuesta.
- Se verifica que el throughput alcance al menos el 95% del TPS configurado.
