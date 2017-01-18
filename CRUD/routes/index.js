var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var mysql = require('mysql');
var sqlOp = require('../models/mysqlOp.js');

router.use(bodyParser.json());
router.use(bodyParser.urlencoded({ extended: false }));

/* GET home page. */
router.get('/', (req, res) => {
  sqlOp.select.selectAll().then((res) => {
    return new Promise((resolve, reject) => {
      resolve(res);
    });
  }).then((response) => {
    res.render('index', {
      title: 'CRUD Assignment',
      pageID: 'home',
      notes: response
    });
  });
});

/* GET Individual Note Page*/
router.get('/note/:key', (req, res, next) => {
  sqlOp.select.selectInd(req.params.key).then((res) => {
    return new Promise((resolve, reject) => {
      resolve(res[0]);
    });
  }).then((response) => {
    res.render('note', {
      title: 'CRUD Assignment',
      note: response
    });
  });
});

/*GET Edit Individual Note Page*/
router.get('/edit/:key', (req, res, next) => {
  sqlOp.select.selectInd(req.params.key).then((res) => {
    return new Promise((resolve, reject) => {
      resolve(res[0]);
    });
  }).then((response) => {
    res.render('edit', {
      title: 'CRUD Assignment',
      note: response
    });
  });
});

module.exports = router;
