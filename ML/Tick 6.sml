type color = int * int * int;
type xy = int * int;
type image = (color array) array;
fun image((w,h):xy) = fn(clr:color) => Array.tabulate(h,fn i => Array.array(w,clr)):image;
fun size(im:image) = (Array.length(Array.sub(im,0)),Array.length(im)):xy;
fun drawPixel(im:image) = fn(clr:color) => fn((x,y):xy) => Array.update(Array.sub(im,x- 1),y-1,clr);
fun format4 i = StringCvt.padLeft #" " 4 (Int.toString i);
fun formatCLR((r,g,b):color) = format4 r ^ format4 g ^ format4 b;
fun toPPM image filename =
  let val oc = TextIO.openOut filename 
      val (w,h) = size image
      val i = ref 0
      val j = ref 0
  in
    TextIO.output(oc,"P3\n" ^ Int.toString w ^ " " ^ Int.toString h ^ "\n255\n"); 
    while (!i < h) do
    (
      while (!j < w) do 
      (
        TextIO.output(oc,formatCLR(Array.sub(Array.sub(image,!i),!j)));
        j := !j+1 
      );
      TextIO.output(oc,"\n"); 
      i := !i +1;
      j := 0
    );
    TextIO.closeOut oc 
  end;
fun drawAll(f:(xy -> color)) = fn im:image =>
                                 let val (w,h) = size im 
                                     val i = ref 0
                                     val j = ref 0
                                 in
                                   while (!i < h) do 
                                   (
                                     while (!j < w) do 
                                     (
                                       drawPixel im (f(!i,!j)) (!i+1,!j+1);
                                       j := !j + 1 
                                     );
                                     j := 0;
                                     i := !i + 1 
                                   )
                                 end;
fun gradient(x,y) = (((x div 30) * 30) mod 256, 0, ((y div 30) * 30) mod 256);
fun gradImage() =
  let val im = ref (image (640,480) (255,255,255)) 
  in
  (
    drawAll gradient (!im); 
    toPPM (!im) "gradient.ppm"
  ) 
  end;
fun mandelbrot maxIter (x,y) = 
  let fun solve(a,b) c = if c = maxIter then 1.0
                         else if (a*a + b*b <= 4.0) then solve (a*a - b*b + x, 2.0*a*b + y) (c+1) 
                         else (real c)/(real maxIter)
  in
    solve (x,y) 0
  end;
fun chooseColour n =
  let val r = round((Math.cos n) * 255.0) 
      val g = round((Math.cos n) * 255.0) 
      val b = round((Math.sin n) * 255.0)
  in
    (r,g,b) 
  end;
fun rescale((w,h):xy) = fn(cx:real,cy:real,s:real) => fn((x,y):xy) => 
                                                        let val p = s*((real y)/(real w) - 0.5) + cx
                                                            val q = s*((real (h-x))/(real h) - 0.5) + cy
                                                        in
                                                          (p,q) 
                                                        end;
fun compute (cx,cy,s) =
  let val im = ref (image (640,640) (255,255,255)) 
      val i = ref 0
      val j = ref 0
  in
  (
    while (!i < 640) do 
    (
      while (!j < 640) do 
      (
        drawPixel (!im) (chooseColour(mandelbrot 60 (rescale (640,640) (cx,cy,s) (!i,!j)))) (!i+1,!j+1);
        j := !j + 1 
      );
      j := 0;
      i := !i + 1
    );
    toPPM (!im) "mandelbrot.ppm" 
  )
  end;
compute (~0.74364990, 0.13188204, 0.00073801);
