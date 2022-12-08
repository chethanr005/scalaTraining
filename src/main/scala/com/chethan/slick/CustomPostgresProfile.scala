package com.chethan.slick

import com.fasterxml.jackson.annotation.JsonValue
import com.github.tminglei.slickpg.{ExPostgresProfile, PgArraySupport, PgHStoreSupport, PgJsonSupport}

/**
  * Created by Chethan on Dec 07, 2022.
  */

trait CustomPostgresProfile extends ExPostgresProfile
                            with PgArraySupport //for list
                            with PgHStoreSupport //for Map with PgJsonSupport
                            {

   override val api = CustomPostgresAPI
                             // override def pgjson="jsonb"

  object CustomPostgresAPI extends API
                           with ArrayImplicits
                           with HStoreImplicits
                         //  with JsonImplicits {

    implicit val stringListTypeMapper = new SimpleArrayJdbcType[String]("varchar").to(_.toList)
    implicit val intListTypeMapper = new SimpleArrayJdbcType[String]("Int").to(_.toList)
    //implicit  val json = new AdvancedArrayJdbcType[JsonValue]()

  }


object CustomPostgresProfile extends CustomPostgresProfile