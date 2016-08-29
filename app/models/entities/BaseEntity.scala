package models.entities

trait BaseEntity {
  val id: Long
  def isValid = true
  val created: java.sql.Timestamp
}