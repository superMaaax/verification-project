class ComputeOverlapBranch {
    /*@ spec_public @*/
    public int computeOverlapArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        // Precondition: rectangles are defined with ax1 <= ax2, ay1 <= ay2, bx1 <= bx2, by1 <= by2
        requires ax1 <= ax2 && ay1 <= ay2 && bx1 <= bx2 && by1 <= by2;
        
        ensures \result >= 0;
        
        int minX2 = bx2;
        /*@ loop_invariant minX2 == bx2 || minX2 == ax2;
            loop_invariant minX2 <= ax2 && minX2 <= bx2;
            loop_invariant minX2 >= ax2 || minX2 >= bx2;
            decreases 0;
        @*/
        if(ax2 < bx2) {
            minX2 = ax2;
        }
        
        int maxX1 = bx1;
        /*@ loop_invariant maxX1 == bx1 || maxX1 == ax1;
            loop_invariant maxX1 >= ax1 && maxX1 >= bx1;
            loop_invariant maxX1 <= ax1 || maxX1 <= bx1;
            decreases 0;
        @*/
        if(ax1 > bx1) {
            maxX1 = ax1;
        }
        
        int minY2 = by2;
        /*@ loop_invariant minY2 == by2 || minY2 == ay2;
            loop_invariant minY2 <= ay2 && minY2 <= by2;
            loop_invariant minY2 >= ay2 || minY2 >= by2;
            decreases 0;
        @*/
        if(ay2 < by2) {
            minY2 = ay2;
        }
        
        int maxY1 = by1;
        /*@ loop_invariant maxY1 == by1 || maxY1 == ay1;
            loop_invariant maxY1 >= ay1 && maxY1 >= by1;
            loop_invariant maxY1 <= ay1 || maxY1 <= by1;
            decreases 0;
        @*/
        if(ay1 > by1) {
            maxY1 = ay1;
        }
        
        int overlapWidth = minX2 - maxX1;
        int overlapHeight = minY2 - maxY1;
        
        /*@ loop_invariant overlapWidth == (minX2 - maxX1);
            loop_invariant (overlapWidth < 0) ==> (\result == 0);
            loop_invariant (overlapWidth >= 0) ==> (\result == overlapHeight * overlapWidth);
            decreases 0;
        @*/
        if(overlapWidth < 0) {
            overlapWidth = 0;
        }
        
        /*@ loop_invariant overlapHeight == (minY2 - maxY1);
            loop_invariant (overlapHeight < 0) ==> (\result == 0);
            loop_invariant (overlapHeight >= 0) ==> (\result == overlapHeight * overlapWidth);
            decreases 0;
        @*/
        if(overlapHeight < 0) {
            overlapHeight = 0;
        }
        
        int overlapArea = overlapHeight * overlapWidth;
        
        ensures \result == (minX2 - maxX1 > 0 ? minX2 - maxX1 : 0) * (minY2 - maxY1 > 0 ? minY2 - maxY1 : 0);
        
        return overlapArea;
    }
}
