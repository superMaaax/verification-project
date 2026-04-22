public class IsSuffix {

    /*@
      @ requires pat != null;
      @ requires txt != null;
      @ ensures \result == (txt.length() >= pat.length() &&
      @     (\forall int k; 0 <= k && k < pat.length();
      @         pat.charAt(pat.length() - 1 - k) == txt.charAt(txt.length() - 1 - k)));
      @*/
    public boolean isSuffix (String pat, String txt) {
	
        int i = pat.length() - 1;

        //@ maintaining (\forall int k; i < k && k < pat.length();
        //@             pat.charAt(k) == txt.charAt(k - pat.length() + txt.length()));
        //@ decreases i;
        while(i >= 0) {
	    int j = i - pat.length() + txt.length();
            if(j < 0 || pat.charAt(i) != txt.charAt(j))
                return false;
            i = i - 1;
        }

        return true;
    }

}

But note: The maintaining clause I wrote might be incorrect because the indices might be out of bounds. Let's think carefully.

We have pat and txt. We are checking from the end of pat. At each iteration, we check if pat.charAt(i) == txt.charAt(j) where j = i - pat.length() + txt.length(). So j is the index in txt that corresponds to the same character from the end. So when i = pat.length() - 1, j = txt.length() - 1. So we are checking the last characters.

We want to maintain that for all k from i+1 to pat.length()-1, we have pat.charAt(k) == txt.charAt(k - pat.length() + txt.length()). That is, the characters that have already been checked (from the end) match.

At the start of the loop, i is the current index we are about to check. So before checking, we have already checked indices from i+1 to pat.length()-1. So the maintaining clause should be true at the beginning of each iteration.

We also need to ensure that the maintaining clause is true when the loop starts. Initially, i = pat.length() - 1, so there are no indices greater than i in the range [i+1, pat.length()-1] because i+1 = pat.length(). So the quantified condition is vacuously true. So that's fine.

Then after checking and decrementing i, we go to the next iteration. So the maintaining clause should hold.

We also need to consider the case when txt is shorter than pat. Then j might become negative. But the loop condition is i >= 0. However, when j becomes negative, we return false. So the loop might not terminate normally. But we have a decreases clause on i, so it will eventually become negative and exit, but we also have the early return. So we need to consider that the loop might exit early due to return. But the loop specification only applies if the loop completes normally. However, we can still add the loop specifications because the method has a return inside. But we need to be careful: The loop specifications are only required to hold for iterations that occur. Since we have a return inside, the loop might not complete. But we can still add the maintaining and decreases clauses. They will be ignored if the loop exits early? Actually, in JML, the loop specifications are only checked for the loop body when the loop executes. So it's fine.

We also need to consider the case when pat is empty. Then i = -1, so the loop condition i >= 0 is false, so the loop body is not executed. Then we return true. So we need to ensure that our specification covers that. Our ensures clause says \result == (txt.length() >= pat.length() && (\forall ...)). When pat.length() = 0, then txt.length() >= 0 is true, and the quantified condition is vacuously true, so \result is true. So that's correct.

We also need to consider that the method returns false if the characters don't match or j<0. Our ensures clause should capture that. Our ensures clause says \result == (txt.length() >= pat.length() && (\forall ...)). If txt.length() < pat.length(), then txt.length() >= pat.length() is false, so \result is false. If txt.length() >= pat.length() but the characters don't match, then the quantified condition will be false, so \result is false. So it's correct.

