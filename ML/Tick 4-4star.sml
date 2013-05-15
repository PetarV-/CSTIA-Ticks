val s = fn x => x+1;
val pr = fn x => fn y => x+y;
val pp = fn x => fn y => x*y;
fun nfold(f,0) = (fn x => x) 
  | nfold(f,n) = (fn x => f(nfold(f,n-1) x));
fun sum(a,b) = nfold(s,a) b;
sum(2,3);
fun product(a,b) = nfold(pr(a),b) a - a;
product(2,3);
fun power(a,b) = nfold(pp(a),b) a div a;
power(2,3);
datatype 'a stream = Cons of 'a * (unit -> 'a stream);
fun from k = Cons(k,fn() => from(k+1));
fun nth(Cons(a,_),1) = a 
  | nth(Cons(_,f),n) = nth(f(),n-1);
nth(from 1, 100);
fun sqSeq k = Cons(k*k,fn() => sqSeq(k+1));
nth(sqSeq 1, 49);
fun map2(f,Cons(x,fx),Cons(y,fy)) = Cons(f(x,y),fn() => map2(f,fx(),fy()));
fun plus(m,n) = m+n;
fun tail(Cons(x,fx)) = fx();
fun fibs() = Cons(1,fn() => Cons(1,fn() => map2(plus,fibs(),tail(fibs())))); 
nth(fibs(), 15);
fun merge(Cons(x,fx),Cons(y,fy)) = if (x < y) then Cons(x,fn() => merge(fx(),Cons(y,fy))) 
                                   else if (x > y) then Cons(y,fn() => merge(Cons(x,fx),fy())) 
                                   else Cons(x,fn() => merge(fx(),fy()));
val mul2 = fn i => i*2;
val mul3 = fn i => i*3;
val mul5 = fn i => i*5;
fun map1(f,Cons(x,fx)) = Cons(f(x),fn() => map1(f,fx()));
fun str23() = Cons(1, fn() => merge(map1(mul2,str23()), map1(mul3,str23()))); 
fun str235() = Cons(1, fn() => tail(merge(str23(),map1(mul5,str235())))); 
nth(str235(), 60);
