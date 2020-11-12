
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;


import javax.swing.BorderFactory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class chartDialog extends JDialog {
	
	JPanel p_id, p_name, p_attend, p_middleterm, p_endterm, p_task, allPanel, topPanel, bottomPanel;
	JLabel id, name, attend, middleterm, endterm, task;
	JTextField f_id, f_name, f_attend, f_middleterm, f_endterm, f_task;
	
	private JButton attendButton = new JButton("�⼮");
	private JButton middletermButton = new JButton("�߰�");
	private JButton endtermButton = new JButton("�⸻");
	private JButton taskButton = new JButton("����");
	private JButton sumButton = new JButton("�հ�");
	private JButton avgButton = new JButton("���");
	
	private Grade_processing_program main_dialog;
	
	private ChartPanel chartPanel;
	private JFreeChart chart;

	public chartDialog(Grade_processing_program main_program, JFrame frame, String title) {
		super(frame, title, true); // ��� ���̾�α׷� ����
		setLayout(new FlowLayout());	
		
		main_dialog = main_program;
		
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        allPanel = new JPanel();
        allPanel.setLayout(new BorderLayout());
        topPanel.add(attendButton);
        topPanel.add(middletermButton);
        topPanel.add(endtermButton);
        topPanel.add(taskButton);
        topPanel.add(sumButton);	
        topPanel.add(avgButton);
        
        allPanel.add(topPanel, "North");
        allPanel.add(bottomPanel, "South");
        
        Container c = getContentPane();

        c.add(allPanel);
        pack();
        setTitle("�׷���");
        setLocationRelativeTo(null);

		setSize(790, 450);
		int frameX = main_program.frame.getX();
		int frameY = main_program.frame.getY();
		setLocation(frameX+30, frameY+100);

		// ���ʿ��� �⼮ �׷����� ǥ����. �⼮�� table column index 2��.
		makeChartPanel(2);
		
		attendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        makeChartPanel(2);
		        chart.getTitle().setText("�⼮ �׷���");
			}
		});

		middletermButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        makeChartPanel(3);
		        chart.getTitle().setText("�߰� �׷���");
			}
		});
		
		endtermButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        makeChartPanel(4);
		        chart.getTitle().setText("�⸻ �׷���");
			}
		});
		
		taskButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        makeChartPanel(5);
		        chart.getTitle().setText("���� �׷���");
			}
		});
		
		sumButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        makeChartPanel(6);
		        chart.getTitle().setText("�հ� �׷���");
			}
		});
		
		avgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        makeChartPanel(7);
		        chart.getTitle().setText("��� �׷���");
			}
		});
		
		setVisible(true);	
	}

// ���° Color�� ���� Dataset���� �Ķ���ͷ� ���� ����	
    private CategoryDataset createDataset(int column) {
    	var dataset = new DefaultCategoryDataset();
    	JTable table = main_dialog.table;
        int rowCount = table.getRowCount();
        
        for(int i=0;i<rowCount;i++)
        {
            dataset.setValue(Integer.valueOf((String)table.getValueAt(i, column)), "", (Comparable)table.getValueAt(i, 1));
        }
        
        return dataset;
    }

    
    private JFreeChart createChart(CategoryDataset dataset) {

        JFreeChart barChart = ChartFactory.createBarChart(
                "�⼮ �׷���",
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        return barChart;
    }
    
    private void makeChartPanel(int chartRow) {
        bottomPanel.removeAll();
        bottomPanel.revalidate();
    	
    	CategoryDataset dataset = createDataset(chartRow);
        chart = createChart(dataset);
    
        chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new java.awt.Dimension(750, 350));
        
        Font font = new Font("����", Font.BOLD, 9) ;
        
        chart.getTitle().setFont(font);
        CategoryPlot p = chart.getCategoryPlot();
        p.getDomainAxis().setLabelFont(font);
        p.getDomainAxis().setTickLabelFont(font);
        p.getRangeAxis().setLabelFont(font);
        p.getRangeAxis().setTickLabelFont(font);

        bottomPanel.add(chartPanel);
    }
    
}
