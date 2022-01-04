package kr.re.keti.FingerMonitor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.SpringLayout;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.FlowLayout;

import javax.swing.JButton;

import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JSlider;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;

import com.fazecast.jSerialComm.SerialPort;	//eunae

public class FingerMonitor extends JFrame
{
	private JComboBox comboBox;	//eunae
	private JButton btnCOMPORT;
	private String[] portList;
	private SerialPort[] portObj;
	private int selectedPortIndex;
	
	private JPanel panel; //joo
	private JPanel panelLeftHand;
	private JPanel panelRightHand;
	private JPanel buttonPanel;
	private JButton btnCalibration;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField tf_L_Raw_1_1;
	private JTextField tf_L_Degree_1_1;
	private JTextField tf_L_Raw_1_2;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField tf_L_Degree_1_2;
	private JTextField tf_L_Raw_1_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JTextField tf_L_Degree_1_3;
	private JSlider slider_L_1_1;
	private JSlider slider_L_1_2;
	private JSlider slider_L_1_3;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JTextField tf_L_Raw_2_1;
	private JTextField tf_L_Degree_2_1;
	private JTextField tf_L_Raw_2_2;
	private JLabel lblNewLabel_8;
	private JLabel lblNewLabel_9;
	private JTextField tf_L_Degree_2_2;
	private JTextField tf_L_Raw_2_3;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JTextField tf_L_Degree_2_3;
	private JSlider slider_L_2_1;
	private JSlider slider_L_2_2;
	private JSlider slider_L_2_3;
	private JLabel lblNewLabel_12;
	private JLabel lblNewLabel_13;
	private JTextField tf_L_Raw_3_1;
	private JTextField tf_L_Degree_3_1;
	private JTextField tf_L_Raw_3_2;
	private JLabel lblNewLabel_14;
	private JLabel lblNewLabel_15;
	private JTextField tf_L_Degree_3_2;
	private JTextField tf_L_Raw_3_3;
	private JLabel lblNewLabel_16;
	private JLabel lblNewLabel_17;
	private JTextField tf_L_Degree_3_3;
	private JSlider slider_L_3_1;
	private JSlider slider_L_3_2;
	private JSlider slider_L_3_3;
	private JLabel lblNewLabel_18;
	private JLabel lblNewLabel_19;
	private JTextField tf_L_Raw_4_1;
	private JTextField tf_L_Degree_4_1;
	private JTextField tf_L_Raw_4_2;
	private JLabel lblNewLabel_20;
	private JLabel lblNewLabel_21;
	private JTextField tf_L_Degree_4_2;
	private JTextField tf_L_Raw_4_3;
	private JLabel lblNewLabel_22;
	private JLabel lblNewLabel_23;
	private JTextField tf_L_Degree_4_3;
	private JSlider slider_L_4_1;
	private JSlider slider_L_4_2;
	private JSlider slider_L_4_3;
	private JTextField tf_L_Raw_5_1;
	private JLabel lblNewLabel_24;
	private JLabel lblNewLabel_25;
	private JTextField tf_L_Degree_5_1;
	private JTextField tf_L_Raw_5_2;
	private JLabel lblNewLabel_26;
	private JLabel lblNewLabel_27;
	private JTextField tf_L_Degree_5_2;
	private JSlider slider_L_5_1;
	private JSlider slider_L_5_2;
	private JLabel lblNewLabel_28;
	private JLabel lblNewLabel_29;
	private JTextField tf_R_Raw_2_1;
	private JTextField tf_R_Degree_2_1;
	private JTextField tf_R_Raw_2_2;
	private JLabel lblNewLabel_30;
	private JLabel lblNewLabel_31;
	private JTextField tf_R_Degree_2_2;
	private JTextField tf_R_Raw_2_3;
	private JLabel lblNewLabel_32;
	private JLabel lblNewLabel_33;
	private JTextField tf_R_Degree_2_3;
	private JSlider slider_R_2_1;
	private JSlider slider_R_2_2;
	private JSlider slider_R_2_3;
	private JLabel lblNewLabel_34;
	private JLabel lblNewLabel_35;
	private JTextField tf_R_Raw_3_1;
	private JTextField tf_R_Degree_3_1;
	private JTextField tf_R_Raw_3_2;
	private JLabel lblNewLabel_36;
	private JLabel lblNewLabel_37;
	private JTextField tf_R_Degree_3_2;
	private JTextField tf_R_Raw_3_3;
	private JLabel lblNewLabel_38;
	private JLabel lblNewLabel_39;
	private JTextField tf_R_Degree_3_3;
	private JSlider slider_R_3_1;
	private JSlider slider_R_3_2;
	private JSlider slider_R_3_3;
	private JLabel lblNewLabel_40;
	private JLabel lblNewLabel_41;
	private JTextField tf_R_Raw_4_1;
	private JTextField tf_R_Degree_4_1;
	private JTextField tf_R_Raw_4_2;
	private JLabel lblNewLabel_42;
	private JLabel lblNewLabel_43;
	private JTextField tf_R_Degree_4_2;
	private JTextField tf_R_Raw_4_3;
	private JLabel lblNewLabel_44;
	private JLabel lblNewLabel_45;
	private JTextField tf_R_Degree_4_3;
	private JSlider slider_R_4_1;
	private JSlider slider_R_4_2;
	private JSlider slider_R_4_3;
	private JLabel lblNewLabel_46;
	private JLabel lblNewLabel_47;
	private JTextField tf_R_Raw_5_1;
	private JTextField tf_R_Degree_5_1;
	private JTextField tf_R_Raw_5_2;
	private JLabel lblNewLabel_48;
	private JLabel lblNewLabel_49;
	private JTextField tf_R_Degree_5_2;
	private JTextField tf_R_Raw_5_3;
	private JLabel lblNewLabel_50;
	private JLabel lblNewLabel_51;
	private JTextField tf_R_Degree_5_3;
	private JSlider slider_R_5_1;
	private JSlider slider_R_5_2;
	private JSlider slider_R_5_3;
	private JTextField tf_R_Raw_1_1;
	private JLabel lblNewLabel_52;
	private JLabel lblNewLabel_53;
	private JTextField tf_R_Degree_1_1;
	private JTextField tf_R_Raw_1_2;
	private JLabel lblNewLabel_54;
	private JLabel lblNewLabel_55;
	private JTextField tf_R_Degree_1_2;
	private JSlider slider_R_1_1;
	private JSlider slider_R_1_2;
	private JLabel lblNewLabel_56;
	private JLabel lblNewLabel_57;
	private JLabel lblNewLabel_58;
	private JLabel lblNewLabel_59;
	private JLabel lblNewLabel_60;
	private JLabel lblNewLabel_61;
	private JLabel lblNewLabel_62;
	private JLabel lblNewLabel_63;
	private JLabel lblNewLabel_64;
	private JLabel lblNewLabel_65;
	
	private final int numOfFinger = 5;
	private final int numOfJoint = 3;
	private final int numOfThumbJoint = 2;
	
	private final int leftLittle = 0;
	private final int leftRing = 1;
	private final int leftMiddle = 2;
	private final int leftIndex = 3;
	private final int leftThumb = 4;
	
	private final int rightLittle = 4;
	private final int rightRing = 3;
	private final int rightMiddle = 2;
	private final int rightIndex = 1;
	private final int rightThumb = 0;
	
	private final int highestJoint = 0;
	private final int middleJoint = 1;
	private final int lowestJoint = 2;
	
//	private final int initialSliderValue = 0;
	
	private JSlider[][] sliderLeft;
	private JTextField[][] tf_Raw_Left;
	private JTextField[][] tf_Degree_Left;
	
	private JSlider[][] sliderRight;
	private JTextField[][] tf_Raw_Right;
	private JTextField[][] tf_Degree_Right;
	
	private void initPorts() {
		portObj = SerialPort.getCommPorts();

		int numOfPorts = portObj.length;

		portList = new String[numOfPorts];

		for (int i = 0; i < numOfPorts; ++i) {
			portList[i] = portObj[i].getSystemPortName();
			comboBox.addItem(portList[i]);
		}
	}

