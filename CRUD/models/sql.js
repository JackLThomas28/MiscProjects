var mysql = require('mysql');

var con = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'notes'
});

con.connect(function(err) {
  if(!err) {
    console.log("Database is connected");
  }
  else {
    console.log('Error in connecting to database', err);
  }
});
 
module.exports = con;
