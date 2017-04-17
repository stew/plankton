package plankton

final class IdOps[A](private val a: A) {
  /**
   * apply a function to a value, also called Thrush, see
   * http://www.angelfire.com/tx4/cus/combinator/birds.html
   */
  def |>[B](f: A => B): B = f(a)
  def ▹[B](f: A => B): B = f(a)
  def $[B](f: A => B): B = f(a)

  /**
   * also called Kestrel, see
   * http://www.angelfire.com/tx4/cus/combinator/birds.html
   */
  def <|(f: A => scala.Unit): A = { f(a); a }
  def ◃(f: A => scala.Unit): A = { f(a); a }
  def unsafeTap(f: A => scala.Unit): A = { f(a); a }
}

trait PhytoBedazzle {
  @scala.inline implicit def augmentString(x: java.lang.String): scala.collection.immutable.StringOps
    = scala.Predef.augmentString(x)

}
