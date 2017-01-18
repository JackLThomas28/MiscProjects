var express    = require('express');
var bodyParser = require('body-parser');
var reload     = require('reload');
var index      = require('./routes/index');

var app = express();
// view engine setup
app.set('port', 3000);
app.set('views', './views');
app.set('view engine', 'ejs');

app.set('appData', require('./data/data.json'));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(express.static('./public'));

//routes
app.use(require('./routes/index'));
app.use(require('./routes/api.js'));

var server = app.listen(app.get('port'), function() {
  console.log('Listening on port ' + app.get('port'));
});

reload(server, app);
