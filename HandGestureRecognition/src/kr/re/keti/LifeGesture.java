package kr.re.keti;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.swing.JTextField;
import javax.swing.JSlider;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import kr.re.keti.sensorvalue.Port;
import kr.re.keti.sensorvalue.PortWorker;
import kr.re.keti.sensorvalue.Sensor;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;


public class LifeGesture extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 * @throws InterruptedException 
	 */

	public static void main(String[] args) throws IOException, InterruptedException
	{
		final int baudRate = 921600;
		final String portName = "COM44"; //"COM7" "COM6" - notebook 
		Port portWorker = new PortWorker(portName, baudRate);
		Sensor SensorsValue = new Sensor(portWorker);
		SensorsValue.openPort();

		int[] order = {0, 1, 2, 3, 4};
		int[] sensorNum = {6, 6, 6, 6, 6}; // 남성용 - 6, 여성용 - 5
		int[] UIsensorNum = {6, 7, 7, 6, 6}; // 남성용 - 6, 여성용 - 5
		int numOfGyro = 6;
		int numOfPressure = 30;
		int numOfSensingPoint = 6; 
		int numOfStrech = 5;
		int allOfSensors = numOfPressure + numOfGyro + numOfStrech;
	
		double[] previous_result = new double [allOfSensors];
		int[] data = new int [allOfSensors];
		int[] UIdata = new int [allOfSensors];
		int IMUsum =0;

		int i=0, j=0, k, sk, err, classsetting=0, uik;
		int prediction=0, predict_count=0, previous_prediction=-1, update_count=0;
		int index3to4=27, index5to7=2, index4to5=4, index0to6=25, index6to9=3, index9to6=14, index7to4=4; //index6to9=27(엄지4), index9to6=14(약지-3) -절대값기준
		int mindex6to9=9;
		double class3to4=4, class5to7=9.0, class4to5=20.0, class0to6=8.0, class7to4=20.0, class6to9=9.0;
		double mclass6to9=10.0;
		int th_count = 10;

		svm_model model_1st = null; //svm.svm_load_model(0); // eunae _v1205 for initialization
//		svm_model model_1st = svm.svm_load_model("hand-model1017.model"); // eunae _v1205
//		svm_model model_2nd = svm.svm_load_model("hand-model1022v2.model"); // eunae _v1205
		int setting = -10;
		while (setting < -1)
		{
			Thread.sleep(1000);
			double[] result = SensorsValue.getDatas3th();
			
			if(result == null)
			{
				System.out.println("글러브를 연결해주세요.");
				continue;
			}
			else
			{
				if(result[5] != 9999 && result[5] > 800 && result[5] < 200) // 여성용일 수도 있고, 센서가 망가진 거 일 수도 있고. 로딩 기다리는 목적
				{
					setting += 1;
				}				
				else if(result[5] == 9999) // 여성용 이면
				{
					setting = 1;
					System.out.println("\n여성용 장갑을 착용하셨습니다.");
					sensorNum[0] -= 1;
					UIsensorNum[0] -= 1;
					numOfPressure -= 1;
//					allOfSensors -= 1; 전체 센서 데이터 값
					model_1st = svm.svm_load_model("hand-model-woman.model"); // eunae _v1205
				}
				else // 모든 센서값이 정상일 때 
				{
					setting = 0;
					System.out.println("\n남성용 장갑을 착용하셨습니다.");
					model_1st = svm.svm_load_model("hand-model-woman.model"); // eunae 1017
				}
			}
		}
		if(setting == -1)
		{
			System.out.println("글러브 센서를 확인해 주세요.");
			System.exit(0);
		}

		int svmOfPressure = numOfPressure; // 1205=allOfPressure, 1217=allOfPressure-5
		if(setting == 0)
			svmOfPressure --; //남성용이면, -> 여성용이랑 같은 학습 모델을 사용할 경우에만.
		double[] max_data = new double [svmOfPressure];
		double[] min_data = new double [svmOfPressure];
		double[] svm_data = new double [svmOfPressure]; //1205
//		System.out.println(sensorNum[0]);
//		System.out.println(numOfPressure);
//		System.out.println(allOfSensors);


		BufferedReader in = null;
		if (setting == 0) //남성용이면
			in = new BufferedReader(new FileReader("data_normalization-woman.txt"));
		else if (setting == 1) //여성용이면
			in = new BufferedReader(new FileReader("data_normalization-woman.txt"));

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
//		System.out.println(i);
		
/*
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
				Thread.sleep(1000);
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
		System.out.printf("Data Normalization Finish\n");
*/
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					LifeGesture frame = new LifeGesture();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
//		updateGesture(10);
		Thread.sleep(1000);
		while(classsetting == 0)
		{
		
			double[] result = null;
			if(hand == 0)
				result = SensorsValue.getDatas3th();
			if(hand == 1)
				result = SensorsValue.getDatas3th(hand);
			if(hand == 2)
				result = SensorsValue.getDatas3th(hand);
		
			if(result == null)
			{
				continue;
			}
//			else if(setting == 1) //남성용이면, -> 여성용이랑 다른 학습 모델을 사용할 경우에만.
//			{
				classsetting = 1;

//				class3to5= (double)(result[index3to5] - min_data[index3to5]) / (double)(max_data[index3to5] - min_data[index3to5]) * 10.0 - 5.0;
				class3to4= (double)(result[index3to4] - min_data[index3to4]) / (double)(max_data[index3to4] - min_data[index3to4]) * 10.0 - 2.5; 
				class5to7= (double)(result[index5to7] - min_data[index5to7]) / (double)(max_data[index5to7] - min_data[index5to7]) * 10.0 + 1.0;
				class4to5= (double)(result[index4to5] - min_data[index4to5]) / (double)(max_data[index4to5] - min_data[index4to5]) * 10.0 + 15.0;
				class0to6= (double)(result[index0to6] - min_data[index0to6]) / (double)(max_data[index0to6] - min_data[index0to6]) * 10.0 - 2.0;
				class7to4= (double)(result[index7to4] - min_data[index7to4]) / (double)(max_data[index7to4] - min_data[index7to4]) * 10.0 + 15.0;
				class6to9= (double)(result[index6to9] - min_data[index6to9]) / (double)(max_data[index6to9] - min_data[index6to9]) * 10.0 - 2.0;
				
//			}
			if(setting == 0) //남성용이면, -> 여성용이랑 다른 학습 모델을 사용할 경우에만.
			{
//				classsetting = 1;
				index6to9 = 8;
				th_count = 8;
				class6to9= (double)(result[index6to9] - min_data[index6to9]) / (double)(max_data[index6to9] - min_data[index6to9]) * 10.0 - 1.0;
				
//				mclass6to9= (double)(result[mindex6to9] - min_data[mindex6to9]) / (double)(max_data[mindex6to9] - min_data[mindex6to9]) * 10.0 - 2.0;
			}
		}
		
		Thread.sleep(1000);
		while(true)
		{
			double[] result;
			if(hand == 0)
				result = SensorsValue.getDatas3th();
			else
				result = SensorsValue.getDatas3th(hand);
		
			if(result == null)
			{
				continue;
			}
			else if(result[0] == -1.0 ) // -12345.67890
			{
				updateGesture(10);
				continue;
			}

			k = 0;
			uik = 0;
			sk = 0;
			err = 0;
//			for(i=0; i<allOfSensors; i++)
//				System.out.printf("%d:%d ", i, (int)result[i]);
//			System.out.printf("\n");
			
			for(i=0; i<5&err==0; i++)
			{
				for(j=0; j<sensorNum[i]&err==0; j++)
				{
					if(setting==0 && i==0 && j==0)  //남성용이면, -> 여성용이랑 같은 학습 모델을 사용할 경우에만.
					{
						UIdata[uik] = (int)result[order[i]*numOfSensingPoint + j];
						j++; //남성용이면, 앞에 한개 버림 (손등쪽 센서)
						uik++;
					}
					data[k] = (int)result[order[i]*numOfSensingPoint + j];
					UIdata[uik] = data[k];
					if(data[k]<250 || data[k]>750)
						err = 1;
					svm_data[sk++] = (double)(data[k] - min_data[k]) / (double)(max_data[k] - min_data[k]) * 10.0;
					k++;
//					System.out.printf("%d:%d ", uik, UIdata[uik]);
					uik ++;
//					System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
//					if(j > 0) //1217
//					{
//						svm_data[sk++] = (double)(data[k-1]-data[k-2])/10.0;
//						System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
//					}
//					System.out.printf("%d:%d ", k, data[k-1]);
				}
				if(i == 1 || i == 2)
				{
					UIdata[uik] = (int)result[numOfPressure+setting+i];
//					System.out.printf("%d:%d ", uik, UIdata[uik]);
					uik++;
				}
			}
//			System.out.printf("\n%d:%10.3f ", allOfSensors-6, result[allOfSensors-6]);
//			System.out.printf("%d:%10.3f ", allOfSensors-5, result[allOfSensors-5]);
//			System.out.printf("%d:%10.3f ", allOfSensors-4, result[allOfSensors-4]);
//			System.out.printf("%d:%10.3f ", allOfSensors-3, result[allOfSensors-3]);
//			System.out.printf("%d:%10.3f ", allOfSensors-2, result[allOfSensors-2]);
//			System.out.printf("%d:%10.3f ", allOfSensors-1, result[allOfSensors-1]);

//			Thread.sleep(10);			
			if(err == 1)
			{
				System.out.printf("sensor data input warning %d:%d [%d]%d\n", i-1, j, k, data[k-1]);
				updateGesture(10);
//				System.exit(0);
				continue ;
			}
//			System.out.println();
//			System.out.printf("%d %d %d %d %d %d \n", (int)result[allOfSensors+setting-6], (int)result[allOfSensors+setting-5], (int)result[allOfSensors+setting-4], (int)result[allOfSensors+setting-3], (int)result[allOfSensors+setting-2], (int)result[allOfSensors+setting-1]);
			updateRawData(UIdata, order, UIsensorNum);
			updateIMU((int)result[allOfSensors-6], (int)result[allOfSensors-5], (int)result[allOfSensors-4], (int)result[allOfSensors-3], (int)result[allOfSensors-2], (int)result[allOfSensors-1]);
			
			IMUsum = 0;
			for(i=6; i>0; i--)
			{
				IMUsum += (int)Math.abs(result[allOfSensors-i]-previous_result[allOfSensors-i]);
//				System.out.printf("%d ", i);
			}
//			System.out.printf("%d \n", IMUsum);
			if(IMUsum < 300 & predict_count>0)
			{
				predict_count -= 1;
				continue ;
			}
			else if(IMUsum < 500 & predict_count==0)
			{
				prediction = 0;
				updateGesture(prediction);
				continue ;
			}
//			System.out.printf("%2.1f %2.1f %2.1f %2.1f %2.1f \n", svm_data[index3to5], svm_data[index3to4], svm_data[index5to7], svm_data[index4to5], svm_data[index0to6]);
//			System.out.printf("%2.1f %2.1f %2.1f %2.1f %2.1f \n", class3to5, class3to4, class5to7, class4to5, class0to6);
 // 1231			
			prediction = hand_predict(model_1st, svm_data, svmOfPressure);
			System.out.printf("%d : ", prediction);//%.0f %.0f %.1f  ", prediction, result[index6to9], result[index9to6], svm_data[index4to5]);// (int) Math.abs(result[allOfSensors-6]-previous_result[allOfSensors-6])); //prediction);
			
/*			if(prediction==3 || prediction==4 || prediction==5 || prediction==7)
			{
				int predictionv2 = hand_predict(model_2nd, svm_data, svmOfPressure);
				System.out.printf("%d : ", predictionv2);//%.0f %.0f %.1f  ", prediction, result[index6to9], result[index9to6], svm_data[index4to5]);// (int) Math.abs(result[allOfSensors-6]-previous_result[allOfSensors-6])); //prediction);
				if(prediction != predictionv2)
				{
				}
			}*/


//			if(setting == 1)  //여성용이면, -> 남성용이랑 다른 학습 모델을 사용할 경우에만.
//			{
//				if(prediction == 3 && svm_data[index3to4]>class3to4)
				if(prediction==3 && svm_data[index3to4]>class3to4)
					prediction = 4;				
				else if(prediction == 4)
				{
					if(svm_data[index4to5]>class4to5 && svm_data[index5to7]<class5to7)
						prediction = 5;
					else if(svm_data[index3to4]<class3to4)
						prediction = 3;
					else if(svm_data[index7to4]>class7to4 && svm_data[index5to7]>class5to7)
						prediction = 7;
				}
				else if(prediction == 5)
				{
					if(Math.abs(svm_data[index3to4]-svm_data[index4to5])<20)
					{
						if(svm_data[index3to4]>class3to4)
							prediction = 4;
						else
							prediction = 3;
					}
					else if(svm_data[index5to7]>class5to7)
						prediction = 7;							
				}
				else if(prediction==0 && svm_data[index0to6]<class0to6)
					prediction = 6;				
				else if(prediction==6 && svm_data[index6to9]>class6to9 && setting==1)// result[index6to9]>result[index9to6])
					prediction = 9;
				else if(prediction==9 && svm_data[index6to9]<class6to9 && setting==1) //&& result[index6to9]<result[index9to6])
					prediction = 6;
				else if(prediction==6 && svm_data[index6to9]<class6to9 && setting==0)// result[index6to9]>result[index9to6])
					prediction = 9;
				else if(prediction==9 && svm_data[index6to9]>class6to9 && setting==0) //&& result[index6to9]<result[index9to6])
					prediction = 6;
				else if(prediction==7 && svm_data[index7to4]<class7to4)
				{
					if(svm_data[index3to4]>class3to4)
						prediction = 4;				
					else
						prediction = 3;
				}
//			}
			
//			else
//			{
//				if(prediction == 6 && svm_data[mindex6to9]>mclass6to9)// result[index6to9]>result[index9to6])
//					prediction = 9;
//				else if(prediction == 9 && svm_data[mindex6to9]<mclass6to9) //&& result[index6to9]<result[index9to6])
//					prediction = 6;			
//			}

			
//			if (none_count > 50)
//				updateGesture(0);
			if (previous_prediction==prediction && predict_count>th_count && Math.abs(result[allOfSensors+setting-6]) > 500) //9-uart 7-serial
			{
				// 가속도, 각속도 체크해서 prediction 변경
				updateGesture(prediction);
				if(predict_count < th_count*2)
					predict_count ++;
//				else if(prediction == 6)
//					predict_count ++;
				
				if(currentInputGesture >= 0 && prediction != 0 && update_count==0)
				{
					if(currentInputGesture == prediction-1)
						actionCorrect();
					else
						actionIncorrect();					
				}
				update_count = 1;
//				else
//					update_count = 0;
					
//				predict_count = 3;
			}
			else if (previous_prediction == prediction && Math.abs(result[allOfSensors+setting-6]) > 500)
			{
				previous_prediction = prediction;
				predict_count ++;
			}
			else if (previous_prediction == prediction && (prediction == 7 || prediction ==9) && Math.abs(result[allOfSensors+setting-3]-previous_result[allOfSensors+setting-3])>300) // result[allOfSensors-5] < 1000 && predict_count > 1 
			{
				previous_prediction = prediction;
				if(predict_count < th_count*2)
					predict_count ++;
			}

			else if (predict_count < 1)
			{
				previous_prediction = prediction;
//				predict_count = 0;
				updateGesture(0);
				update_count = 0;
			}
			else
			{
				predict_count -= 1;
				if(previous_prediction == 0)
					predict_count = 0;
//					update_count = 0;
		}

			System.out.printf("\n\t%.1f:%.1f\t", svm_data[index6to9], Math.abs(result[allOfSensors+setting-3]));
			System.out.printf("%.1f:%.1f:%.1f %d\t%d\t[%d]\t%d\n", svm_data[index3to4], svm_data[index4to5], svm_data[index5to7], previous_prediction, prediction, predict_count, update_count);
			System.arraycopy(result, 0, previous_result, 0, allOfSensors);
		}
		
	}	
	
	
	public static void main1hand(String[] args) throws IOException, InterruptedException //only woman 
	{
		final int baudRate = 921600;
		final String portName = "COM49"; //"COM7" "COM6" - notebook 
		Port portWorker = new PortWorker(portName, baudRate);
		Sensor SensorsValue = new Sensor(portWorker);
		SensorsValue.openPort();

		int[] order = {4, 0, 1, 2, 3};
		int[] sensorNum = {5, 6, 6, 6, 6};
		int numOfGyro = 6;
		int numOfPressure = 29;
		int numOfSensingPoint = 6; 
		int numOfStrech = 5;
		int allOfSensors = numOfPressure + numOfGyro + numOfStrech;
		double[] previous_result = new double [allOfSensors];
		int[] data = new int [allOfSensors];

		int i=0, j=0, k, sk, err, classsetting=0;
		int prediction=0, predict_count=0, previous_prediction=-1, update_count=0;
		int index3to4=27, index5to7=2, index4to5=4, index0to6=25, index6to9=3, index9to6=14, index7to4=4; //index6to9=27(엄지4), index9to6=14(약지-3) -절대값기준
		double class3to4=4, class5to7=9.0, class4to5=20.0, class0to6=8.0, class7to4=20.0, class6to9=9.0;

		svm_model model_1st = svm.svm_load_model("hand-model1017.model"); // eunae _v1205
//		svm_model model_2nd = svm.svm_load_model("hand-model1022v2.model"); // eunae _v1205

		int svmOfPressure = numOfPressure; // 1205=allOfPressure, 1217=allOfPressure-5
		double[] max_data = new double [svmOfPressure];
		double[] min_data = new double [svmOfPressure];
		double[] svm_data = new double [svmOfPressure]; //1205
		int IMUsum =0;


		BufferedReader in = new BufferedReader(new FileReader("data_normalization-eunae.txt"));
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
	
/*
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
				Thread.sleep(1000);
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
		System.out.printf("Data Normalization Finish\n");
*/
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					LifeGesture frame = new LifeGesture();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
//		updateGesture(10);
		Thread.sleep(1000);
		while(classsetting == 0)
		{
			double[] result = SensorsValue.getDatas3th();
		
			if(result == null)
			{
				continue;
			}
			else
			{
				classsetting = 1;

//				class3to5= (double)(result[index3to5] - min_data[index3to5]) / (double)(max_data[index3to5] - min_data[index3to5]) * 10.0 - 5.0;
				class3to4= (double)(result[index3to4] - min_data[index3to4]) / (double)(max_data[index3to4] - min_data[index3to4]) * 10.0 - 2.5; 
				class5to7= (double)(result[index5to7] - min_data[index5to7]) / (double)(max_data[index5to7] - min_data[index5to7]) * 10.0 + 1.0;
				class4to5= (double)(result[index4to5] - min_data[index4to5]) / (double)(max_data[index4to5] - min_data[index4to5]) * 10.0 + 15.0;
				class0to6= (double)(result[index0to6] - min_data[index0to6]) / (double)(max_data[index0to6] - min_data[index0to6]) * 10.0 - 2.0;
				class7to4= (double)(result[index7to4] - min_data[index7to4]) / (double)(max_data[index7to4] - min_data[index7to4]) * 10.0 + 15.0;
				class6to9= (double)(result[index6to9] - min_data[index6to9]) / (double)(max_data[index6to9] - min_data[index6to9]) * 10.0 - 2.0;
				
			}
		}
		
		while(true)
		{
			double[] result = SensorsValue.getDatas3th();
		
			if(result == null)
			{
				continue;
			}
			else if(result[0] == -1.0 ) // -12345.67890
			{
				updateGesture(10);
				continue;
			}
//				System.out.printf("%10.3f ", result[0]);

			k = 0;
			sk = 0;
			err = 0;
			for(i=0; i<5&err==0; i++)
			{
				for(j=0; j<sensorNum[i]&err==0; j++)
				{
					data[k] = (int)result[order[i]*numOfSensingPoint + j];
					if(data[k]<250 || data[k]>750)
						err = 1;
					svm_data[sk++] = (double)(data[k] - min_data[k]) / (double)(max_data[k] - min_data[k]) * 10.0;
					k++;
//					System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
//					if(j > 0) //1217
//					{
//						svm_data[sk++] = (double)(data[k-1]-data[k-2])/10.0;
//						System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
//					}
//					System.out.printf("%d:%d ", k, data[k-1]);
				}
			}
			updateRawData(data, order, sensorNum);
			updateIMU((int)result[allOfSensors-6], (int)result[allOfSensors-5], (int)result[allOfSensors-4], (int)result[allOfSensors-3], (int)result[allOfSensors-2], (int)result[allOfSensors-1]);
			if(err == 1)
			{
				System.out.printf("sensor data input warning %d:%d [%d]%d\n", i-1, j, k, data[k-1]);
				updateGesture(10);
//				System.exit(0);
				continue ;
			}
//			System.out.println();
//1			System.out.printf("%d %d %d %d %d %d \n", (int)result[allOfSensors-6], (int)result[allOfSensors-5], (int)result[allOfSensors-4], (int)result[allOfSensors-3], (int)result[allOfSensors-2], (int)result[allOfSensors-1]);
			
			IMUsum = 0;
			for(i=6; i>0; i--)
			{
				IMUsum += (int)Math.abs(result[allOfSensors-i]-previous_result[allOfSensors-i]);
//				System.out.printf("%d ", i);
			}
//			System.out.printf("%d \n", IMUsum);
			if(IMUsum < 500 & predict_count>0)
			{
				predict_count -= 1;
				continue ;
			}
			else if(IMUsum < 500 & predict_count==0)
			{
				prediction = 0;
				updateGesture(prediction);
				continue ;
			}
//			System.out.printf("%2.1f %2.1f %2.1f %2.1f %2.1f \n", svm_data[index3to5], svm_data[index3to4], svm_data[index5to7], svm_data[index4to5], svm_data[index0to6]);
//			System.out.printf("%2.1f %2.1f %2.1f %2.1f %2.1f \n", class3to5, class3to4, class5to7, class4to5, class0to6);
 // 1231			
			prediction = hand_predict(model_1st, svm_data, svmOfPressure);
			System.out.printf("%d : ", prediction);//%.0f %.0f %.1f  ", prediction, result[index6to9], result[index9to6], svm_data[index4to5]);// (int) Math.abs(result[allOfSensors-6]-previous_result[allOfSensors-6])); //prediction);
			
/*			if(prediction==3 || prediction==4 || prediction==5 || prediction==7)
			{
				int predictionv2 = hand_predict(model_2nd, svm_data, svmOfPressure);
				System.out.printf("%d : ", predictionv2);//%.0f %.0f %.1f  ", prediction, result[index6to9], result[index9to6], svm_data[index4to5]);// (int) Math.abs(result[allOfSensors-6]-previous_result[allOfSensors-6])); //prediction);
				if(prediction != predictionv2)
				{
				}
			}*/


			if(prediction == 3 && svm_data[index3to4]>class3to4)
				prediction = 4;				
			else if(prediction == 4)
			{
				if(svm_data[index4to5]>class4to5 && svm_data[index5to7]<class5to7)
					prediction = 5;
				else if(svm_data[index3to4]<class3to4)
					prediction = 3;
				else if(svm_data[index7to4]>class7to4 && svm_data[index5to7]>class5to7)
					prediction = 7;
			}
			else if(prediction == 5)
			{
				if(Math.abs(svm_data[index3to4]-svm_data[index4to5])<20)
				{
					if(svm_data[index3to4]>class3to4)
						prediction = 4;
					else
						prediction = 3;
				}
				else if(svm_data[index5to7]>class5to7)
					prediction = 7;							
			}
			else if(prediction == 0 && svm_data[index0to6]<class0to6)
				prediction = 6;				
			else if(prediction == 6 && svm_data[index6to9]>class6to9)// result[index6to9]>result[index9to6])
				prediction = 9;
			else if(prediction == 9 && svm_data[index6to9]<class6to9) //&& result[index6to9]<result[index9to6])
				prediction = 6;
			else if(prediction == 7 && svm_data[index7to4]<class7to4)
			{
				if(svm_data[index3to4]>class3to4)
					prediction = 4;				
				else
					prediction = 3;
			}

			
//			if (none_count > 50)
//				updateGesture(0);
			if (previous_prediction==prediction && predict_count>4 && result[allOfSensors-5] > 0) //9-uart 7-serial
			{
				// 가속도, 각속도 체크해서 prediction 변경
				updateGesture(prediction);
				if(predict_count < 10)
					predict_count ++;
				
				if(currentInputGesture >= 0 && prediction != 0 && update_count==0)
				{
					if(currentInputGesture == prediction-1)
						actionCorrect();
					else
						actionIncorrect();					
				}
				update_count = 1;
//				else
//					update_count = 0;
					
//				predict_count = 3;
			}
			else if (previous_prediction == prediction && result[allOfSensors-5] > 3000)
			{
				previous_prediction = prediction;
				predict_count ++;
			}
			else if (previous_prediction == prediction && prediction == 7 && Math.abs(result[allOfSensors-3])>500) // result[allOfSensors-5] < 1000 && predict_count > 1 
			{
				previous_prediction = prediction;
				predict_count ++;
			}
			else if (predict_count < 1)
			{
				previous_prediction = prediction;
//				predict_count = 0;
				updateGesture(0);
				update_count = 0;
			}
			else
			{
				predict_count -= 1;
				if(previous_prediction == 0)
					predict_count = 0;
//					update_count = 0;
		}

			System.out.printf("\t%.1f:%.1f\t", svm_data[index6to9], Math.abs(result[allOfSensors-3]));
			System.out.printf("%.1f:%.1f:%.1f %d\t%d\t[%d]\t%d\n", svm_data[index3to4], svm_data[index4to5], svm_data[index5to7], previous_prediction, prediction, predict_count, update_count);
			System.arraycopy(result, 0, previous_result, 0, allOfSensors);
		}
		
	}	
