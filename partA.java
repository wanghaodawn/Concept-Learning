//Your implementation goes here
import java.io.*;
import java.util.*;

public class partA {
	public static void main(String[] args) {
		
        // Read "9-Cat-Train.labeled"
        File inFile = new File("9Cat-Train.labeled");
        
        // If file doesnt exists, then create it
        if (!inFile.exists()) {
            System.err.println("No file called: 9Cat-Train.labeled");
            System.exit(-1);
        }

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
        try {
        	// Read string from the input file
            String sCurrentLine;
            
            br = new BufferedReader(new FileReader(inFile));

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);
                sb.append(sCurrentLine);

        	}
        	
        	// 1: Number of distinct hypothesis - |X|
        	int x = (int) Math.pow(2, 9);
        	System.out.println(x); // 512

        	// 2: Number of decimal digits in - |C|
        	int dec = 1;
        	double temp_num = 2.0;
        	// Calculate the decimal digits of |C|
        	for (int i = 0; i < x; i++) {
        		temp_num *= 2;
        		while (temp_num >= 10) {
        			temp_num /= 10;
        			dec++;
        		}
        	}
        	System.out.println(dec);

        	// 3: Number of semantically distinct conjunctions of |C|
        	int hypothesis = 1 + (int) Math.pow(3,9);
        	System.out.println(hypothesis);


			// Write File - "partA4.txt"
        	try {

				String content = "This is the content to write into file";

				File outFile = new File("partA4.txt");

				// If file doesnt exists, then create it
				if (!outFile.exists()) {
					outFile.createNewFile();
				}

				FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);

				// 4: Run FIND-S algorithm on "9Cat-Train.labeled"
				String s = sb.toString();
	        	sb = null; // Release the space

                // Initialize H
                String[] H = new String[18];
                for (int i = 0; i < 17; i++) {
                    if (i % 2 == 0) {
                        H[i] = "null";
                    } else {
                        H[i] = "\t";
                    }
                }

                // Preprocessing data
                s = s.replaceAll("Gender", " ");
	        	String[] ss = s.split("\\s"); // Create lines

                s = "";
                int line = 0;
                for (int i = 2; i < ss.length; i += 2) {
                    if (i % 20 == 2) {
                        s += line + "\t";
                        line++;
                    }
                    s += ss[i];
                    if (i % 20 == 0) {
                        s += "\n";
                    } else {
                        s += "\t";
                    }
                }
                // bw.write(s);
                ss = null; // Release the space

                // Run the algorithm to train
                ss = s.split("\n");
                for (int i = 0; i < ss.length; i++) {
                    String tmp = ss[i];
                    // System.out.println(tmp);
                    String[] t = tmp.split("\t");
                        
                    // System.out.println(t[10]);
                    // Ignore all counter examples
                    if (t[10].equals("high")) {
                    // If high
                        for (int j = 0; j < 17; j += 2) {
                            //System.out.println(H[j]);
                            //System.out.println(t[j/2+1]);
                            if (H[j] == "null") {
                                H[j] = t[j/2+1];
                                continue;
                            }
                            if (!H[j].equals(t[j/2+1])) {
                                H[j] = "?";
                            }
                        }
                    }

                    // Print Hypothesis every 30 lines
                    // System.out.println(i);
                    // System.out.println((i+1)%30);
                    // System.out.println("");
                    if ((i+1) % 30 == 0) {
                        String temp = "";
                        for (int j = 0; j < 17; j++) {
                            temp += H[j];
                        }
                        temp += "\n";
                        bw.write(temp);
                    }
                }
				
				//bw.close();

                // 5: Hypothesize 9Cat-Dev.labeled and compute the precision rate
                int num_good = 0, num_bad = 0;

                // Read "9-Cat-Dev.labeled"
                File inFile2 = new File("9Cat-Dev.labeled");
                
