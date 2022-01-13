package training

object prime extends App{
  var res:Boolean=true;
def IsPrime(n:Int): Boolean ={ for(i<-2 until n) if (n % i == 0) res=false; res};
  println(if(IsPrime(27))"Prime" else "Not Prime");
}
