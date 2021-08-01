const http = require('http')

http.createServer(async function (request, response) {

    if (request.url.startsWith("/failure")) {
        response.statusCode = 500
        response.write('Failure')
    } else if (request.url.startsWith("/slow")) {
        await new Promise((resolve => {
            setTimeout(function () {resolve()}, 800)
        })).then(() => {
            sendSuccessfulResponse(response)
        })
    } else if (request.url.startsWith("/timeout")) {
        await new Promise((resolve => {
            setTimeout(function () {resolve()}, 1800)
        })).then(() => {
            sendSuccessfulResponse(response)
        })
    } else {
        sendSuccessfulResponse(response)
    }

    response.end()
}).listen(5000)


function sendSuccessfulResponse(response) {
    response.writeHead(200, {"Content-Type": "application/json"})
    response.write(JSON.stringify(randomDish()))
}

function randomDish() {
    const menu = [
        {description: "Sesame chicken"},
        {description: "Lo mein noodles, plain"},
        {description: "Sweet & sour beef"},
    ]
    return menu[Math.floor(Math.random() * 3)]
}