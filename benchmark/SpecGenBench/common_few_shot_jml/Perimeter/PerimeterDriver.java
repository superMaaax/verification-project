public class PerimeterDriver {
	
	 //@ requires select >= 0 && select <= 5;
	 //@ ensures select == 0 ==> \result == 4 * (long) shortNum;
	 //@ ensures select == 1 ==> \result == 5 * (long) w;
	 //@ ensures select == 2 ==> \result == 6 * longNum;
	 //@ ensures select == 3 ==> \result == 2 * ((long) w + (long) x);
	 //@ ensures select == 4 ==> \result == (long) w + (long) x + (long) y;
	 //@ ensures select == 5 ==> \result == (long) w + (long) x + (long) y + (long) z;
	 public  long driver(int select, int w, int x, int y, int z, short shortNum, long longNum) {

		Perimeter p = new Perimeter();
		long result = 0;

		switch (select) {
		case 0:
			result = p.Perimeter(shortNum);
			break;
		case 1:
			result = p.Perimeter(w);
			break;
		case 2:
			result = p.Perimeter(longNum);
			break;
		case 3:	
			result = p.Perimeter(w, x);
			break;
		case 4:
			result = p.Perimeter(w, x, y);
			break;
		case 5:
			result = p.Perimeter(w, x, y, z);
			break;
		}
		return result;
	}
}
