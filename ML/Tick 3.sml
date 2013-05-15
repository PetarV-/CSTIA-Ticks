fun first(x1,y1) = x1;
fun second(x1,y1) = y1;
fun startpoints([],_) = [] 
  | startpoints(x::xs,z) = if z=second(x) then first(x)::startpoints(xs,z) 
                           else startpoints(xs,z);
startpoints([(1,2), (2,3), (2,1)], 2);
fun endpoints(_,[]) = [] 
  | endpoints(z,x::xs) = if z=first(x) then second(x)::endpoints(z,xs) 
                         else endpoints(z,xs);
endpoints(2, [(1,2), (2,3), (2,1)]);
fun spari(x,y::[]) = [(x,y)] 
  | spari(x,y::ys) = (x,y)::spari(x,ys);
fun allpairs([], []) = [] 
  | allpairs([], y::ys) = [] 
  | allpairs(x::xs,[]) = [] 
  | allpairs(x::[], ys) = spari(x,ys) 
  | allpairs(x::xs,ys) = spari(x,ys) @ allpairs(xs,ys); 
allpairs([1,2,3], [4,5,6]);
fun member(x,[]) = false 
  | member(x,y::ys) = if not (x=y) then member(x,ys) 
                      else true;
fun prune([],ys) = ys 
  | prune(x::xs,ys) = if member(x,ys) then prune(xs,ys) 
                      else prune(xs,x::ys);
fun unique(l) = prune(l,[]);
fun addnew((x,y),pairs) = unique((x,y)::pairs @ (allpairs(startpoints(pairs,x),y::[]) @ allpairs(x::[], endpoints(y,pairs)))); 
addnew((1,2),[(3,1)]);
fun routeH([],acc) = acc 
  | routeH(x::xs,acc) = routeH(xs,addnew(x,acc));
fun routes(xs) = routeH(xs,[]);
routes [(1,2), (2,3), (2,1)];
