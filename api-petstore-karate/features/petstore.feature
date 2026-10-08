Feature: PetStore - ciclo de vida de una mascota (API REST)

  Background:
    * url baseUrl
    * header Accept = 'application/json'
    * header Content-Type = 'application/json'
    # Datos de entrada (se generan únicos para no chocar con otras personas que usan la API pública)
    * def petId = java.lang.System.currentTimeMillis()
    * def petName = 'Firulais-' + petId
    * def updatedName = 'Firulais-Actualizado-' + petId

  @e2e
  Scenario: Crear, consultar por ID, actualizar y consultar por status
    # ---------- 1. Añadir una mascota a la tienda ----------
    Given path 'pet'
    And request
      """
      {
        "id": #(petId),
        "category": { "id": 1, "name": "Perros" },
        "name": "#(petName)",
        "photoUrls": ["https://example.com/firulais.jpg"],
        "tags": [ { "id": 1, "name": "amigable" } ],
        "status": "available"
      }
      """
    When method post
    Then status 200
    And match response.id == petId
    And match response.name == petName
    And match response.status == 'available'
    * print 'Mascota creada con id:', petId

    # ---------- 2. Consultar la mascota creada (búsqueda por ID) ----------
    Given path 'pet', petId
    When method get
    Then status 200
    And match response ==
      """
      {
        "id": "#(petId)",
        "category": { "id": 1, "name": "Perros" },
        "name": "#(petName)",
        "photoUrls": ["https://example.com/firulais.jpg"],
        "tags": [ { "id": 1, "name": "amigable" } ],
        "status": "available"
      }
      """

    # ---------- 3. Actualizar nombre y status a "sold" ----------
    Given path 'pet'
    And request
      """
      {
        "id": #(petId),
        "category": { "id": 1, "name": "Perros" },
        "name": "#(updatedName)",
        "photoUrls": ["https://example.com/firulais.jpg"],
        "tags": [ { "id": 1, "name": "amigable" } ],
        "status": "sold"
      }
      """
    When method put
    Then status 200
    And match response.id == petId
    And match response.name == updatedName
    And match response.status == 'sold'

    # ---------- 4. Consultar la mascota modificada (búsqueda por status) ----------
    # La API pública es compartida y puede tardar en reflejar el cambio, por eso se reintenta.
    * configure retry = { count: 5, interval: 2000 }
    * def buscar = function(lista) { return karate.filter(lista, function(p) { return p.id == petId }) }
    Given path 'pet', 'findByStatus'
    And param status = 'sold'
    And retry until responseStatus == 200 && buscar(response).length > 0
    When method get
    Then status 200
    * def encontradas = buscar(response)
    And match encontradas == '#[1]'
    And match encontradas[0].name == updatedName
    And match encontradas[0].status == 'sold'

  @negativo
  Scenario: Consultar una mascota que no existe devuelve 404
    Given path 'pet', 999999999999
    When method get
    Then status 404
    And match response.message == 'Pet not found'