	private void initComponents()
	{
		sliderLeft = new JSlider[numOfFinger][numOfJoint];
		sliderRight = new JSlider[numOfFinger][numOfJoint];
		
		tf_Raw_Left = new JTextField[numOfFinger][numOfJoint];
		tf_Degree_Left = new JTextField[numOfFinger][numOfJoint];
		
		tf_Raw_Right = new JTextField[numOfFinger][numOfJoint];
		tf_Degree_Right = new JTextField[numOfFinger][numOfJoint];
		
		sliderLeft[leftLittle][highestJoint] = slider_L_1_1;
		sliderLeft[leftLittle][middleJoint] = slider_L_1_2;
		sliderLeft[leftLittle][lowestJoint] = slider_L_1_3;
		
		sliderLeft[leftRing][highestJoint] = slider_L_2_1;
		sliderLeft[leftRing][middleJoint] = slider_L_2_2;
		sliderLeft[leftRing][lowestJoint] = slider_L_2_3;
		
		sliderLeft[leftMiddle][highestJoint] = slider_L_3_1;
		sliderLeft[leftMiddle][middleJoint] = slider_L_3_2;
		sliderLeft[leftMiddle][lowestJoint] = slider_L_3_3;
		
		sliderLeft[leftIndex][highestJoint] = slider_L_4_1;
		sliderLeft[leftIndex][middleJoint] = slider_L_4_2;
		sliderLeft[leftIndex][lowestJoint] = slider_L_4_3;
		
		sliderLeft[leftThumb][highestJoint] = slider_L_5_1;
		sliderLeft[leftThumb][middleJoint] = slider_L_5_2;
		sliderLeft[leftThumb][lowestJoint] = null;
		
		tf_Raw_Left[leftLittle][highestJoint] = tf_L_Raw_1_1;
		tf_Raw_Left[leftLittle][middleJoint] = tf_L_Raw_1_2;
		tf_Raw_Left[leftLittle][lowestJoint] = tf_L_Raw_1_3;
		
		tf_Raw_Left[leftRing][highestJoint] = tf_L_Raw_2_1;
		tf_Raw_Left[leftRing][middleJoint] = tf_L_Raw_2_2;
		tf_Raw_Left[leftRing][lowestJoint] = tf_L_Raw_2_3;
		
		tf_Raw_Left[leftMiddle][highestJoint] = tf_L_Raw_3_1;
		tf_Raw_Left[leftMiddle][middleJoint] = tf_L_Raw_3_2;
		tf_Raw_Left[leftMiddle][lowestJoint] = tf_L_Raw_3_3;
		
		tf_Raw_Left[leftIndex][highestJoint] = tf_L_Raw_4_1;
		tf_Raw_Left[leftIndex][middleJoint] = tf_L_Raw_4_2;
		tf_Raw_Left[leftIndex][lowestJoint] = tf_L_Raw_4_3;
		
		tf_Raw_Left[leftThumb][highestJoint] = tf_L_Raw_5_1;
		tf_Raw_Left[leftThumb][middleJoint] = tf_L_Raw_5_2;
		tf_Raw_Left[leftThumb][lowestJoint] = null;
		
		tf_Degree_Left[leftLittle][highestJoint] = tf_L_Degree_1_1;
		tf_Degree_Left[leftLittle][middleJoint] = tf_L_Degree_1_2;
		tf_Degree_Left[leftLittle][lowestJoint] = tf_L_Degree_1_3;
		
		tf_Degree_Left[leftRing][highestJoint] = tf_L_Degree_2_1;
		tf_Degree_Left[leftRing][middleJoint] = tf_L_Degree_2_2;
		tf_Degree_Left[leftRing][lowestJoint] = tf_L_Degree_2_3;
		
		tf_Degree_Left[leftMiddle][highestJoint] = tf_L_Degree_3_1;
		tf_Degree_Left[leftMiddle][middleJoint] = tf_L_Degree_3_2;
		tf_Degree_Left[leftMiddle][lowestJoint] = tf_L_Degree_3_3;
		
		tf_Degree_Left[leftIndex][highestJoint] = tf_L_Degree_4_1;
		tf_Degree_Left[leftIndex][middleJoint] = tf_L_Degree_4_2;
		tf_Degree_Left[leftIndex][lowestJoint] = tf_L_Degree_4_3;
		
		tf_Degree_Left[leftThumb][highestJoint] = tf_L_Degree_5_1;
		tf_Degree_Left[leftThumb][middleJoint] = tf_L_Degree_5_2;
		tf_Degree_Left[leftThumb][lowestJoint] = null;
		
		sliderRight[rightLittle][highestJoint] = slider_R_5_1;
		sliderRight[rightLittle][middleJoint] = slider_R_5_2;
		sliderRight[rightLittle][lowestJoint] = slider_R_5_3;
		
		sliderRight[rightRing][highestJoint] = slider_R_4_1;
		sliderRight[rightRing][middleJoint] = slider_R_4_2;
		sliderRight[rightRing][lowestJoint] = slider_R_4_3;
		
		sliderRight[rightMiddle][highestJoint] = slider_R_3_1;
		sliderRight[rightMiddle][middleJoint] = slider_R_3_2;
		sliderRight[rightMiddle][lowestJoint] = slider_R_3_3;
		
		sliderRight[rightIndex][highestJoint] = slider_R_2_1;
		sliderRight[rightIndex][middleJoint] = slider_R_2_2;
		sliderRight[rightIndex][lowestJoint] = slider_R_2_3;
		
		sliderRight[rightThumb][highestJoint] = slider_R_1_1;
		sliderRight[rightThumb][middleJoint] = slider_R_1_2;
		sliderRight[rightThumb][lowestJoint] = null;
		
		tf_Raw_Right[rightLittle][highestJoint] = tf_R_Raw_5_1;
		tf_Raw_Right[rightLittle][middleJoint] = tf_R_Raw_5_2;
		tf_Raw_Right[rightLittle][lowestJoint] = tf_R_Raw_5_3;
		
		tf_Raw_Right[rightRing][highestJoint] = tf_R_Raw_4_1;
		tf_Raw_Right[rightRing][middleJoint] = tf_R_Raw_4_2;
		tf_Raw_Right[rightRing][lowestJoint] = tf_R_Raw_4_3;
		
		tf_Raw_Right[rightMiddle][highestJoint] = tf_R_Raw_3_1;
		tf_Raw_Right[rightMiddle][middleJoint] = tf_R_Raw_3_2;
		tf_Raw_Right[rightMiddle][lowestJoint] = tf_R_Raw_3_3;
		
		tf_Raw_Right[rightIndex][highestJoint] = tf_R_Raw_2_1;
		tf_Raw_Right[rightIndex][middleJoint] = tf_R_Raw_2_2;
		tf_Raw_Right[rightIndex][lowestJoint] = tf_R_Raw_2_3;
		
		tf_Raw_Right[rightThumb][highestJoint] = tf_R_Raw_1_1;
		tf_Raw_Right[rightThumb][middleJoint] = tf_R_Raw_1_2;
		tf_Raw_Right[rightThumb][lowestJoint] = null;
		
		tf_Degree_Right[rightLittle][highestJoint] = tf_R_Degree_5_1;
		tf_Degree_Right[rightLittle][middleJoint] = tf_R_Degree_5_2;
		tf_Degree_Right[rightLittle][lowestJoint] = tf_R_Degree_5_3;
		
		tf_Degree_Right[rightRing][highestJoint] = tf_R_Degree_4_1;
		tf_Degree_Right[rightRing][middleJoint] = tf_R_Degree_4_2;
		tf_Degree_Right[rightRing][lowestJoint] = tf_R_Degree_4_3;
		
		tf_Degree_Right[rightMiddle][highestJoint] = tf_R_Degree_3_1;
		tf_Degree_Right[rightMiddle][middleJoint] = tf_R_Degree_3_2;
		tf_Degree_Right[rightMiddle][lowestJoint] = tf_R_Degree_3_3;
		
		tf_Degree_Right[rightIndex][highestJoint] = tf_R_Degree_2_1;
		tf_Degree_Right[rightIndex][middleJoint] = tf_R_Degree_2_2;
		tf_Degree_Right[rightIndex][lowestJoint] = tf_R_Degree_2_3;
		
		tf_Degree_Right[rightThumb][highestJoint] = tf_R_Degree_1_1;
		tf_Degree_Right[rightThumb][middleJoint] = tf_R_Degree_1_2;
		tf_Degree_Right[rightThumb][lowestJoint] = null;
	}
	
	public void setLeftRawValue(int[][] value)
	{
		if(value != null)
		{
			int currentValue;
			
			for(int fingerNum = leftLittle; fingerNum <= leftThumb; ++fingerNum)
			{
				for(int jointNum = highestJoint; jointNum <= lowestJoint; ++jointNum)
				{
					if(tf_Raw_Left[fingerNum][jointNum] != null)
					{
						currentValue = value[fingerNum][jointNum];
						
						tf_Raw_Left[fingerNum][jointNum].setText(Integer.toString(currentValue));
						
						sliderLeft[fingerNum][jointNum].setValue(currentValue);;
					}
				}
			}
		}
	}
	
