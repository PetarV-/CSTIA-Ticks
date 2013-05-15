fun sumGeneral(x:real, n:int) : real = if n=1 then x 
                                       else x+sumGeneral(x/2.0,n-1); 
fun sumt(n) = sumGeneral(1.0, n);
sumt(2);
fun eGeneral(x:real, n:int, count:real) = if floor(count)=n then x 
                                          else x+eGeneral(x/count,n,count+1.0);
fun eapprox(n:int) = eGeneral(1.0,n,1.0); 
eapprox(4);
eapprox(7);
eapprox(15);
fun eExp(z:real, x:real, n:int, count:real) = if n=floor(count) then x 
                                              else x+eExp(z,x*z/count,n,count+1.0);
fun exp(z:real, n:int) = eExp(z, 1.0, n, 1.0);
exp(2.0, 4);
exp(2.0, 9); 
exp(2.0, 11); 
exp(2.0, 20);
