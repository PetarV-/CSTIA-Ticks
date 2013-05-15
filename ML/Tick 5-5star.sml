type color = int * int * int;
type xy = int * int;
type image = (color array) array;
fun image((w,h):xy) = fn(clr:color) => Array.tabulate(h,fn i => Array.array(w,clr)):image;
fun size(im:image) = (Array.length(Array.sub(im,0)),Array.length(im)):xy;
fun drawPixel(im:image) = fn(clr:color) => fn((x,y):xy) => Array.update(Array.sub(im,x-1),y-1,clr);
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
        j := !j + 1 
      ); 
      TextIO.output(oc,"\n"); 
      i := !i + 1; 
      j := 0 
    ); 
    TextIO.closeOut oc 
  end;
fun drawHorLine(im,x,clr:color) = 
  let val (w,h) = size im 
      val j = ref 0 
  in 
    while (!j < w) do 
    (
      drawPixel im clr (x,!j+1); 
      j := !j + 1 
    ) 
  end;
fun drawVertLine(im,y,clr:color) = 
  let val (w,h) = size im 
      val i = ref 0 
  in 
    while (!i < h) do 
    (
      drawPixel im clr (!i+1,y); 
      i := !i + 1 
    ) 
  end;
fun drawLine(im:image) = fn (clr:color) => fn ((x0,y0):xy) => fn ((x1,y1):xy) => 
                                                                let val dx = Int.abs(x1-x0) 
                                                                    val dy = Int.abs(y1-y0) 
                                                                    val sx = if (x0 < x1) then 1 
                                                                             else ~1 
                                                                    val sy = if (y0 < y1) then 1 
                                                                             else ~1 
                                                                    val x = ref x0 
                                                                    val y = ref y0 
                                                                    val err = ref (dx - dy) 
                                                                    val e2 = ref 1 
                                                                in 
                                                                  while (not((!x = x1) andalso (!y = y1))) do 
                                                                  ( 
                                                                    drawPixel im clr (!x,!y); 
                                                                    e2 := 2*(!err); 
                                                                    if (!e2 > ~dy) then (err := !err - dy; x := !x + sx ) 
                                                                    else err := !err; 
                                                                    if (!e2 < dx) then ( err := !err + dx; y := !y + sy) 
                                                                    else err := !err 
                                                                  ); 
                                                                  drawPixel im clr (x1,y1) 
                                                                end;
val PETAR = image (2000,400) (200,200,200);
drawHorLine(PETAR,50,(0,0,0));
drawHorLine(PETAR,350,(0,0,0));
drawVertLine(PETAR,50,(0,0,0));
drawVertLine(PETAR,1950,(0,0,0));
drawLine PETAR (255,0,0) (100,100) (300,100);
drawLine PETAR (255,0,0) (100,100) (100,300);
drawLine PETAR (255,0,0) (100,300) (200,300);
drawLine PETAR (255,0,0) (200,300) (200,100);
drawLine PETAR (0,100,0) (100,500) (300,500);
drawLine PETAR (0,100,0) (100,500) (100,700);
drawLine PETAR (0,100,0) (200,500) (200,700);
drawLine PETAR (0,100,0) (300,500) (300,700);
drawLine PETAR (0,0,255) (100,900) (100,1100);
drawLine PETAR (0,0,255) (100,1000) (300,1000);
drawLine PETAR (255,0,255) (100,1400) (300,1300);
drawLine PETAR (255,0,255) (100,1400) (300,1500);
drawLine PETAR (255,0,255) (200,1300) (200,1500);
drawLine PETAR (255,255,0) (100,1700) (300,1700);
drawLine PETAR (255,255,0) (100,1700) (100,1900);
drawLine PETAR (255,255,0) (100,1900) (200,1900);
drawLine PETAR (255,255,0) (200,1900) (200,1700);
drawLine PETAR (255,255,0) (200,1700) (300,1900);
toPPM PETAR "Petar.PPM";