/*
	public static void main2nd(String[] args) throws IOException
	{
		final int baudRate = 115200;
		final String portName = "COM44";
		Port portWorker = new PortWorker(portName, baudRate);
		Sensor SensorsValue = new Sensor(portWorker);
		SensorsValue.openPort();

		int numOfSensingPoint=19;
		int numOfPressure = 9;
		int numOfGyro = 9;
		int allOfGyro = 45;
		int[] order = {4, 0, 1, 2, 3};
		int[] sensorNum = {6, 7, 8, 7, 6};
		String[] directionBuffer = {"None", "Up", "Down", "Left", "Right"};
		int check = 1, i, j, k, sk;
		int prediction=0, predict_count=0, previous_prediction=-1, none_count=0, update_count=0;
		int prediction_1st=0, prediction_2nd=0, prediction_3rd=0;
		double class7to5=10.0, class3to4=10.0, class1to2=10.0, class2to5=10.0, class0to2=10.0, class2to4=10.0, class9to8=10.0;
		int index7to5=0, index3to4=31, index1to2=17, index2to5=25, index0to2=9, index2to4=11, index9to8=10;

		int allOfSensingPoints = numOfSensingPoint*5+1;
		double[] previous_result = new double [allOfSensingPoints];
		int allOfPressure = 34; // 38=1128, 39=남성용, 34=여성용
		int[] data = new int [allOfPressure];
		int svmOfPressure = allOfPressure; // 1205=allOfPressure, 1217=allOfPressure-5
		double[] svm_data = new double [svmOfPressure]; //1205
//		svm_model model_1st = svm.svm_load_model("hand-train_v0103-1.model"); // eunae _v0103
//		svm_model model_2nd = svm.svm_load_model("hand-train_v0103-2.model"); // eunae _v0103
//		svm_model model_3rd = svm.svm_load_model("hand-train_v0103-3.model"); // eunae _v0103	
//		svm_model model_2nd0 = svm.svm_load_model("hand-train_v1205.model"); // eunae _v1205
		svm_model model_1st = svm.svm_load_model("hand-train_v1205.model"); // eunae _v1205
		svm_model model_2nd1 = svm.svm_load_model("hand-train35_v2_v1205.model"); // eunae _v1205
		svm_model model_2nd2 = svm.svm_load_model("hand-train25_v2_v1206.model"); // eunae _v1205

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					LifeGesture frame = new LifeGesture();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		
//		updateGesture(10);
		while(true)
		{
			double[] result = SensorsValue.getDatas();
		
			if(result == null)
				continue;
			else if(result[0] == -12345.67890)
			{
				updateGesture(10);
				continue;
			}

			k = 0;
			sk = 0;
			for(i = 0; i<5; i++)
			{
				for(j = 0; j<sensorNum[i]; j++)
				{
//					System.out.printf("%10.3f ", result[order[i]*numOfSensingPoint + j]);
					data[k++] = (int)result[order[i]*numOfSensingPoint + j];
					svm_data[sk++] = (double)data[k-1]/500.0; //1205
//					System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
//					if(j > 0) //1217
//					{
//						svm_data[sk++] = (double)(data[k-1]-data[k-2])/10.0;
//						System.out.printf("%d:%5.3f ", sk, svm_data[sk-1]);
//					}
//					System.out.printf("%d:%d ", k, data[k-1]);
				}
//				System.out.printf("[%.1f]%5s\t", result[i*numOfSensingPoint+18], directionBuffer[(int)result[i*numOfSensingPoint+18]]);
				if (result[i*numOfSensingPoint+18] == 0)
					none_count ++;
				else 
					none_count = 0;
				System.out.printf("%s\t", directionBuffer[(int)result[i*numOfSensingPoint+18]]);
			}
			System.out.println();
			updateRawData(data, order, sensorNum);
			updateIMU((int)result[numOfPressure], (int)result[numOfPressure+1], (int)result[numOfPressure+2], (int)result[numOfPressure+3], (int)result[numOfPressure+4], (int)result[numOfPressure+5]);

 // 1231			
			prediction = hand_predict(model_1st, svm_data, svmOfPressure);
			System.out.printf("%d : ", prediction);

			if(check == 1 && prediction == 0)
			{
				class3to4 = svm_data[index3to4] - 0.5;
				class7to5 = svm_data[index7to5] + 0.1;
				class1to2 = svm_data[index1to2] - 0.2;
				class2to5 = svm_data[index2to5] - 0.8;
				class2to4 = svm_data[index2to4] - 0.1;
				class0to2 = svm_data[index0to2] - 0.2;
				class9to8 = svm_data[index9to8] + 0.2;
				check = -1;
			}
			
			if(prediction == 3)
			{
				prediction = hand_predict(model_2nd1, svm_data, svmOfPressure);
				if(prediction == 3 && svm_data[index3to4]>class3to4)
					prediction = 4;				
			}
			else if(prediction == 2)
			{			
				prediction = hand_predict(model_2nd2, svm_data, svmOfPressure);
				if(prediction == 2 && svm_data[index2to5]<class2to5)
					prediction = 5;
				else if(prediction == 2 && svm_data[index1to2]>class1to2)
					prediction = 1;
				else if(prediction == 2 && svm_data[index2to4]<class2to4)
					prediction = 4;
			}
			
			else if(prediction == 1 && svm_data[index1to2]<class1to2)
				prediction = 2; 
			else if(prediction == 4 && svm_data[index3to4]<class3to4)
				prediction = 3;
			else if (prediction == 5)
			{
				 if(svm_data[index2to5]>class2to5)
						prediction = 2;
				 else if(svm_data[index7to5]>class7to5)
						prediction = 7;
			}
			else if(prediction == 7 && svm_data[index7to5]>class7to5)
				prediction = 5;
			else if(prediction == 0 && svm_data[index0to2]<class0to2)
				prediction = 2;
			else if(prediction == 9 && svm_data[index9to8]>class9to8)
				prediction = 8;


			System.out.printf("%d\t%d\t[%d]\t%d\n", prediction, none_count, predict_count, currentInputGesture);

			
			if (none_count > 50)
				updateGesture(0);
			else if (previous_prediction==prediction && predict_count>1)
			{
				// 가속도, 각속도 체크해서 prediction 변경
				updateGesture(prediction);
				
				if(currentInputGesture >= 0 && prediction != 0 && update_count==0)
				{
					if(currentInputGesture == prediction-1)
						actionCorrect();
					else
						actionIncorrect();					
				}
				update_count = 1;
//				else
//					update_count = 0;
					
//				predict_count = 3;
			}
			else if (previous_prediction == prediction)
			{
				predict_count ++;
			}
			else if (predict_count < 1)
			{
				updateGesture(0);
			}
			else
			{
				predict_count --;
				update_count = 0;
			}

			previous_prediction = prediction;
			System.arraycopy(result, 0, previous_result, 0, allOfSensingPoints);
		}
		
	}
*/
	
	public static int hand_predict(svm_model model, double[] svm_data, int dimension)
	{
		int prediction = 0;

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);

