package snippet;

public class Snippet {
	<!DOCTYPE html>
	<html lang="pt">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>Memória Viva</title>
	    <style>
	        body {
	            font-family: Arial, sans-serif;
	            background-color:  #e6f2ff;
	            text-align: center;
	            margin: 0;
	            padding: 0;
	        }
	
	        .barra-topo, .barra-inferior {
	            background-color: #d9ecff;
	            height: 80px;
	            width: 100%;
	            position: absolute;
	            left: 0;
	            display: flex;
	            align-items: center;
	            justify-content: center;
	        }
	
	        .barra-topo {
	            top: 0;
	        }
	
	        .barra-inferior {
	            bottom: 0;
	        }
	
	        .titulo {
	            font-size: 26px;
	            font-weight: bold;
	            color: black;
	        }
	
	        .container {
	            width: 80%;
	            margin: auto;
	            padding: 20px;
	            margin-top: 120px; 
	        }
	
	        h2 {
	            margin-top: 20px;
	        }
	
	        .games {
	            display: flex;
	            justify-content: center;
	            gap: 30px;
	            margin-top: 20px;
	        }
	
	        .game {
	            padding: 10px;
	            background-color: white;
	            border-radius: 8px;
	            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1); 
	        }
	
	        .game img {
	            width: 200px;
	            border-radius: 5px;
	        }
	
	        .game p {
	            font-size: 18px;
	            margin-top: 10px;
	        }
	
	        .button {
	            padding: 10px 20px;
	            font-size: 16px;
	            background-color: white;
	            border: 2px solid black;
	            cursor: pointer;
	            font-weight: bold;
	            border-radius: 5px;
	        }
	
	        .button:hover {
	            background-color: #ddd;
	        }
	
	        .btn-voltar {
	            position: absolute;
	            left: 20px;
	            bottom: 20px;
	            background-color: #7aa9d6; 
	            color: white;
	            padding: 12px 25px;
	            border: none;
	            font-size: 16px;
	            font-weight: bold;
	            cursor: pointer;
	            border-radius: 8px;
	            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	            transition: background-color 0.3s ease, transform 0.2s ease;
	        }
	
	        .btn-voltar:hover {
	            background-color: #5f8cb7; 
	            transform: scale(1.05);
	        }
	
	        .btn-voltar:active {
	            transform: scale(0.95);
	        }
	    </style>
	</head>
	<body>
	
	    <div class="barra-topo">
	        <span class="titulo">Memória Viva 🔍</span>
	    </div>
	
	    <div class="container">
	        <h2>Treine a sua memória!</h2>
	        <div class="games">
	            <div class="game">
	                <img src="main/resources/templates/imagens/memory_game.png" alt="Jogo da Memória">
	                <p>Jogo da Memória 🧠</p>
	            </div>
	            <div class="game">
	                <img src="main/resources/templates/imagens/pairs_game.png" alt="Jogo dos Pares">
	                <p>Jogo dos Pares 🍀</p>
	            </div>
	        </div>
	    </div>
	
	    <div class="barra-inferior">
	        <button class="btn-voltar">Voltar</button>
	    </div>
	
	</body>
	</html>
	
}

