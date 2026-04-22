/*@
  maintaining n > 0;
  maintaining \old(n) == n * (2^loop_count);
  decreases n;
@*/
while (n % 2 == 0) {
    n /= 2;
}