//		int m = dimension; // input data dimension
		svm_node[] x = new svm_node[dimension];
		for(int j=0; j<dimension; j++)
		{
			x[j] = new svm_node();
			x[j].index = j+1;
			x[j].value = svm_data[j];
		}
		prediction = (int)svm.svm_predict(model,x); // eunae
		
		return prediction;
	}

	/**
	 * Create the frame.
	 */
	public LifeGesture()
	{
		setResizable(false);
		setTitle("\uC77C\uC0C1\uC0DD\uD65C\uB3D9\uC791");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1193, 826);
		
		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				switch(e.getKeyCode())
				{
					case KeyEvent.VK_A:
						//actionCorrect();
						btnCorrect.doClick();
						break;
						
					case KeyEvent.VK_X:
						//actionIncorrect();
						btnIncorrect.doClick();
						break;
						
					case KeyEvent.VK_1:	// HA
						btnHa.doClick();
						break;
						
					case KeyEvent.VK_2:	// GL
						btnGl.doClick();
						break;
						
					case KeyEvent.VK_3:	// FK -> CS
						btnFk.doClick();
						break;
						
					case KeyEvent.VK_4:	// SP
						btnSp.doClick();
						break;
						
					case KeyEvent.VK_5:	// CP
						btnCp.doClick();
						break;
						
					case KeyEvent.VK_6:	// PH
						btnPh.doClick();
						break;
						
					case KeyEvent.VK_7:	// TB
						btnTb.doClick();
						break;
						
					case KeyEvent.VK_8:	// HB
						btnHb.doClick();
						break;
						
					case KeyEvent.VK_9:	// HD
						btnHd.doClick();
						break;
						
					case KeyEvent.VK_0:	// Any
						btnAny.doClick();
						break;
						
					case KeyEvent.VK_R:
						//resetLabels();
						btnReset.doClick();
						break;
				}
			}
		});

		setFocusable(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{}
		
		initArrays();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\uC815\uD655\uB3C4 \uBD84\uC11D", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(753, 654, 419, 125);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		btnReset = new JButton("Reset (R)");
		btnReset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				resetLabels();
				
				requestFocus();
			}
		});
		btnReset.setBounds(302, 12, 109, 39);
		panel_1.add(btnReset);
		
		JLabel label = new JLabel("\uC785\uB825 \uAC1C\uC218:");
		label.setBounds(12, 24, 57, 15);
		panel_1.add(label);
		
		JLabel lblNewLabel = new JLabel("\uC778\uC2DD \uC644\uB8CC:");
		lblNewLabel.setBounds(12, 40, 57, 15);
		panel_1.add(lblNewLabel);
		
		lblNumInput = new JLabel("0");
		lblNumInput.setBounds(81, 24, 57, 15);
		panel_1.add(lblNumInput);
		
		lblNumCorrect = new JLabel("0");
		lblNumCorrect.setBounds(81, 40, 57, 15);
		panel_1.add(lblNumCorrect);
		
		lblAccuracy = new JLabel("0 %");
		lblAccuracy.setForeground(Color.BLUE);
		lblAccuracy.setHorizontalAlignment(SwingConstants.CENTER);
		lblAccuracy.setFont(new Font("굴림", Font.BOLD, 34));
		lblAccuracy.setBounds(118, 12, 168, 45);
		panel_1.add(lblAccuracy);
		
		lblHa = new JLabel("HA");
		lblHa.setFont(new Font("굴림", Font.BOLD, 12));
		lblHa.setBounds(24, 75, 23, 15);
		panel_1.add(lblHa);
		
		lblPh = new JLabel("PH");
		lblPh.setFont(new Font("굴림", Font.BOLD, 12));
		lblPh.setBounds(24, 100, 23, 15);
		panel_1.add(lblPh);
		
		labelHA = new JLabel("");
		labelHA.setBounds(47, 75, 44, 15);
		
		labelPH = new JLabel("");
		labelPH.setBounds(47, 100, 44, 15);
		
		lblGl = new JLabel("GL");
		lblGl.setFont(new Font("굴림", Font.BOLD, 12));
		lblGl.setBounds(103, 75, 23, 15);
		panel_1.add(lblGl);
		
		labelGL = new JLabel("");
		labelGL.setBounds(126, 75, 44, 15);
		
		lblTb = new JLabel("TB");
		lblTb.setFont(new Font("굴림", Font.BOLD, 12));
		lblTb.setBounds(103, 100, 23, 15);
		panel_1.add(lblTb);
		
		labelTB = new JLabel("");
		labelTB.setBounds(126, 100, 44, 15);
		
		lblFk = new JLabel("FK"); //FK -> CS
		lblFk.setFont(new Font("굴림", Font.BOLD, 12));
		lblFk.setBounds(182, 75, 23, 15);
		panel_1.add(lblFk);
		
		labelFK = new JLabel("");
		labelFK.setBounds(205, 75, 44, 15);
		
		lblHb = new JLabel("HB");
		lblHb.setFont(new Font("굴림", Font.BOLD, 12));
		lblHb.setBounds(182, 100, 23, 15);
		panel_1.add(lblHb);
		
		labelHB = new JLabel("");
		labelHB.setBounds(205, 100, 44, 15);
		
		lblSp = new JLabel("SP");
		lblSp.setFont(new Font("굴림", Font.BOLD, 12));
		lblSp.setBounds(261, 75, 23, 15);
		panel_1.add(lblSp);
		
		labelSP = new JLabel("");
		labelSP.setBounds(284, 75, 44, 15);
		
		lblHd = new JLabel("HD");
		lblHd.setFont(new Font("굴림", Font.BOLD, 12));
		lblHd.setBounds(261, 100, 23, 15);
		panel_1.add(lblHd);
		
		labelHD = new JLabel("");
		labelHD.setBounds(284, 100, 44, 15);
		
		lblCp = new JLabel("CP");
		lblCp.setFont(new Font("굴림", Font.BOLD, 12));
		lblCp.setBounds(340, 75, 23, 15);
		panel_1.add(lblCp);
		
		labelCP = new JLabel("");
		labelCP.setBounds(363, 75, 44, 15);
		
		gestureLabels = new JLabel[] {labelHA, labelGL, labelFK, labelSP, labelCP, labelPH, labelTB, labelHB, labelHD};
		
		for(int labelIndex = 0; labelIndex < gestureLabels.length; ++labelIndex)
		{
			gestureLabels[labelIndex].setToolTipText(gestureDescription[labelIndex + 1]);
			
			panel_1.add(gestureLabels[labelIndex]);
		}
		
		btnCorrect = new JButton("\uC778\uC2DD \uC644\uB8CC (A)");
		btnCorrect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				actionCorrect();
				
				requestFocus();
			}
		});
		btnCorrect.setBounds(12, 654, 173, 53);
		contentPane.add(btnCorrect);
		btnCorrect.setFont(new Font("굴림", Font.PLAIN, 20));
		
		btnIncorrect = new JButton("\uD2C0\uB9BC (X)");
		btnIncorrect.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				actionIncorrect();
				
				requestFocus();
			}
		});
		btnIncorrect.setBounds(12, 726, 173, 53);
		contentPane.add(btnIncorrect);
		btnIncorrect.setFont(new Font("굴림", Font.PLAIN, 20));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "\uC785\uB825 \uB370\uC774\uD130", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 10, 1160, 521);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblStrain = new JLabel("Strain");
		lblStrain.setForeground(Color.BLACK);
		lblStrain.setFont(new Font("굴림", Font.BOLD, 16));
		lblStrain.setBounds(12, 23, 81, 15);
		panel_2.add(lblStrain);
		
		JLabel lblImu = new JLabel("IMU");
		lblImu.setForeground(Color.BLACK);
		lblImu.setFont(new Font("굴림", Font.BOLD, 16));
		lblImu.setBounds(12, 396, 57, 15);
		panel_2.add(lblImu);
		
		JLabel lblAcceleration = new JLabel("Acceleration");
		lblAcceleration.setBounds(82, 416, 131, 15);
		panel_2.add(lblAcceleration);
		
		JLabel lblAngularSpeed = new JLabel("Angular Speed");
		lblAngularSpeed.setBounds(636, 416, 139, 15);
		panel_2.add(lblAngularSpeed);
		
		JLabel lblX = new JLabel("X");
		lblX.setBounds(48, 441, 29, 15);
		panel_2.add(lblX);
		
		JLabel lblY = new JLabel("Y");
		lblY.setBounds(48, 466, 29, 15);
		panel_2.add(lblY);
		
		JLabel lblZ = new JLabel("Z");
		lblZ.setBounds(48, 491, 29, 15);
		panel_2.add(lblZ);
		
		JLabel label_1 = new JLabel("X");
		label_1.setBounds(602, 441, 29, 15);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("Y");
		label_2.setBounds(602, 466, 29, 15);
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel("Z");
		label_3.setBounds(602, 491, 29, 15);
		panel_2.add(label_3);
		
		accel_X_Text = new JTextField();
		accel_X_Text.setEditable(false);
		accel_X_Text.setHorizontalAlignment(SwingConstants.TRAILING);
		accel_X_Text.setText("0");
		accel_X_Text.setBounds(68, 438, 116, 21);
		panel_2.add(accel_X_Text);
		accel_X_Text.setColumns(10);
		
		accel_Y_Text = new JTextField();
		accel_Y_Text.setEditable(false);
		accel_Y_Text.setHorizontalAlignment(SwingConstants.TRAILING);
		accel_Y_Text.setText("0");
		accel_Y_Text.setBounds(68, 463, 116, 21);
		panel_2.add(accel_Y_Text);
		accel_Y_Text.setColumns(10);
		
		accel_Z_Text = new JTextField();
		accel_Z_Text.setEditable(false);
		accel_Z_Text.setHorizontalAlignment(SwingConstants.TRAILING);
		accel_Z_Text.setText("0");
		accel_Z_Text.setBounds(68, 488, 116, 21);
		panel_2.add(accel_Z_Text);
		accel_Z_Text.setColumns(10);
		
		angular_X_Text = new JTextField();
		angular_X_Text.setEditable(false);
		angular_X_Text.setText("0");
		angular_X_Text.setHorizontalAlignment(SwingConstants.TRAILING);
		angular_X_Text.setColumns(10);
		angular_X_Text.setBounds(623, 438, 116, 21);
		panel_2.add(angular_X_Text);
		
		angular_Y_Text = new JTextField();
		angular_Y_Text.setEditable(false);
		angular_Y_Text.setText("0");
		angular_Y_Text.setHorizontalAlignment(SwingConstants.TRAILING);
		angular_Y_Text.setColumns(10);
		angular_Y_Text.setBounds(623, 463, 116, 21);
		panel_2.add(angular_Y_Text);
		
		angular_Z_Text = new JTextField();
		angular_Z_Text.setEditable(false);
		angular_Z_Text.setText("0");
		angular_Z_Text.setHorizontalAlignment(SwingConstants.TRAILING);
		angular_Z_Text.setColumns(10);
		angular_Z_Text.setBounds(623, 488, 116, 21);
		panel_2.add(angular_Z_Text);
		
		accel_X_Slider = new JSlider();
		accel_X_Slider.setValue(0);
		accel_X_Slider.setMaximum(defaultMaxAccelerationValue);
		accel_X_Slider.setMinimum(defaultMaxAccelerationValue * (-1));
		accel_X_Slider.setBounds(196, 441, 298, 15);
		panel_2.add(accel_X_Slider);
		accel_X_Slider.setFocusable(false);
		
		accel_Y_Slider = new JSlider();
		accel_Y_Slider.setValue(0);
		accel_Y_Slider.setMaximum(defaultMaxAccelerationValue);
		accel_Y_Slider.setMinimum(defaultMaxAccelerationValue * (-1));
		accel_Y_Slider.setBounds(196, 466, 298, 15);
		panel_2.add(accel_Y_Slider);
		accel_Y_Slider.setFocusable(false);
		
		accel_Z_Slider = new JSlider();
		accel_Z_Slider.setValue(0);
		accel_Z_Slider.setMaximum(defaultMaxAccelerationValue);
		accel_Z_Slider.setMinimum(defaultMaxAccelerationValue * (-1));
		accel_Z_Slider.setBounds(196, 491, 298, 15);
		panel_2.add(accel_Z_Slider);
		accel_Z_Slider.setFocusable(false);
		
		angular_X_Slider = new JSlider();
		angular_X_Slider.setValue(0);
		angular_X_Slider.setMinimum(defaultMaxAngularSpeedValue * (-1));
		angular_X_Slider.setMaximum(defaultMaxAngularSpeedValue);
		angular_X_Slider.setBounds(751, 441, 298, 15);
		panel_2.add(angular_X_Slider);
		angular_X_Slider.setFocusable(false);
		
		angular_Y_Slider = new JSlider();
		angular_Y_Slider.setValue(0);
		angular_Y_Slider.setMinimum(defaultMaxAngularSpeedValue * (-1));
		angular_Y_Slider.setMaximum(defaultMaxAngularSpeedValue);
		angular_Y_Slider.setBounds(751, 466, 298, 15);
		panel_2.add(angular_Y_Slider);
		angular_Y_Slider.setFocusable(false);
		
		angular_Z_Slider = new JSlider();
		angular_Z_Slider.setValue(0);
		angular_Z_Slider.setMinimum(defaultMaxAngularSpeedValue * (-1));
		angular_Z_Slider.setMaximum(defaultMaxAngularSpeedValue);
		angular_Z_Slider.setBounds(751, 491, 298, 15);
		panel_2.add(angular_Z_Slider);
		angular_Z_Slider.setFocusable(false);
		
		JLabel lblNewLabel_1 = new JLabel("0");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 16));
		lblNewLabel_1.setBounds(168, 42, 812, 22);
		panel_2.add(lblNewLabel_1);
		
		JLabel label_4 = new JLabel("1");
		label_4.setForeground(Color.BLUE);
		label_4.setFont(new Font("굴림", Font.BOLD, 16));
		label_4.setBounds(364, 42, 616, 22);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("2");
		label_5.setForeground(Color.BLUE);
		label_5.setFont(new Font("굴림", Font.BOLD, 16));
		label_5.setBounds(560, 42, 420, 22);
		panel_2.add(label_5);
		
		JLabel label_6 = new JLabel("3");
		label_6.setForeground(Color.BLUE);
		label_6.setFont(new Font("굴림", Font.BOLD, 16));
		label_6.setBounds(756, 42, 224, 22);
		panel_2.add(label_6);
		
		JLabel label_7 = new JLabel("4");
		label_7.setForeground(Color.BLUE);
		label_7.setFont(new Font("굴림", Font.BOLD, 16));
		label_7.setBounds(952, 42, 28, 22);
		panel_2.add(label_7);
		
		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				hand = 1;
			}
		});
		btnRight.setBounds(1160-108, 12, 100, 35);
		panel_2.add(btnRight);
		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				hand = 2;
			}
		});
		btnLeft.setBounds(1160-108, 50, 100, 35);
		panel_2.add(btnLeft);
		
		btnHa = new JButton("HA (1)");
		btnHa.setBounds(197, 723, 97, 23);
		
		btnGl = new JButton("GL (2)");
		btnGl.setBounds(306, 723, 97, 23);
		
		btnFk = new JButton("FK (3)"); //FK -> CS
		btnFk.setBounds(415, 723, 97, 23);
		
		btnSp = new JButton("SP (4)");
		btnSp.setBounds(524, 723, 97, 23);
		
		btnCp = new JButton("CP (5)");
		btnCp.setBounds(633, 723, 97, 23);
		
		btnPh = new JButton("PH (6)");
		btnPh.setBounds(197, 756, 97, 23);
		
		btnTb = new JButton("TB (7)");
		btnTb.setBounds(306, 756, 97, 23);
		
		btnHb = new JButton("HB (8)");
		btnHb.setBounds(415, 756, 97, 23);
		
		btnHd = new JButton("HD (9)");
		btnHd.setBounds(524, 756, 97, 23);
		
		btnAny = new JButton("Any (0)");
		btnAny.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				actionButtonAny();
				
				requestFocus();
			}
		});
		btnAny.setToolTipText("Please don't care about the gesture!");
		btnAny.setBounds(633, 756, 97, 23);
		contentPane.add(btnAny);
		
		gestureButtons = new JButton[] {btnHa, btnGl, btnFk, btnSp, btnCp, btnPh, btnTb, btnHb, btnHd};
		
		for(int btnIndex = 0; btnIndex < gestureButtons.length; ++btnIndex)
		{
			gestureButtons[btnIndex].setToolTipText(gestureDescription[btnIndex + 1]);
			
			final int innerBtnIndex = btnIndex;
			
			gestureButtons[btnIndex].addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					lblInput.setText(gestureDescription[innerBtnIndex + 1]);
					
					currentInputGesture = innerBtnIndex;
					
					requestFocus();
				}
			});
			
			contentPane.add(gestureButtons[btnIndex]);
		}
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setBounds(197, 673, 533, 34);
		contentPane.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		lblInput = new JLabel("");
		lblInput.setFont(new Font("굴림", Font.PLAIN, 14));
		panel_4.add(lblInput);
		lblInput.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblYouWillInput = new JLabel("You will input:");
		lblYouWillInput.setBounds(197, 654, 107, 15);
		contentPane.add(lblYouWillInput);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 541, 1160, 103);
		contentPane.add(panel);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "\uB3D9\uC791 \uC778\uC2DD \uC0C1\uD669", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		lblStatus = new JLabel("\uC778\uC2DD\uC911...");
		panel_3.add(lblStatus);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("굴림", Font.BOLD, 40));
		
		int textFieldPosX = textFieldInitialX;
		int textFieldPosY;
		int sliderPosX = sliderInitialX;
		int sliderPosY;
		
		for(int i = 0; i < numOfFingers; ++i)
		{
			textFieldPosY = textFieldInitialY;
			sliderPosY = sliderInitialY;
			
			for(int ii = 0; ii < numOfPoints; ++ii)
			{
				textFieldArray[i][ii].setEditable(false);
				textFieldArray[i][ii].setHorizontalAlignment(SwingConstants.TRAILING);
				textFieldArray[i][ii].setText("0");
				textFieldArray[i][ii].setColumns(10);
				textFieldArray[i][ii].setBounds(textFieldPosX, textFieldPosY, textFieldWidth, textFieldHeight);
				panel_2.add(textFieldArray[i][ii]);
				
				sliderArray[i][ii].setMaximum(defaultMaxStrainValue);
				sliderArray[i][ii].setValue(0);
				sliderArray[i][ii].setBounds(sliderPosX, sliderPosY, sliderWidth, sliderHeight);
				sliderArray[i][ii].setFocusable(false);
				sliderArray[i][ii].setInverted(true);
				panel_2.add(sliderArray[i][ii]);
				
				textFieldPosY += textFieldIntervalY;
				sliderPosY += sliderIntervalY;
			}
			
			textFieldPosX += textFieldIntervalX;
			sliderPosX += sliderIntervalX;
		}

		init();
	}
	
	private void actionButtonAny()
	{
		currentInputGesture = initialInputGesture;
		
		lblInput.setText(btnTextAny);
	}
	
	private static void actionCorrect()
	{
		++numCorrect;
		++numInput;
		
		if(currentInputGesture > initialInputGesture)
		{
			++numCorrectGestures[currentInputGesture];
			++numInputOfGestures[currentInputGesture];
		}
		
		updateAccuracy();
	}
	
	private static void actionIncorrect()
	{
		++numInput;
		
		if(currentInputGesture > initialInputGesture)
		{
			++numInputOfGestures[currentInputGesture];
		}
		
		updateAccuracy();
	}
	
	private static void initArrays()
	{
		textFieldArray = new JTextField[][]{
			new JTextField[]{new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField()},
			new JTextField[]{new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField()},
			new JTextField[]{new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField()},
			new JTextField[]{new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField()},
			new JTextField[]{new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField()}
		};
		
		sliderArray = new JSlider[][] {
			new JSlider[]{new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider()},
			new JSlider[]{new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider()},
			new JSlider[]{new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider()},
			new JSlider[]{new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider()},
			new JSlider[]{new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider(), new JSlider()}
		};
	}
	
	/**
	 *  각종 초기화 작업
	 */
	private void init()
	{
		numInputOfGestures = new int[gestureDescription.length - 1];
		numCorrectGestures = new int[gestureDescription.length - 1];
		
		resetLabels();
		
	
		

	}
	
	/**
	 * IMU 수집 데이터(가속도/각속도) 관련 UI 표시
	 */
	private static void updateIMU(int accelX, int accelY, int accelZ, int angularX, int angularY, int angularZ)
	{
		accel_X_Text.setText(Integer.toString(accelX));
		accel_Y_Text.setText(Integer.toString(accelY));
		accel_Z_Text.setText(Integer.toString(accelZ));
		
		accel_X_Slider.setValue(accelX);
		accel_Y_Slider.setValue(accelY);
		accel_Z_Slider.setValue(accelZ);
		
		angular_X_Text.setText(Integer.toString(angularX));
		angular_Y_Text.setText(Integer.toString(angularY));
		angular_Z_Text.setText(Integer.toString(angularZ));
		
		angular_X_Slider.setValue(angularX);
		angular_Y_Slider.setValue(angularY);
		angular_Z_Slider.setValue(angularZ);
	}
	
	/**
	 * 일상생활동작 분류 UI 표시
	 * @param gestureNum 일상생활동작 번호
	 */
	private static void updateGesture(int gestureNum)
	{
		if(gestureNum >= 0 && gestureNum < gestureDescription.length)
		{
			lblStatus.setText(gestureDescription[gestureNum]);
		}
	}
	
	/**
	 * 글러브 센서 raw 데이터 UI 표시
	 */
	private static void updateRawData(int finger, int[] sensorData)
	{
		boolean checkFinger = finger >= 0 && finger < numOfFingers;
		boolean checkDataLength = sensorData != null && sensorData.length > 0 && sensorData.length < numOfPoints;
		
		if(checkFinger && checkDataLength)
		{
			int dataLength = (sensorData.length < numOfPoints)?(sensorData.length):(numOfPoints);
			
			for(int i = 0; i < dataLength; ++i)
			{
				textFieldArray[finger][i].setText(Integer.toString(sensorData[i]));
				sliderArray[finger][i].setValue(sensorData[i]);
			}
		}
	}
	private static void updateRawData(int[] sensorData, int[] order, int[] sensorNum) {
//		boolean checkFinger = finger >= 0 && finger < numOfFingers;
		boolean checkDataLength = sensorData != null && sensorData.length > 0; // && sensorData.length < numOfPoints;
		
		if(checkDataLength)
		{
//			int dataLength = (sensorData.length < numOfPoints)?(sensorData.length):(numOfPoints);
			int k=0;
			for(int i = 0; i <numOfFingers; ++i)
			{
				for(int j = 0; j < sensorNum[i]; ++j)
				{
					System.out.printf("%d %d : %s \t", i, j, Integer.toString(sensorData[k]));
					textFieldArray[i][j].setText(Integer.toString(sensorData[k]));
					sliderArray[i][j].setValue(sensorData[k]);
					k ++;
	
				}
			}
		}
		
	}
	
	private void resetLabels()
	{
		numInput = 0;
		numCorrect = 0;
		
		Arrays.fill(numInputOfGestures, 0);
		Arrays.fill(numCorrectGestures, 0);
		
		currentInputGesture = initialInputGesture;
		
		for(int i = 0; i < gestureLabels.length; ++i)
		{
			gestureLabels[i].setText(calcAccuracy(numCorrectGestures[i], numInputOfGestures[i]));
		}
		
		actionButtonAny();
		updateGesture(10);
		updateAccuracy();
	}
	
	private static void updateAccuracy()
	{
		lblNumInput.setText(Integer.toString(numInput));
		lblNumCorrect.setText(Integer.toString(numCorrect));
		
		if(currentInputGesture > initialInputGesture)
		{
			gestureLabels[currentInputGesture].setText(calcAccuracy(numCorrectGestures[currentInputGesture], numInputOfGestures[currentInputGesture]));
		}
		
		lblAccuracy.setText(calcAccuracy(numCorrect, numInput));
	}
	
	private static String calcAccuracy(int correct, int total)
	{
		int accuracyMajor = 0;
		int accuracyMinor = 0;
		
		if(total > 0)
		{			
			accuracyMajor = correct * 100 / total;
			accuracyMinor = (correct * 1000 / total) % 10;
		}
		
		return accuracyMajor + "." + accuracyMinor + percent;
	}
	
	private final static String percent = "%";
	
	private final static String gestureDescription[] = {
			"인식중...",
			"Eat with the hand (HA)",
			"Drink from a glass (GL)",
			"Eat some fruit with a fork/chopsticks from a dish (FK)", //"Eat some cut fruit with a fork from a dish (FK)", // Eat some cut fruit with a chopsticks from a dish (CS)"
			"Eat some yogurt with a spoon from a bowl (SP)",
			"Drink from a mug (CP)",
			"Answer the telephone (PH)",
			"Brush the teeth with a toothbrush (TB)",
			"Brush hair with a hairbrush (HB)",
			"Use a hair dryer (HD)",
			"글로브를 확인하세요"
	};
	
	private static int numInputOfGestures[];
	private static int numCorrectGestures[];
	
	private static int currentInputGesture;
	private final static int initialInputGesture = -1;
	
	private final String btnTextAny = "Please don't care about the gesture!";
	
	private final static int numOfPoints = 10;
	private final static int numOfFingers = 5;
	
	private final int textFieldWidth = 39;
	private final int textFieldHeight = 21;
	private final int textFieldInitialX = 90;
	private final int textFieldInitialY = 73;
	private final int textFieldIntervalX = 197;
	private final int textFieldIntervalY = 31;
	
	private final int sliderWidth = 110;
	private final int sliderHeight = 20;
	private final int sliderInitialX = 135;
	private final int sliderInitialY = 74;
	private final int sliderIntervalX = 197;
	private final int sliderIntervalY = 31;
	
	private static JSlider accel_X_Slider;
	private static JSlider accel_Y_Slider;
	private static JSlider accel_Z_Slider;
	private static JSlider angular_X_Slider;
	private static JSlider angular_Y_Slider;
	private static JSlider angular_Z_Slider;
	
	private static JLabel lblNumInput;
	private static JLabel lblNumCorrect;
	private static JLabel lblAccuracy;
	private static JLabel lblStatus;
	
	private static int numInput;
	private static int numCorrect;
	
	private static JTextField accel_X_Text;
	private static JTextField accel_Y_Text;
	private static JTextField accel_Z_Text;
	private static JTextField angular_X_Text;
	private static JTextField angular_Y_Text;
	private static JTextField angular_Z_Text;
	
	private JButton btnReset;
	private JButton btnRight;
	private JButton btnLeft;
	private JButton btnCorrect;
	private JButton btnIncorrect;
	
	private static JTextField textFieldArray[][];
	private static JSlider sliderArray[][];
	private JPanel panel_3;
	private JLabel lblHa;
	private JLabel lblPh;
	private JLabel labelHA;
	private JLabel labelPH;
	private JLabel lblGl;
	private JLabel labelGL;
	private JLabel lblTb;
	private JLabel labelTB;
	private JLabel lblFk;
	private JLabel labelFK;
	private JLabel lblHb;
	private JLabel labelHB;
	private JLabel lblSp;
	private JLabel labelSP;
	private JLabel lblHd;
	private JLabel labelHD;
	private JLabel lblCp;
	private JLabel labelCP;
	private JButton btnHa;
	private JButton btnGl;
	private JButton btnFk;
	private JButton btnSp;
	private JButton btnCp;
	private JButton btnPh;
	private JButton btnTb;
	private JButton btnHb;
	private JButton btnHd;
	private JButton btnAny;
	private JLabel lblInput;
	private JPanel panel_4;
	private JLabel lblYouWillInput;
	
	private static JLabel gestureLabels[];
	private JButton gestureButtons[];
	
	private final int defaultMaxStrainValue = 650;
	private final int defaultMaxAccelerationValue = 500;
	private final int defaultMaxAngularSpeedValue = 15000;
	private static int hand=0;
}
