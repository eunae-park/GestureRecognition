package kr.re.keti.sensorvalue;

import com.fazecast.jSerialComm.*;

import java.awt.EventQueue;
import java.io.*;

import kr.re.keti.LifeGesture;
import libsvm.svm;
import libsvm.svm_model;

public class SensorDemo
{

	public static void main(String[] args) throws IOException, InterruptedException
	{
		final int baudRate = 921600;
		final String portName = "COM6";
		Port portWorker = new PortWorker(portName, baudRate);
		Sensor SensorsValue = new Sensor(portWorker);
		SensorsValue.openPort();

		int[] order = {0, 1, 2, 3, 4};
		int[] sensorNum = {6, 6, 6, 6, 6};
		int numOfGyro = 6;
		int numOfPressure = 30;
		int numOfSensingPoint = 6; 
		int numOfStrech = 5;
		int allOfSensors = numOfPressure + numOfGyro + numOfStrech;
		double[] previous_result = new double [allOfSensors];
		int[] data = new int [allOfSensors];

		int check = 0, i, j, k=0, sk;

		int svmOfPressure = numOfPressure; // 1205=allOfPressure, 1217=allOfPressure-5
		double[] max_data = new double [svmOfPressure];
		double[] min_data = new double [svmOfPressure];
		double[] svm_data = new double [svmOfPressure]; //1205

/*		
///////////////////////////////////////////////	data normalization make
		int data_count=0, data_countM=200;
		
		System.out.printf("\ngrab start\n");
		while(data_count < data_countM)
		{
			double[] result = SensorsValue.getDatas3th();
		
			if(result == null)
				continue;
			else if(result[0] == -12345.67890)
			{
//				updateGesture(10);
				continue;
			}

			k = 0;
//			System.out.printf("%d\t", data_count);
			if(data_count == 0)
			{
				Thread.sleep(1000);
			}
			else if(data_count == data_countM/2)
			{
				System.out.printf("\npaper start\n");
				Thread.sleep(5000);
			}

			for(i = 0; i<5; i++)
			{
				for(j = 0; j<sensorNum[i]; j++)
				{
//					System.out.printf("%10.3f ", result[order[i]*numOfSensingPoint + j]);
//					System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
					data[k++] = (int)result[order[i]*numOfSensingPoint + j];
					System.out.printf("%d:%d ", k, data[k-1]);
					if( data_count == 0 )
					{
						max_data[k-1] = data[k-1];
						min_data[k-1] = data[k-1];
					}
					if( max_data[k-1] < data[k-1] )
					{
						max_data[k-1] = data[k-1];
					}
					if( min_data[k-1] > data[k-1] )
					{
						min_data[k-1] = data[k-1];
					}
					
				}
			}		
			data_count ++;
			System.out.println();
		}
		for (i=0; i<k; i++)
			System.out.printf("%d\t", (int)max_data[i]);
		System.out.println();
		for (i=0; i<k; i++)
			System.out.printf("%d\t", (int)min_data[i]);
			
		System.out.printf("Data Normalization Finish\n");
		System.exit(0);		
///////////////////////////////////////////////	data normalization make
*/
		
		BufferedReader in = new BufferedReader(new FileReader("data_normalization-man.txt"));
		String maxline, minline, line;
		maxline = in.readLine();
		System.out.println("sensor - max : " + maxline);
		minline = in.readLine();
		System.out.println("sensor - min : " + minline);
		in.close();
		
		j=0;
		k=0;
		for(i=0; i<svmOfPressure; i++)
		{
			line = maxline.substring(j, maxline.indexOf("\t",j+1));
			max_data[i] = Double.parseDouble(line);
			j += line.length() + 1;
			line = minline.substring(k, maxline.indexOf("\t",k+1));
			min_data[i] = Double.parseDouble(line);
			k += line.length() + 1;
			
//			System.out.println(i + " : " + max_data[i] + "\t" + min_data[i]);
		}
			

		BufferedWriter out = new BufferedWriter(new FileWriter("out-.txt"));
		while(check < 3000)
		{
			double[] result = SensorsValue.getDatas3th();
		
			if(result == null)
				continue;
			else if(result[0] == -12345.67890)
			{
				continue;
			}

			k = 0;
			sk = 0;
			int error = 0;// v191014 - data=0 delete
			String dataS="9 "; //class number
			for(i = 0; i<5&error==0; i++)
			{
				for(j = 0; j<sensorNum[i]&error==0; j++)
				{
					System.out.printf("%d:%.0f  ", k, result[order[i]*numOfSensingPoint + j]);
					data[k] = (int)result[order[i]*numOfSensingPoint + j];
					if (data[k] == 0)
					{
						System.out.printf("sensor data input warning %d:%d\n", i, j);
						error = 1;
//						System.exit(0);
						continue;
					}
					svm_data[sk++] = (double)(data[k] - min_data[k]) / (double)(max_data[k] - min_data[k]) * 10.0;
					dataS += sk + ":" + svm_data[sk-1] + " ";
					k++;
//					System.out.printf("%d:%6.3f  ", sk, svm_data[sk-1]);
				}
				
			}
			if(error == 0)
			{
				System.out.printf("\t%d\n", check);
				out.write(dataS);
				out.flush();
				out.newLine();
				check ++;
			}
		}
		System.out.printf("finish\n");
		out.close();
		System.exit(0);
	}
	
