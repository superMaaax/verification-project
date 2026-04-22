class NextGreaterElem {
    /*@ requires nums1 != null && nums2 != null;
      @ requires (\forall int i; 0 <= i && i < nums1.length; (\exists int j; 0 <= j && j < nums2.length; nums1[i] == nums2[j]));
      @ ensures \result != null;
      @ ensures \result.length == nums1.length;
      @ ensures (\forall int i; 0 <= i && i < nums1.length;
      @              (\exists int j; 0 <= j && j < nums2.length && nums2[j] == nums1[i] &&
      @               (\exists int k; j < k && k < nums2.length;
      @                   nums2[k] == \result[i] &&
      @                   (\forall int t; j < t && t < k; nums2[t] < nums1[i])))
      @              || (\result[i] == -1 &&
      @                  (\forall int k; 0 <= k && k < nums2.length;
      @                      (\forall int j; 0 <= j && j < nums2.length && nums2[j] == nums1[i] && j < k;
      @                          nums2[k] < nums1[i]))));
      @*/
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] res = new int[m];
        //@ maintaining 0 <= i && i <= m;
        //@ maintaining res != null && res.length == m;
        //@ decreases m - i;
        for (int i = 0; i < m; ++i) {
            int j = 0;
            //@ maintaining 0 <= j && j <= n;
            //@ maintaining (\forall int t; 0 <= t && t < j; nums2[t] != nums1[i]);
            //@ decreases n - j;
            while (j < n && nums2[j] != nums1[i]) {
                ++j;
            }
            int k = j + 1;
            //@ maintaining j + 1 <= k && k <= n;
            //@ maintaining (\forall int t; j < t && t < k; nums2[t] < nums2[j]);
            //@ decreases n - k;
            while (k < n && nums2[k] < nums2[j]) {
                ++k;
            }
            res[i] = k < n ? nums2[k] : -1;
        }
        return res;
    }
}