	public void setRightRawValue(int[][] value)
	{
		if(value != null)
		{
			int currentValue;
			
			for(int fingerNum = rightThumb; fingerNum <= rightLittle; ++fingerNum)
			{
				for(int jointNum = highestJoint; jointNum <= lowestJoint; ++jointNum)
				{
					if(tf_Raw_Right[fingerNum][jointNum] != null)
					{
						currentValue = value[fingerNum][jointNum];
						
						tf_Raw_Right[fingerNum][jointNum].setText(Integer.toString(currentValue));
						
						sliderRight[fingerNum][jointNum].setValue(currentValue);
					}
				}
			}
		}
	}
	
	public void setLeftDegreeValue(int[][] value)
	{
		if(value != null)
		{
			int currentValue;
			
			for(int fingerNum = leftLittle; fingerNum <= leftThumb; ++fingerNum)
			{
				for(int jointNum = highestJoint; jointNum <= lowestJoint; ++jointNum)
				{
					if(tf_Degree_Left[fingerNum][jointNum] != null)
					{
						currentValue = value[fingerNum][jointNum];
						
						tf_Degree_Left[fingerNum][jointNum].setText(Integer.toString(currentValue));
					}
				}
			}
		}
	}
	
	public void setRightDegreeValue(int[][] value)
	{
		if(value != null)
		{
			int currentValue;
			
			for(int fingerNum = rightThumb; fingerNum <= rightLittle; ++fingerNum)
			{
				for(int jointNum = highestJoint; jointNum <= lowestJoint; ++jointNum)
				{
					if(tf_Degree_Right[fingerNum][jointNum] != null)
					{
						currentValue = value[fingerNum][jointNum];
						
						tf_Degree_Right[fingerNum][jointNum].setText(Integer.toString(currentValue));
					}
				}
			}
		}
	}
	
	public void setSliderRange(int min, int max, int initialValue)
	{
		JSlider currentSlider;
		
		for(int fingerNum = leftLittle; fingerNum <= leftThumb; ++fingerNum)
		{
			for(int jointNum = highestJoint; jointNum <= lowestJoint; ++jointNum)
			{
				currentSlider = sliderLeft[fingerNum][jointNum];
				
				if(currentSlider != null)
				{
					currentSlider.setMaximum(max);
					currentSlider.setMinimum(min);
//					currentSlider.setValue(initialSliderValue);
					currentSlider.setValue(initialValue);
				}
			}
		}
		
		for(int fingerNum = rightThumb; fingerNum <= rightLittle; ++fingerNum)
		{
			for(int jointNum = highestJoint; jointNum <= lowestJoint; ++jointNum)
			{
				currentSlider = sliderRight[fingerNum][jointNum];
				
				if(currentSlider != null)
				{
					currentSlider.setMaximum(max);
					currentSlider.setMinimum(min);
//					currentSlider.setValue(initialSliderValue);
					currentSlider.setValue(initialValue);
				}
			}
		}
		
		/*
		slider_L_1_1.setMinimum(min);
		slider_L_1_1.setMaximum(max);
		slider_L_1_2.setMinimum(min);
		slider_L_1_2.setMaximum(max);
		slider_L_1_3.setMinimum(min);
		slider_L_1_3.setMaximum(max);
		
		slider_L_2_1.setMinimum(min);
		slider_L_2_1.setMaximum(max);
		slider_L_2_2.setMinimum(min);
		slider_L_2_2.setMaximum(max);
		slider_L_2_3.setMinimum(min);
		slider_L_2_3.setMaximum(max);
		
		slider_L_3_1.setMinimum(min);
		slider_L_3_1.setMaximum(max);
		slider_L_3_2.setMinimum(min);
		slider_L_3_2.setMaximum(max);
		slider_L_3_3.setMinimum(min);
		slider_L_3_3.setMaximum(max);
		
		slider_L_4_1.setMinimum(min);
		slider_L_4_1.setMaximum(max);
		slider_L_4_2.setMinimum(min);
		slider_L_4_2.setMaximum(max);
		slider_L_4_3.setMinimum(min);
		slider_L_4_3.setMaximum(max);
		
		slider_L_5_1.setMinimum(min);
		slider_L_5_1.setMaximum(max);
		slider_L_5_2.setMinimum(min);
		slider_L_5_2.setMaximum(max);
		
		slider_R_1_1.setMinimum(min);
		slider_R_1_1.setMaximum(max);
		slider_R_1_2.setMinimum(min);
		slider_R_1_2.setMaximum(max);
		
		slider_R_2_1.setMinimum(min);
		slider_R_2_1.setMaximum(max);
		slider_R_2_2.setMinimum(min);
		slider_R_2_2.setMaximum(max);
		slider_R_2_3.setMinimum(min);
		slider_R_2_3.setMaximum(max);
		
		slider_R_3_1.setMinimum(min);
		slider_R_3_1.setMaximum(max);
		slider_R_3_2.setMinimum(min);
		slider_R_3_2.setMaximum(max);
		slider_R_3_3.setMinimum(min);
		slider_R_3_3.setMaximum(max);
		
		slider_R_4_1.setMinimum(min);
		slider_R_4_1.setMaximum(max);
		slider_R_4_2.setMinimum(min);
		slider_R_4_2.setMaximum(max);
		slider_R_4_3.setMinimum(min);
		slider_R_4_3.setMaximum(max);
		
		slider_R_5_1.setMinimum(min);
		slider_R_5_1.setMaximum(max);
		slider_R_5_2.setMinimum(min);
		slider_R_5_2.setMaximum(max);
		slider_R_5_3.setMinimum(min);
		slider_R_5_3.setMaximum(max);
		*/
	}
	
	public void setCalibrationButtonAction(ActionListener listener)
	{
		btnCalibration.addActionListener(listener);
		
	}

	public void setbtnCOMPORTAction(ActionListener listener)
	{
		btnCOMPORT.addActionListener(listener);
	}
	public String setcomboAction()
	{
		return portList[selectedPortIndex];
	}
	/**
	 * Create the frame.
	 */
	public FingerMonitor()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{}
		