	public static void main2nd(String[] args) throws Exception
	{
		final int baudRate = 115200;
		final String portName = "COM44";
		Port portWorker = new PortWorker(portName, baudRate);
		
		//final String mapFileName = "sensor_data.txt";
		//degreeObj.readDegreeMap(new java.io.File(mapFileName));
		
		Sensor SensorsValue = new Sensor(portWorker);
		SensorsValue.openPort();
		
		
/*// 1126이전
		int numOfSensingPoint = 19;
		int numOfSensingPoints = numOfSensingPoint*5+1;
		String[] directionBuffer = {"None", "Up", "Down", "Left", "Right"};
*/

		int numOfSensingPoint=19;
		int allOfSensingPoints = numOfSensingPoint*5+1;
		int numOfPressure = 9;
		int allOfPressure = 34;
		int numOfGyro = 9;
		int allOfGyro = 45;
		int[] order = {4, 0, 1, 2, 3};
		int[] sensorNum = {6, 7, 8, 7, 6}; // 여성용
//		int[] sensorNum = {7, 8, 9, 8, 7}; // 남성용
//		int[] sensorNum = {7, 8, 9, 8, 6}; // ~1128
		String[] directionBuffer = {"None", "Up", "Down", "Left", "Right"};
		int[] data = new int [allOfPressure];
		double[] svm_data = new double [allOfPressure];
		int check = 0, i, j, k, sk;
		
		BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"));
		while(true)
		{
			double[] result = SensorsValue.getDatas();
		
			if(result == null)
				continue;
/*			
// 센싱 데이터 확인 ///////////////////////////////////////////////////////////////////////////////////
			for(i = 0; i < allOfSensingPoints-1; i++)
			{
				if(i%numOfSensingPoint == 0)
					System.out.printf("\nS%d : %10.3f ", (int)i/19, result[i]);
				else if((i+1)%numOfSensingPoint == 0)
					System.out.printf(" %s(%.3f) ", directionBuffer[(int)result[i]], result[i]);
				else
					System.out.printf("%10.3f ", result[i]);
			}
			System.out.printf("\nCount : %d\n\n\n", (int)result[allOfSensingPoints-1]);
// 센싱 데이터 확인 ///////////////////////////////////////////////////////////////////////////////////
*/
			
//학습 데이터 수집 ///////////////////////////////////////////////////////////////////////////////////
			k = 0;
			sk = 0;
			out.write("4 ");
			out.flush();
			System.out.printf("\n%d\t", check);
			for(i = 0; i<5; i++)
			{
				for(j = 0; j<sensorNum[i]; j++)
				{
//					System.out.printf("%10.3f ", result[order[i]*numOfSensingPoint + j]);
					data[k++] = (int)result[order[i]*numOfSensingPoint + j];
					if (j > 0) // 1217
						svm_data[sk++] = (double)(data[k-1]-data[k-2])/10.0;
//					svm_data[sk++] = (double)data[k-1] / 500.0; // 1216
					
//					System.out.printf("%d:%d ", k, data[k-1]);
//					String dataS = k + ":" + (double)(data[k-1]/500.0) + " ";
					System.out.printf("%d:%6.3f  ", sk, svm_data[sk-1]);
					String dataS = sk + ":" + svm_data[sk-1] + " ";
					out.write(dataS);
					out.flush();
				}
			}
			out.newLine();
			check ++;
			if (check > 3000)
				System.exit(0);
//학습 데이터 수집  ///////////////////////////////////////////////////////////////////////////////////

/*// 1126이전
			for(int i = 0; i < numOfSensingPoints-1; i++)
			{
				if(i%numOfSensingPoint == 0)
					System.out.printf("\nS%d : %10.3f ", (int)i/19, result[i]);
				else if((i+1)%numOfSensingPoint == 0)
					System.out.printf(" %s(%.3f) ", directionBuffer[(int)result[i]], result[i]);
				else
					System.out.printf("%10.3f ", result[i]);
			}
			System.out.printf("\nCount : %d\n\n\n", (int)result[numOfSensingPoints-1]);
*/
//			System.out.printf("Direction : %s\n", directionBuffer[(int)result[numOfSensingPoints-2]]);
			
		}
//		out.close();
		
	}

}
