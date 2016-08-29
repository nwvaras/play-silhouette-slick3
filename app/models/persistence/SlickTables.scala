package models.persistence

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo
import models.User
import models.entities._
import play.api.Play
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfig }
import play.api.libs.json.Json
import slick.driver.JdbcProfile
/**
 * The companion object.
 */
object SlickTables extends HasDatabaseConfig[JdbcProfile] {

  protected lazy val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._
  abstract class BaseTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def created = column[java.sql.Timestamp]("created")

  }
  abstract class BaseUserTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
    def created = column[java.sql.Timestamp]("created")

  }

  case class SimpleSupplier(name: String, desc: String)

  class SuppliersTable(tag: Tag) extends BaseTable[Supplier](tag, "suppliers") {
    def name = column[String]("name")
    def desc = column[String]("desc")
    def * = (id, name, desc, created) <> (Supplier.tupled, Supplier.unapply)
  }
  val suppliersTableQ: TableQuery[SuppliersTable] = TableQuery[SuppliersTable]
  //////////////////////////////////////////////////////////////
  ///case class User(id: Long, username: String, mail: String, password: String, userType: String,  marketId : Long) extends BaseEntity
  //////////////////////////////////////////////////////////////

  /*
  case class User(
  userID: UUID,
  loginInfo: LoginInfo,
  firstName: Option[String],
  lastName: Option[String],
  fullName: Option[String],
  email: Option[String],
  avatarURL: Option[String],
  activated: Boolean) extends Identity {
   */
  class UsersTable(tag: Tag) extends BaseUserTable[User](tag, "users") {
    def id = column[UUID]("user_id")
    def loginInfo = column[LoginInfo]("login_info")
    def firstName = column[Option[String]]("first_name")
    def lastName = column[Option[String]]("last_name")
    def fullName = column[Option[String]]("full_name")
    def email = column[Option[String]]("email")
    def avatarURL = column[Option[String]]("avatar_url")
    def activated = column[Boolean]("activated")
    def marketId = column[Option[Long]]("market_id")
    def rut = column[Option[String]]("rut")
    //def market = foreignKey("market_k", marketId, marketsTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    implicit val boolColumnType = MappedColumnType.base[LoginInfo, String](
      { loginInfo => Json.obj("providerId" -> loginInfo.providerID, "providerKey" -> loginInfo.providerKey).toString }, // map Bool to Int
      { string =>
        {
          val js = Json.parse(string)
          LoginInfo((js \ "providerId").toString, (js \ "providerKey").toString)
        }
      } // map Int to Bool
    )
    def * = (id, loginInfo, firstName, lastName, fullName, email, avatarURL, activated, rut, marketId, created) <> ((User.apply _).tupled, User.unapply)
  }
  val usersTableQ: TableQuery[UsersTable] = TableQuery[UsersTable]

  /////////////////////////////////////////////////////////////////
  //case class Offer(id: Long, offUserId: Long, offProductId: Long, offAmount:Long, wantedUserId: Long, wantedProductId: Long ,wantedAmount: Long) extends BaseEntity
  ////
  class OffersTable(tag: Tag) extends BaseTable[Offer](tag, "offers") {

    def marketId = column[Long]("market_id")
    // def market = foreignKey("market_k", marketId, marketsTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def offProductId = column[Long]("off_product_id")
    //def offProduct = foreignKey("off_product_k", offProductId, productsTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def offAmount = column[Long]("off_amount")
    def wantedUserId = column[Long]("wanted_user_id")
    // def wantedUser = foreignKey("wanted_user_k", wantedUserId, usersTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def wantedProductId = column[Long]("wanted_product_id")
    //  def wantedProduct = foreignKey("wanted_product_k", wantedProductId, productsTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def wantedAmount = column[Long]("wanted_amount")

    def * = (id, marketId, offProductId, offAmount, wantedUserId, wantedProductId, wantedAmount, created) <> (Offer.tupled, Offer.unapply)
  }
  val offersTableQ: TableQuery[OffersTable] = TableQuery[OffersTable]
  ////////////////////////////////////////////////////////////////
  //case class Market(id: Long, name: String, desc: String) extends BaseEntity}
  /////////////////////////////////////////////////////////////////
  class MarketsTable(tag: Tag) extends BaseTable[Market](tag, "markets") {
    def name = column[String]("name")
    def desc = column[String]("desc")
    def * = (id, name, desc, created) <> (Market.tupled, Market.unapply)
  }
  val marketsTableQ: TableQuery[MarketsTable] = TableQuery[MarketsTable]
  ///////////////////////////////////////////////////////////////////
  //////case class Transaction(id: Long, offUserId: Long, offProductId: Long, offAmount:Long, offMarginal: Double, wantedUserId: Long, wantedProductId: Long ,wantedAmount: Long, wantedMarginal: Double) extends BaseEntity

  class TransactionsTable(tag: Tag) extends BaseTable[Transaction](tag, "transactions") {
    def desc = column[String]("desc")
    def offUserId = column[Long]("off_user_id")
    // def offUser = foreignKey("wanted_user_k", offUserId, usersTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def offProductId = column[Long]("off_product_id")
    // def offProduct = foreignKey("off_product_k", offProductId, productsTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def offAmount = column[Long]("off_amount")
    def offMarginal = column[Double]("off_marginal")
    def wantedUserId = column[Long]("wanted_user_id")
    // def wantedUser = foreignKey("wanted_user_k", wantedUserId, usersTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def wantedProductId = column[Long]("wanted_product_id")
    //  def wantedProduct = foreignKey("wanted_product_k", wantedProductId, productsTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def wantedAmount = column[Long]("wanted_amount")
    def wantedMarginal = column[Double]("wanted_marginal")

    def * = (id, desc, offUserId, offProductId, offAmount, offMarginal, wantedUserId, wantedProductId, wantedAmount, wantedMarginal, created) <> (Transaction.tupled, Transaction.unapply)
  }
  val transactionsTableQ: TableQuery[TransactionsTable] = TableQuery[TransactionsTable]

  ////////////////////////////////////////////////////////////////////////
  //case class Product(id: Long, userId: Long, productTypeId: Long, productQuantity :Long, productConstant: Double, productExponential: Double,override val createdAt: java.sql.Timestamp,override val updatedAt: java.sql.Timestamp) extends BaseEntity

  //
  class ProductsTable(tag: Tag) extends BaseTable[Product](tag, "products") {
    def userId = column[Long]("user_id")
    //  def user = foreignKey("wanted_user_k", userId, usersTableQ)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def productTypeId = column[Long]("product_type_id")
    def productQuantity = column[Long]("product_quantity")
    def productConstant = column[Double]("product_constant_")
    def productExponential = column[Double]("product_exponential")

    def * = (id, userId, productTypeId, productQuantity, productConstant, productExponential, created) <> (Product.tupled, Product.unapply)
  }
  val productsTableQ: TableQuery[ProductsTable] = TableQuery[ProductsTable]
  //////////////////////////////
  ///  /case class ProductType(id: Long, name: String) extends BaseEntity
  /////////
  class ProductTypesTable(tag: Tag) extends BaseTable[ProductType](tag, "product_types") {
    def name = column[String]("name")
    def * = (id, name, created) <> (ProductType.tupled, ProductType.unapply)
  }
  val productTypesTableQ: TableQuery[ProductTypesTable] = TableQuery[ProductTypesTable]
}
