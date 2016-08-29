package models.daos

import java.util.UUID
import javax.inject.{ Inject, Singleton }

import com.mohiva.play.silhouette.api.LoginInfo
import models.User
import models.persistence.SlickTables
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the user object.
 */
@Singleton
class UserDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.db
  import dbConfig.driver.api._

  protected val tableQ = SlickTables.usersTableQ
  /**
   * Finds a user by its login info.
   *
   * @param loginInfo The login info of the user to find.
   * @return The found user or None if no user for the given login info could be found.
   */
  def find(loginInfo: LoginInfo): Future[Option[User]] = {
    db.run(tableQ.filter(_.providerId === loginInfo.providerID).filter(_.providerKey === loginInfo.providerKey).result.headOption)
  }

  /**
   * Finds a user by its user ID.
   *
   * @param userID The ID of the user to find.
   * @return The found user or None if no user for the given ID could be found.
   */
  def find(userID: UUID) = db.run(tableQ.filter(_.id === userID).result.headOption)

  /**
   * Saves a user.
   *
   * @param user The user to save.
   * @return The saved user.
   */
  def save(user: User) = {
    db.run(tableQ += user).flatMap(x => Future(user))
  }
}