		setResizable(false);
		setTitle("FingerMonitor");
		setBounds(100, 100, 1270, 610);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		panelLeftHand = new JPanel();
		panelLeftHand.setBorder(new TitledBorder(null, "Left", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelLeftHand.setPreferredSize(new Dimension(627, 500));
		panel.add(panelLeftHand, BorderLayout.WEST);
		panelLeftHand.setLayout(null);
		
		lblNewLabel = new JLabel("Raw");
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel.setBounds(31, 77, 44, 15);
		panelLeftHand.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Degree");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(31, 105, 44, 15);
		panelLeftHand.add(lblNewLabel_1);
		
		tf_L_Raw_1_1 = new JTextField();
		tf_L_Raw_1_1.setEditable(false);
		tf_L_Raw_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_1_1.setText("0");
		tf_L_Raw_1_1.setBounds(82, 74, 53, 21);
		panelLeftHand.add(tf_L_Raw_1_1);
		tf_L_Raw_1_1.setColumns(10);
		
		tf_L_Degree_1_1 = new JTextField();
		tf_L_Degree_1_1.setEditable(false);
		tf_L_Degree_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_1_1.setText("0");
		tf_L_Degree_1_1.setBounds(82, 102, 53, 21);
		panelLeftHand.add(tf_L_Degree_1_1);
		tf_L_Degree_1_1.setColumns(10);
		
		tf_L_Raw_1_2 = new JTextField();
		tf_L_Raw_1_2.setEditable(false);
		tf_L_Raw_1_2.setText("0");
		tf_L_Raw_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_1_2.setColumns(10);
		tf_L_Raw_1_2.setBounds(82, 232, 53, 21);
		panelLeftHand.add(tf_L_Raw_1_2);
		
		lblNewLabel_2 = new JLabel("Raw");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_2.setBounds(31, 235, 44, 15);
		panelLeftHand.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Degree");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_3.setBounds(31, 263, 44, 15);
		panelLeftHand.add(lblNewLabel_3);
		
		tf_L_Degree_1_2 = new JTextField();
		tf_L_Degree_1_2.setEditable(false);
		tf_L_Degree_1_2.setText("0");
		tf_L_Degree_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_1_2.setColumns(10);
		tf_L_Degree_1_2.setBounds(82, 260, 53, 21);
		panelLeftHand.add(tf_L_Degree_1_2);
		
		tf_L_Raw_1_3 = new JTextField();
		tf_L_Raw_1_3.setEditable(false);
		tf_L_Raw_1_3.setText("0");
		tf_L_Raw_1_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_1_3.setColumns(10);
		tf_L_Raw_1_3.setBounds(82, 384, 53, 21);
		panelLeftHand.add(tf_L_Raw_1_3);
		
		lblNewLabel_4 = new JLabel("Raw");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_4.setBounds(31, 387, 44, 15);
		panelLeftHand.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Degree");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_5.setBounds(31, 415, 44, 15);
		panelLeftHand.add(lblNewLabel_5);
		
		tf_L_Degree_1_3 = new JTextField();
		tf_L_Degree_1_3.setEditable(false);
		tf_L_Degree_1_3.setText("0");
		tf_L_Degree_1_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_1_3.setColumns(10);
		tf_L_Degree_1_3.setBounds(82, 412, 53, 21);
		panelLeftHand.add(tf_L_Degree_1_3);
		
		slider_L_1_1 = new JSlider();
		slider_L_1_1.setFocusTraversalKeysEnabled(false);
		slider_L_1_1.setFocusable(false);
		slider_L_1_1.setBounds(41, 130, 94, 26);
		panelLeftHand.add(slider_L_1_1);
		
		slider_L_1_2 = new JSlider();
		slider_L_1_2.setFocusTraversalKeysEnabled(false);
		slider_L_1_2.setFocusable(false);
		slider_L_1_2.setBounds(41, 288, 94, 26);
		panelLeftHand.add(slider_L_1_2);
		
		slider_L_1_3 = new JSlider();
		slider_L_1_3.setRequestFocusEnabled(false);
		slider_L_1_3.setFocusTraversalKeysEnabled(false);
		slider_L_1_3.setFocusable(false);
		slider_L_1_3.setBounds(41, 440, 94, 26);
		panelLeftHand.add(slider_L_1_3);
		
		lblNewLabel_6 = new JLabel("Raw");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_6.setBounds(147, 77, 44, 15);
		panelLeftHand.add(lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel("Degree");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_7.setBounds(147, 105, 44, 15);
		panelLeftHand.add(lblNewLabel_7);
		
		tf_L_Raw_2_1 = new JTextField();
		tf_L_Raw_2_1.setEditable(false);
		tf_L_Raw_2_1.setText("0");
		tf_L_Raw_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_2_1.setColumns(10);
		tf_L_Raw_2_1.setBounds(198, 74, 53, 21);
		panelLeftHand.add(tf_L_Raw_2_1);
		
		tf_L_Degree_2_1 = new JTextField();
		tf_L_Degree_2_1.setEditable(false);
		tf_L_Degree_2_1.setText("0");
		tf_L_Degree_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_2_1.setColumns(10);
		tf_L_Degree_2_1.setBounds(198, 102, 53, 21);
		panelLeftHand.add(tf_L_Degree_2_1);
		
		tf_L_Raw_2_2 = new JTextField();
		tf_L_Raw_2_2.setEditable(false);
		tf_L_Raw_2_2.setText("0");
		tf_L_Raw_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_2_2.setColumns(10);
		tf_L_Raw_2_2.setBounds(198, 232, 53, 21);
		panelLeftHand.add(tf_L_Raw_2_2);
		
		lblNewLabel_8 = new JLabel("Raw");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_8.setBounds(147, 235, 44, 15);
		panelLeftHand.add(lblNewLabel_8);
		
		lblNewLabel_9 = new JLabel("Degree");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_9.setBounds(147, 263, 44, 15);
		panelLeftHand.add(lblNewLabel_9);
		
		tf_L_Degree_2_2 = new JTextField();
		tf_L_Degree_2_2.setEditable(false);
		tf_L_Degree_2_2.setText("0");
		tf_L_Degree_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_2_2.setColumns(10);
		tf_L_Degree_2_2.setBounds(198, 260, 53, 21);
		panelLeftHand.add(tf_L_Degree_2_2);
		
		tf_L_Raw_2_3 = new JTextField();
		tf_L_Raw_2_3.setEditable(false);
		tf_L_Raw_2_3.setText("0");
		tf_L_Raw_2_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_2_3.setColumns(10);
		tf_L_Raw_2_3.setBounds(198, 384, 53, 21);
		panelLeftHand.add(tf_L_Raw_2_3);
		
		lblNewLabel_10 = new JLabel("Raw");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_10.setBounds(147, 387, 44, 15);
		panelLeftHand.add(lblNewLabel_10);
		
		lblNewLabel_11 = new JLabel("Degree");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_11.setBounds(147, 415, 44, 15);
		panelLeftHand.add(lblNewLabel_11);
		
		tf_L_Degree_2_3 = new JTextField();
		tf_L_Degree_2_3.setEditable(false);
		tf_L_Degree_2_3.setText("0");
		tf_L_Degree_2_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_2_3.setColumns(10);
		tf_L_Degree_2_3.setBounds(198, 412, 53, 21);
		panelLeftHand.add(tf_L_Degree_2_3);
		
		slider_L_2_1 = new JSlider();
		slider_L_2_1.setFocusTraversalKeysEnabled(false);
		slider_L_2_1.setFocusable(false);
		slider_L_2_1.setBounds(157, 130, 94, 26);
		panelLeftHand.add(slider_L_2_1);
		
		slider_L_2_2 = new JSlider();
		slider_L_2_2.setFocusTraversalKeysEnabled(false);
		slider_L_2_2.setFocusable(false);
		slider_L_2_2.setBounds(157, 288, 94, 26);
		panelLeftHand.add(slider_L_2_2);
		
		slider_L_2_3 = new JSlider();
		slider_L_2_3.setFocusTraversalKeysEnabled(false);
		slider_L_2_3.setFocusable(false);
		slider_L_2_3.setBounds(157, 440, 94, 26);
		panelLeftHand.add(slider_L_2_3);
		
		lblNewLabel_12 = new JLabel("Raw");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_12.setBounds(263, 77, 44, 15);
		panelLeftHand.add(lblNewLabel_12);
		
		lblNewLabel_13 = new JLabel("Degree");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_13.setBounds(263, 105, 44, 15);
		panelLeftHand.add(lblNewLabel_13);
		
		tf_L_Raw_3_1 = new JTextField();
		tf_L_Raw_3_1.setEditable(false);
		tf_L_Raw_3_1.setText("0");
		tf_L_Raw_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_3_1.setColumns(10);
		tf_L_Raw_3_1.setBounds(314, 74, 53, 21);
		panelLeftHand.add(tf_L_Raw_3_1);
		
		tf_L_Degree_3_1 = new JTextField();
		tf_L_Degree_3_1.setEditable(false);
		tf_L_Degree_3_1.setText("0");
		tf_L_Degree_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_3_1.setColumns(10);
		tf_L_Degree_3_1.setBounds(314, 102, 53, 21);
		panelLeftHand.add(tf_L_Degree_3_1);
		
		tf_L_Raw_3_2 = new JTextField();
		tf_L_Raw_3_2.setEditable(false);
		tf_L_Raw_3_2.setText("0");
		tf_L_Raw_3_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_3_2.setColumns(10);
		tf_L_Raw_3_2.setBounds(314, 232, 53, 21);
		panelLeftHand.add(tf_L_Raw_3_2);
		
		lblNewLabel_14 = new JLabel("Raw");
		lblNewLabel_14.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_14.setBounds(263, 235, 44, 15);
		panelLeftHand.add(lblNewLabel_14);
		
		lblNewLabel_15 = new JLabel("Degree");
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_15.setBounds(263, 263, 44, 15);
		panelLeftHand.add(lblNewLabel_15);
		
		tf_L_Degree_3_2 = new JTextField();
		tf_L_Degree_3_2.setEditable(false);
		tf_L_Degree_3_2.setText("0");
		tf_L_Degree_3_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_3_2.setColumns(10);
		tf_L_Degree_3_2.setBounds(314, 260, 53, 21);
		panelLeftHand.add(tf_L_Degree_3_2);
		
		tf_L_Raw_3_3 = new JTextField();
		tf_L_Raw_3_3.setEditable(false);
		tf_L_Raw_3_3.setText("0");
		tf_L_Raw_3_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_3_3.setColumns(10);
		tf_L_Raw_3_3.setBounds(314, 384, 53, 21);
		panelLeftHand.add(tf_L_Raw_3_3);
		
		lblNewLabel_16 = new JLabel("Raw");
		lblNewLabel_16.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_16.setBounds(263, 387, 44, 15);
		panelLeftHand.add(lblNewLabel_16);
		
		lblNewLabel_17 = new JLabel("Degree");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_17.setBounds(263, 415, 44, 15);
		panelLeftHand.add(lblNewLabel_17);
		
		tf_L_Degree_3_3 = new JTextField();
		tf_L_Degree_3_3.setEditable(false);
		tf_L_Degree_3_3.setText("0");
		tf_L_Degree_3_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_3_3.setColumns(10);
		tf_L_Degree_3_3.setBounds(314, 412, 53, 21);
		panelLeftHand.add(tf_L_Degree_3_3);
		
		slider_L_3_1 = new JSlider();
		slider_L_3_1.setFocusTraversalKeysEnabled(false);
		slider_L_3_1.setFocusable(false);
		slider_L_3_1.setBounds(273, 130, 94, 26);
		panelLeftHand.add(slider_L_3_1);
		
		slider_L_3_2 = new JSlider();
		slider_L_3_2.setFocusTraversalKeysEnabled(false);
		slider_L_3_2.setFocusable(false);
		slider_L_3_2.setBounds(273, 288, 94, 26);
		panelLeftHand.add(slider_L_3_2);
		
		slider_L_3_3 = new JSlider();
		slider_L_3_3.setFocusTraversalKeysEnabled(false);
		slider_L_3_3.setFocusable(false);
		slider_L_3_3.setBounds(273, 440, 94, 26);
		panelLeftHand.add(slider_L_3_3);
		
		lblNewLabel_18 = new JLabel("Raw");
		lblNewLabel_18.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_18.setBounds(379, 77, 44, 15);
		panelLeftHand.add(lblNewLabel_18);
		
		lblNewLabel_19 = new JLabel("Degree");
		lblNewLabel_19.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_19.setBounds(379, 105, 44, 15);
		panelLeftHand.add(lblNewLabel_19);
		
		tf_L_Raw_4_1 = new JTextField();
		tf_L_Raw_4_1.setEditable(false);
		tf_L_Raw_4_1.setText("0");
		tf_L_Raw_4_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_4_1.setColumns(10);
		tf_L_Raw_4_1.setBounds(430, 74, 53, 21);
		panelLeftHand.add(tf_L_Raw_4_1);
		
		tf_L_Degree_4_1 = new JTextField();
		tf_L_Degree_4_1.setEditable(false);
		tf_L_Degree_4_1.setText("0");
		tf_L_Degree_4_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_4_1.setColumns(10);
		tf_L_Degree_4_1.setBounds(430, 102, 53, 21);
		panelLeftHand.add(tf_L_Degree_4_1);
		
		tf_L_Raw_4_2 = new JTextField();
		tf_L_Raw_4_2.setEditable(false);
		tf_L_Raw_4_2.setText("0");
		tf_L_Raw_4_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_4_2.setColumns(10);
		tf_L_Raw_4_2.setBounds(430, 232, 53, 21);
		panelLeftHand.add(tf_L_Raw_4_2);
		
		lblNewLabel_20 = new JLabel("Raw");
		lblNewLabel_20.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_20.setBounds(379, 235, 44, 15);
		panelLeftHand.add(lblNewLabel_20);
		
		lblNewLabel_21 = new JLabel("Degree");
		lblNewLabel_21.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_21.setBounds(379, 263, 44, 15);
		panelLeftHand.add(lblNewLabel_21);
		
		tf_L_Degree_4_2 = new JTextField();
		tf_L_Degree_4_2.setEditable(false);
		tf_L_Degree_4_2.setText("0");
		tf_L_Degree_4_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_4_2.setColumns(10);
		tf_L_Degree_4_2.setBounds(430, 260, 53, 21);
		panelLeftHand.add(tf_L_Degree_4_2);
		
		tf_L_Raw_4_3 = new JTextField();
		tf_L_Raw_4_3.setEditable(false);
		tf_L_Raw_4_3.setText("0");
		tf_L_Raw_4_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_4_3.setColumns(10);
		tf_L_Raw_4_3.setBounds(430, 384, 53, 21);
		panelLeftHand.add(tf_L_Raw_4_3);
		
		lblNewLabel_22 = new JLabel("Raw");
		lblNewLabel_22.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_22.setBounds(379, 387, 44, 15);
		panelLeftHand.add(lblNewLabel_22);
		
		lblNewLabel_23 = new JLabel("Degree");
		lblNewLabel_23.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_23.setBounds(379, 415, 44, 15);
		panelLeftHand.add(lblNewLabel_23);
		
		tf_L_Degree_4_3 = new JTextField();
		tf_L_Degree_4_3.setEditable(false);
		tf_L_Degree_4_3.setText("0");
		tf_L_Degree_4_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_4_3.setColumns(10);
		tf_L_Degree_4_3.setBounds(430, 412, 53, 21);
		panelLeftHand.add(tf_L_Degree_4_3);
		
		slider_L_4_1 = new JSlider();
		slider_L_4_1.setFocusTraversalKeysEnabled(false);
		slider_L_4_1.setFocusable(false);
		slider_L_4_1.setBounds(389, 130, 94, 26);
		panelLeftHand.add(slider_L_4_1);
		
		slider_L_4_2 = new JSlider();
		slider_L_4_2.setFocusTraversalKeysEnabled(false);
		slider_L_4_2.setFocusable(false);
		slider_L_4_2.setBounds(389, 288, 94, 26);
		panelLeftHand.add(slider_L_4_2);
		
		slider_L_4_3 = new JSlider();
		slider_L_4_3.setFocusTraversalKeysEnabled(false);
		slider_L_4_3.setFocusable(false);
		slider_L_4_3.setBounds(389, 440, 94, 26);
		panelLeftHand.add(slider_L_4_3);
		
		tf_L_Raw_5_1 = new JTextField();
		tf_L_Raw_5_1.setEditable(false);
		tf_L_Raw_5_1.setText("0");
		tf_L_Raw_5_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_5_1.setColumns(10);
		tf_L_Raw_5_1.setBounds(546, 232, 53, 21);
		panelLeftHand.add(tf_L_Raw_5_1);
		
		lblNewLabel_24 = new JLabel("Raw");
		lblNewLabel_24.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_24.setBounds(495, 235, 44, 15);
		panelLeftHand.add(lblNewLabel_24);
		
		lblNewLabel_25 = new JLabel("Degree");
		lblNewLabel_25.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_25.setBounds(495, 263, 44, 15);
		panelLeftHand.add(lblNewLabel_25);
		
		tf_L_Degree_5_1 = new JTextField();
		tf_L_Degree_5_1.setEditable(false);
		tf_L_Degree_5_1.setText("0");
		tf_L_Degree_5_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_5_1.setColumns(10);
		tf_L_Degree_5_1.setBounds(546, 260, 53, 21);
		panelLeftHand.add(tf_L_Degree_5_1);
		
		tf_L_Raw_5_2 = new JTextField();
		tf_L_Raw_5_2.setEditable(false);
		tf_L_Raw_5_2.setText("0");
		tf_L_Raw_5_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Raw_5_2.setColumns(10);
		tf_L_Raw_5_2.setBounds(546, 384, 53, 21);
		panelLeftHand.add(tf_L_Raw_5_2);
		
		lblNewLabel_26 = new JLabel("Raw");
		lblNewLabel_26.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_26.setBounds(495, 387, 44, 15);
		panelLeftHand.add(lblNewLabel_26);
		
		lblNewLabel_27 = new JLabel("Degree");
		lblNewLabel_27.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_27.setBounds(495, 415, 44, 15);
		panelLeftHand.add(lblNewLabel_27);
		
		tf_L_Degree_5_2 = new JTextField();
		tf_L_Degree_5_2.setEditable(false);
		tf_L_Degree_5_2.setText("0");
		tf_L_Degree_5_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_L_Degree_5_2.setColumns(10);
		tf_L_Degree_5_2.setBounds(546, 412, 53, 21);
		panelLeftHand.add(tf_L_Degree_5_2);
		
		slider_L_5_1 = new JSlider();
		slider_L_5_1.setFocusTraversalKeysEnabled(false);
		slider_L_5_1.setFocusable(false);
		slider_L_5_1.setBounds(505, 288, 94, 26);
		panelLeftHand.add(slider_L_5_1);
		
		slider_L_5_2 = new JSlider();
		slider_L_5_2.setFocusTraversalKeysEnabled(false);
		slider_L_5_2.setFocusable(false);
		slider_L_5_2.setBounds(505, 440, 94, 26);
		panelLeftHand.add(slider_L_5_2);
		
		lblNewLabel_56 = new JLabel("little finger");
		lblNewLabel_56.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_56.setBounds(56, 31, 80, 15);
		panelLeftHand.add(lblNewLabel_56);
		
		lblNewLabel_57 = new JLabel("ring finger");
		lblNewLabel_57.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_57.setBounds(171, 31, 70, 15);
		panelLeftHand.add(lblNewLabel_57);
		
		lblNewLabel_58 = new JLabel("middle finger");
		lblNewLabel_58.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_58.setBounds(273, 31, 94, 15);
		panelLeftHand.add(lblNewLabel_58);
		
		lblNewLabel_59 = new JLabel("index finger");
		lblNewLabel_59.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_59.setBounds(389, 31, 80, 15);
		panelLeftHand.add(lblNewLabel_59);
		
		lblNewLabel_60 = new JLabel("thumb");
		lblNewLabel_60.setBounds(533, 191, 53, 15);
		panelLeftHand.add(lblNewLabel_60);
		lblNewLabel_60.setFont(new Font("±º∏≤", Font.BOLD, 12));
		
		panelRightHand = new JPanel();
		panelRightHand.setBorder(new TitledBorder(null, "Right", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelRightHand.setPreferredSize(new Dimension(627, 500));
		panel.add(panelRightHand, BorderLayout.EAST);
		panelRightHand.setLayout(null);
		
		lblNewLabel_28 = new JLabel("Raw");
		lblNewLabel_28.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_28.setBounds(144, 79, 44, 15);
		panelRightHand.add(lblNewLabel_28);
		
		lblNewLabel_29 = new JLabel("Degree");
		lblNewLabel_29.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_29.setBounds(144, 107, 44, 15);
		panelRightHand.add(lblNewLabel_29);
		
		tf_R_Raw_2_1 = new JTextField();
		tf_R_Raw_2_1.setEditable(false);
		tf_R_Raw_2_1.setText("0");
		tf_R_Raw_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_2_1.setColumns(10);
		tf_R_Raw_2_1.setBounds(195, 76, 53, 21);
		panelRightHand.add(tf_R_Raw_2_1);
		
		tf_R_Degree_2_1 = new JTextField();
		tf_R_Degree_2_1.setEditable(false);
		tf_R_Degree_2_1.setText("0");
		tf_R_Degree_2_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_2_1.setColumns(10);
		tf_R_Degree_2_1.setBounds(195, 104, 53, 21);
		panelRightHand.add(tf_R_Degree_2_1);
		
		tf_R_Raw_2_2 = new JTextField();
		tf_R_Raw_2_2.setEditable(false);
		tf_R_Raw_2_2.setText("0");
		tf_R_Raw_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_2_2.setColumns(10);
		tf_R_Raw_2_2.setBounds(195, 234, 53, 21);
		panelRightHand.add(tf_R_Raw_2_2);
		
		lblNewLabel_30 = new JLabel("Raw");
		lblNewLabel_30.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_30.setBounds(144, 237, 44, 15);
		panelRightHand.add(lblNewLabel_30);
		
		lblNewLabel_31 = new JLabel("Degree");
		lblNewLabel_31.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_31.setBounds(144, 265, 44, 15);
		panelRightHand.add(lblNewLabel_31);
		
		tf_R_Degree_2_2 = new JTextField();
		tf_R_Degree_2_2.setEditable(false);
		tf_R_Degree_2_2.setText("0");
		tf_R_Degree_2_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_2_2.setColumns(10);
		tf_R_Degree_2_2.setBounds(195, 262, 53, 21);
		panelRightHand.add(tf_R_Degree_2_2);
		
		tf_R_Raw_2_3 = new JTextField();
		tf_R_Raw_2_3.setEditable(false);
		tf_R_Raw_2_3.setText("0");
		tf_R_Raw_2_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_2_3.setColumns(10);
		tf_R_Raw_2_3.setBounds(195, 386, 53, 21);
		panelRightHand.add(tf_R_Raw_2_3);
		
		lblNewLabel_32 = new JLabel("Raw");
		lblNewLabel_32.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_32.setBounds(144, 389, 44, 15);
		panelRightHand.add(lblNewLabel_32);
		
		lblNewLabel_33 = new JLabel("Degree");
		lblNewLabel_33.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_33.setBounds(144, 417, 44, 15);
		panelRightHand.add(lblNewLabel_33);
		
		tf_R_Degree_2_3 = new JTextField();
		tf_R_Degree_2_3.setEditable(false);
		tf_R_Degree_2_3.setText("0");
		tf_R_Degree_2_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_2_3.setColumns(10);
		tf_R_Degree_2_3.setBounds(195, 414, 53, 21);
		panelRightHand.add(tf_R_Degree_2_3);
		
		slider_R_2_1 = new JSlider();
		slider_R_2_1.setFocusTraversalKeysEnabled(false);
		slider_R_2_1.setFocusable(false);
		slider_R_2_1.setBounds(154, 132, 94, 26);
		panelRightHand.add(slider_R_2_1);
		
		slider_R_2_2 = new JSlider();
		slider_R_2_2.setFocusTraversalKeysEnabled(false);
		slider_R_2_2.setFocusable(false);
		slider_R_2_2.setBounds(154, 290, 94, 26);
		panelRightHand.add(slider_R_2_2);
		
		slider_R_2_3 = new JSlider();
		slider_R_2_3.setFocusTraversalKeysEnabled(false);
		slider_R_2_3.setFocusable(false);
		slider_R_2_3.setBounds(154, 442, 94, 26);
		panelRightHand.add(slider_R_2_3);
		
		lblNewLabel_34 = new JLabel("Raw");
		lblNewLabel_34.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_34.setBounds(260, 79, 44, 15);
		panelRightHand.add(lblNewLabel_34);
		
		lblNewLabel_35 = new JLabel("Degree");
		lblNewLabel_35.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_35.setBounds(260, 107, 44, 15);
		panelRightHand.add(lblNewLabel_35);
		
		tf_R_Raw_3_1 = new JTextField();
		tf_R_Raw_3_1.setEditable(false);
		tf_R_Raw_3_1.setText("0");
		tf_R_Raw_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_3_1.setColumns(10);
		tf_R_Raw_3_1.setBounds(311, 76, 53, 21);
		panelRightHand.add(tf_R_Raw_3_1);
		
		tf_R_Degree_3_1 = new JTextField();
		tf_R_Degree_3_1.setEditable(false);
		tf_R_Degree_3_1.setText("0");
		tf_R_Degree_3_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_3_1.setColumns(10);
		tf_R_Degree_3_1.setBounds(311, 104, 53, 21);
		panelRightHand.add(tf_R_Degree_3_1);
		
		tf_R_Raw_3_2 = new JTextField();
		tf_R_Raw_3_2.setEditable(false);
		tf_R_Raw_3_2.setText("0");
		tf_R_Raw_3_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_3_2.setColumns(10);
		tf_R_Raw_3_2.setBounds(311, 234, 53, 21);
		panelRightHand.add(tf_R_Raw_3_2);
		
		lblNewLabel_36 = new JLabel("Raw");
		lblNewLabel_36.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_36.setBounds(260, 237, 44, 15);
		panelRightHand.add(lblNewLabel_36);
		
		lblNewLabel_37 = new JLabel("Degree");
		lblNewLabel_37.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_37.setBounds(260, 265, 44, 15);
		panelRightHand.add(lblNewLabel_37);
		
		tf_R_Degree_3_2 = new JTextField();
		tf_R_Degree_3_2.setEditable(false);
		tf_R_Degree_3_2.setText("0");
		tf_R_Degree_3_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_3_2.setColumns(10);
		tf_R_Degree_3_2.setBounds(311, 262, 53, 21);
		panelRightHand.add(tf_R_Degree_3_2);
		
		tf_R_Raw_3_3 = new JTextField();
		tf_R_Raw_3_3.setEditable(false);
		tf_R_Raw_3_3.setText("0");
		tf_R_Raw_3_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_3_3.setColumns(10);
		tf_R_Raw_3_3.setBounds(311, 386, 53, 21);
		panelRightHand.add(tf_R_Raw_3_3);
		
		lblNewLabel_38 = new JLabel("Raw");
		lblNewLabel_38.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_38.setBounds(260, 389, 44, 15);
		panelRightHand.add(lblNewLabel_38);
		
		lblNewLabel_39 = new JLabel("Degree");
		lblNewLabel_39.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_39.setBounds(260, 417, 44, 15);
		panelRightHand.add(lblNewLabel_39);
		
		tf_R_Degree_3_3 = new JTextField();
		tf_R_Degree_3_3.setEditable(false);
		tf_R_Degree_3_3.setText("0");
		tf_R_Degree_3_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_3_3.setColumns(10);
		tf_R_Degree_3_3.setBounds(311, 414, 53, 21);
		panelRightHand.add(tf_R_Degree_3_3);
		
		slider_R_3_1 = new JSlider();
		slider_R_3_1.setFocusTraversalKeysEnabled(false);
		slider_R_3_1.setFocusable(false);
		slider_R_3_1.setBounds(270, 132, 94, 26);
		panelRightHand.add(slider_R_3_1);
		
		slider_R_3_2 = new JSlider();
		slider_R_3_2.setFocusTraversalKeysEnabled(false);
		slider_R_3_2.setFocusable(false);
		slider_R_3_2.setBounds(270, 290, 94, 26);
		panelRightHand.add(slider_R_3_2);
		
		slider_R_3_3 = new JSlider();
		slider_R_3_3.setFocusTraversalKeysEnabled(false);
		slider_R_3_3.setFocusable(false);
		slider_R_3_3.setBounds(270, 442, 94, 26);
		panelRightHand.add(slider_R_3_3);
		
		lblNewLabel_40 = new JLabel("Raw");
		lblNewLabel_40.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_40.setBounds(376, 79, 44, 15);
		panelRightHand.add(lblNewLabel_40);
		
		lblNewLabel_41 = new JLabel("Degree");
		lblNewLabel_41.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_41.setBounds(376, 107, 44, 15);
		panelRightHand.add(lblNewLabel_41);
		
		tf_R_Raw_4_1 = new JTextField();
		tf_R_Raw_4_1.setEditable(false);
		tf_R_Raw_4_1.setText("0");
		tf_R_Raw_4_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_4_1.setColumns(10);
		tf_R_Raw_4_1.setBounds(427, 76, 53, 21);
		panelRightHand.add(tf_R_Raw_4_1);
		
		tf_R_Degree_4_1 = new JTextField();
		tf_R_Degree_4_1.setEditable(false);
		tf_R_Degree_4_1.setText("0");
		tf_R_Degree_4_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_4_1.setColumns(10);
		tf_R_Degree_4_1.setBounds(427, 104, 53, 21);
		panelRightHand.add(tf_R_Degree_4_1);
		
		tf_R_Raw_4_2 = new JTextField();
		tf_R_Raw_4_2.setEditable(false);
		tf_R_Raw_4_2.setText("0");
		tf_R_Raw_4_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_4_2.setColumns(10);
		tf_R_Raw_4_2.setBounds(427, 234, 53, 21);
		panelRightHand.add(tf_R_Raw_4_2);
		
		lblNewLabel_42 = new JLabel("Raw");
		lblNewLabel_42.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_42.setBounds(376, 237, 44, 15);
		panelRightHand.add(lblNewLabel_42);
		
		lblNewLabel_43 = new JLabel("Degree");
		lblNewLabel_43.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_43.setBounds(376, 265, 44, 15);
		panelRightHand.add(lblNewLabel_43);
		
		tf_R_Degree_4_2 = new JTextField();
		tf_R_Degree_4_2.setEditable(false);
		tf_R_Degree_4_2.setText("0");
		tf_R_Degree_4_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_4_2.setColumns(10);
		tf_R_Degree_4_2.setBounds(427, 262, 53, 21);
		panelRightHand.add(tf_R_Degree_4_2);
		
		tf_R_Raw_4_3 = new JTextField();
		tf_R_Raw_4_3.setEditable(false);
		tf_R_Raw_4_3.setText("0");
		tf_R_Raw_4_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_4_3.setColumns(10);
		tf_R_Raw_4_3.setBounds(427, 386, 53, 21);
		panelRightHand.add(tf_R_Raw_4_3);
		
		lblNewLabel_44 = new JLabel("Raw");
		lblNewLabel_44.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_44.setBounds(376, 389, 44, 15);
		panelRightHand.add(lblNewLabel_44);
		
		lblNewLabel_45 = new JLabel("Degree");
		lblNewLabel_45.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_45.setBounds(376, 417, 44, 15);
		panelRightHand.add(lblNewLabel_45);
		
		tf_R_Degree_4_3 = new JTextField();
		tf_R_Degree_4_3.setEditable(false);
		tf_R_Degree_4_3.setText("0");
		tf_R_Degree_4_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_4_3.setColumns(10);
		tf_R_Degree_4_3.setBounds(427, 414, 53, 21);
		panelRightHand.add(tf_R_Degree_4_3);
		
		slider_R_4_1 = new JSlider();
		slider_R_4_1.setFocusTraversalKeysEnabled(false);
		slider_R_4_1.setFocusable(false);
		slider_R_4_1.setBounds(386, 132, 94, 26);
		panelRightHand.add(slider_R_4_1);
		
		slider_R_4_2 = new JSlider();
		slider_R_4_2.setFocusTraversalKeysEnabled(false);
		slider_R_4_2.setFocusable(false);
		slider_R_4_2.setBounds(386, 290, 94, 26);
		panelRightHand.add(slider_R_4_2);
		
		slider_R_4_3 = new JSlider();
		slider_R_4_3.setFocusTraversalKeysEnabled(false);
		slider_R_4_3.setFocusable(false);
		slider_R_4_3.setBounds(386, 442, 94, 26);
		panelRightHand.add(slider_R_4_3);
		
		lblNewLabel_46 = new JLabel("Raw");
		lblNewLabel_46.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_46.setBounds(492, 79, 44, 15);
		panelRightHand.add(lblNewLabel_46);
		
		lblNewLabel_47 = new JLabel("Degree");
		lblNewLabel_47.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_47.setBounds(492, 107, 44, 15);
		panelRightHand.add(lblNewLabel_47);
		
		tf_R_Raw_5_1 = new JTextField();
		tf_R_Raw_5_1.setEditable(false);
		tf_R_Raw_5_1.setText("0");
		tf_R_Raw_5_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_5_1.setColumns(10);
		tf_R_Raw_5_1.setBounds(543, 76, 53, 21);
		panelRightHand.add(tf_R_Raw_5_1);
		
		tf_R_Degree_5_1 = new JTextField();
		tf_R_Degree_5_1.setEditable(false);
		tf_R_Degree_5_1.setText("0");
		tf_R_Degree_5_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_5_1.setColumns(10);
		tf_R_Degree_5_1.setBounds(543, 104, 53, 21);
		panelRightHand.add(tf_R_Degree_5_1);
		
		tf_R_Raw_5_2 = new JTextField();
		tf_R_Raw_5_2.setEditable(false);
		tf_R_Raw_5_2.setText("0");
		tf_R_Raw_5_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_5_2.setColumns(10);
		tf_R_Raw_5_2.setBounds(543, 234, 53, 21);
		panelRightHand.add(tf_R_Raw_5_2);
		
		lblNewLabel_48 = new JLabel("Raw");
		lblNewLabel_48.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_48.setBounds(492, 237, 44, 15);
		panelRightHand.add(lblNewLabel_48);
		
		lblNewLabel_49 = new JLabel("Degree");
		lblNewLabel_49.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_49.setBounds(492, 265, 44, 15);
		panelRightHand.add(lblNewLabel_49);
		
		tf_R_Degree_5_2 = new JTextField();
		tf_R_Degree_5_2.setEditable(false);
		tf_R_Degree_5_2.setText("0");
		tf_R_Degree_5_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_5_2.setColumns(10);
		tf_R_Degree_5_2.setBounds(543, 262, 53, 21);
		panelRightHand.add(tf_R_Degree_5_2);
		
		tf_R_Raw_5_3 = new JTextField();
		tf_R_Raw_5_3.setEditable(false);
		tf_R_Raw_5_3.setText("0");
		tf_R_Raw_5_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_5_3.setColumns(10);
		tf_R_Raw_5_3.setBounds(543, 386, 53, 21);
		panelRightHand.add(tf_R_Raw_5_3);
		
		lblNewLabel_50 = new JLabel("Raw");
		lblNewLabel_50.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_50.setBounds(492, 389, 44, 15);
		panelRightHand.add(lblNewLabel_50);
		
		lblNewLabel_51 = new JLabel("Degree");
		lblNewLabel_51.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_51.setBounds(492, 417, 44, 15);
		panelRightHand.add(lblNewLabel_51);
		
		tf_R_Degree_5_3 = new JTextField();
		tf_R_Degree_5_3.setEditable(false);
		tf_R_Degree_5_3.setText("0");
		tf_R_Degree_5_3.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_5_3.setColumns(10);
		tf_R_Degree_5_3.setBounds(543, 414, 53, 21);
		panelRightHand.add(tf_R_Degree_5_3);
		
		slider_R_5_1 = new JSlider();
		slider_R_5_1.setFocusTraversalKeysEnabled(false);
		slider_R_5_1.setFocusable(false);
		slider_R_5_1.setBounds(502, 132, 94, 26);
		panelRightHand.add(slider_R_5_1);
		
		slider_R_5_2 = new JSlider();
		slider_R_5_2.setFocusTraversalKeysEnabled(false);
		slider_R_5_2.setFocusable(false);
		slider_R_5_2.setBounds(502, 290, 94, 26);
		panelRightHand.add(slider_R_5_2);
		
		slider_R_5_3 = new JSlider();
		slider_R_5_3.setFocusTraversalKeysEnabled(false);
		slider_R_5_3.setFocusable(false);
		slider_R_5_3.setBounds(502, 442, 94, 26);
		panelRightHand.add(slider_R_5_3);
		
		tf_R_Raw_1_1 = new JTextField();
		tf_R_Raw_1_1.setEditable(false);
		tf_R_Raw_1_1.setText("0");
		tf_R_Raw_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_1_1.setColumns(10);
		tf_R_Raw_1_1.setBounds(79, 234, 53, 21);
		panelRightHand.add(tf_R_Raw_1_1);
		
		lblNewLabel_52 = new JLabel("Raw");
		lblNewLabel_52.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_52.setBounds(28, 237, 44, 15);
		panelRightHand.add(lblNewLabel_52);
		
		lblNewLabel_53 = new JLabel("Degree");
		lblNewLabel_53.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_53.setBounds(28, 265, 44, 15);
		panelRightHand.add(lblNewLabel_53);
		
		tf_R_Degree_1_1 = new JTextField();
		tf_R_Degree_1_1.setEditable(false);
		tf_R_Degree_1_1.setText("0");
		tf_R_Degree_1_1.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_1_1.setColumns(10);
		tf_R_Degree_1_1.setBounds(79, 262, 53, 21);
		panelRightHand.add(tf_R_Degree_1_1);
		
		tf_R_Raw_1_2 = new JTextField();
		tf_R_Raw_1_2.setEditable(false);
		tf_R_Raw_1_2.setText("0");
		tf_R_Raw_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Raw_1_2.setColumns(10);
		tf_R_Raw_1_2.setBounds(79, 386, 53, 21);
		panelRightHand.add(tf_R_Raw_1_2);
		
		lblNewLabel_54 = new JLabel("Raw");
		lblNewLabel_54.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_54.setBounds(28, 389, 44, 15);
		panelRightHand.add(lblNewLabel_54);
		
		lblNewLabel_55 = new JLabel("Degree");
		lblNewLabel_55.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_55.setBounds(28, 417, 44, 15);
		panelRightHand.add(lblNewLabel_55);
		
		tf_R_Degree_1_2 = new JTextField();
		tf_R_Degree_1_2.setEditable(false);
		tf_R_Degree_1_2.setText("0");
		tf_R_Degree_1_2.setHorizontalAlignment(SwingConstants.TRAILING);
		tf_R_Degree_1_2.setColumns(10);
		tf_R_Degree_1_2.setBounds(79, 414, 53, 21);
		panelRightHand.add(tf_R_Degree_1_2);
		
		slider_R_1_1 = new JSlider();
		slider_R_1_1.setFocusTraversalKeysEnabled(false);
		slider_R_1_1.setFocusable(false);
		slider_R_1_1.setBounds(38, 290, 94, 26);
		panelRightHand.add(slider_R_1_1);
		
		slider_R_1_2 = new JSlider();
		slider_R_1_2.setFocusTraversalKeysEnabled(false);
		slider_R_1_2.setFocusable(false);
		slider_R_1_2.setBounds(38, 442, 94, 26);
		panelRightHand.add(slider_R_1_2);
		
		lblNewLabel_61 = new JLabel("thumb");
		lblNewLabel_61.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_61.setBounds(65, 190, 53, 15);
		panelRightHand.add(lblNewLabel_61);
		
		lblNewLabel_62 = new JLabel("index finger");
		lblNewLabel_62.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_62.setBounds(156, 28, 79, 15);
		panelRightHand.add(lblNewLabel_62);
		
		lblNewLabel_63 = new JLabel("middle finger");
		lblNewLabel_63.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_63.setBounds(260, 28, 90, 15);
		panelRightHand.add(lblNewLabel_63);
		
		lblNewLabel_64 = new JLabel("ring finger");
		lblNewLabel_64.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_64.setBounds(397, 28, 73, 15);
		panelRightHand.add(lblNewLabel_64);
		
		lblNewLabel_65 = new JLabel("little finger");
		lblNewLabel_65.setFont(new Font("±º∏≤", Font.BOLD, 12));
		lblNewLabel_65.setBounds(505, 28, 79, 15);
		panelRightHand.add(lblNewLabel_65);
		
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(10, 40));
		panel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(null);
		
		btnCalibration = new JButton("Calibration");
		btnCalibration.setBounds(1129, 0, 123, 35);
		buttonPanel.add(btnCalibration);
		
		comboBox = new JComboBox();
		comboBox.setBounds(12, 0, 97, 35);
		buttonPanel.add(comboBox);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final int firstIndex = 0;

				int selectedIndex = comboBox.getSelectedIndex();

				if (selectedIndex >= firstIndex) {
					selectedPortIndex = selectedIndex;
				}
			}
		});
		btnCOMPORT = new JButton("CONNECT");
		btnCOMPORT.setBounds(115, 0, 97, 35);
		buttonPanel.add(btnCOMPORT);
		
		initComponents();	// Constructor¿« ∞°¿Â ∏∂¡ˆ∏∑¡Ÿø° ¿ßƒ°«ÿæﬂ «‘
		initPorts();
	}
}


