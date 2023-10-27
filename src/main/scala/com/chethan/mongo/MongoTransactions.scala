package com.chethan.mongo

import java.util

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.{gt, in}
import com.mongodb.client.model.{Filters, ReplaceOptions, UpdateOptions, Updates}
import org.bson.Document
import org.bson.types.ObjectId

/**
  * Created by Chethan on Dec 22, 2022.
  */

object MongoTransactions {
  private def getMongoDatabase(username: String, password: String, host: String, port: Int, database: String): MongoDatabase = {
    val mongoConnection = new MongoConnection
    val mongoClient     = mongoConnection.getMongoClient(username, password, host, port)
    mongoClient.getDatabase(database)
  }


  def insertDocument(username: String, password: String, host: String, port: Int, database: String, collection: String) = {

    val mongoDatabase   = MongoTransactions.getMongoDatabase(username, password, host, port, database)
    val mongoCollection = mongoDatabase.getCollection(collection)

    val maps = new java.util.HashMap[String, String]()
    maps.put("a", "a1")
    maps.put("b", "b1")
    maps.put("c", "c1")

    val document = new Document()
      .append("_id", new ObjectId())
      .append("name", "apple")
      .append("gender", "female")
      .append("activities", util.Arrays.asList("running", "sleeping"))
      .append("maps", maps)
    mongoCollection.insertOne(document)
  }

  def insertMany(username: String, password: String, host: String, port: Int, database: String, collection: String) = {
    val mongoDatabase   = MongoTransactions.getMongoDatabase(username, password, host, port, database)
    val mongoCollection = mongoDatabase.getCollection(collection)

    val document1 = new Document().append("id", "abc123").append("name", "zach")
    val document2 = new Document().append("id", "xyz987").append("name", "yach")
    val documents = util.Arrays.asList(document1, document2)
    mongoCollection.insertMany(documents)
  }

  def updateDocument(username: String, password: String, host: String, port: Int, database: String, collection: String) = {
    val mongoDatabase   = MongoTransactions.getMongoDatabase(username, password, host, port, database)
    val mongoCollection = mongoDatabase.getCollection(collection)
    val document        = new Document().append("_id", "abc123") // filter condition

    val bson = Updates.combine(Updates.set("runtime", 99),
      Updates.addToSet("genres", "Sports"), //  field to be updated to added
      Updates.currentTimestamp("lastUpdated"))

    val updateOptions = new UpdateOptions().upsert(true)
    val result        = mongoCollection.updateOne(document, bson, updateOptions)
    result.getUpsertedId
    result.getModifiedCount
  }


  def updateMany(username: String, password: String, host: String, port: Int, database: String, collection: String) = {
    val mongoDatabase   = MongoTransactions.getMongoDatabase(username, password, host, port, database)
    val mongoCollection = mongoDatabase.getCollection(collection)

    val query = gt("name", "")

    val inQuery = in("_id", new ObjectId("63a3ee99dbdb44494fbe952b"), new ObjectId("63a3efd42268706f62f807a5"), new ObjectId("63a3f1eb779f792cf90a3abb"))
    // val document = new Document().append("name", "updatedName").append("runtime", 99)

    val bson = Updates.combine(
      Updates.addToSet("updatedAll", "Frequently Discussed"),
      Updates.currentTimestamp("lastUpdated"),
      Updates.addEachToSet("lastUpdated2", java.util.Arrays.asList("aaaa", "bbbb")))

    mongoCollection.updateMany(inQuery, util.Arrays.asList(bson, bson))
  }


  def replaceDocument(username: String, password: String, host: String, port: Int, database: String, collection: String) = {
    val mongoDatabase   = MongoTransactions.getMongoDatabase(username, password, host, port, database)
    val mongoCollection = mongoDatabase.getCollection(collection)

    val document        = new Document().append("name", "updatedName")
    val replaceDocument = new Document().append("simple", "123456789").append("nothing", "-=-=-=-=-")

    val replace = new ReplaceOptions().upsert(true)
    mongoCollection.replaceOne(document, replaceDocument, replace)
  }

  def deleteDocument(username: String, password: String, host: String, port: Int, database: String, collection: String) = {
    val mongoDatabase   = MongoTransactions.getMongoDatabase(username, password, host, port, database)
    val mongoCollection = mongoDatabase.getCollection(collection)

    val document = new Document().append("simple", new ObjectId("63a3f4452a4a82f4a6f6784b"))
    val eq       = Filters.eq("_id", new ObjectId("63a3db6da651465d047ab1ee"))

    val json    = """{"_id" : "63c523be25bfe50def898618", "_id" : "63c523702e28d873ea185486"}"""
    val jsonDoc = Document.parse(json)
    mongoCollection.deleteOne(jsonDoc)
  }

  def deleteMany(username: String, password: String, host: String, port: Int, database: String, collection: String) = {
    val mongoDatabase   = MongoTransactions.getMongoDatabase(username, password, host, port, database)
    val mongoCollection = mongoDatabase.getCollection(collection)

    // val query = lt("gender", "female")

    //    val delMen= Map("_id" -> "63a3ee99dbdb44494fbe952b")
    //    val json  = """{"name" : "apple"}"""
    //    val query = Document.parse(json)
    //val in      = Filters.in("_id",new ObjectId("63c523dd7ee18e5007faba8c"), new ObjectId("63c523dd7ee18e5007faba8b"),new ObjectId("63c523be25bfe50def898619"))
    val json    = """{"_id" :[ "63c523be25bfe50def898618", "63c523702e28d873ea185486"]}"""
    val jsonDoc = Document.parse(json)

    val del    = """{_id: { $in: [   ObjectId("63c523be25bfe50def898618"),   ObjectId("63c523702e28d873ea185486") ]}}"""
    val delDoc = Document.parse(del)


    mongoCollection.deleteMany(delDoc)
  }

  def main(args: Array[String]): Unit = {
    //  insertDocument("", "", "", 4, "MangoDB", "Mango")
    //  insertMany("", "", "", 4, "MangoDB", "Mango")
    //    updateDocument("", "", "", 4, "MangoDB", "Mango")
    //    updateMany("", "", "", 4, "MangoDB", "Mango")
    //    replaceDocument("", "", "", 4, "MangoDB", "Mango")
    //    deleteDocument("", "", "", 4, "MangoDB", "Mango")
    deleteMany("", "", "", 4, "MangoDB", "Mango")

    //println(Simple(10).getClass)

    val simple1 = new Simple1(10)
    //    simple1 match {
    //      case Simple(a)  => println(a)
    //      case a: Simple  => a.dummy
    //      case a@ Simple1(b) => a.dummy
    //    }

    // val simple=new Simple(99)

    // println(simple.unapply(simple))


    val simple = Simple(10)

  }

  case class Simple(val a: Int) {

    def dummy = true

    def this() =
      this(10)
  }

  case class Simple1(val a: Int) {

    def dummy = true

    def this() =
      this(10)

  }

}
