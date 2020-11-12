import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class addDialog extends JDialog {

	JPanel p_id, p_name, p_attend, p_middleterm, p_endterm, p_task;
	JLabel id, name, attend, middleterm, endterm, task;
	JTextField f_id, f_name, f_attend, f_middleterm, f_endterm, f_task;
	
	private JButton okButton = new JButton("�߰�");
	private JButton cancelButton = new JButton("���");
	
	public addDialog(Grade_processing_program main_program, JFrame frame, String title) {
		
		super(frame, title, true); // ��� ���̾�α׷� ����
		setLayout(new FlowLayout());
		
		String path = main_program.path;
		
		id = new JLabel("�� ��  ");
		f_id = new JTextField(15);
		p_id = new JPanel();
		p_id.add(id);
		p_id.add(f_id);
		
		name = new JLabel("�� ��  ");
		f_name = new JTextField(15);
		p_name = new JPanel();
		p_name.add(name);
		p_name.add(f_name);
		
		attend = new JLabel("�� ��  ");
		f_attend = new JTextField(15);
		p_attend = new JPanel();
		p_attend.add(attend);
		p_attend.add(f_attend);
		
		middleterm = new JLabel("�� ��  ");
		f_middleterm = new JTextField(15);
		p_middleterm = new JPanel();
		p_middleterm.add(middleterm);
		p_middleterm.add(f_middleterm);
		
		endterm = new JLabel("�� ��  ");
		f_endterm = new JTextField(15);
		p_endterm = new JPanel();
		p_endterm.add(endterm);
		p_endterm.add(f_endterm);
		
		task = new JLabel("�� ��  ");
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
						message = "�й��� �Է����ּ���.";
						JOptionPane.showMessageDialog(frame, message, "�޼���", JOptionPane.INFORMATION_MESSAGE);
					}
					else if(name_tmp.contentEquals("")) {
						message = "�̸��� �Է����ּ���.";
						JOptionPane.showMessageDialog(frame, message, "�޼���", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(attend_tmp.contentEquals("")) {
						message = "�⼮ ������ �Է����ּ���.";
						JOptionPane.showMessageDialog(frame, message, "�޼���", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(middleterm_tmp.contentEquals("")) {
						message = "�߰���� ������ �Է����ּ���.";
						JOptionPane.showMessageDialog(frame, message, "�޼���", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(endterm_tmp.contentEquals("")) {
						message = "�⸻��� ������ �Է����ּ���.";
						JOptionPane.showMessageDialog(frame, message, "�޼���", JOptionPane.INFORMATION_MESSAGE);		
					}
					else if(task_tmp.contentEquals("")) {
						message = "���� ������ �Է����ּ���.";
						JOptionPane.showMessageDialog(frame, message, "�޼���", JOptionPane.INFORMATION_MESSAGE);		
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
