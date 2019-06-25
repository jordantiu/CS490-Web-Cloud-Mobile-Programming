/**
 * Created by Vijaya Yeruva on 5/27/2017.
 */
var http = require('http');
var fs = require('fs');
http.createServer(function (req, res) {
    fs.readFile('demofile1.html', function (err, data) {
        res.writeHead(200, {'Content-Type': 'text/html'});
        res.write(data);
        res.end();
    });
}).listen(8080);
console.log('Server running at http://localhost:8080');