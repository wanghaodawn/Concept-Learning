//Your implementation goes here
import java.io.*;
import java.util.*;

public class partB {
	public static void main(String[] args) {
		// Read "4-Cat-Train.labeled"
        File inFile1 = new File("4Cat-Train.labeled");
        
        // If file doesnt exists, then create it
        if (!inFile1.exists()) {
            System.err.println("No file called: 4Cat-Train.labeled");
            System.exit(-1);
        }

		BufferedReader br1 = null;
		StringBuilder sb1 = new StringBuilder();
        try {
        	// Read string from the input file
            String sCurrentLine1;
            
            br1 = new BufferedReader(new FileReader(inFile1));

            while ((sCurrentLine1 = br1.readLine()) != null) {
                //System.out.println(sCurrentLine);
                sb1.append(sCurrentLine1);
        	}

        	// 1: Number of distinct hypothesis - |X|
        	int x = (int) Math.pow(2, 4);
        	System.out.println(x); // 16

        	// 2: Size of concept space - |C|
        	int c = (int) Math.pow(2, x);
        	System.out.println(c); // 128

        	// 3: The size of the version space - VS(H,D)
        	int num_vs = 0;
        	String s = sb1.toString();
        	sb1 = null; // Release the space

        	// Preprocessing data
            s = s.replaceAll("Gender", " ");
        	String[] ss = s.split("\\s"); // Create lines
        	// System.out.println(ss.length);
        	
        	// for (int i = 0; i < ss.length; i++) {
        	// 	System.out.print("" + i + ss[i] + "\t");
        	// 	if (i % 10 == 0) {
        	// 		System.out.print("\n");
        	// 	}
        	// }

        	// Store training data
        	String[][] train = new String[11][5];

            s = "";
            int line = 11;
            for (int i = 0; i < 11; i++) {
            	for (int j = 0; j < 5; j++) {
            		train[i][j] = ss[10*i + 2*j+2];
            	}
            }
            // System.out.print(s);
            // for (int i = 0; i < 11; i++) {
            // 	for (int j = 0; j < 5; j++) {
            // 		System.out.print(train[i][j] + "\t");
            // 	}
            // 	System.out.println("");
            // }

            num_vs = c / (int)Math.pow(2,line); // 32
            System.out.println(num_vs);

            // 4: Test custom input
            File inFile2 = null;
            if (args.length > 0) {
                inFile2 = new File(args[0]);
            } else {
                // No input file path
                System.err.println("Invalid arguments count:" + args.length);
                System.exit(-1);
            }

            BufferedReader br2 = null;
            StringBuilder sb2 = new StringBuilder();

            try {
                // Read string from the input file
                String sCurrentLine2;
                
                br2 = new BufferedReader(new FileReader(inFile2));

                while ((sCurrentLine2 = br2.readLine()) != null) {
                    //System.out.println(sCurrentLine);
                    sb2.append(sCurrentLine2);
                }
                //System.out.print(sb2);

                s = sb2.toString();
                sb2 = null; // Release the space

                // Preprocessing data
	            s = s.replaceAll("Gender", " ");
	        	ss = s.split("\\s"); // Create lines

	        	// The lines of data in the new file
	        	int n = (ss.length-1) / 10;

	        	// Store test data
        		String[][] test = new String[n][5];
	        	for (int i = 0; i < n; i++) {
	            	for (int j = 0; j < 5; j++) {
	            		test[i][j] = ss[10*i + 2*j+2];
	            	}
	            }

	            // for (int i = 0; i < n; i++) {
	            // 	for (int j = 0; j < 5; j++) {
	            // 		System.out.print(test[i][j] + "\t");
	            // 	}
	            // 	System.out.println("");
	            // }

	            // List-Then-Eliminate
	            for (int i = 0; i < n; i++) {
	            	int flag = 0;
	            	// Test this line in the whole training data
	            	for (int ii = 0; ii < 11; ii++) {
	            		if (train[ii][0].equals(test[i][0]) && train[ii][1].equals(test[i][1]) &&
	            			train[ii][2].equals(test[i][2]) && train[ii][3].equals(test[i][3])) {
	            			if (train[ii][4].equals("high")) {
	            				flag = 1;
	            			} else {
	            				flag = 2;
	            			}
	            			break;
	            		}
	            	}
	            	if (flag == 1) {
	            		// Found in training data - high
	            		System.out.println(num_vs + " " + 0);
	            	} else if (flag == 2) {
	            		// Found in training data - low
	            		System.out.println(0 + " " + num_vs);
	            	} else {
	            		// Not found in training data
	            		System.out.println(num_vs/2 + " " + num_vs/2);
	            	}

	            }
            // Read file2 exception
            } catch (IOException e) {
                // Throws exception
                e.printStackTrace();
            }
        // Read file1 exception
        } catch (IOException e) {
        	// Throws exception
            e.printStackTrace();
        }
	}
}