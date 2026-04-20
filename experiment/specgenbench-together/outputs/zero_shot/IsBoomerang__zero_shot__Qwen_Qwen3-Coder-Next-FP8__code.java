class IsBoomerang {
    /*@ spec_public @*/
    public boolean isBoomerang(int[][] points) {
        // Precondition: points must be a 3x2 array representing three points in 2D space
        // @ requires points != null && points.length == 3 && 
        // @          points[0].length == 2 && points[1].length == 2 && points[2].length == 2;
        
        int[] v1 = {points[1][0] - points[0][0], points[1][1] - points[0][1]};
        int[] v2 = {points[2][0] - points[0][0], points[2][1] - points[0][1]};
        
        // Postcondition: returns true if and only if the three points form a boomerang
        // (i.e., they are not collinear, meaning the area of the triangle they form is non-zero)
        // @ ensures \result <==> (v1[0] * v2[1] - v1[1] * v2[0] != 0);
        
        return v1[0] * v2[1] - v1[1] * v2[0] != 0;
    }
}
