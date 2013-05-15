fun area(x,y) = x*y/2.0;
area(3.0, 4.0);
1.0-0.9-0.1;
fun rootplus(a,b,c) = (~b + Math.sqrt(b*b-4.0*a*c))/(2.0*a); 
fun compute(a:real, b:real, c:real, x:real) = a*x*x + b*x + c; 
rootplus(1.0, 121.0, 11.0);
compute(1.0, 121.0, 11.0, it);
rootplus(1.22, 3.34, 2.28);
compute(1.22, 3.34, 2.28, it);
fun facr(n:int) = if n = 0 then 1 
                  else n*facr(n-1); 
facr(3);
fun faci(n:int, comp:int) = if n = 0 then comp 
                            else faci(n-1,comp*n); 
faci(3,1);
