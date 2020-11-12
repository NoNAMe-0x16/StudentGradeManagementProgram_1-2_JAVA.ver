import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class addDialog extends JDialog {

	JPanel p_id, p_name, p_attend, p_middleterm, p_endterm, p_task;
	JLabel id, name, attend, middleterm, endterm, task;
	JTextField f_id, f_name, f_attend, f_middleterm, f_endterm, f_task;
	
	private JButton okButton = new JButton("추가");
	private JButton cancelButton = new JButton("취소");
	
	public addDialog(Grade_processing_program main_program, JFrame frame, String title) {
		
		super(frame, title, true); // 모달 다이얼로그로 설정
		setLayout(new FlowLayout());
		
		String path = main_program.path;
		
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
				String id_tmp = f_id.getText();
				String name_tmp = f_name.getText();
				String attend_tmp = f_attend.getText();
				String middleterm_tmp = f_middleterm.getText();
				String endterm_tmp = f_endterm.getText();
				String task_tmp = f_task.getText();
				String message = null;
				try {
					if(id_tmp.contentEquals("")) {
						message = "학번을 입력해주세요.";
						JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(name_tmp.contentEquals("")) {
						message = "이름을 입력해주세요.";
						JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(attend_tmp.contentEquals("")) {
						message = "출석 점수를 입력해주세요.";
						JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(middleterm_tmp.contentEquals("")) {
						message = "중간고사 점수를 입력해주세요.";
						JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(endterm_tmp.contentEquals("")) {
						message = "기말고사 점수를 입력해주세요.";
						JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(task_tmp.contentEquals("")) {
						message = "과제 점수를 입력해주세요.";
						JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);		
					}
					else {
						FileWriter fileout = new FileWriter(path, true);
						
						fileout.append(id_tmp);
						fileout.append(",");
						fileout.append(name_tmp);
						fileout.append(",");
						fileout.append(attend_tmp);
						fileout.append(",");
						fileout.append(middleterm_tmp);
						fileout.append(",");
						fileout.append(endterm_tmp);
						fileout.append(",");
						fileout.append(task_tmp);
						fileout.append("\n");
						fileout.flush();
						fileout.close();
						setVisible(false);
						dispose();
					}
				} catch(FileNotFoundException fne) {
					System.out.println("FileNotFoundException error");
				} catch(IOException ioe) {
					System.out.println("IOException error");
				}	
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		setSize(460, 200);
		
		int frameX = main_program.frame.getX();
		int frameY = main_program.frame.getY();
		setLocation(frameX+100, frameY+150);

		setVisible(true);		
	}
}
