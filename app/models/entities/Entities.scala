package models.entities

object DateMapper {

  def d = new java.sql.Timestamp((new java.util.Date).getTime)

}
case class Market(id: Long, name: String, desc: String, created: java.sql.Timestamp = DateMapper.d) extends BaseEntity
case class Offer(id: Long, marketId: Long, offProductId: Long, offAmount: Long, wantedUserId: Long, wantedProductId: Long, wantedAmount: Long, created: java.sql.Timestamp = DateMapper.d) extends BaseEntity
case class Product(id: Long, userId: Long, productTypeId: Long, productQuantity: Long, productConstant: Double, productExponential: Double, created: java.sql.Timestamp = DateMapper.d) extends BaseEntity
case class ProductType(id: Long, name: String, created: java.sql.Timestamp = DateMapper.d) extends BaseEntity
case class Supplier(id: Long, name: String, desc: String, created: java.sql.Timestamp = DateMapper.d) extends BaseEntity
//case class User(id: Long, username: String, mail: String, password: String, userType: String,  marketId : Long, created: java.sql.Timestamp =DateMapper.d ) extends BaseEntity
case class Transaction(id: Long, desc: String, offUserId: Long, offProductId: Long, offAmount: Long, offMarginal: Double, wantedUserId: Long, wantedProductId: Long, wantedAmount: Long, wantedMarginal: Double, created: java.sql.Timestamp = DateMapper.d) extends BaseEntity