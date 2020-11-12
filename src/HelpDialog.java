import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class HelpDialog extends JDialog {

	JLabel help_label;
	public HelpDialog(Grade_processing_program main_program, JFrame frame, String title) {
		super(frame, title, true); // 모달 다이얼로그로 설정
		setLayout(new FlowLayout());	
		
		help_label = new JLabel("오규태의 성적처리 프로그램.ver_1.13");
		
		this.add(help_label);
		setSize(390, 250);
		int frameX = main_program.frame.getX();
		int frameY = main_program.frame.getY();
		setLocation(frameX+130, frameY+100);
		
		setVisible(true);	
	}
}
