class PassPillow {
    //@ requires n > 1;
    //@ requires time >= 0;
    //@ ensures 1 <= \result && \result <= n;
    //@ ensures \result == ((\old(time) % ((n-1)*2) < n) ? (\old(time) % ((n-1)*2) + 1) : (n*2 - (\old(time) % ((n-1)*2)) - 1));
    public int passPillow(int n, int time) {
        time %= (n - 1) * 2;
        return time < n ? time + 1 : n * 2 - time - 1;
    }
}
