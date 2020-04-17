import java.util.Arrays;
import java.util.stream.Stream;

public class ScratchPad {
    private static final char ONE = '1';
    private static final char ZERO = '0';
    public static void main(String[] args){
        Runtime.getRuntime().

    }

    public static int r(int x){
        char[] chars = String.valueOf(x).toCharArray();
        int digits = String.valueOf(x).length();
        chars[0] = ONE;
        for(int i = digits-1; i>0; i--){
            chars[i] = ZERO;
        }
        return digits==1? 0 : Integer.parseInt(new String(chars));
    }
}
