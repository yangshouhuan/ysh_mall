let websocket = null
const websocketUrl = 'ws://localhost:8090/websocket/'


function orderWebsocket(oid) {

	websocket = new WebSocket(websocketUrl + oid)

	websocket.onmessage = (e) => {
		console.log(e)
		console.log('支付完成')
		websocket.close();
	}
}