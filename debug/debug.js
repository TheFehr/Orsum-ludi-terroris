window.addEventListener("load", function() {
	var xmlReq = new XMLHttpRequest();
	xmlReq.open("GET", "export.txt", true);
	xmlReq.onload = function() {
		if (xmlReq.readyState == XMLHttpRequest.DONE) {
			var obj = JSON.parse(xmlReq.response);
			
			if (obj) {
				document.body.removeChild(document.querySelector("#status"));
				
				var can = document.querySelector("canvas");
				var size = obj.size * 100
				can.width = size;
				can.height = size;
				var ctx = can.getContext("2d");
				
				drawCoordinatesGrid(ctx, obj);
				drawLines(ctx, obj, size);
				drawNodes(ctx, obj, size);
				drawObstacles(ctx, obj, size);
			} else {
				document.querySelector("#status").innerHTML = "Can't parse JSON";
			}
		}
	};
	
	xmlReq.onerror = function() {
		document.querySelector("#status").innerHTML = "Can't load export file";
	};
	
	xmlReq.send();
});

function drawNodes(ctx, obj, size) {
	var player = obj.player;
	var monster = obj.monster;
	for (var i = 0; i < obj.nodes.length; i++) {
		var node = obj.nodes[i];
		var x = node[0] * size;
		var y = size - node[1] * size;
		
		if (player[0] == node[0] && player[1] == node[1]) {
			ctx.fillStyle = "blue";
		}
		
		if (monster[0] == node[0] && monster[1] == node[1]) {
			ctx.fillStyle = "red";
		}
		
		ctx.beginPath()
		ctx.arc(x, y, 6, 0, Math.PI * 2, true);
		ctx.fill();
		ctx.closePath();
	}
}

function drawCoordinatesGrid(ctx, obj) {
	ctx.strokeStyle = "#ababab";
	
	for (var i = 1; i < obj.size; i++) {
		var x = (ctx.canvas.width / obj.size) * i;
		var y = (ctx.canvas.height / obj.size) * i;
		
		ctx.beginPath();
		ctx.moveTo(x, 0);
		ctx.lineTo(x, ctx.canvas.height);
		ctx.stroke();
		ctx.closePath();
		
		ctx.beginPath();
		ctx.moveTo(0, y);
		ctx.lineTo(ctx.canvas.width, y);
		ctx.stroke();
		ctx.closePath();
	}
}

function drawLines(ctx, obj, size) {
	ctx.strokeStyle = "#ddaaa9";
	
	for (var i = 0; i < obj.lines.length; i++) {
		var line = obj.lines[i];
		var start = { x: line[0][0] * size, y: size - line[0][1] * size };
		var end = 	{ x: line[1][0] * size, y: size - line[1][1] * size };
		
		ctx.beginPath();
		ctx.moveTo(start.x, start.y);
		ctx.lineTo(end.x, end.y);
		ctx.stroke();
		ctx.closePath();
	}
}

function drawObstacles(ctx, obj, size) {
	ctx.fillStyle = "#e88";
	
	for (var i = 0; i < obj.obstacles.length; i++) {
		var obstacle = obj.obstacles[i];
		var quadSize = size / obj.size;
		var x = (obstacle[0] / obj.size) * size;
		var y = size - (obstacle[1] / obj.size) * size;
		
		var bottomLeft =  { x:  x, 			  y:  y 		   };
		var bottomRight = { x:  x + quadSize, y:  y 		   };
		var topLeft = 	  { x:  x, 			  y:  y - quadSize };
		var topRight = 	  { x:  x + quadSize, y:  y - quadSize };
		
		console.log(bottomLeft);
		console.log(bottomRight);
		console.log(" ");
		
		ctx.beginPath();
		ctx.moveTo(bottomLeft.x, bottomLeft.y);
		ctx.lineTo(bottomRight.x, bottomRight.y);
		ctx.lineTo(topRight.x, topRight.y);
		ctx.lineTo(topLeft.x, topLeft.y);
		ctx.lineTo(bottomLeft.x, bottomLeft.y);
		ctx.fill();
		ctx.closePath();
	}
}
