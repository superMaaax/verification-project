if (y > 0) {
            int n = y;
            //@ decreases n;
            //@ maintaining sum == x + y - n && 0 <= n;
            while (n > 0) {
                sum = sum + 1;
                n = n - 1;
            }
        }
