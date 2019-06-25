/**
 * Created by karthik on 7/14/17.
 */
const MongoClient = require('mongodb').MongoClient;
const assert = require('assert');
const bodyParser = require("body-parser");
const express = require('express');
const cors = require('cors');
const app = express();

const url = 'mongodb+srv://user:123@cluster0-4sgqu.mongodb.net/test?retryWrites=true&w=majority';//1.Modify this url with the credentials of your db name and password.
const ObjectID = require('mongodb').ObjectID;

app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const insertDocument = function(db, data, res, callback) {
    db.collection('books').insertOne( data, function(err) {
        if(err)
        {
            res.write("Insert Failed, Error While Inserting");
            res.end();
        }
        console.log("Inserted a document into the books collection.");
        callback();
    });
};
app.post('/create', function (req, res) {
    MongoClient.connect(url, function(err, client) {
        if(err)
        {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }

        const db = client.db('library');

        insertDocument(db, req.body, res, function() {
            res.write("Successfully inserted");
            res.end();
        });
    });
});

app.get('/get', function (req, res) {
    MongoClient.connect(url, function(err, client) {
        if(err)
        {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }

        const db = client.db('library');

        db.collection('books').find().toArray(function(err, result){
            if(err)
            {
                res.write("get Failed");
                res.end();
            }else
            {

                res.send(JSON.stringify(result));
            }
            console.log("Got All Documents");

        });
    });
});

app.get('/delete/:id', function (req, res) {
    MongoClient.connect(url, function(err, client) {
        if(err)
        {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }

        const db = client.db('library');

        db.collection('books').deleteOne({ _id: new ObjectID(req.params.id) }, function(err){
            if(err)
            {
                res.write("delete Failed");
                res.end();
            }else
            {
                res.write("Successfully deleted");
                res.end();
            }
        });
    });
});


app.get('/update', function (req, res) {
    MongoClient.connect(url, function(err, client) {
        if(err)
        {
            res.write("Failed, Error while connecting to Database");
            res.end();
        }

        const db = client.db('library');

        const query = { _id: new ObjectID(req.query.id) };
        const values = { $set: {bookName: req.query.bookName,
                authorName: req.query.authorName,
                noOfCopies: req.query.noOfCopies,
                edition: req.query.edition,
                ISBN:req.query.ISBN } };

        db.collection('books').updateOne(query, values, function(err, result) {
            if(err)
            {
                res.write("update Failed");
                res.end();
            }else
            {
                res.write("Successfully deleted");
                res.end();
            }
        });
    });
});


const server = app.listen(8081, function () {
    const host = server.address().address;
    const port = server.address().port;

    console.log("Example app listening at http://%s:%s", host, port)
});