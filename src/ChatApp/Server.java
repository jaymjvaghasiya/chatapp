package ChatApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.Calendar;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;

public class Server implements ActionListener {

	static JFrame f = new JFrame();
	
	JTextField text;
	static JPanel a1;
	static Box vertical = Box.createVerticalBox();
	static DataOutputStream dout;
	
	Server() {
		
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
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon ii2 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
		Image img2 = ii2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon i2 = new ImageIcon(img2);
		JLabel profile = new JLabel(i2);
		profile.setBounds(40, 5, 50, 50);
		p1.add(profile);
		
		JLabel name = new JLabel("Ram Patel");
		name.setForeground(Color.WHITE);
		name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		name.setBounds(105, 13, 100, 20);
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
		
		a1 = new JPanel();
		a1.setBounds(5, 65, 440, 570);
		f.add(a1);
		
		text = new JTextField();
		text.setBounds(5, 640, 330, 40);
		text.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
		f.add(text);
		
		JButton send = new JButton("Send");
		send.setBounds(340, 640, 100, 40);
		send.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
		send.setBackground(new Color(7, 94, 84));
		send.setForeground(Color.WHITE);
		send.addActionListener(this);
		f.add(send);
		
		f.setSize(450, 685);
		f.setLocation(200, 70);
		f.setUndecorated(true);
		f.getContentPane().setBackground(Color.WHITE);
		f.setVisible(true);	
	}
	
	public void actionPerformed(ActionEvent ae) {
		 String out = text.getText();
		 if(out.length() > 0) {			 
			 JPanel p2 = formatLabel(out);
			 
			 a1.setLayout(new BorderLayout());
			 
			 JPanel right = new JPanel(new BorderLayout());
			 right.add(p2, BorderLayout.LINE_END);
			 vertical.add(right);
			 vertical.add(Box.createVerticalStrut(15));
			 
			 a1.add(vertical, BorderLayout.PAGE_START);
			 
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
	
	public static JPanel formatLabel(String out) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 130px\">" + out + "</p></html>");
		output.setFont(new Font("Tahoma", Font.PLAIN, 18));
		output.setBackground(Color.GREEN);
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 15, 15, 20));
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		
		panel.add(time);
		
		return panel;
	}
	
	public static JPanel clientFormatLabel(String out) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel output = new JLabel("<html><p style=\"width: 130px\">" + out + "</p></html>");
		output.setFont(new Font("Tahom", Font.PLAIN, 18));
		output.setBackground(Color.WHITE);
		output.setOpaque(true);
		output.setBorder(new EmptyBorder(15, 15, 15, 30));
		panel.add(output);
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		JLabel time = new JLabel();
		time.setText(sdf.format(cal.getTime()));
		panel.add(time);
		
		return panel;
	}
	
	public static void main(String[] args) {
		new Server();
		try {
			ServerSocket skt = new ServerSocket(6001);
			while(true) {
				Socket s = skt.accept();
				DataInputStream din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				
				while(true) {
					a1.setLayout(new BorderLayout());
					String msg = din.readUTF();
					JPanel panel = clientFormatLabel(msg);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);
					vertical.add(Box.createVerticalStrut(15));
					
					a1.add(vertical, BorderLayout.PAGE_START);
					
					f.validate();
				}
				
			}
			
		} catch (Exception e) {};
	}

}
