package training

object Demo extends App {
  val author = new Writer("Manan","Vohara",1988)
  val novel = new Novel("Preethiya Parivala",2009,author)
  println(novel.authorAge)
  /***
   *
   */
  val counter = new Counter
  counter.inc.print
  counter.inc.inc.print
  counter.dec(-8).print

  val obj=new Check
  val obj1=new Check
  //println(obj)
  println(obj.dem)
  //println(obj1)
  println(obj1.dem)
}
/////////////////////////////////////////////////////////////////////////////
class Writer(firstName:String, surName:String, val year:Int)
{
  def fullName() = firstName+surName
}

class Novel(novelName:String, yearOfRelease:Int, authorName:Writer)
{
  def authorAge = yearOfRelease - authorName.year
  def isWrittenBy(authorName: Writer) = authorName == this.authorName
  //def copy(newYear:Int) = Novel = new Novel(novelName,newYear,authorName)
}
////////////////////////////////////////////////////////////////////////////
class Counter(val count:Int=0) {
  //def currentCount = count
  def inc = {
    println("Incrementing")
    new Counter(count + 1)
  }

  def dec = {
    println("Decrementing")
    new Counter(count - 1)
  }

  def inc(n: Int): Counter = {
    if (n <= 0) this
    else inc.inc(n - 1)
  }

  def dec(n: Int): Counter = {
    if (n <= 0) this
    else dec.dec(n - 1)
  }

  def print = println(count)


  def default(n: Int): Counter = {
    if (1 == 1) this
    else new Counter
  }
}

/////////////////////////////////////////////////////////////////////////////
trait Example extends Counter
{
  def ex :String = "hey"
  inc
  dec
}
class Check()
{
  def dem = this
}
