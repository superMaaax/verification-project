public class Perimeter
{   

    /*@ public normal_behavior
      @   ensures \result == 4L * x;
      @*/
     long Perimeter(short x)
    {
	long squarePerimeter = 4 * (long)x;
	return squarePerimeter;
    }

    /*@ public normal_behavior
      @   ensures \result == 5L * x;
      @*/
     long Perimeter(int x)
    {
	long pentagonPerimeter = 5 * (long)x;
	return pentagonPerimeter;
    }

    /*@ public normal_behavior
      @   ensures \result == 6L * x;
      @*/
     long Perimeter(long x)
    {
	long hexagonalPerimeter = 6 * x;
	return hexagonalPerimeter;
    }

    /*@ public normal_behavior
      @   ensures \result == 2L * ((long)x + (long)y);
      @*/
     long Perimeter(int x, int y)
    {
	long perimeterRectangle = 2*((long)x + (long)y);
	return perimeterRectangle;
    }

    /*@ public normal_behavior
      @   ensures \result == (long)x + (long)y + (long)z;
      @*/
     long Perimeter(int x, int y, int z)
    {
	long trianglePerimeter = (long)x + (long)y + (long)z;
	return trianglePerimeter;
    }

    /*@ public normal_behavior
      @   ensures \result == (long)w + (long)x + (long)y + (long)z;
      @*/
     long Perimeter(int w, int x, int y, int z)
    {
	long trapeziumPerimeter = (long)w + (long)x + (long)y + (long)z;
	return trapeziumPerimeter;
    }
}
