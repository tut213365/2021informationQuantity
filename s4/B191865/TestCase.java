package s4.B191865; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 

import java.lang.*;

import s4.specification.*;

/*
interface FrequencerInterface {     // This interface provides the design for frequency counter.
    void setTarget(byte[]  target); // set the data to search.
    void setSpace(byte[]  space);  // set the data to be searched target from.
    int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
                    //Otherwise, it return 0, when SPACE is not set or Space's length is zero
                    //Otherwise, get the frequency of TAGET in SPACE
    int subByteFrequency(int start, int end);
    // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
    // For the incorrect value of START or END, the behavior is undefined.
}
*/

/*
package s4.specification;
public interface InformationEstimatorInterface{
    void setTarget(byte target[]); // set the data for computing the information quantities
    void setSpace(byte space[]); // set data for sample space to computer probability
    double estimation(); // It returns 0.0 when the target is not set or Target's length is zero;
// It returns Double.MAX_VALUE, when the true value is infinite, or space is not set.
// The behavior is undefined, if the true value is finete but larger than Double.MAX_VALUE.
// Note that this happens only when the space is unreasonably large. We will encounter other problem anyway.
// Otherwise, estimation of information quantity, 
}                        
*/


public class TestCase {
    static boolean success = true;

    public static void main(String[] args) {

        // Test Frequencer.java
        try {
            FrequencerInterface  myObject;
            int freq;
            System.out.println("checking Frequencer");

            // This is smoke test
            // Case 1
            myObject = new Frequencer();
            myObject.setSpace("Hi Ho Hi Ho".getBytes());
            myObject.setTarget("H".getBytes());
            freq = myObject.frequency();
            assert freq == 4: "Hi Ho Hi Ho, H: " + freq;
            // Write your testCase here

            // Case 2
            myObject = new Frequencer();
            myObject.setSpace("ababab".getBytes());
            myObject.setTarget("abab".getBytes());
            freq = myObject.frequency();
            assert freq == 2: "ababab, abab: " + freq;

            // Case 3: -1 when target length == 0 but we dont create new Frequencer object
            myObject.setSpace("ababab".getBytes());
            myObject.setTarget("".getBytes());
            freq = myObject.frequency();
            assert freq == -1: "ababab, TARGET LENGTH 0: " + freq;

            // Case 4: 0 when space length == 0
            myObject = new Frequencer();
            myObject.setSpace("".getBytes());
            myObject.setTarget("abab".getBytes());
            freq = myObject.frequency();
            assert freq == 0: "SPACE LENGTH 0, abab: " + freq;

            // Case 5: -1 when target is not set
            myObject = new Frequencer();
            myObject.setSpace("".getBytes());
            myObject.setTarget(null);
            freq = myObject.frequency();
            assert freq == -1: "ababab, TARGET IS NOT SET: " + freq;

            // Case 6: 0 when space is not set
            myObject = new Frequencer();
            myObject.setSpace(null);
            myObject.setTarget("abab".getBytes());
            freq = myObject.frequency();
            assert freq == 0: "SPACE IS NOT SET, abab: " + freq;

            // Case 7
            myObject = new Frequencer();
            myObject.setSpace("ababab".getBytes());
            myObject.setTarget("c".getBytes());
            freq = myObject.frequency();
            assert freq == 0: "ababab, c: " + freq;

            System.out.println("checking Frequencer OK");

        }
        catch(Exception e) {
            System.out.println("Exception occurred in Frequencer Object");
            success = false;
        }

        // Test InformationEstimator.java
        try {
            InformationEstimatorInterface myObject;
            double value;
            System.out.println("checking InformationEstimator");

            myObject = new InformationEstimator();
            myObject.setSpace("3210321001230123".getBytes());
            myObject.setTarget("0".getBytes());
            value = myObject.estimation();
            assert (value > 1.9999) && (2.0001 >value): "IQ for 0 in 3210321001230123 should be 2.0. But it returns "+value;

            myObject.setTarget("01".getBytes());
            value = myObject.estimation();
            assert (value > 2.9999) && (3.0001 >value): "IQ for 01 in 3210321001230123 should be 3.0. But it returns "+value;

            myObject.setTarget("0123".getBytes());
            value = myObject.estimation();
            assert (value > 2.9999) && (3.0001 >value): "IQ for 0123 in 3210321001230123 should be 3.0. But it returns "+value;

            myObject.setTarget("00".getBytes());
            value = myObject.estimation();
            assert (value > 3.9999) && (4.0001 >value): "IQ for 00 in 3210321001230123 should be 4.0. But it returns "+value;

            // target length is 0
            myObject = new InformationEstimator();
            myObject.setSpace("3210321001230123".getBytes());
            myObject.setTarget("".getBytes());
            value = myObject.estimation();
            assert (value > -0.0001) && (0.0001 >value): "IQ for '' in 3210321001230123 should be 0.0. But it returns "+value;

            // target is not set
            myObject = new InformationEstimator();
            myObject.setSpace("3210321001230123".getBytes());
            myObject.setTarget(null);
            value = myObject.estimation();
            assert (value > -0.0001) && (0.0001 >value): "IQ for null target in 3210321001230123 should be 0.0. But it returns "+value;

            // space length is 0
            myObject = new InformationEstimator();
            myObject.setSpace("".getBytes());
            myObject.setTarget("0123".getBytes());
            value = myObject.estimation();
            assert (value == Double.MAX_VALUE): "IQ for 0123 in '' space should be Double.MAX_VALUE. But it returns "+value;

            // space is not set
            myObject = new InformationEstimator();
            myObject.setSpace(null);
            myObject.setTarget("0123".getBytes());
            value = myObject.estimation();
            assert (value == Double.MAX_VALUE): "IQ for 0123 in null space should be Double.MAX_VALUE. But it returns "+value;

            System.out.println("checking InformationEstimator OK");
        }
        catch(Exception e) {
            System.out.println("Exception occurred in InformationEstimator Object");
            success = false;
        }

        if(success) { System.out.println("TestCase OK"); }

    }
}