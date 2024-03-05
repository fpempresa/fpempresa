<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error en el servidor</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff; /* Fondo blanco */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .error-container {
            text-align: center;
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.5); /* Ajustar sombreado */
            max-width: 600px;
            position: relative;
        }
        .error-icon {
            width: 100px;
            height: 100px;
            background-color: #0056B8;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto 20px;
        }
        .error-icon::before {
            content: "!";
            font-size: 48px;
            color: white;
        }
        h1 {
            color: #0056B8;
            font-size: 36px;
            margin-bottom: 20px;
        }
        p {
            color: #333;
            font-size: 18px;
            line-height: 1.6;
        }
        .error-code {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
            color: #0056B8;
        }
        .code {
            margin-top: 20px;
            font-size: 14px;
            color: #777;
        }
        
        .caption-error {
            font-weight: bold;
            color:#0056B8;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon"></div>
        <h1>¡Oops!</h1>
        <p class="error-code">Código de error: <%= response.getStatus() %></p> <!-- Obtener código de error -->
        <p>Lo sentimos, parece que ha habido un error al procesar su petición.</p>
        <p>Si el error persiste, contacte con el soporte de EmpleaFP.</p>
        <p class="code"><span class="caption-error">Fecha y hora actual:</span> <span id="currentDateTime"></span></p>
        <p class="code"><span class="caption-error">URL:</span> <span id="currentURL"></span></span></p>
    </div>

    <script>
        // Función para obtener la fecha y hora actual
        function getCurrentDateTime() {
            var now = new Date();
            var date = now.toLocaleDateString();
            var time = now.toLocaleTimeString();
            return date + ' ' + time;
        }
        function getCurrentURL() {
            var url = window.location.href;
            var queryString = window.location.search;
            return url.replace(queryString, "");
        }
        
        // Actualizar la fecha y hora actual en la página
        document.getElementById('currentDateTime').textContent = getCurrentDateTime();
        document.getElementById('currentURL').textContent = getCurrentURL();
    </script>
</body>
</html>
