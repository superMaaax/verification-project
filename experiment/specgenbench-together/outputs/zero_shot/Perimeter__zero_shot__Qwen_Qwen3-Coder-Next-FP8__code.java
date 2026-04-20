public class Perimeter
{   

     /*@ 
      @ requires x >= 0;
      @ ensures \result == 4 * (long)x;
      @ assignable \nothing;
      @*/
     long Perimeter(short x)
    {
	long squarePerimeter = 4 * (long)x;
	return squarePerimeter;
    }

     /*@ 
      @ requires x >= 0;
      @ ensures \result == 5 * (long)x;
      @ assignable \nothing;
      @*/
     long Perimeter(int x)
    {
	long pentagonPerimeter = 5 * (long)x;
	return pentagonPerimeter;
    }

     /*@ 
      @ requires x >= 0;
      @ ensures \result == 6 * x;
      @ assignable \nothing;
      @*/
     long Perimeter(long x)
    {
	long hexagonalPerimeter = 6 * x;
	return hexagonalPerimeter;
    }

     /*@ 
      @ requires x >= 0 && y >= 0;
      @ ensures \result == 2 * ((long)x + (long)y);
      @ assignable \nothing;
      @*/
     long Perimeter(int x, int y)
    {
	long perimeterRectangle = 2*((long)x + (long)y);
	return perimeterRectangle;
    }

     /*@ 
      @ requires x >= 0 && y >= 0 && z >= 0;
      @ ensures \result == (long)x + (long)y + (long)z;
      @ assignable \nothing;
      @*/
     long Perimeter(int x, int y, int z)
    {
	long trianglePerimeter = (long)x + (long)y + (long)z;
	return trianglePerimeter;
    }

     /*@ 
      @ requires w >= 0 && x >= 0 && y >= 0 && z >= 0;
      @ ensures \result == (long)w + (long)x + (long)y + (long)z;
      @ assignable \nothing;
      @*/
     long Perimeter(int w, int x, int y, int z)
    {
	long trapeziumPerimeter = (long)w + (long)x + (long)y + (long)z;
	return trapeziumPerimeter;
    }
}
