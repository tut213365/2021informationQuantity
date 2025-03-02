package s4.B213377; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.

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
		try {
			FrequencerInterface myObject;
			int freq;
			System.out.println("checking Frequencer");

			// This is smoke test
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			myObject.setTarget("H".getBytes());
			freq = myObject.frequency();
			assert freq == 4 : "Hi Ho Hi Ho, H: " + freq;
			// Write your testCase here
			// Test case for not setting the target
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			freq = myObject.frequency();
			assert freq == -1 : "NEW TEST1: No TARGET is set or TARGET's length is zero";
			// Test case for not setting the space
			myObject = new Frequencer();
			myObject.setTarget("H".getBytes());
			freq = myObject.frequency();
			assert freq == 0 : "NEW TEST2: No Space is set or Space's length is zero";
			// Test case for when the TARGET's length is longer than Space'length
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			myObject.setTarget("Hi Ho Hi Ho Hi".getBytes());
			freq = myObject.frequency();
			assert freq == 0 : "NEW TEST3: ARGET's length is longer than Space'length";
			// Test case for positive case
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			myObject.setTarget("Hi".getBytes());
			freq = myObject.frequency();
			assert freq == 2 : "NEW TEST4: Hi Ho Hi Ho, H: " + freq;
			// Test case for positive case
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			myObject.setTarget("Car".getBytes());
			freq = myObject.frequency();
			assert freq == 0 : "NEW TEST5: Hi Ho Hi Ho, Car: NOT FOUND!";
			// Test case for when the TARGET's length is longer than Space'length
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			myObject.setTarget("oi".getBytes());
			freq = myObject.frequency();
			assert freq == 0 : "NEW TEST6: Out of search index!";
			// Test case for subByteTarget() function
			myObject = new Frequencer();
			myObject.setSpace("abcdekicbc".getBytes());
			myObject.setTarget("abc".getBytes());
			freq = myObject.frequency();
			assert freq == 1 : "NEW TEST7: abcdekicbc, abc: " + freq;
			freq = myObject.subByteFrequency(1, 2);
			assert freq == 2 : "NEW TEST8: abcdekicbc, bc: " + freq;

			// Add more tests for Suffix Array
			myObject = new Frequencer();
			myObject.setSpace("Hi Ho Hi Ho".getBytes());
			myObject.setTarget("Hi".getBytes());
			freq = myObject.frequency();
			assert freq == 2 : "NEW TEST9: Hi Ho Hi Ho, Hi: " + freq;

			myObject.setTarget(" Hi".getBytes());
			freq = myObject.frequency();
			assert freq == 1 : "NEW TEST10: Hi Ho Hi Ho, _Hi: " + freq;

			myObject.setTarget(" ".getBytes());
			freq = myObject.frequency();
			assert freq == 3 : "NEW TEST11: Hi Ho Hi Ho, _: " + freq;

			myObject.setTarget("o".getBytes());
			freq = myObject.frequency();
			assert freq == 2 : "NEW TEST12: Hi Ho Hi Ho, o: " + freq;

			myObject.setTarget("o H".getBytes());
			freq = myObject.frequency();
			assert freq == 1 : "NEW TEST13: Hi Ho Hi Ho, o H: " + freq;

		} catch (Exception e) {
			System.out.println("Exception occurred in Frequencer Object");
			success = false;
		}

		try {
			InformationEstimatorInterface myObject;
			double value;
			System.out.println("checking InformationEstimator");
			myObject = new InformationEstimator();
			myObject.setSpace("3210321001230123".getBytes());
			myObject.setTarget("0".getBytes());
			value = myObject.estimation();
			assert (value > 1.9999) && (2.0001 > value)
					: "IQ for 0 in 3210321001230123 should be 2.0. But it returns " + value;
			myObject.setTarget("01".getBytes());
			value = myObject.estimation();
			assert (value > 2.9999) && (3.0001 > value)
					: "IQ for 01 in 3210321001230123 should be 3.0. But it returns " + value;
			myObject.setTarget("0123".getBytes());
			value = myObject.estimation();
			assert (value > 2.9999) && (3.0001 > value)
					: "IQ for 0123 in 3210321001230123 should be 3.0. But it returns " + value;
			myObject.setTarget("00".getBytes());
			value = myObject.estimation();
			assert (value > 3.9999) && (4.0001 > value)
					: "IQ for 00 in 3210321001230123 should be 3.0. But it returns " + value;

			// Newly added test cases
			myObject.setSpace("abdcdcddabfefegh".getBytes());
			myObject.setTarget("a".getBytes());
			value = myObject.estimation();
			assert (value > 2.9999) && (3.0001 > value)
					: "IQ for a in abdcdcddabfefegh should be 3.0. But it returns " + value;
			myObject.setTarget("d".getBytes());
			value = myObject.estimation();
			assert (value > 1.9999) && (2.0001 > value)
					: "IQ for d in abdcdcddabfefegh should be 2.0. But it returns " + value;
			myObject.setTarget("h".getBytes());
			value = myObject.estimation();
			assert (value > 3.9999) && (4.0001 > value)
					: "IQ for h in abdcdcddabfefegh should be 4.0. But it returns " + value;
			myObject = new InformationEstimator();
			value = myObject.estimation();
			assert (value == Double.MAX_VALUE)
					: "It should return Double.MAX_VALUE because the space is not set. But it returns " + value;
			myObject = new InformationEstimator();
			myObject.setSpace("3210321001230123".getBytes());
			value = myObject.estimation();
			assert (value == 0.0)
					: "It should return 0.0 because the target is not set. But it returns " + value;
			myObject = new InformationEstimator();
			myObject.setSpace("3210321001230123".getBytes());
			myObject.setTarget("".getBytes());
			value = myObject.estimation();
			assert (value == 0.0)
					: "It should return 0.0 becuase the target's length is zero. But it returns " + value;

		} catch (Exception e) {
			System.out.println("Exception occurred in InformationEstimator Object");
			success = false;
		}
		if (success) {
			System.out.println("TestCase OK");
		}
	}
}
