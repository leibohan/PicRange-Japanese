package pintu.viewer;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import pintu.dao.HandleDB;
import pintu.handle.HandleImage;
import javax.swing.DefaultComboBoxModel;

public class MainFrame extends JFrame implements ActionListener {
	private JPanel contentPane;
	JLabel lblShow = new JLabel("");
	JPanel imagePanel = new JPanel();
	private int nums = 3;
	HandleDB hdb = new HandleDB();
	private AudioClip music = null;
	//private AudioClip newMusic = null;
	private int musicrec = 0;
	private boolean changeMusic = true;
	
	private Random rand = new Random();
	int[][] numArr = new int[5][5];
	
	//private String []strGrade = {"難度を選ぶ", "3 x 3", "4 x 4", "5 x 5"};
	private JComboBox<String> cbGrade = new JComboBox<String>(/*strGrade*/);
	//private String []strImage = {"写真を選ぶ", "セルフィー", "南ことり", "友利奈緒", "内田彩"};
	private JComboBox<String> cbImage = new JComboBox<String>(/*strImage*/);
	private boolean isRun = false;
	
	private int blankR = nums - 1;
	private int blankC = nums - 1;
	
	private Timer timer;
	
/*	public void setValue(int r, int c) {
		
	}
	
	public void setFont(Font font) {
		
	}
	
	public int getBlankR() {
		return blankR;
	}

	public void setBlankR(int blankR) {
		this.blankR = blankR;
	}

	public int getBlankC() {
		return blankC;
	}

	public void setBlankC(int blankC) {
		this.blankC = blankC;
	}*/
	
	private int timeleft = 1200;
	
