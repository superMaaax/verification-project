class NextGreaterElem {
    //@ requires nums1 != null && nums2 != null;
    //@ ensures \result != null && \result.length == nums1.length;
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] res = new int[m];
        //@ maintaining 0 <= i && i <= m;
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
            //@ maintaining j + 1 <= k && k <= n + 1;
            //@ maintaining j < n ==> (\forall int t; j + 1 <= t && t < k; nums2[t] < nums2[j]);
            //@ decreases n + 1 - k;
            while (k < n && nums2[k] < nums2[j]) {
                ++k;
            }
            res[i] = k < n ? nums2[k] : -1;
        }
        return res;
    }
}
