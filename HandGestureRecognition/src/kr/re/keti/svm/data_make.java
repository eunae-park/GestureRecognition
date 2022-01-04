package kr.re.keti.svm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import libsvm.svm_node;


public class data_make {

	public static void main(String argv[]) throws IOException
	{
		train_data_make(); // 
//		data_integration(); // data ¸¸µé±â
	}
	
	private static double atof(String s)
	{
		double d = Double.valueOf(s).doubleValue();
		if (Double.isNaN(d) || Double.isInfinite(d))
		{
			System.err.print("NaN or Infinity in input\n");
			System.exit(1);
		}
		return(d);
	}
	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}
	static void data_integration() throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter("out-9v2.txt"));
		BufferedReader in = new BufferedReader(new FileReader("out-9.txt"));
//		int classNumber = 1;
/*
		BufferedReader in0 = new BufferedReader(new FileReader("out-0.txt"));
		BufferedReader in1 = new BufferedReader(new FileReader("out-1.txt"));
		BufferedReader in2 = new BufferedReader(new FileReader("out-1.txt"));
		BufferedReader in3 = new BufferedReader(new FileReader("out-3.txt"));
		BufferedReader in4 = new BufferedReader(new FileReader("out-4.txt"));
		BufferedReader in7 = new BufferedReader(new FileReader("out-7.txt"));
		BufferedReader in5 = new BufferedReader(new FileReader("out-5.txt"));
		BufferedReader in6 = new BufferedReader(new FileReader("out-6.txt"));
		BufferedReader in8 = new BufferedReader(new FileReader("out-8.txt"));
		BufferedReader in9 = new BufferedReader(new FileReader("out-9.txt"));
 */


		int[] sensorNum = {6, 7, 8, 7, 6};
		int allOfPressure = 34;
		int si, sj, sk, dn, interval=2, total=3001;
		int index;
		double[] data = new double [allOfPressure];
		double[] svm_data = new double [allOfPressure-5];
		String line;
		while(true)
		{
			line = in.readLine();
/*
			line = in0.readLine();
			line = in1.readLine();
			line = in2.readLine();
			line = in3.readLine();
			line = in4.readLine();
			line = in7.readLine();
			line = in5.readLine();
			line = in6.readLine();
			line = in8.readLine();
			line = in9.readLine();
*/			
			if(line == null) break;
			System.out.println(line);
			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			sk = 0;
			dn = 0;
			index = atoi(st.nextToken());
			out.write(index + " ");
			out.flush();
			for (si=0; si<5; si++)
			{
				for (sj=0; sj<sensorNum[si]; sj++)
				{
					index = atoi(st.nextToken());
					data[sk++] = (int)(atof(st.nextToken()) * 500);
					System.out.printf("%.3f  ", data[sk-1]);
					if (sj > 0)
					{
						svm_data[dn++] = (data[sk-1] - data[sk-2]) / 10.0;
						
						System.out.printf("%d:%6.3f  ", dn, svm_data[dn-1]);
						String dataS = dn + ":" + svm_data[dn-1] + " ";
						out.write(dataS);
						out.flush();
					}
				}
			}
			System.out.println();
			out.newLine();
		}
		out.close();
		System.out.println("data make : end");
	}

	static void train_data_make() throws IOException
	{
		BufferedWriter out = new BufferedWriter(new FileWriter("hand-train1022v2.txt"));
		BufferedReader in5 = new BufferedReader(new FileReader("out-5.txt"));
		BufferedReader in3 = new BufferedReader(new FileReader("out-3.txt"));
		BufferedReader in4 = new BufferedReader(new FileReader("out-4.txt"));
//		BufferedReader in2 = new BufferedReader(new FileReader("out-2.txt"));
//		BufferedReader in6 = new BufferedReader(new FileReader("out-6.txt"));
//		BufferedReader in9 = new BufferedReader(new FileReader("out-9.txt"));
//		BufferedReader in1 = new BufferedReader(new FileReader("out-1.txt"));
//		BufferedReader in0 = new BufferedReader(new FileReader("out-0.txt"));
		BufferedReader in7 = new BufferedReader(new FileReader("out-7.txt"));
//		BufferedReader in8 = new BufferedReader(new FileReader("out-8.txt"));
/*
		BufferedReader in10 = new BufferedReader(new FileReader("out-02.txt"));
		BufferedReader in11 = new BufferedReader(new FileReader("out-347.txt"));
*/
		
		int i, total=3000, start=total/20, finish=total-total/20, interval=1;
		String line;
		
		start = 0;
		finish = total;
		for (i=start; i<finish; i++)
		{
			if (i < start)
				continue;

			if (i % interval == 0)
			{
				line = in5.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
	
				line = in4.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
	
				line = in3.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
/*	
				line = in2.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
	
				line = in6.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
	
				line = in9.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
	
				line = in8.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
*/				
				line = in7.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
/*	
				line = in0.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
	
				line = in1.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
*/				
	/*			
	
				line = in10.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
				
				line = in11.readLine();
				System.out.println(line);
				out.write(line);
				out.newLine();
	
	*/
			}
		}
		out.close();
		System.out.println("train data make : end");
	}
}

