import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class fixDialog extends JDialog {
	JPanel p_id, p_name, p_attend, p_middleterm, p_endterm, p_task;
	JLabel id, name, attend, middleterm, endterm, task;
	JTextField f_id, f_name, f_attend, f_middleterm, f_endterm, f_task;
	
	private JButton okButton = new JButton("수정");
	private JButton cancelButton = new JButton("취소");
	
	public fixDialog(Grade_processing_program main_program, JFrame frame, String title,
		int row, String i_id, String i_name, String i_attend, 
		String i_middleterm, String i_endterm, String i_task) {
		
		super(frame, title, true); // 모달 다이얼로그로 설정
		setLayout(new FlowLayout());
		
		id = new JLabel("학 번  ");
		f_id = new JTextField(15);
		p_id = new JPanel();
		p_id.add(id);
		p_id.add(f_id);
		
		name = new JLabel("이 름  ");
		f_name = new JTextField(15);
		p_name = new JPanel();
		p_name.add(name);
		p_name.add(f_name);
		
		attend = new JLabel("출 석  ");
		f_attend = new JTextField(15);
		p_attend = new JPanel();
		p_attend.add(attend);
		p_attend.add(f_attend);
		
		middleterm = new JLabel("중 간  ");
		f_middleterm = new JTextField(15);
		p_middleterm = new JPanel();
		p_middleterm.add(middleterm);
		p_middleterm.add(f_middleterm);
		
		endterm = new JLabel("기 말  ");
		f_endterm = new JTextField(15);
		p_endterm = new JPanel();
		p_endterm.add(endterm);
		p_endterm.add(f_endterm);
		
		task = new JLabel("과 제  ");
		f_task = new JTextField(15);
		p_task = new JPanel();
		p_task.add(task);
		p_task.add(f_task);
		
		this.add(p_id);
		add(p_name);
		add(p_attend);
		add(p_middleterm);
		add(p_endterm);
		add(p_task);
		add(okButton);
		add(cancelButton);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

        		int sum = Integer.parseInt(f_attend.getText()) 
        				+ Integer.parseInt(f_middleterm.getText())
        				+ Integer.parseInt(f_endterm.getText())
        				+ Integer.parseInt(f_task.getText());
        		int ave = sum / 4;
				
				main_program.table.setValueAt(f_id.getText(), row, 0);
				main_program.table.setValueAt(f_name.getText(), row, 1);
				main_program.table.setValueAt(f_attend.getText(), row, 2);
				main_program.table.setValueAt(f_middleterm.getText(), row, 3);
				main_program.table.setValueAt(f_endterm.getText(), row, 4);
				main_program.table.setValueAt(f_task.getText(), row, 5);
				main_program.table.setValueAt(String.valueOf(sum), row, 6);
				main_program.table.setValueAt(String.valueOf(ave), row, 7);
				
				setVisible(false);
				dispose();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		setSize(460, 200);
		
		f_id.setText(i_id);
		f_name.setText(i_name);
		f_attend.setText(i_attend);
		f_middleterm.setText(i_middleterm);
		f_endterm.setText(i_endterm);
		f_task.setText(i_task);		
		
		int frameX = main_program.frame.getX();
		int frameY = main_program.frame.getY();
		setLocation(frameX+100, frameY+150);
	
		// 배경 Color가 왜 안먹을까?
		this.setBackground(new Color(255,255,204));
		
		setVisible(true);		
	}



}
