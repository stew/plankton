package plankton

object Zoo extends cats.syntax.AllSyntax {
  // Tags
  type deprecated       = scala.deprecated
  type inline           = scala.inline
  type tailrec          = scala.annotation.tailrec
  type transient        = scala.transient
  type unchecked        = scala.unchecked
  type volatile         = scala.volatile
  type implicitNotFound = scala.annotation.implicitNotFound

  // TYPES
  type Any                  = scala.Any
  type AnyRef               = scala.AnyRef
  type AnyVal               = scala.AnyVal
  type BigInt               = scala.BigInt
  type BigDecimal           = scala.BigDecimal
  type Boolean              = scala.Boolean
  type Byte                 = scala.Byte
  type Char                 = scala.Char
  type Double               = scala.Double
  type Float                = scala.Float
  type Int                  = scala.Int
  type Long                 = scala.Long
  type Nothing              = scala.Nothing
  type PartialFunction[A,B] = scala.PartialFunction[A,B]
  type Product              = scala.Product
  type Serializable         = scala.Serializable
  type Short                = scala.Short
  type String               = java.lang.String
  type Unit                 = scala.Unit
  type StringContext        = scala.StringContext
  type Option[A]            = scala.Option[A]
  type Either[A,B]          = scala.Either[A,B]
  type List[A]              = scala.List[A]
  type Nel[A]               = cats.data.NonEmptyList[A]
  type ::[A]                = scala.::[A]
  type NonEmptyList[A]      = cats.data.NonEmptyList[A]

  // COMPANIONS

  final val BigInt          = scala.BigInt
  final val BigDecimal      = scala.BigDecimal
  final val Boolean         = scala.Boolean
  final val Byte            = scala.Byte
  final val Char            = scala.Char
  final val Double          = scala.Double
  final val Float           = scala.Float
  final val Int             = scala.Int
  final val Long            = scala.Long
  final val Short           = scala.Short
  final val Unit            = scala.Unit
  final val StringContext   = scala.StringContext
  final val Option          = scala.Option
  final val Either          = scala.Either
  final val Left            = scala.Left
  final val Right           = scala.Right
  final val Some            = scala.Some
  final val None            = scala.None
  final val List    = scala.List
  final val Nil    = scala.Nil
  final val NonEmptyList    = cats.data.NonEmptyList
  final val Nel             = cats.data.NonEmptyList
  final val ::              = scala.::

  // from cats
  type Eval[A]    = cats.Eval[A]
  type Eq[A]      = cats.Eq[A]
  type Order[A]   = cats.Order[A]
  type PartialOrder[A]   = cats.PartialOrder[A]

  type Applicative[F[_]] = cats.Applicative[F]
  type Alternative[F[_]] = cats.Alternative[F]
  type Cartesian[F[_]] = cats.Cartesian[F]
  type Monad[F[_]] = cats.Monad[F]
  type Comonad[F[_]] = cats.Comonad[F]
  type MonadCombine[F[_]] = cats.MonadCombine[F]
  type Traverse[F[_]] = cats.Traverse[F]
  type FlatMap[F[_]] = cats.FlatMap[F]
  type CoflatMap[F[_]] = cats.CoflatMap[F]
  type Foldable[F[_]] = cats.Foldable[F]
  type Reducible[F[_]] = cats.Reducible[F]
  type SemigroupK[F[_]] = cats.SemigroupK[F]
  type MonoidK[F[_]] = cats.MonoidK[F]
  type Monoid[A] = cats.Monoid[A]
  type Semigroup[A] = cats.Semigroup[A]
  type Show[A] = cats.Show[A]
  type Ior[L,R] = cats.data.Ior[L,R]
  type Validated[E,A] = cats.data.Validated[E,A]
  type ValidatedNel[E,A] = cats.data.ValidatedNel[E,A]

  val Applicative = cats.Applicative
  val Comonad = cats.Comonad
  val Cartesian = cats.Cartesian
  val Monad = cats.Monad
  val MonadCombine = cats.MonadCombine
  val Traverse = cats.Traverse
  val FlatMap = cats.FlatMap
  val CoflatMap = cats.CoflatMap
  val Foldable = cats.Foldable
  val Reducible = cats.Reducible
  val SemigroupK = cats.SemigroupK
  val MonoidK = cats.MonoidK
  val Monoid = cats.Monoid
  val Semigroup = cats.Semigroup
  val Validated = cats.data.Validated
  val Show = cats.Show
  val Ior = cats.data.Ior

  final val Eval  = cats.Eval
  final val Eq    = cats.Eq
  final val Order = cats.Order
  final val PartialOrder = cats.PartialOrder

  // SUBTYPING
  type <~<[A,B] = cats.evidence.<~<[A,B]
  type  As[A,B] = cats.evidence.As[A,B]
  type ===[A,B] = cats.evidence.===[A,B]
  type  Is[A,B] = cats.evidence.Is[A,B]

  /**
   * An instance of `A <:< B` witnesses that `A` is a subtype of `B`.
   * Requiring an implicit argument of the type `A <:< B` encodes
   * the generalized constraint `A <: B`.
   *
   * @note we need a new type constructor `<:<` and evidence `conforms`,
   * as reusing `Function1` and `identity` leads to ambiguities in
   * case of type errors (`any2stringadd` is inferred)
   *
   * To constrain any abstract type T that's in scope in a method's
   * argument list (not just the method's own type parameters) simply
   * add an implicit argument of type `T <:< U`, where `U` is the required
   * upper bound; or for lower-bounds, use: `L <:< T`, where `L` is the
   * required lower bound.
   *
   * In part contributed by Jason Zaugg.
   */
  @implicitNotFound(msg = "Cannot prove that ${From} <:< ${To}.")
  sealed abstract class <:<[-From, +To] extends (From => To) with Serializable
  private[this] final val singleton_<:< = new <:<[Any,Any] { def apply(x: Any): Any = x }
  // The dollar prefix is to dodge accidental shadowing of this method
  // by a user-defined method of the same name (SI-7788).
  // The collections rely on this method.
  implicit def $conforms[A]: A <:< A = singleton_<:<.asInstanceOf[A <:< A]

  // TYPE EQUALITY

  /** An instance of `A =:= B` witnesses that the types `A` and `B` are equal.
   *
   * @see `<:<` for expressing subtyping constraints
   */
  @implicitNotFound(msg = "Cannot prove that ${From} =:= ${To}.")
  sealed abstract class =:=[From, To] extends (From => To) with Serializable
  private[this] final val singleton_=:= = new =:=[Any,Any] { def apply(x: Any): Any = x }
  object =:= {
     implicit def tpEquals[A]: A =:= A = singleton_=:=.asInstanceOf[A =:= A]
  }

  // METHODS

  @inline def identity[A](a: A): A = a

  /**
   * Summon an instance of a type from implicit scope.
   * 
   * http://stackoverflow.com/questions/5598085/where-does-scala-look-for-implicits
   */
  def implicitly[A](implicit a: A): A = a

  @inline def print(x: Any): Unit   = scala.Console.print(x)
  @inline def println(): Unit       = scala.Console.println()
  @inline def println(x: Any): Unit = scala.Console.println(x)

  implicit def toIdOps[A](a: A): IdOps[A] = new IdOps(a)

  implicit final class ArrowAssoc[A](val self: A) extends AnyVal {
    import scala.Tuple2
    @inline def -> [B](y: B): Tuple2[A, B] = Tuple2(self, y)
    def →[B](y: B): Tuple2[A, B] = ->(y)
  }  

}
