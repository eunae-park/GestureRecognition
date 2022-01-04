package kr.re.keti.FingerMonitor;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.re.keti.sensorvalue.*;

/*
	raw data = 엄지 손등 ~ 엄지 손끝 & 압력 ~ 새끼 손등 ~ 새끼 손끝 & 압력
	viewer left = 새끼 손끝 ~ 새끼 손등 -> 엄지 손끝 ~ 엄지 손등		== raw data 역순
	viewer right = 엄지 손끝 ~ 엄지 손등 -> 새끼 손끝 ~ 새끼 손등 
 */
final public class FingerMonitorMain
{
	public FingerMonitorMain()
	{
		frame = new FingerMonitor();
		frame.setSliderRange(sliderMin, sliderMax, initialValue);
	}
	
	public void invoke()
	{
		frame.setCalibrationButtonAction(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				calibration ++;
				// 여기에 Calibration 버튼 동작 작성
			}
		});
	
		frame.setbtnCOMPORTAction(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				portName = frame.setcomboAction();
				serial_setting = 1;
				System.out.printf(portName + "\t" + serial_setting + "\n");
			}
		});

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void setLeftRawValue(int[][] value)
	{
		frame.setLeftRawValue(value);
	}
	
	public static void setLeftDegreeValue(int[][] value)
	{
		frame.setLeftDegreeValue(value);
	}
	
	public static void setRightRawValue(int[][] value)
	{
		frame.setRightRawValue(value);
	}
	
	public static void setRightDegreeValue(int[][] value)
	{
		frame.setRightDegreeValue(value);
	}
	
	public static void calibration_data()
	{
		int i, j, k, d;
		int time_count=0, time=40, dataL, dataR, repeat=5, repeat_count=0;

		while(repeat_count < repeat)
//		while(data_count < data_countM)
		{
			try {
				result = SensorValue.getDatas4th();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(result == null)
				continue;
			else if(result[0] == -12345.67890)
			{
//				updateGesture(10);
				continue;
			}

			if(time_count == 0)
			{
//				System.out.printf("\ngrab start\n");
				framec.set_img_text("img/rock2.png", "주먹을 쥐세요", repeat_count+1, repeat);
				framec.setVisible(true);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(time_count == time/2)
			{
//				System.out.printf("\npaper start\n");
				framec.set_img_text("img/paper2.png", "손을 펴세요", repeat_count+1, repeat);
				framec.setVisible(true);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for(k=0; k<numOfPressure; k++)
			{
//				System.out.print((int)result[k] + "\t");
				dataL = (int)result[k];
				dataR = (int)result[k+30];
				if(time_count == 0 && repeat_count == 0)
				{
					if(dataL == 0)
					{
						max_data_L[k] = 450;
						min_data_L[k] = 450;						
					}
					else
					{
						max_data_L[k] = dataL;
						min_data_L[k] = dataL;
					}
					if(dataR == 0)
					{
						max_data_R[k] = 450;
						min_data_R[k] = 450;
					}
					else
					{
						max_data_R[k] = dataR;
						min_data_R[k] = dataR;
					}
				}
				// left
				if( max_data_L[k] < dataL && dataL!=0)
					max_data_L[k] = dataL;
				if( min_data_L[k] > dataL && dataL!=0)
					min_data_L[k] = dataL;
				//right
				if( max_data_R[k] < dataR && dataR!=0)
					max_data_R[k] = dataR;
				if( min_data_R[k] > dataR && dataR!=0)
					min_data_R[k] = dataR;
			}
			time_count ++;
			if(time_count==time)
			{
				time_count = 0;
				repeat_count ++;
			}
		}
//		private static int[][] pointNumber_L = {{28, 26, 24}, {22, 20, 18}, {16, 14, 12}, {10, 8, 6}, {4, 2, 0}}; // 새끼손가락 ~ 엄지손가락 순서
//		private static int[][] pointNumber_R = {{4, 2, 0}, {10, 8, 6}, {16, 14, 12}, {22, 20, 18}, {28, 26, 24}}; // 엄지손가락 ~ 새끼손가락 순서


		for(i=0; i<5; i++)
		{
			for(j=0; j<2; j++)
			{
				pointNumber_L[i][j] = pointNumber_Li[i][j];
				pointNumber_R[i][j] = pointNumber_Ri[i][j];
				System.out.print(pointNumber_L[i][j] + "\t" + min_data_L[pointNumber_L[i][j]] + "\t" + min_data_L[pointNumber_L[i][j]-1] + "\t");// + min_data_L[pointNumber_Li[i][j]-2] + "\t");
				if(min_data_L[pointNumber_L[i][j]] > min_data_L[pointNumber_Li[i][j]-1])
					pointNumber_L[i][j] = pointNumber_Li[i][j] - 1;
				if(min_data_L[pointNumber_L[i][j]] > min_data_L[pointNumber_Li[i][j]-2] && i==4 && j==1 && setting==2) // 남성용 엄지이면
					pointNumber_L[i][j] = pointNumber_Li[i][j] - 2;
				System.out.print(pointNumber_L[i][j] + "\n");
				if(min_data_R[pointNumber_R[i][j]] > min_data_R[pointNumber_Ri[i][j]-1])
					pointNumber_R[i][j] = pointNumber_Ri[i][j] - 1;
				if(min_data_R[pointNumber_R[i][j]] > min_data_R[pointNumber_Ri[i][j]-2] && i==0 && j==1 && setting==2) // 남성용 엄지이면
					pointNumber_R[i][j] = pointNumber_Ri[i][j] - 2;
			}
			pointNumber_L[i][j] = pointNumber_Li[i][j]; // 3번째 관절
			pointNumber_R[i][j] = pointNumber_Ri[i][j];
			if(pointNumber_L[i][1]%2==0 && i!=4) // 왼손 - 엄지손가락 제외 , 두번째 관절이 짝수면 센서면 검사 
			{
//				System.out.print(pointNumber_L[i][j] + "\t" + min_data_L[pointNumber_L[i][j]] + "\t" + min_data_L[pointNumber_L[i][j]+1] + "\t");
				if(min_data_L[pointNumber_L[i][j]] > min_data_L[pointNumber_Li[i][j]+1])
					pointNumber_L[i][j] = pointNumber_Li[i][j] + 1;
//				System.out.print(pointNumber_L[i][j] + "\n");
			}
			if(pointNumber_R[i][1]%2==0 && i!=0) // 오른손 - 엄지손가락 제외 , 두번째 관절이 짝수면 센서면 검사
			{
				if(min_data_R[pointNumber_R[i][j]] > min_data_R[pointNumber_Ri[i][j]+1])
					pointNumber_R[i][j] = pointNumber_Ri[i][j] + 1;
			}
		}
			
		System.out.println();
	}
	
	public static void ReadDegreeCSV(File csvname)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvname));
			String line = "";
			int i;

			while ((line = br.readLine()) != null) {
				String[] token = line.split(",", -1);
				for(i=0;i<numOfAngleUnits;i++)
					find_degree[i] = Integer.parseInt(token[i])/10;
			}
			br.close();

		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		ReadDegreeCSV(new File("find_degree.csv"));
		File fw_l = new File("left_result.xls");
		File fw_r = new File("right_result.xls");
		FileWriter writer_l = new FileWriter(fw_l, false);
		FileWriter writer_r = new FileWriter(fw_r, false);
		String text_ls, text_ld, text_rs, text_rd;

		FingerMonitorMain monitor = new FingerMonitorMain();
		monitor.invoke();

		while (serial_setting == 0)
		{
			Thread.sleep(100);
//			System.out.println(serial_setting);
		}
		portWorker = new PortWorker(portName, baudRate);
		SensorValue = new Sensor(portWorker);
		SensorValue.openPort();
		System.out.println("serial open");
		result = SensorValue.getDatas3th();
//		double[][] sensor;

		while(result == null)
		{
			System.out.println("글러브를 확인하세요.");
			Thread.sleep(100);
			result = SensorValue.getDatas3th();
		}
		
		if(result[5] != 9999 && (result[5] > 800 || result[5] < 200)) // 센서가 망가진 거 일 수도 있고. 로딩 기다리는 목적
		{
			System.out.println("\n글로브 센서를 확인하세요.");
			System.exit(0);
		}				
		else if(result[5] == 9999) // 여성용 이면
		{
			setting = 1;
			sensorNum[0] --;
			pointNumber_L[4][0] --;
			pointNumber_L[4][1] --;
			pointNumber_R[0][0] --;
			pointNumber_R[0][1] --;
			System.out.println("\n여성용 장갑을 착용하셨습니다.");
		}
		else // 모든 센서값이 정상일 때 
		{
			setting = 2;
			System.out.println("\n남성용 장갑을 착용하셨습니다.");
		}

		int i, j, d;
		while(true)
		{
			text_ls = "";
			text_ld = "";
			text_rs = "";
			text_rd = "";
			if(calibration == 0)
			{
				result = SensorValue.getDatas4th();
				if (result == null)
					continue;
	/*
				for (i=0; i<numOfPressure*2; i++) // left, right
				{
					System.out.printf("%.0f\t", result[i]);
				}
				System.out.println("");
	*/			
				for(i=0; i<5; i++)
				{
					for(j=0; j<3; j++)
					{
						if ((int)result[pointNumber_L[i][j]] != 0)
						{
							sensor_L[i][j] = (int)result[pointNumber_L[i][j]];
							for (d=0; d<numOfAngleUnits; d++)
							{
								if(sensor_L[i][j] >= find_degree[d])
								{
									degree_L[i][j] = (AngleInterval * (d));
									break;
								}
							}
						}
						if ((int)result[pointNumber_R[i][j]+30] != 0)
						{
							sensor_R[i][j] = (int)result[pointNumber_R[i][j]+30];
							for (d=0; d<numOfAngleUnits; d++)
							{
								if(sensor_R[i][j] >= find_degree[d])
								{
									degree_R[i][j] = (AngleInterval * (d));
									break;
								}
							}
						}
						text_ls += Integer.toString(sensor_L[i][j]) + "\t";
						text_ld += Integer.toString(degree_L[i][j]) + "\t";
						text_rs += Integer.toString(sensor_R[i][j]) + "\t";
						text_rd += Integer.toString(degree_R[i][j]) + "\t";
					}
				}
				setLeftRawValue(sensor_L);
				setRightRawValue(sensor_R);
				setLeftDegreeValue(degree_L);
				setRightDegreeValue(degree_R);
//				System.out.println(text_ls);
				text_ls += "\n";
				text_ld += "\n";
				text_rs += "\n";
				text_rd += "\n";
				writer_l.write(text_ls);
				writer_l.flush();
				writer_l.write(text_ld);
				writer_l.flush();
				writer_r.write(text_rs);
				writer_r.flush();
				writer_r.write(text_rd);
				writer_r.flush();
			}
			else if (calibration == 1)
			{
				writer_l.write("calibration\n");
				writer_l.flush();
				writer_r.write("calibration\n");
				writer_r.flush();

				framec = new calibration();
				calibration_data(); 
				calibration ++;
				framec.dispose();
			}
			else if (calibration % 2 == 1)
			{
				calibration_data(); 
				calibration ++;
				framec.dispose();
			}
			else if (calibration % 2 == 0) // calibration 적용
			{
				result = SensorValue.getDatas4th();
				if (result == null)
					continue;

//				for (i=0; i<numOfPressure*2; i++) // left, right
//					System.out.printf("%.0f\t", result[i]);
//				System.out.println("");
			
				for(i=0; i<5; i++)
				{
					for(j=0; j<3; j++)
					{
						if ((int)result[pointNumber_L[i][j]] != 0)
						{
							sensor_L[i][j] = (int)result[pointNumber_L[i][j]];
							for (d=0; d<numOfAngleUnits; d++)
							{
								if(((result[pointNumber_L[i][j]]-min_data_L[pointNumber_L[i][j]])/(max_data_L[pointNumber_L[i][j]]-min_data_L[pointNumber_L[i][j]])) >= (double)((find_degree[d]-350)/150.0))
								{
									degree_L[i][j] = (AngleInterval * (d));
									break;
								}
							}
						}
						if ((int)result[pointNumber_R[i][j]+30] != 0)
						{
							sensor_R[i][j] = (int)result[pointNumber_R[i][j]+30];
							for (d=0; d<numOfAngleUnits; d++)
							{
								if(((result[pointNumber_R[i][j]+30]-min_data_R[pointNumber_R[i][j]])/(max_data_R[pointNumber_R[i][j]]-min_data_R[pointNumber_R[i][j]])) >= (double)((find_degree[d]-350)/150.0))
								{
									degree_R[i][j] = (AngleInterval * (d));
									break;
								}
							}
						}
						text_ls += Integer.toString(sensor_L[i][j]) + "\t";
						text_ld += Integer.toString(degree_L[i][j]) + "\t";
						text_rs += Integer.toString(sensor_R[i][j]) + "\t";
						text_rd += Integer.toString(degree_R[i][j]) + "\t";
					}
				}
				setLeftRawValue(sensor_L);
				setRightRawValue(sensor_R);
				setLeftDegreeValue(degree_L);
				setRightDegreeValue(degree_R);
				text_ls += "\n";
				text_ld += "\n";
				text_rs += "\n";
				text_rd += "\n";
				writer_l.write(text_ls);
				writer_l.flush();
				writer_l.write(text_ld);
				writer_l.flush();
				writer_r.write(text_rs);
				writer_r.flush();
				writer_r.write(text_rd);
				writer_r.flush();
			}
		}
		
//		SensorValue.closePort();
	}
	
	private static FingerMonitor frame;
	private static calibration framec;
	
	private final int sliderMin = 300;
	private final int sliderMax = 600;
	private final int initialValue = 500;
	
	private final static int numOfAngleUnits = 25;		//edited	
	private final static int AngleInterval = 5;		//edited
	private final static int find_degree[] = new int[numOfAngleUnits];
	
	final static int baudRate = 921600;
	static String portName = "COM6"; //"COM7" "COM6" - notebook 
	static Port portWorker = null;// = new PortWorker(portName, baudRate);
	static Sensor SensorValue = null;// = new Sensor(portWorker);
	static int serial_setting = 0;
	
	private static int setting = -1;
	private static int numOfPressure = 30;
	private static int[] sensorNum = {6, 6, 6, 6, 6}; // 남성용 - 6, 여성용 - 5
	private static int[][] pointNumber_L = {{28, 26, 24}, {22, 20, 18}, {16, 14, 12}, {10, 8, 6}, {4, 2, 0}}; // 새끼손가락 ~ 엄지손가락 순서
	private static int[][] pointNumber_R = {{4, 2, 0}, {10, 8, 6}, {16, 14, 12}, {22, 20, 18}, {28, 26, 24}}; // 엄지손가락 ~ 새끼손가락 순서
	private static int[][] pointNumber_Li = {{28, 26, 24}, {22, 20, 18}, {16, 14, 12}, {10, 8, 6}, {4, 2, 0}}; // 새끼손가락 ~ 엄지손가락 순서
	private static int[][] pointNumber_Ri = {{4, 2, 0}, {10, 8, 6}, {16, 14, 12}, {22, 20, 18}, {28, 26, 24}}; // 엄지손가락 ~ 새끼손가락 순서
	private static int calibration = 0;
	private static int[][] sensor_L= new int[5][3];
	private static int[][] sensor_R= new int[5][3];
	private static int[][] degree_L= new int[5][3];
	private static int[][] degree_R= new int[5][3];

	private static double[] max_data_L = new double [numOfPressure];
	private static double[] max_data_R = new double [numOfPressure];
	private static double[] min_data_L = new double [numOfPressure];
	private static double[] min_data_R = new double [numOfPressure];
	private static double[] result;
}
