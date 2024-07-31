var host = 'http://localhost:8080';

function saveOrderData(orderDto) {
    fetch("http://localhost:8080/order/save", {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(orderDto)
    })
}