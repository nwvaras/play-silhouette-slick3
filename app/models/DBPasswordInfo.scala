package models

import com.mohiva.play.silhouette.api.LoginInfo

/**
 * Created by Nicolas on 29-08-2016.
 */
/**
 * def hasher = column[String]("hasher")
 * def password = column[String]("password")
 * def salt = column[Option[String]]("salt")
 * def providerId = column[String]("provider_id")
 * def providerKey = column[String]("provider_key")
 *
 */
case class DBPasswordInfo(hasher: String, password: String, salt: Option[String], providerId: String, providerKey: String) {
  val loginInfo: LoginInfo = LoginInfo(providerId, providerKey)
}