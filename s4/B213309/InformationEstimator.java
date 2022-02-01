package s4.B213309; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 
import java.lang.*;
import s4.specification.*;

// import java.util.Arrays;

/* What is imported from s4.specification
package s4.specification;
public interface InformationEstimatorInterface {
    void setTarget(byte target[]);  // set the data for computing the information quantities
    void setSpace(byte space[]);  // set data for sample space to computer probability
    double estimation();  // It returns 0.0 when the target is not set or Target's length is zero;
    // It returns Double.MAX_VALUE, when the true value is infinite, or space is not set.
    // The behavior is undefined, if the true value is finete but larger than Double.MAX_VALUE.
    // Note that this happens only when the space is unreasonably large. We will encounter other problem anyway.
    // Otherwise, estimation of information quantity,
}
*/


public class InformationEstimator implements InformationEstimatorInterface {
    static boolean debugMode = false;
    // Code to test, *warning: This code is slow, and it lacks the required test
    byte[] myTarget; // data to compute its information quantity
    byte[] mySpace;  // Sample space to compute the probability
    FrequencerInterface myFrequencer;  // Object for counting frequency

    private void showVariables() {
	for(int i=0; i< mySpace.length; i++) { System.out.write(mySpace[i]); }
	System.out.write(' ');
	for(int i=0; i< myTarget.length; i++) { System.out.write(myTarget[i]); }
	System.out.write(' ');
    }

    byte[] subBytes(byte[] x, int start, int end) {
        // corresponding to substring of String for byte[],
        // It is not implement in class library because internal structure of byte[] requires copy.
        byte[] result = new byte[end - start];
        for(int i = 0; i<end - start; i++) { result[i] = x[start + i]; };
        return result;
    }

    // IQ: information quantity for a count, -log2(count/sizeof(space))
    double iq(int freq) {
        return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
    }

    @Override
    public void setTarget(byte[] target) {
        myTarget = target;
    }

    @Override
    public void setSpace(byte[] space) {
        myFrequencer = new Frequencer();
        mySpace = space; 
        myFrequencer.setSpace(space);
    }

    @Override
    public double estimation(){
        double value = Double.MAX_VALUE; // value = mininimum of each "value1".
        // 先頭からn文字目の計算結果を格納する
        double [] mySuffixEstimation  = new double[myTarget.length];

        int np = 1<<(myTarget.length-1);
	    if(debugMode) { showVariables(); }
        if(debugMode) { System.out.printf("np=%d length=%d ", np, +myTarget.length); }

        // 例："abcd"
        // esti("a")       = iq("a")                                            <- mySuffixEstimation[0]
        //
        // esti("ab")      = min( iq("ab"),    (esti("a")      + iq("b")));     <- mySuffixEstimation[1]
        //
        // esti("abc")     = min( iq("abc"),   (esti("a")      + iq("bc")),
        //                                     (esti("ab")     + iq("c"))   );  <- mySuffixEstimation[2]
        //
        // esti("abcd")    = min( iq("abcd"),  (esti("a")      + iq("bcd")),
        //                                     (esti("ab")     + iq("cd")),
        //                                     (esti("abc")    + iq("d"))   );  <- mySuffixEstimation[3]
        // ----------------+------------------+----------------+------------
        //                     文字列そのまま  | 既出の計算結果  と　その後ろの文字列

        // 先頭1文字目の情報量計算
        myFrequencer.setTarget(subBytes(myTarget, 0, 1));
        mySuffixEstimation[0] = iq(myFrequencer.frequency());
        
        // 2文字以上の情報量計算
        // 区切られた文字列ごとに情報量を求め、最小値がその文字列の情報量(?)となる
        for(int len = 1; len < mySuffixEstimation.length; len++){
            double value1;
            // 文字列全部
            myFrequencer.setTarget(subBytes(myTarget, 0, len + 1));
            value1 = iq(myFrequencer.frequency());

            //　区切られた文字列
            for(int slash = 1; slash < len + 1; slash++){
                myFrequencer.setTarget(subBytes(myTarget, slash, len + 1)); // 区切りより後ろの文字列
                double value2 = mySuffixEstimation[slash - 1] + iq(myFrequencer.frequency());
                if(value2 < value1)
                    value1 = value2;
            }
            mySuffixEstimation[len] = value1;
        }
        double ans = mySuffixEstimation[mySuffixEstimation.length - 1];
        if(ans < value)
                value = ans;

        if(debugMode)
            System.out.printf("%10.5f\n", mySuffixEstimation[mySuffixEstimation.length - 1]);
        
        return value;
    }

    public static void main(String[] args) {
        InformationEstimator myObject;
        double value;
	debugMode = true;
        myObject = new InformationEstimator();
        myObject.setSpace("3210321001230123".getBytes());
        myObject.setTarget("0".getBytes());
        value = myObject.estimation();
        myObject.setTarget("01".getBytes());
        value = myObject.estimation();
        myObject.setTarget("0123".getBytes());
        value = myObject.estimation();
        myObject.setTarget("00".getBytes());
        value = myObject.estimation();
        // additional case
        myObject.setSpace("1212121221212112121212121221221".getBytes());
        myObject.setTarget("1212121211212222222212111111111121211212111212121221".getBytes());
        value = myObject.estimation();
    }
}

