const http = require('http')

http.createServer(async function (request, response) {
    if (request.url.startsWith("/failure")) {
        response.statusCode = 500
        response.write('Failure')
    } else if (request.url.startsWith("/slow")) {
        await new Promise((resolve => {
            setTimeout(function () {
                resolve('Success But Slow')
            }, 800)
        })).then(resoledMessage => {
            response.statusCode = 200
            response.write(resoledMessage)
        })
    } else {
        response.statusCode = 200
        response.write('Success')
    }

    response.end()
}).listen(5000)