package ChatApp;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

import javax.swing.border.EmptyBorder;
import javax.swing.*;

public class Client implements ActionListener {

	static JFrame f = new JFrame();
	
	JTextField text;
	static JPanel p2;
	static Box vertical  = Box.createVerticalBox();
	static DataOutputStream dout;
	
	Client() {

		f.setLayout(null);
		
		JPanel p1 = new JPanel();
		p1.setBackground(new Color(7, 94, 84));
		p1.setBounds(0, 0, 450, 60);
		p1.setLayout(null);
		f.add(p1);
		
		ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image img1 = ii1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon i1 = new ImageIcon(img1);
		JLabel back = new JLabel(i1);
		back.setBounds(5, 20, 25, 25);
		p1.add(back);
		
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		ImageIcon ii2 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		Image img2 = ii2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i2 = new ImageIcon(img2);
		JLabel profile = new JLabel(i2);
		profile.setBounds(40, 5, 50, 50);
		p1.add(profile);
		
		JLabel name = new JLabel("Shyam Patel");
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		name.setBounds(105, 13, 120, 20);
		p1.add(name);
		
		JLabel status = new JLabel("Active");
		status.setForeground(Color.WHITE);
		status.setFont(new Font("SAN_SERIF", Font.ITALIC, 13));
		status.setBounds(105, 34, 60, 15);
		p1.add(status);
		
		ImageIcon ii3 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image img3 = ii3.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(img3);
		JLabel video = new JLabel(i3);
		video.setBounds(310, 14, 30, 30);
		p1.add(video);
				
		ImageIcon ii4 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image img4 = ii4.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i4 = new ImageIcon(img4);
		JLabel phone = new JLabel(i4);
		phone.setBounds(360, 14, 30, 30);
		p1.add(phone);
		
		ImageIcon ii5 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image img5 = ii5.getImage().getScaledInstance(15, 30, Image.SCALE_DEFAULT);
		ImageIcon i5 = new ImageIcon(img5);
		JLabel more = new JLabel(i5);
		more.setBounds(400, 14, 15, 30);
		p1.add(more);
		
		p2 = new JPanel();
		p2.setBounds(5, 65, 440, 570);
		f.add(p2);
		
		text = new JTextField();
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
		text.setBounds(5, 640, 330, 40);
		f.add(text);
		
		JButton send = new JButton("Send");
		send.setForeground(Color.WHITE);
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
		send.setBackground(new Color(7, 94, 84));
		send.setBounds(340, 640, 100, 40);
		send.addActionListener(this);
		f.add(send);
		
		f.setSize(450, 685);
		f.getContentPane().setBackground(Color.WHITE);
		f.setUndecorated(true);
		f.setLocation(880, 70);
		f.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae) {
		String out = text.getText();
		if(out.length() > 0) {
			JPanel textPanel = lebalFormat(out);
			
			p2.setLayout(new BorderLayout());
			
			JPanel right = new JPanel(new BorderLayout());
			right.add(textPanel, BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));
		
			p2.add(vertical, BorderLayout.PAGE_START);
			
		}
		try {
			dout.writeUTF(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		text.setText("");
		
		f.repaint();
		f.invalidate();
		f.validate();
	}
	
	public static JPanel lebalFormat(String out)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 130px\">" + out + "</p></html>");
		
		output.setFont(new Font("Tahoma", Font.PLAIN, 18));
		output.setBackground(Color.GREEN);
		output.setBorder(new EmptyBorder(15, 15, 15, 30));
		output.setOpaque(true);
		
		panel.add(output);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		return panel;
	}
	
	public static JPanel serverLabelFormat(String out) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 130px\">" + out + "</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 18));
		output.setBackground(Color.WHITE);
		output.setBorder(new EmptyBorder(15, 15, 15, 30));
		output.setOpaque(true);
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		return panel;
	}
	
	public static void main(String[] args) {       
		new Client();
		try {
			Socket s = new Socket("127.0.0.1", 6001);
			
			DataInputStream din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			while(true) {
				p2.setLayout(new BorderLayout());
				String msg = din.readUTF();
				JPanel panel = serverLabelFormat(msg);
				
				JPanel left = new JPanel(new BorderLayout());
				left.add(panel, BorderLayout.LINE_START);
				vertical.add(left);
				vertical.add(Box.createVerticalStrut(15));
				p2.add(vertical, BorderLayout.PAGE_START);
				
				f.validate();
			}
		} catch (Exception e) {};
	}

}
