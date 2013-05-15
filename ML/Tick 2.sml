fun last(x::[]) = x 
  | last(x::xs) = last(xs);
last [1,2,3];
fun butLast(x::[]) = [] 
  | butLast(x::xs) = x::butLast(xs); 
butLast [1,2,3];
fun nth(x::xs,0) = x 
  | nth(x::xs,n) = nth(xs,n-1); 
nth([1,2,3], 1);
