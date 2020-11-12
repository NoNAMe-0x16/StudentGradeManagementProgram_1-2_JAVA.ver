import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Grade_processing_program implements MouseListener, ActionListener {

	File csvFile;
	String line = null;
	String path = "grade_data.csv";
	String cvsSplit = ",";
	
	JFrame frame;
	JPanel rightpanel, bottompanel, center, p_padding, p_id, p_name, p_attend, p_middleterm, p_endterm, p_task, p_sum, p_average;
	JLabel empty, L_id, L_name, L_attend, L_middleterm, L_endterm, L_task, L_sum, L_average ;
	JTextField search, s_id, s_name, s_attend, s_middleterm, s_endterm, s_task, s_sum, s_average;
	JTable table;
	JButton add,delete,update,save,clear,all,sbutton;
	CustomTableModel model;
	
	JMenuItem chartMenuItem, CalcMenuItem, helpMenuItem, fileOpenMenuItem, otherSaveMenuItem,
		newFileMenuItem, exitMenuItem, excelMenuItem, htmlMenuItem, txtMenuItem;
	
	Vector<String> title;
	Vector<Object> outer;
	
	JMenuBar mb;
	JMenu fileMenu;
	JMenu printMenu;
	JMenu toolMenu;
	JMenu helpMenu;
	
	String items[] = {"�й�", "�̸�"};
	JComboBox<String> combo; {
		try {
			makeGui();
			initTable(); // ���̺� �ʱ�ȭ					
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeGui() {	
		frame = new JFrame("�������� ���� ���� ���α׷�");
		createMenu();
		
		add = new JButton("���� ������ �Է�");		
		delete = new JButton("���� ������ ����");		
		update = new JButton("���� ������ ����");
		save = new JButton("���� ������ ����");
						
		combo = new JComboBox<>(items);
		search = new JTextField(15);

		sbutton = new JButton("�˻�");		
		clear = new JButton("�����");		
		all = new JButton("��ü����");
		
		// ��� �̺�Ʈ �߻� ��ư�� ActionListener ����ϱ�**
        add.addActionListener(this);
        delete.addActionListener(this);
        update.addActionListener(this);
        save.addActionListener(this);
        search.addActionListener(this);
        sbutton.addActionListener(this);
        clear.addActionListener(this);
        all.addActionListener(this);
		
		bottompanel = new JPanel();
		bottompanel.add(combo);
		bottompanel.add(search);
		bottompanel.add(sbutton);
		bottompanel.add(clear);
		bottompanel.add(all);		
		
		L_id = new JLabel("�� �� : ");
		s_id = new JTextField(10);
		s_id.setEditable(false);
		
		L_name = new JLabel("�� �� : ");
		s_name = new JTextField(10);
		s_name.setEditable(false);
		
		L_attend = new JLabel("�� �� : ");
		s_attend = new JTextField(10);
		s_attend.setEditable(false);
		
		L_middleterm = new JLabel("�� �� : ");
		s_middleterm = new JTextField(10);
		s_middleterm.setEditable(false);
		
		L_endterm = new JLabel("�� �� : ");
		s_endterm = new JTextField(10);
		s_endterm.setEditable(false);
		
		L_task = new JLabel("�� �� : ");
		s_task = new JTextField(10);
		s_task.setEditable(false);
		
		L_sum = new JLabel("�� �� : ");
		s_sum = new JTextField(10);
		s_sum.setEditable(false);
		
		L_average = new JLabel("�� �� : ");
		s_average = new JTextField(10);
		s_average.setEditable(false);
		
		p_padding = new JPanel();
		empty = new JLabel("----------------------------");
		
		p_padding.add(empty);


		p_id = new JPanel(new BorderLayout());
		p_id.add(L_id, "West");
		p_id.add(s_id, "East");
		
		p_name = new JPanel(new BorderLayout());
		p_name.add(L_name, "West");
		p_name.add(s_name, "East");
		
		p_attend = new JPanel(new BorderLayout());
		p_attend.add(L_attend, "West");
		p_attend.add(s_attend, "East");
		
		p_middleterm = new JPanel(new BorderLayout());
		p_middleterm.add(L_middleterm, "West");
		p_middleterm.add(s_middleterm, "East");
		
		p_endterm = new JPanel(new BorderLayout());
		p_endterm.add(L_endterm, "West");
		p_endterm.add(s_endterm, "East");
		
		p_task = new JPanel(new BorderLayout());
		p_task.add(L_task, "West");
		p_task.add(s_task, "East");
		
		p_sum = new JPanel(new BorderLayout());
		p_sum.add(L_sum, "West");
		p_sum.add(s_sum, "East");
		
		p_average = new JPanel(new BorderLayout());
		p_average.add(L_average, "West");
		p_average.add(s_average, "East");
		
		rightpanel = new JPanel();
		rightpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		rightpanel.add(add);
		rightpanel.add(delete);
		rightpanel.add(update);
		rightpanel.add(save);
		
		rightpanel.add(p_padding);
		rightpanel.add(p_id);
		rightpanel.add(p_name);
		rightpanel.add(p_attend);
		rightpanel.add(p_middleterm);
		rightpanel.add(p_endterm);
		rightpanel.add(p_task);
		rightpanel.add(p_sum);
		rightpanel.add(p_average);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // x��ưŬ���� â����
		
		Container c = frame.getContentPane();

		// JTable, JTable ������ �����ϱ�**
		model = new CustomTableModel();
        table = new JTable(model);

		// ���̺� MouseListener ����ϱ�**
		table.addMouseListener(this);

		table.getTableHeader().setBackground(new Color(192, 192, 192));
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		
// Table ��ü�� Sort ����� Ȱ��ȭ �ϸ� �� �κ��� �ʿ� ����.		
//        table.getTableHeader().addMouseListener(new HeaderSortListener());

		// ���̺��� JScrollPane(sp) ���� ���**
		JScrollPane pane = new JScrollPane(table);

		center = new JPanel(new BorderLayout());
		center.add(rightpanel,"Center");
		
		// **center�� ����(West)�� table(pane) ���̱�**
        center.add(pane, "West");
		
		c.add(center,"Center");	
		c.add(bottompanel,"South");	
		
		frame.setLocation(400,200);
		frame.setResizable(false);
		frame.setSize(650,500);
		frame.setVisible(true);	
	}

// Table ��ü�� Sort ��� Ȱ��ȭ �ϸ� �� �κ� �ʿ� ����.	
/*	
	class HeaderSortListener extends MouseAdapter  {
	    public void mouseClicked(MouseEvent e) {
	         TableColumnModel columnModel = table.getColumnModel();
	         int column = columnModel.getColumnIndexAtX(e.getX());
	         if (e.getClickCount() == 1 && column != -1) {
	              model.sortColumn(column);
	         }
	    }
	}
*/
	
	public void createMenu() {
		mb = new JMenuBar();
		fileMenu = new JMenu("����");
		printMenu = new JMenu("���");
		toolMenu = new JMenu("����");
		helpMenu = new JMenu("����");

		chartMenuItem = new JMenuItem("�׷�������");
		CalcMenuItem = new JMenuItem("����");
		fileOpenMenuItem = new JMenuItem("�ҷ�����");
		otherSaveMenuItem = new JMenuItem("�ٸ��̸���������");
		newFileMenuItem = new JMenuItem("���� �����");
		exitMenuItem = new JMenuItem("������");
		excelMenuItem = new JMenuItem("Excel����ǥ");
		htmlMenuItem = new JMenuItem("HTML����ǥ");
		txtMenuItem = new JMenuItem("TXT����ǥ");
		helpMenuItem = new JMenuItem("������~");
		

		fileOpenMenuItem.addActionListener(this);
		otherSaveMenuItem.addActionListener(this);
		newFileMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);

		excelMenuItem.addActionListener(this);
		htmlMenuItem.addActionListener(this);
		txtMenuItem.addActionListener(this);

		chartMenuItem.addActionListener(this);
		CalcMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
		
		fileMenu.add(fileOpenMenuItem);
		fileMenu.add(otherSaveMenuItem);
		fileMenu.add(newFileMenuItem);
		fileMenu.addSeparator(); // �и��� ����
		fileMenu.add(exitMenuItem);
		
		printMenu.add(excelMenuItem);
		printMenu.add(htmlMenuItem);
		printMenu.add(txtMenuItem);

		toolMenu.add(CalcMenuItem);
		toolMenu.add(chartMenuItem);
		
		helpMenu.add(helpMenuItem);
		

		mb.add(fileMenu);
		mb.add(printMenu); 
		mb.add(toolMenu);
		mb.add(helpMenu);
		
		frame.setJMenuBar(mb);
	}
	
	private void initTable() {
		
		title = new Vector<>();

		title.add("�� ��");
		title.add("�� ��");
		title.add("�� ��");
		title.add("�� ��");
		title.add("�� ��");
		title.add("�� ��");
		title.add("�� ��");
		title.add("�� ��");
		
		outer = new Vector<>();
        
        csvFile = new File(path);
        
        try {
        	BufferedReader bufReader = new BufferedReader(new FileReader(csvFile));

        	while((line = bufReader.readLine()) != null) {
        		String s_tmp[] = line.split(cvsSplit);
    			String [] tmp = new String [8];
        		for(int i = 0; i < s_tmp.length; i++) {
        			tmp[i] = s_tmp[i];
        		}
        		int sum = 0, ave = 0;
        		sum = Integer.parseInt(s_tmp[2]) + Integer.parseInt(s_tmp[3]) + Integer.parseInt(s_tmp[4]) + Integer.parseInt(s_tmp[5]);
        		tmp[6] = String.valueOf(sum);
        		ave = sum / 4;
        		tmp[7] = String.valueOf(ave);
        		outer.add(changeVector(tmp));
        	}
        
        } catch(FileNotFoundException e) {
        	e.printStackTrace();
        } catch(IOException e) {
        	e.printStackTrace();
        }
        
	    // JTable �����ڷ� ���̺� �׸���
        model.setDataVector((Vector)outer, (Vector)title);

        // Table�� Sort ��� Ȱ��ȭ
        table.setAutoCreateRowSorter(true);
        TableRowSorter<CustomTableModel> tablesorter = new TableRowSorter(model);

        // ������ ��� ���ڷ� ���ϵ��� Comparator ���� (Override)
        Comparator<String> comparator = new Comparator<String>() {
        	@Override
            public int compare(String o1, String o2)  {

            	if (o1 == null) return -1;
                if (o2 == null) return 1;

                return Integer.valueOf(o2) - Integer.valueOf(o1);
            }
        };
        // 2,3,4,5,6,7 �÷��� ������ ũ��� Sort
        tablesorter.setComparator(2, comparator);
        tablesorter.setComparator(3, comparator);
        tablesorter.setComparator(4, comparator);
        tablesorter.setComparator(5, comparator);
        tablesorter.setComparator(6, comparator);
        tablesorter.setComparator(7, comparator);
        table.setRowSorter(tablesorter);

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer celAlignLeft = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.LEFT);
		
		table.getColumn("�� ��").setPreferredWidth(120);
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		table.getColumn("�� ��").setPreferredWidth(100);
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		table.getColumn("�� ��").setCellRenderer(celAlignCenter);
		
		table.setBackground(new Color(255,255,204));
		
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "�޼���", JOptionPane.INFORMATION_MESSAGE);		
	}
	
	public Vector<String> changeVector(String[] array) {
		Vector<String> in = new Vector<>();
		for(String data : array){
			in.add(data);
		}		
		return in;
	}
	
    private void delete() {
        int index = table.getSelectedRow();
        if(index < 0){
            showMessage("������ ���� ������ �ּ���.");
        }else{
            model.removeRow(index);
        }
    }
    
    private void update() {
        int index = table.getSelectedRow();
        if(index < 0){
            showMessage("������ ���� ������ �ּ���.");
        }else{
        	int selectedRow = table.getSelectedRow();
        	String s_id = String.valueOf(table.getValueAt(selectedRow, 0));
        	String s_name = String.valueOf(table.getValueAt(selectedRow, 1));
        	String s_attend = String.valueOf(table.getValueAt(selectedRow, 2));
        	String s_middleterm = String.valueOf(table.getValueAt(selectedRow, 3));
        	String s_endterm = String.valueOf(table.getValueAt(selectedRow, 4));
        	String s_task = String.valueOf(table.getValueAt(selectedRow, 5));
        	
        	fixDialog dialog = new fixDialog(this, frame, "���� ������ ����", selectedRow, s_id, s_name, s_attend, s_middleterm, s_endterm, s_task);
        }
    }
    
    public void save() {
    	 try {
    		FileWriter fileout = new FileWriter(path);
    		
    		for(int i = 0; i < model.getRowCount(); i++) {
    			for(int k = 0; k < 6; k++) {
    				fileout.write((String)(model.getValueAt(i, k)));
    				fileout.write(cvsSplit);
    			}
    			fileout.write("\n");
     		}
    		fileout.close();
			showMessage("���������� ����Ǿ����ϴ�.");
         
         } catch(FileNotFoundException e) {
         	e.printStackTrace();
         } catch(IOException e) {
         	e.printStackTrace();
         }
    }
    
	public void clear() {
        search.setText("");
    }
	
	private void search() {
        // �޺��ڽ� �� �˾Ƴ��� - �˻�����
        String field = (String)combo.getSelectedItem();
        
        // �ؽ�Ʈ�ʵ尪 �˾ƿ��� - �˻���
        String word = search.getText();
        Vector<Vector> sresult = new Vector<>();
        // ã�Ƽ� ��������
        int index = 0;
        if(field.equals("�й�")){
            index = 0;
        } else if(field.equals("�̸�")) {
        	index = 1;
        } else if(field.equals("�̸�")) {
        	
        }
        for(Object in : outer){
        	Vector<String> tmp = (Vector<String>) in;
            if(word.equals(tmp.get(index))){
                sresult.add(tmp);
            }
        }
        // �˻���� üũ�� ���̺� ǥ��
        if(sresult.size()>0){
            model.setDataVector(sresult, title);
        }else{
            showMessage("�˻� ����� �����ϴ�.");
        }
    }
	
	private void showAll() {
        model.setDataVector((Vector)outer, (Vector)title);
    }
	
	private void popupChart() {
		System.out.println("popupChart~");
		
    	chartDialog dialog = new chartDialog(this, frame, "Chart");
	}
	
	private void popupCalc() {
		System.out.println("popupCalc~");
		
    	Calculator dialog = new Calculator(this, frame, "Calculator");
	}
	
	private void popupHelp() {
		System.out.println("popupHelp~");
		
		HelpDialog dialog = new HelpDialog(this, frame, "Help");
	}
	
	private void fileOpen() {
		System.out.println("fileOpen~");

		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("����ó�� CSV File","csv"));

        int returnVal = jfc.showOpenDialog(null);
        if(returnVal == 0) {
        	csvFile = jfc.getSelectedFile();
        	path = csvFile.getPath();
        	
        	initTable();
        }
        else
        {
            System.out.println("Cancel fileopen~");
        }		
	}

	private void otherSave() {
		System.out.println("otherSave~");
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("����ó�� CSV File","csv"));
		int returnVal = jfc.showOpenDialog(null);
        if(returnVal == 0) {
        	csvFile = jfc.getSelectedFile();
        	path = csvFile.getPath();
        	csvFile = new File(path);
        	save();
        }
        else
        {
            System.out.println("Cancel fileopen~");
        }		
	}

	private void newFile() {
		System.out.println("newFile~");
		
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("����ó�� CSV File","csv"));		

		int returnVal = jfc.showOpenDialog(null);
        if(returnVal == 0) {
        	csvFile = jfc.getSelectedFile();
        	path = csvFile.getPath();
        	csvFile = new File(path);
        	
        	try {
        		csvFile.createNewFile();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        	
        	while(model.getRowCount() > 0) {
        		model.removeRow(0);
        	}
        }
        else
        {
            System.out.println("Cancel fileopen~");
        }		
	}

	private void exit() {
		System.out.println("exit~");
		System.exit(0);
	}

	private void excel() {
		System.out.println("excel~");
		
		File excelfile = new File("gradezip_excel.csv");
		FileWriter writer = null;
		
		if(excelfile.exists()) {
			if(excelfile.isDirectory()) {
				File[] files = excelfile.listFiles();
				
				for(int i = 0; i < files.length; i++) {
					if(files[i].delete() ) {
						System.out.println(files[i].getName() + "���� ����.");
					}
					else {
						System.out.println(files[i].getName() + "���� ����.");
					}
				}
			}
			
			if(excelfile.delete()) {
				System.out.println("���ϻ��� ����.");
			} else {
				System.out.println("���ϻ��� ����.");
			}
		} else {
			System.out.println("������ �������� �ʽ��ϴ�.");
		}
		
		try {
			writer = new FileWriter(excelfile, true);
			writer.write("\n\t��������ǥ.csv");
			writer.write("\n =========,======,======,======,======,======,======,======");
			writer.write("\n �й�,�̸�,�⼮,�߰�,�⸻,����,�հ�,���");
			writer.write("\n =========,======,======,======,======,======,======,====== \n");
			
			for(int i = 0; i < model.getRowCount(); i++) {
    			for(int k = 0; k < 8; k++) {
    				writer.write((String)(model.getValueAt(i, k)));
    				writer.write(cvsSplit);
    			}
    			writer.write("\n");
     		}
			writer.write("\n =========,======,======,======,======,======,======,======");
			
		} catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
	}

	private void html() {
		System.out.println("html~");
		
		File HTMLfile = new File("gradezip_HTML.HTML");
		FileWriter writer = null;
		if(HTMLfile.exists()) {
			if(HTMLfile.isDirectory()) {
				File[] files = HTMLfile.listFiles();
				
				for(int i = 0; i < files.length; i++) {
					if(files[i].delete() ) {
						System.out.println(files[i].getName() + "���� ����.");
					}
					else {
						System.out.println(files[i].getName() + "���� ����.");
					}
				}
			}
			
			if(HTMLfile.delete()) {
				System.out.println("���ϻ��� ����.");
			} else {
				System.out.println("���ϻ��� ����.");
			}
		} else {
			System.out.println("������ �������� �ʽ��ϴ�.");
		}
		
		try {
			writer = new FileWriter(HTMLfile, true);
			writer.write("<!DOCTYPE html><HTML><HEAD> \n");
			writer.write("<TITLE>�������� HTML ����ǥ</TITLE> \n");
			writer.write("<STYLE> \n");
			writer.write("table {border-collapse: collapse; text-align: center; width: 500px; } \n");
			writer.write("table tr:hover {background-color: lightgreen; height:40px;} \n");
			writer.write("</STYLE></HEAD><BODY> \n");
			writer.write("<DIV align=center><H2>�������� ����ǥ �����(HTML)</H2></DIV> \n");
			writer.write("<TABLE border=1 align=center> \n");
			writer.write("<THEAD> \n");
			writer.write("<TR><TH>�й�</TH><TH>�̸�</TH><TH>�⼮</TH><TH>�߰�</TH><TH>�⸻</TH><TH>����</TH><TH>�հ�</TH><TH>���</TH></TR> \n");
			writer.write("</THEAD><TBODY> \n");
			
			for(int i = 0; i < model.getRowCount(); i++) {
				writer.write("<TR>\n");
    			for(int k = 0; k < 8; k++) {
    				writer.write("<TD>");
    				writer.write((String)(model.getValueAt(i, k)));
    				writer.write("</TD>");
    			}
    			writer.write("</TR>\n");
     		}
			writer.write("</TBODY> \n");
			writer.write("</TABLE> \n");
			writer.write("</BODY> \n </HTML> \n");
		} catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
	}

	private void txt() {
		System.out.println("txt~");
		
		File txtfile = new File("gradezip_txt.txt");
		FileWriter writer = null;
		
		if(txtfile.exists()) {
			if(txtfile.isDirectory()) {
				File[] files = txtfile.listFiles();
				
				for(int i = 0; i < files.length; i++) {
					if(files[i].delete() ) {
						System.out.println(files[i].getName() + "���� ����.");
					}
					else {
						System.out.println(files[i].getName() + "���� ����.");
					}
				}
			}
			
			if(txtfile.delete()) {
				System.out.println("���ϻ��� ����.");
			} else {
				System.out.println("���ϻ��� ����.");
			}
		} else {
			System.out.println("������ �������� �ʽ��ϴ�.");
		}
		
		try {
			writer = new FileWriter(txtfile, true);
			writer.write("\n\t��������ǥ.txt");
			writer.write("\n========= ====== ==== ==== ==== ==== ==== ====");
			writer.write("\n��          ��   ��    ��  �� ��  �� ��  �� ��  �� ��  �� ��  �� ��");
			writer.write("\n========= ====== ==== ==== ==== ==== ==== ==== \n");
			
			for(int i = 0; i < model.getRowCount(); i++) {
    			for(int k = 0; k < 8; k++) {
    				writer.write((String)(model.getValueAt(i, k)));
    				writer.write(" ");
    			}
    			writer.write("\n");
     		}
			writer.write("\n========= ====== ==== ==== ==== ==== ==== ====");
			
		} catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
		
	}

	// ActionEvent �߻� �� ����Ǵ� �޼ҵ� -> ��ưŬ����, �Է�ĭ�� �Է��� ����Ű�� ������
	public void actionPerformed(ActionEvent w) {		
		Object o = w.getSource(); // �̺�Ʈ�� �߻��� ��ü�� ����
	
        if(o == add){
        	addDialog dialog = new addDialog(this, frame, "���� ������ �Է�");
        	initTable();
        }else if(o == delete){
            delete();
            //clear();
        }else if(o == update){
            update();
//            initTable();
        }else if(o == save){
            save();
            initTable();
        }else if(o == search || o == sbutton){
            search();
        }else if(o == clear){
            clear();
        }else if(o == all){
            showAll();
        }else if(o == chartMenuItem) {
        	popupChart();
        }else if(o == CalcMenuItem) {
        	popupCalc();
        }else if(o == helpMenuItem) {
        	popupHelp();
        }else if(o == fileOpenMenuItem) {
        	fileOpen();
        }else if(o == otherSaveMenuItem) {
        	otherSave();
        }else if(o == newFileMenuItem) {
        	newFile();
        }else if(o == exitMenuItem) {
        	exit();
        }else if(o == excelMenuItem) {
        	excel();
        }else if(o == htmlMenuItem) {
        	html();
        }else if(o == txtMenuItem) {
        	txt();
        }
        
	}
	
	// ���̺��� ���� ���콺�� Ŭ������ �� ����Ǵ� �޼ҵ�
	public void mouseClicked(MouseEvent arg0) {
        System.out.println("mouse click");
        int index = table.getSelectedRow();
        
        s_id.setText((String)table.getValueAt(index, 0));
        s_name.setText((String)table.getValueAt(index, 1));
        s_attend.setText((String)table.getValueAt(index, 2));
        s_middleterm.setText((String)table.getValueAt(index, 3));
        s_endterm.setText((String)table.getValueAt(index, 4));
        s_task.setText((String)table.getValueAt(index, 5));
        s_sum.setText((String)table.getValueAt(index, 6));
        s_average.setText((String)table.getValueAt(index, 7));
    }
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Grade_processing_program f = new Grade_processing_program();
	}

}
