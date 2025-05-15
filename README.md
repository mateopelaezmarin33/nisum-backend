🧪 Cómo probar la aplicación

Esta sección te guía para ejecutar y probar la API RESTful localmente.

✅ 1. Requisitos previos
   -Java 17+

   -Gradle 8+ o usar el wrapper (./gradlew)

   -IntelliJ, VS Code u otro IDE

   -(opcional) Postman o Swagger

🚀 2. Ejecutar la aplicación
    Desde línea de comandos:
    ./gradlew bootRun
    O desde tu IDE, ejecuta la clase: com.nisum.nisum.NisumApplication
    
🌐 3. Acceder a Swagger UI (documentación interactiva)
    Una vez corriendo, abre:

    http://localhost:8080/swagger-ui/index.html

📬 4. Probar el endpoint de registro
    Endpoint: POST /user/create
    Content-Type: application/json

📥 Request de ejemplo:
    json
    {
      "name": "Juan Pérez",
      "email": "juan@example.com",
      "password": "Abcd1234!",
      "phones": [
        {
          "number": "1234567",
          "citycode": "1",
          "contrycode": "57"
        }
      ]
    }
📤 Response esperado:
    {
      "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
      "name": "Juan Pérez",
      "email": "juan@example.com",
      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI...",
      "created": "2025-05-14T10:00:00",
      "modified": "2025-05-14T10:00:00",
      "lastLogin": "2025-05-14T10:00:00",
      "isActive": true
    }
❗ 5. Errores esperados
      Email ya registrado	400	{"mensaje": "Email ya registrado"}
      Email inválido	400	{"mensaje": "Email inválido"}
      Contraseña no válida	400	{"mensaje": "Contraseña inválida"}

🧪 6. Ejecutar pruebas unitarias
      ./gradlew test
      Usa Run with Coverage en IntelliJ para ver cobertura por clase.

