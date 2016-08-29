package models.daos

import javax.inject.{ Inject, Singleton }

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import models.DBPasswordInfo
import models.persistence.SlickTables
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

/**
 * Created by Nicolas on 29-08-2016.
 */
@Singleton
class PasswordInfoDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends DelegableAuthInfoDAO[PasswordInfo] {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig.db
  import dbConfig.driver.api._
  protected val tableQ = SlickTables.usersTableQ
  protected val tableQ2 = SlickTables.passwordInfosTableQ
  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] = {

    getOptionPassword(loginInfo)
  }

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    getPassword(loginInfo)
  }

  override def remove(loginInfo: LoginInfo): Future[Unit] = {
    db.run(tableQ.filter(_.providerId === loginInfo.providerID).filter(_.providerKey === loginInfo.providerKey).delete).flatMap(x => Future({}))
  }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    db.run(tableQ2 += DBPasswordInfo(authInfo.hasher, authInfo.password, authInfo.salt, loginInfo.providerID, loginInfo.providerKey)).flatMap(x => Future(authInfo))
  }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = {
    db.run(tableQ2 += DBPasswordInfo(authInfo.hasher, authInfo.password, authInfo.salt, loginInfo.providerID, loginInfo.providerKey)).flatMap(x => Future(authInfo))
  }

  protected def findDBPassword(loginInfo: LoginInfo) = {
    db.run(tableQ2.filter(_.providerId === loginInfo.providerID).filter(_.providerKey === loginInfo.providerKey).result.headOption)
  }
  protected def getOptionPassword(loginInfo: LoginInfo) = {
    findDBPassword(loginInfo).flatMap {
      case s: Some[DBPasswordInfo] =>
        println("yes")
        val ps = s.get
        val realPassword = PasswordInfo(ps.hasher, ps.password, ps.salt)
        Future(Some(realPassword))
      case _ =>
        println("nope")
        Future(None)

    }
  }
  protected def getPassword(loginInfo: LoginInfo) = {
    findDBPassword(loginInfo).flatMap {
      case s: Some[DBPasswordInfo] =>
        val ps = s.get
        val realPassword = PasswordInfo(ps.hasher, ps.password, ps.salt)
        Future(realPassword)
      case _ =>
        Future(null)

    }

  }
}
