fun allcons(x,[]) = [] | allcons(x,xs::ys) = (x::xs)::allcons(x,ys); 
allcons(6, [[1,2,3],[2],[]]);
fun concat([],ys) = ys | concat(x::xs,ys) = x::concat(xs,ys); 
concat([[1],[2,3]],[[],[4,5,6]]);
fun choose(0,_) = [[]] | choose(_,[]) = [] | choose(k,x::xs) = if (k > length(x::xs)) then [] else if (k < length(x::xs)) then concat(allcons(x,choose(k-1,xs)), choose(k,xs)) else allcons(x,choose(k-1,xs));
choose(3,[1,2]);
choose(3,[1,2,3]); 
choose(3,[1,2,3,4,5]);
