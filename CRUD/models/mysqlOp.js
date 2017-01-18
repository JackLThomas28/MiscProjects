var con = require('./sql.js');

var insert = function(notekey, title, body) {
  var note = {
    notekey: notekey,
    title: title,
    body: body
  };
  return new Promise((resolve, reject) => {
    con.query('INSERT INTO notes(notekey, title, body) VALUES(?,?,?)', [notekey, title, body], function(err) {
      if(err) reject(err);
      else resolve(note);
    });
  });
};

var update = function(title, body, URLkey) {
  return new Promise((resolve, reject) => {
    con.query('UPDATE notes SET title = ?, body = ? WHERE notekey = ?', [title, body, URLkey], function(err, results) {
      if(err) reject(err);
      else resolve(URLkey);
    });
  });
};

var destroy = function(URLkey) {
  return new Promise((resolve, reject) => {
    con.query('DELETE FROM notes WHERE notekey = ?', [URLkey], function(err, result) {
      if(err) reject(err);
      else resolve(URLkey);
    });
  });
};

var selectAll = function() {
  return new Promise((resolve, reject) => {
    con.query('SELECT * FROM notes', function(err, rows, fields) {
      if(err) reject(err);
      else resolve(rows);
    });
  });
};

var selectInd = function(notekey) {
  return new Promise((resolve, reject) => {
    con.query('SELECT * FROM notes WHERE notekey = ?', [notekey], function(err, row, fields) {
      if(err) reject(err);
      else resolve(row);
    });
  });
};

var select = {
  selectAll: selectAll,
  selectInd: selectInd,
};

var mysqlOperations = {
  update:  update,
  select:  select,
  destroy: destroy,
  insert:  insert
};

module.exports = mysqlOperations;