class calibration extends JFrame
{
	JFrame framec;
	JPanel panelC;
	JLabel jLabel2, jLabel3;
	JLabel jLabel1, jLabel4;
	ImageIcon imgIcon;
	Image img; //ªÃæ∆ø¬ ¿ÃπÃ¡ˆ ∞¥√º ªÁ¿Ã¡Ó∏¶ ªı∑”∞‘ ∏∏µÈ±‚!
	JLabel imgLabel;
    
	public void set_img_text(String image, String text, int repeat_count, int repeat)
	{
        jLabel2.setText("       " + text + "       ");

        imgIcon = new ImageIcon(image);
//    	img = imgIcon.getImage(); //ªÃæ∆ø¬ ¿ÃπÃ¡ˆ ∞¥√º ªÁ¿Ã¡Ó∏¶ ªı∑”∞‘ ∏∏µÈ±‚!
    	imgLabel.setIcon(imgIcon);
    	
        String str =  "            " + Integer.toString(repeat_count);
        str += " / ";
        str += Integer.toString(repeat) +  "            ";
        jLabel3.setText(str);
  
//		System.out.printf(image);

		setVisible(true);
	}
	
	calibration()
	{
		framec = new JFrame();
		panelC = new JPanel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();
//		jLabel1 = new JLabel();
//		jLabel4 = new JLabel();

//		framec.getContentPane().add(panelC, BorderLayout.CENTER);
		framec.getContentPane().setLayout(null);
		framec.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Calibration");
		setContentPane(panelC);
		
	    jLabel2.setFont(new java.awt.Font("±º∏≤", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel2.setBounds(0,20,400,50);
        panelC.add(jLabel2);
		
        jLabel3.setFont(new java.awt.Font("±º∏≤", 0, 26)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        jLabel3.setBounds(0,70,400,40);
        panelC.add(jLabel3);

		
		imgLabel = new JLabel(imgIcon,JLabel.CENTER);
//    	imgLabel.setBounds(0,120,315,315);
    	panelC.add(imgLabel);

//		pack();
		
		setSize(400,350);
		setResizable(false);
		setVisible(true);

	}
}
