package edu_vul_use;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class gui {
	private JFrame jf;
	private JPanel jp;
	private JTextField jt;//ѧ��
	private JLabel jl1;
	Dimension size = new Dimension(350, 170);//��С
	JButton jb1,jb2;
	
	public gui(){
		   jf=new JFrame("�廪��ѧѡ��ϵͳ©�����ù���");
		   jf.setBounds(0, 0, 350, 170);
		   jp=new JPanel();
		   jp.setLayout(null);   
		   jl1=new JLabel("ѧ�ţ�");
		   jl1.setBounds(30, 25, 50, 30);
		   jp.add(jl1);
	       jt=new JTextField(12);
	       jt.setBounds(100,25,200,30);
	       jp.add(jt);
	       jp.setPreferredSize(size);
	       jb1=new JButton("������");
	       jb1.addMouseListener(new MouseAdapter(){    
	    	   public void mouseClicked(MouseEvent e)    {
	    	   String sno=jt.getText();
	    	   String password= client.re_password(client.request(client.get_url_1(sno)));
	    	   JOptionPane.showMessageDialog(null,"���룺 "+password,"����",JOptionPane.INFORMATION_MESSAGE);
	    	   }});
	       jb1.setBounds(30,80,100,30);
	       jp.add(jb1);
	       jb2=new JButton("����Ϣ");
	       jb2.setBounds(180,80,100,30);
	       jb2.addMouseListener(new MouseAdapter(){    
	    	   public void mouseClicked(MouseEvent e)    {
	    	   String sno=jt.getText();
	    	   ArrayList<String> list= client.re_info(client.request(client.get_url_2(sno)));
	    	   String info="\n";
	    	   Iterator<String> it=list.iterator();
	    	   while(it.hasNext())
	    		   info=info+it.next()+"\n";
	    	   JOptionPane.showMessageDialog(null,"��Ϣ�� "+info,"ѧ����Ϣ��",JOptionPane.INFORMATION_MESSAGE);
	    	   
	    	   }});
	       jp.add(jb2);
	       jf.add(jp);     
	       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       jf.setVisible(true);


	}
	public static void main(String[] args) {
		new gui();	
	}

}
