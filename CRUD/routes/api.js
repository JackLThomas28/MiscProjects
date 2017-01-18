var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var sqlOp = require('../models/mysqlOp.js');

router.use(bodyParser.json());
router.use(bodyParser.urlencoded({ extended: false }));

// Set the default api route that returns data
var table;
router.get('/api', function(req, res, next) {
  sqlOp.select.selectAll().then((res) => {
    return new Promise((resolve, reject) => {
      table = res;
      resolve(res);
    });
  }).then((response) => {
    res.json(response);
  }, (err) =>{
    console.log("Err", err);
  });
});

//Set the route for posting a note
router.post('/api/submit/', (req, res) => {
  sqlOp.insert(req.body.notekey, req.body.title, req.body.body).then((resp) => {
    return new Promise((resolve, reject) => {
      table.unshift(resp);
      resolve(resp);
    });
  }, (err) => {
    console.log("Err", err);
  });
  res.sendStatus(200);
});

//Set the route for deleting a note
router.delete('/api/delete/:key', (req, res) => {
  sqlOp.destroy(req.params.key).then((res) => {
    return new Promise((resolve, reject) => {
      for (var i = 0; i < table.length; i++) {
        if(res === table[i].notekey) {
          table.splice(i, 1);
        }
      }
      resolve(table);
    });
  }).then((response) => {
    res.json(response);
  }, (err) => {
    console.log("Err", err);
  });
});

//set the route for editing a note
router.put('/api/edit/:key', (req, res) => {
  sqlOp.update(req.body.title, req.body.body, req.params.key).then((res) => {
    return new Promise((resolve, reject) => {
      for (var i = 0; i < table.length; i++) {
        if(res === table[i].notekey) {
          table[i].title = req.body.title;
          table[i].body  = req.body.body;
        }
      }
      resolve(table);
    }, (err) => {
      console.log("Err", err);
    });
  })
  .then((response) => {
    console.log('table', table)
    res.send({});
  }, (err) => {
    console.log("Err", err);
  });
});

// con.end();
module.exports = router;