Now, we need to check the maintaining clause. The indices in the quantified condition: pat.charAt(k) and txt.charAt(k - pat.length() + txt.length()). We need to ensure that the index for txt is within bounds. Since we are checking from the end, and we only check when j >= 0, we have that k - pat.length() + txt.length() >= 0 for k > i? Actually, for k > i, we have k >= i+1. Then j for k would be k - pat.length() + txt.length(). Since we are checking from the end, and we only proceed as long as the previous characters matched, we can assume that for k > i, the corresponding index in txt is non-negative. But we need to ensure that the maintaining clause is true even when j becomes negative? Actually, the loop condition is i >= 0, but we might have j negative only when i is small enough. But note: j = i - pat.length() + txt.length(). So j < 0 when i < pat.length() - txt.length(). But if txt is shorter than pat, then pat.length() - txt.length() > 0, so j might become negative. But in that case, we return false. So the loop will not continue with j negative. So in the loop body, we check if j < 0, then return false. So we never have j negative in the loop without returning. So the maintaining clause only needs to hold for iterations where j >= 0. But we can still write the maintaining clause without worrying about j negative because the loop will exit if j negative. However, the maintaining clause might refer to txt index that is negative? But we are only quantifying over k such that i < k && k < pat.length(). For such k, we have k >= i+1. Then the corresponding txt index is k - pat.length() + txt.length(). Since we are in the loop, we have not returned yet, so we must have that for all previous iterations, the characters matched. But we don't know if txt is long enough. Actually, if txt is shorter than pat, then for some k, the txt index might be negative. But then the condition pat.charAt(k) == txt.charAt(negative) would be out of bounds. So we need to ensure that the maintaining clause only holds when txt is long enough. But we can add a condition that txt.length() >= pat.length() in the maintaining clause? Or we can rely on the fact that the loop only runs when i >= 0 and we haven't returned. Actually, the loop condition is i >= 0, but we can have i >= 0 even if txt is shorter than pat. For example, pat = "abc", txt = "a", then pat.length() = 3, txt.length() = 1. Then i starts at 2. Then j = 2 - 3 + 1 = 0, so j is 0. Then we check pat.charAt(2) vs txt.charAt(0). Then i becomes 1, j = 1 - 3 + 1 = -1, so j < 0, then we return false. So the loop runs for i=2 and i=1. At i=1, we return. So in the first iteration, we have k > i? Actually, at the beginning of the first iteration, i=2, so there are no k > i in the range [i+1, pat.length()-1] because i+1=3, which is not less than pat.length(). So the maintaining clause is vacuously true. Then we check the character at i=2. Then we decrement i to 1. Then we enter the second iteration with i=1. At the beginning of the second iteration, we have i=1, so we need to maintain that for all k in (1, 3) (i.e., k=2), pat.charAt(2) == txt.charAt(2 - 3 + 1) = txt.charAt(0). That is true because we just checked it. So the maintaining clause holds. Then we check j = 1 - 3 + 1 = -1, which is <0, so we return false. So the loop exits. So the maintaining clause is fine.

But what about the case when txt is shorter than pat and we have i such that j becomes negative? We already handled it by returning. So the maintaining clause is only required to hold for iterations where we haven't returned. So it's fine.

However, we need to ensure that the maintaining clause does not refer to an index that is out of bounds for txt. In the second iteration, we have k=2, and txt index is 0, which is valid. So it's fine.

But what about the case when txt is longer than pat? Then j will always be non-negative. So it's fine.

So the maintaining clause seems okay.

But note: The maintaining clause I wrote uses k - pat.length() + txt.length(). We need to ensure that for k in (i, pat.length()), the txt index is within 0 and txt.length()-1. Since we are checking from the end, and we only proceed if the characters matched, we can assume that the txt index is txt.length() - (pat.length() - k). Actually, we can derive: j = i - pat.length() + txt.length(). For a given k > i, the corresponding txt index is k - pat.length() + txt.length(). Since k <= pat.length()-1, we have k - pat.length() + txt.length() <= (pat.length()-1) - pat.length() + txt.length() = txt.length() - 1. Also, since k > i, we have k - pat.length() + txt.length() > i - pat.length() + txt.length() = j. And since we are in the loop and we have not returned, we have that j >= 0. So the txt index is >= j >= 0. So it's within bounds. So the maintaining clause is safe.

Now, we need to consider the decreases clause. We want to ensure that i decreases each iteration. We can use decreases i. But note that i is an int and can become negative. However, the loop condition is i >= 0, so i will eventually become -1 and exit. So we can use decreases i. But we need to ensure that the decreases clause is bounded below. Since i is an int, it can go down to Integer.MIN_VALUE, but that's not a problem because the loop will exit when i becomes -1. Actually, the loop condition is i >= 0, so when i becomes -1, the loop exits. So we can use decreases i, but note that i is decreased by 1 each time, so it will eventually become negative. So it's fine.

Alternatively, we can use decreases pat.length() - i, but that might be simpler. But we can use i.

We also need to consider that the loop might exit early due to return. But the decreases clause is only required for iterations that occur. So it's fine.

Now, we need to output the full Java file. We need