                // If file doesnt exists, then create it
                if (!inFile2.exists()) {
                    System.err.println("No file called: 9Cat-Dev.labeled");
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
                    // System.out.print(sb2);

                    s = sb2.toString();
                    sb2 = null; // Release the space

                    // Preprocessing data
                    s = s.replaceAll("Gender", " ");
                    ss = null;
                    ss = s.split("\\s"); // Create lines

                    s = "";
                    line = 0;
                    for (int i = 2; i < ss.length; i += 2) {
                        if (i % 20 == 2) {
                            s += line + "\t";
                            line++;
                        }
                        s += ss[i];
                        if (i % 20 == 0) {
                            s += "\n";
                        } else {
                            s += "\t";
                        }
                    }
                    // bw.write(s);
                    ss = null; // Release the space

                    // Run the algorithm to predict the result
                    ss = s.split("\n");
                    for (int i = 0; i < ss.length; i++) {
                        String tmp = ss[i];
                        // System.out.println(tmp);
                        String[] t = tmp.split("\t");

                        // If flag == 0 -> correct, else -> incorrect
                        if (t[7].equals("Car")) {
                            if (t[10].equals("high")) {
                                num_good++;
                                // System.out.println(i + ": " + 1);
                            } else {
                                num_bad++;
                                // System.out.println(i + ": " + 0);
                            }
                        } else {
                            if (t[10].equals("low")) {
                                num_good++;
                                // System.out.println(i + ": " + 1);
                            } else {
                                num_bad++;
                                // System.out.println(i + ": " + 0);
                            }
                        }
                    }
                    double precision = num_bad * 1.0 / (num_good + num_bad);
                    System.out.println(precision);

                    // System.out.print(s);
                    // bw.write(s);

                    // 6: Test custom input
                    File inFile3 = null;
                    if (args.length > 0) {
                        inFile3 = new File(args[0]);
                    } else {
                        // No input file path
                        System.err.println("Invalid arguments count:" + args.length);
                        System.exit(-1);
                    }

                    BufferedReader br3 = null;
                    StringBuilder sb3 = new StringBuilder();

                    try {
                        // Read string from the input file
                        String sCurrentLine3;
                        
                        br3 = new BufferedReader(new FileReader(inFile3));

                        while ((sCurrentLine3 = br3.readLine()) != null) {
                            //System.out.println(sCurrentLine);
                            sb3.append(sCurrentLine3);
                        }
                        // System.out.print(sb2);

                        s = sb3.toString();
                        sb3 = null; // Release the space

                        // Preprocessing data
                        s = s.replaceAll("Gender", " ");
                        ss = null;
                        ss = s.split("\\s"); // Create lines

                        s = "";
                        line = 0;
                        for (int i = 2; i < ss.length; i += 2) {
                            if (i % 20 == 2) {
                                s += line + "\t";
                                line++;
                            }
                            s += ss[i];
                            if (i % 20 == 0) {
                                s += "\n";
                            } else {
                                s += "\t";
                            }
                        }
                        // bw.write(s);
                        ss = null; // Release the space

                        // Run the algorithm to predict the result
                        ss = s.split("\n");
                        for (int i = 0; i < ss.length; i++) {
                            String tmp = ss[i];
                            // System.out.println(tmp);
                            String[] t = tmp.split("\t");

                            // If flag == 0 -> correct, else -> incorrect
                            if (t[7].equals("Car")) {
                                System.out.println("high");
                            } else {
                                System.out.println("low");
                            }
                        }

                    // Read file3 exception
                    } catch (IOException e) {
                        // Throws exception
                        e.printStackTrace();
                    }

                // Read file2 exception
                } catch (IOException e) {
                    // Throws exception
                    e.printStackTrace();
                }

                // Finish writing file
                bw.close();

			// Write file exception
			} catch (IOException e) {
				e.printStackTrace();
			}
        	
		// Read file exception
        } catch (IOException e) {
        	// Throws exception
            e.printStackTrace();
        }
	}
}