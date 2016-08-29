package models

import java.util.UUID

import com.mohiva.play.silhouette.api.{ LoginInfo, Identity }
import models.entities.DateMapper

/**
 * The user object.
 *
 * @param userID The unique ID of the user.
 * @param firstName Maybe the first name of the authenticated user.
 * @param lastName Maybe the last name of the authenticated user.
 * @param fullName Maybe the full name of the authenticated user.
 * @param email Maybe the email of the authenticated provider.
 * @param avatarURL Maybe the avatar URL of the authenticated provider.
 * @param activated Indicates that the user has activated its registration.
 */
//Json.obj("providerId" -> loginInfo.providerID, "providerKey" -> loginInfo.providerKey).toString }
case class User(
  userID: UUID,
  providerId: String,
  providerKey: String,
  firstName: Option[String],
  lastName: Option[String],
  fullName: Option[String],
  email: Option[String],
  avatarURL: Option[String],
  activated: Boolean,
  rut: Option[String] = Some(""),
  marketId: Option[Long] = Some(0),
  created: java.sql.Timestamp = DateMapper.d
) extends Identity {

  /**
   * Tries to construct a name.
   *
   * @return Maybe a name.
   */
  def name = fullName.orElse {
    firstName -> lastName match {
      case (Some(f), Some(l)) => Some(f + " " + l)
      case (Some(f), None) => Some(f)
      case (None, Some(l)) => Some(l)
      case _ => None
    }
  }

  val loginInfo = LoginInfo(providerId, providerKey)
}
