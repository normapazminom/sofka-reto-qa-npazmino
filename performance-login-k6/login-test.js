import http from 'k6/http';
import { check } from 'k6';
import { Rate } from 'k6/metrics';
import { SharedArray } from 'k6/data';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';

// ---------- Parámetros (se pueden sobreescribir con -e VARIABLE=valor) ----------
const BASE_URL = __ENV.BASE_URL || 'https://fakestoreapi.com';
const TPS = parseInt(__ENV.TPS || '20');          // peticiones por segundo objetivo (mínimo exigido: 20)
const DURATION = __ENV.DURATION || '2m';          // duración de la carga sostenida
const MAX_RESPONSE_MS = 1500;                     // tiempo de respuesta máximo permitido
const MAX_ERROR_RATE = 0.03;                      // tasa de error aceptable (< 3%)

// ---------- Datos: se cargan una sola vez desde el CSV ----------
const users = new SharedArray('users', function () {
  return open('./data/users.csv')
    .split('\n')
    .slice(1)                                     // omite el encabezado
    .map((l) => l.trim())
    .filter((l) => l.length > 0)
    .map((l) => {
      const [user, passwd] = l.split(',');
      return { user, passwd };
    });
});

// Métrica propia: peticiones que superan 1,5 s
const slowResponses = new Rate('slow_responses');

export const options = {
  scenarios: {
    login_load: {
      executor: 'constant-arrival-rate',          // mantiene un ritmo fijo de TPS
      rate: TPS,
      timeUnit: '1s',
      duration: DURATION,
      preAllocatedVUs: 30,
      maxVUs: 100,
    },
  },
  thresholds: {
    http_req_failed: [`rate<${MAX_ERROR_RATE}`],                // errores < 3%
    http_req_duration: [`p(95)<${MAX_RESPONSE_MS}`, `max<${MAX_RESPONSE_MS * 2}`],
    slow_responses: [`rate<${MAX_ERROR_RATE}`],                 // máx. 3% de respuestas > 1,5 s
    checks: ['rate>0.97'],
    http_reqs: [`rate>=${TPS * 0.95}`],                         // verifica que se alcanzó el TPS
  },
};

export default function () {
  // Reparte los usuarios del CSV de forma rotativa entre iteraciones
  const u = users[(__ITER + __VU) % users.length];

  const payload = JSON.stringify({ username: u.user, password: u.passwd });
  const params = {
    headers: { 'Content-Type': 'application/json' },
    timeout: '60s',                               // equivalente a --max-time 60 del curl
    tags: { name: 'POST /auth/login' },
  };

  const res = http.post(`${BASE_URL}/auth/login`, payload, params);

  slowResponses.add(res.timings.duration > MAX_RESPONSE_MS);

  check(res, {
    'status es 200/201': (r) => r.status === 200 || r.status === 201,
    'devuelve token': (r) => {
      try { return !!r.json('token'); } catch (e) { return false; }
    },
    'respuesta < 1.5 s': (r) => r.timings.duration < MAX_RESPONSE_MS,
  });
}

// Genera reportes al finalizar
export function handleSummary(data) {
  return {
    'reports/report.html': htmlReport(data),
    'reports/summary.json': JSON.stringify(data, null, 2),
    'reports/textSummary.txt': textSummary(data, { indent: ' ', enableColors: false }),
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
  };
}