	private class MyTask extends TimerTask {		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("+1s");
			timeleft--;
			lblLeftTime.setText(timeleft + "s");
			if (timeleft == 0) {
				JOptionPane.showMessageDialog(null, "時間切れ");
				this.cancel();
			}
		}
	}
	
	public void startThread() {
		if (timer != null) {
			timer.cancel();
		}
		timeleft = 1200;
		lblLeftTime.setText(timeleft + "s");
		timer = new Timer();
		timer.schedule(new MyTask(), 1000, 1000);
	}

	private JButton btnShow = new JButton("サポット");
	private JButton btnStart = new JButton("スタット");
	private JButton btnMusic = new JButton("おんがく");
	private JButton btnOrder = new JButton("ランクメ");
	private JLabel label = new JLabel("残り時間：");
	private JLabel lblLeftTime = new JLabel("");

	ImageButton [][] btnArr = null;
	
	private String imageName = "3.jpg";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				}catch(Exception e) {
					e.getStackTrace();
				}
			}
		});
	}
	
	public void initNum() {
		int row1 = nums - 1;
		int col1 = nums - 1;
		int row2 = nums - 1;
		int col2 = nums - 1;
		int tmp = 0;
		
		row1 = rand.nextInt(nums);
		col1 = rand.nextInt(nums);
		
		for (int i = 0; i < nums; i++) {
			for (int j = 0; j < nums; j++) {
				numArr[i][j] = ++tmp;
			}
		}

		for (int i = 0; i < 9999; i++) {
			row2 = rand.nextInt(nums);
			col2 = rand.nextInt(nums);
			
			if (Math.abs(col1 - col2) + Math.abs(row1 - row2) == 1) {
				tmp = numArr[row1][col1];
				numArr[row1][col1] = numArr[row2][col2];
				numArr[row2][col2] = tmp;
				row1 = row2;
				col1 = col2;
			}
		}
		
		while (row1 != nums - 1 || col1 != nums - 1) {
			row2 = rand.nextInt(nums);
			col2 = rand.nextInt(nums);
			
			if (Math.abs(col1 - col2) + Math.abs(row1 - row2) == 1) {
				tmp = numArr[row1][col1];
				numArr[row1][col1] = numArr[row2][col2];
				numArr[row2][col2] = tmp;
				row1 = row2;
				col1 = col2;
			}
		}
		for (int i = 0; i < nums; i++) {
			for (int j = 0; j < nums; j++) {
				System.out.println(numArr[i][j]);
			}
		}
	}
	
	public void init() {
		imagePanel.removeAll();
		initImage();
		initGrade();
		initNum();
		initMusic();
		imagePanel.setLayout(new GridLayout(nums,nums));
		btnArr = new ImageButton[nums][nums];
		
		long l = System.currentTimeMillis();
		String prename = String.valueOf(l);
		
		HandleImage ih = new HandleImage();
		ih.deleteAll();
		
		ih.cutImage(500/nums, nums, nums, prename, imageName);
		
		for (int i = 0; i < nums; i++) {
			for (int j = 0; j < nums; j++) {
				ImageButton btn = new ImageButton(i, j, numArr[i][j], prename);
				imagePanel.add(btn);
				btnArr[i][j] = btn;
				btnArr[i][j].addActionListener(this);
			}
		}
		
		blankR = nums - 1;
		blankC = nums - 1;
		btnArr[blankR][blankC].updateImage(false);
	}
	
	public void musicSwitch(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (!isRun){
			new JOptionPane().showMessageDialog(null, "音楽はゲーム始めるからです");
		}
		else if ("おんがく".equals(btn.getText().trim())) {
			music.loop();
			btn.setText("音楽切れ");
		}
		else{
			music.stop();
			btn.setText("おんがく");
		}
	}
	
	public boolean checkOrder() {
		int n = 1;
		for (int i = 0; i < nums; i++) {
			for (int j = 0; j < nums; j++) {
				if (btnArr[i][j].getNum() != n++)
					return false;
			}
		}
		return true;
	}
	
	public void initImage() {
		int n = cbImage.getSelectedIndex();
		
		if (n == 0 || n == 1) 
			n = 1;
		imageName = n + ".jpg";
		
		URL url = this.getClass().getResource("/images/" + imageName);
		ImageIcon icon = new ImageIcon(url);
		icon.setImage(icon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
		lblShow.setIcon(icon);
	}
	
	public void initGrade() {
		nums = cbGrade.getSelectedIndex();
		if(nums == 0)
			nums = 1;
		nums += 2;
		System.out.println("level = " + nums);
	}
	
	public void initMusic() {
		int n = cbImage.getSelectedIndex();
		if (n == 0) n = 1;
		changeMusic = musicrec != n;
		System.out.println(changeMusic);
		URL urlMusic = this.getClass().getResource("/music/" + n + ".wav");
		if ("音楽切れ".equals(btnMusic.getText().trim()) && changeMusic) {
			music.stop();
			music = Applet.newAudioClip(urlMusic);
			music.loop();
		}
		else if (changeMusic)
			music = Applet.newAudioClip(urlMusic);
		musicrec = n;
	}
	
	public MainFrame() {
		this.setTitle("パーゾゲーム〜いかずち殿の作品〜");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.EAST, cbImage, -53, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, cbGrade, 17, SpringLayout.SOUTH, cbImage);
		sl_contentPane.putConstraint(SpringLayout.EAST, cbGrade, 0, SpringLayout.EAST, cbImage);
		sl_contentPane.putConstraint(SpringLayout.NORTH, cbImage, 14, SpringLayout.SOUTH, label);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnMusic, 83, SpringLayout.EAST, imagePanel);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnMusic, -66, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnShow, 83, SpringLayout.EAST, imagePanel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnShow, -11, SpringLayout.NORTH, btnStart);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnShow, -66, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnStart, -66, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnMusic, -11, SpringLayout.NORTH, btnShow);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnStart, 598, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnStart, -31, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblShow, 20, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblShow, 535, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblShow, 220, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblShow, 735, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, imagePanel, 15, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, imagePanel, 15, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, imagePanel, 515, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, imagePanel, 515, SpringLayout.WEST, contentPane);
		contentPane.setLayout(sl_contentPane);
		
		imagePanel.setBackground(Color.GRAY);
		contentPane.add(imagePanel);
		lblShow.setForeground(Color.GRAY);
		lblShow.setBounds(535, 15, 210, 220);
		lblShow.setBorder(new TitledBorder(null, "サンプル"));
		lblShow.setBackground(Color.MAGENTA);
		contentPane.add(lblShow);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnStart);
		btnStart.addActionListener(this);
		contentPane.add(btnShow);
		btnShow.addActionListener(this);
		contentPane.add(btnMusic);
		btnOrder.addActionListener(this);
		
		sl_contentPane.putConstraint(SpringLayout.NORTH, label, 6, SpringLayout.SOUTH, lblShow);
		contentPane.add(label);
		
		sl_contentPane.putConstraint(SpringLayout.WEST, lblLeftTime, 667, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, label, -35, SpringLayout.WEST, lblLeftTime);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblLeftTime, 6, SpringLayout.SOUTH, lblShow);
		lblLeftTime.setForeground(new Color(102, 102, 255));
		contentPane.add(lblLeftTime);
		
		sl_contentPane.putConstraint(SpringLayout.WEST, btnOrder, 83, SpringLayout.EAST, imagePanel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnOrder, -11, SpringLayout.NORTH, btnMusic);
		contentPane.add(btnOrder);
		cbImage.setModel(new DefaultComboBoxModel(new String[] {"写真を選ぶ", "セルフィー", "南ことり", "友利奈緒", "内田彩"}));
		cbImage.setToolTipText("");
		cbImage.setBounds(591, 271, 110, 29);
		contentPane.add(cbImage);
		cbGrade.setModel(new DefaultComboBoxModel(new String[] {"難度を選ぶ", "3 x 3", "4 x 4", "5 x 5"}));
		cbGrade.setBounds(591, 323, 110, 29);
		contentPane.add(cbGrade);
		btnMusic.addActionListener(this);
		
		
		//init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnMusic) {
			//System.out.println("Music");
			musicSwitch(e);
		}
		else if (e.getSource() == btnStart) {
			//System.out.println("Start");
			if (isRun) {
				int n = JOptionPane.showConfirmDialog(this, "今のゲームから離れますか？");
				//System.out.println(n);
				if (n == 0)
					init();
					startThread();
			}
			else {
				init();
				btnStart.setText("やり直し");
				isRun = true;
				startThread();
			}
		}
		else if (e.getSource() == btnShow) {
			//System.out.println("info");
			JOptionPane.showMessageDialog(this, " このゲームは説明ないから、信じならないでしょう！\r\n如果看不懂日语，版权者雷伯涵推荐您去学一学，或者查字典\r\n");
		}
		else if (e.getSource() == btnOrder) {
			//System.out.println("rank");
			TimeOrderDialog dia = new TimeOrderDialog(this, true);
		}
		else {
			switchImage(e);
		}
	}
	
	
	public void switchImage(ActionEvent e) {
		ImageButton btn = (ImageButton) e.getSource();
		int row = btn.getRow();
		int col = btn.getCol();
		
		if (Math.abs(blankC - col) + Math.abs(blankR - row) == 1) {
			int tmp = btnArr[row][col].getNum();
			btnArr[row][col].setNum(btnArr[blankR][blankC].getNum());
			btnArr[blankR][blankC].setNum(tmp);
			btnArr[blankR][blankC].updateImage(true);
			blankR = row;
			blankC = col;
			btnArr[blankR][blankC].updateImage(false);
			
			if (checkOrder()) {
				JOptionPane.showMessageDialog(this, "成功しました");
				int timeleft = Integer.parseInt(lblLeftTime.getName());
				int time = 1200 - timeleft;
				String name = JOptionPane.showInputDialog(this, "名前を入り込みなさい");
				if (name == null || "".equals(name.trim())) {
					name = "nounymous";
				hdb.insertInfo(name, time);
				}
			}
		}
		
	}
}
