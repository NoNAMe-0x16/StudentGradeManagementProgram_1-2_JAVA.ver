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
	
	String items[] = {"학번", "이름"};
	JComboBox<String> combo; {
		try {
			makeGui();
			initTable(); // 테이블 초기화					
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeGui() {	
		frame = new JFrame("오규태의 성적 관리 프로그램");
		createMenu();
		
		add = new JButton("성적 데이터 입력");		
		delete = new JButton("성적 데이터 삭제");		
		update = new JButton("성적 데이터 수정");
		save = new JButton("성적 데이터 저장");
						
		combo = new JComboBox<>(items);
		search = new JTextField(15);

		sbutton = new JButton("검색");		
		clear = new JButton("지우기");		
		all = new JButton("전체보기");
		
		// 모든 이벤트 발생 버튼에 ActionListener 등록하기**
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
		
		L_id = new JLabel("학 번 : ");
		s_id = new JTextField(10);
		s_id.setEditable(false);
		
		L_name = new JLabel("이 름 : ");
		s_name = new JTextField(10);
		s_name.setEditable(false);
		
		L_attend = new JLabel("출 석 : ");
		s_attend = new JTextField(10);
		s_attend.setEditable(false);
		
		L_middleterm = new JLabel("중 간 : ");
		s_middleterm = new JTextField(10);
		s_middleterm.setEditable(false);
		
		L_endterm = new JLabel("기 말 : ");
		s_endterm = new JTextField(10);
		s_endterm.setEditable(false);
		
		L_task = new JLabel("과 제 : ");
		s_task = new JTextField(10);
		s_task.setEditable(false);
		
		L_sum = new JLabel("합 계 : ");
		s_sum = new JTextField(10);
		s_sum.setEditable(false);
		
		L_average = new JLabel("평 균 : ");
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
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // x버튼클릭시 창꺼짐
		
		Container c = frame.getContentPane();

		// JTable, JTable 관리자 생성하기**
		model = new CustomTableModel();
        table = new JTable(model);

		// 테이블에 MouseListener 등록하기**
		table.addMouseListener(this);

		table.getTableHeader().setBackground(new Color(192, 192, 192));
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		
// Table 자체의 Sort 기능을 활성화 하면 이 부분은 필요 없음.		
//        table.getTableHeader().addMouseListener(new HeaderSortListener());

		// 테이블을 JScrollPane(sp) 위에 얹기**
		JScrollPane pane = new JScrollPane(table);

		center = new JPanel(new BorderLayout());
		center.add(rightpanel,"Center");
		
		// **center의 서쪽(West)에 table(pane) 붙이기**
        center.add(pane, "West");
		
		c.add(center,"Center");	
		c.add(bottompanel,"South");	
		
		frame.setLocation(400,200);
		frame.setResizable(false);
		frame.setSize(650,500);
		frame.setVisible(true);	
	}

// Table 자체의 Sort 기능 활성화 하면 이 부분 필요 없음.	
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
		fileMenu = new JMenu("파일");
		printMenu = new JMenu("출력");
		toolMenu = new JMenu("도구");
		helpMenu = new JMenu("도움말");

		chartMenuItem = new JMenuItem("그래프보기");
		CalcMenuItem = new JMenuItem("계산기");
		fileOpenMenuItem = new JMenuItem("불러오기");
		otherSaveMenuItem = new JMenuItem("다른이름으로저장");
		newFileMenuItem = new JMenuItem("새로 만들기");
		exitMenuItem = new JMenuItem("나가기");
		excelMenuItem = new JMenuItem("Excel집계표");
		htmlMenuItem = new JMenuItem("HTML집계표");
		txtMenuItem = new JMenuItem("TXT집계표");
		helpMenuItem = new JMenuItem("도와줘~");
		

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
		fileMenu.addSeparator(); // 분리선 삽입
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

		title.add("학 번");
		title.add("이 름");
		title.add("출 석");
		title.add("중 간");
		title.add("기 말");
		title.add("과 제");
		title.add("합 계");
		title.add("평 균");
		
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
        
	    // JTable 관리자로 테이블 그리기
        model.setDataVector((Vector)outer, (Vector)title);

        // Table의 Sort 기능 활성화
        table.setAutoCreateRowSorter(true);
        TableRowSorter<CustomTableModel> tablesorter = new TableRowSorter(model);

        // 숫자일 경우 숫자로 비교하도록 Comparator 구현 (Override)
        Comparator<String> comparator = new Comparator<String>() {
        	@Override
            public int compare(String o1, String o2)  {

            	if (o1 == null) return -1;
                if (o2 == null) return 1;

                return Integer.valueOf(o2) - Integer.valueOf(o1);
            }
        };
        // 2,3,4,5,6,7 컬럼은 숫자의 크기로 Sort
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
		
		table.getColumn("학 번").setPreferredWidth(120);
		table.getColumn("학 번").setCellRenderer(celAlignCenter);
		table.getColumn("이 름").setPreferredWidth(100);
		table.getColumn("이 름").setCellRenderer(celAlignCenter);
		
		table.getColumn("출 석").setCellRenderer(celAlignCenter);
		table.getColumn("중 간").setCellRenderer(celAlignCenter);
		table.getColumn("기 말").setCellRenderer(celAlignCenter);
		table.getColumn("과 제").setCellRenderer(celAlignCenter);
		table.getColumn("합 계").setCellRenderer(celAlignCenter);
		table.getColumn("평 균").setCellRenderer(celAlignCenter);
		
		table.setBackground(new Color(255,255,204));
		
	}
	
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(frame, message, "메세지", JOptionPane.INFORMATION_MESSAGE);		
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
            showMessage("삭제할 행을 선택해 주세요.");
        }else{
            model.removeRow(index);
        }
    }
    
    private void update() {
        int index = table.getSelectedRow();
        if(index < 0){
            showMessage("수정할 행을 선택해 주세요.");
        }else{
        	int selectedRow = table.getSelectedRow();
        	String s_id = String.valueOf(table.getValueAt(selectedRow, 0));
        	String s_name = String.valueOf(table.getValueAt(selectedRow, 1));
        	String s_attend = String.valueOf(table.getValueAt(selectedRow, 2));
        	String s_middleterm = String.valueOf(table.getValueAt(selectedRow, 3));
        	String s_endterm = String.valueOf(table.getValueAt(selectedRow, 4));
        	String s_task = String.valueOf(table.getValueAt(selectedRow, 5));
        	
        	fixDialog dialog = new fixDialog(this, frame, "성적 데이터 수정", selectedRow, s_id, s_name, s_attend, s_middleterm, s_endterm, s_task);
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
			showMessage("성공적으로 저장되었습니다.");
         
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
        // 콤보박스 값 알아내기 - 검색기준
        String field = (String)combo.getSelectedItem();
        
        // 텍스트필드값 알아오기 - 검색어
        String word = search.getText();
        Vector<Vector> sresult = new Vector<>();
        // 찾아서 가져오기
        int index = 0;
        if(field.equals("학번")){
            index = 0;
        } else if(field.equals("이름")) {
        	index = 1;
        } else if(field.equals("이름")) {
        	
        }
        for(Object in : outer){
        	Vector<String> tmp = (Vector<String>) in;
            if(word.equals(tmp.get(index))){
                sresult.add(tmp);
            }
        }
        // 검색결과 체크후 테이블에 표시
        if(sresult.size()>0){
            model.setDataVector(sresult, title);
        }else{
            showMessage("검색 결과가 없습니다.");
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
		jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("성적처리 CSV File","csv"));

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
		jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("성적처리 CSV File","csv"));
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
		jfc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("성적처리 CSV File","csv"));		

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
						System.out.println(files[i].getName() + "삭제 성공.");
					}
					else {
						System.out.println(files[i].getName() + "삭제 실패.");
					}
				}
			}
			
			if(excelfile.delete()) {
				System.out.println("파일삭제 성공.");
			} else {
				System.out.println("파일삭제 실패.");
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
		}
		
		try {
			writer = new FileWriter(excelfile, true);
			writer.write("\n\t성적집계표.csv");
			writer.write("\n =========,======,======,======,======,======,======,======");
			writer.write("\n 학번,이름,출석,중간,기말,과제,합계,평균");
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
						System.out.println(files[i].getName() + "삭제 성공.");
					}
					else {
						System.out.println(files[i].getName() + "삭제 실패.");
					}
				}
			}
			
			if(HTMLfile.delete()) {
				System.out.println("파일삭제 성공.");
			} else {
				System.out.println("파일삭제 실패.");
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
		}
		
		try {
			writer = new FileWriter(HTMLfile, true);
			writer.write("<!DOCTYPE html><HTML><HEAD> \n");
			writer.write("<TITLE>오규태의 HTML 성적표</TITLE> \n");
			writer.write("<STYLE> \n");
			writer.write("table {border-collapse: collapse; text-align: center; width: 500px; } \n");
			writer.write("table tr:hover {background-color: lightgreen; height:40px;} \n");
			writer.write("</STYLE></HEAD><BODY> \n");
			writer.write("<DIV align=center><H2>오규태의 성적표 만들기(HTML)</H2></DIV> \n");
			writer.write("<TABLE border=1 align=center> \n");
			writer.write("<THEAD> \n");
			writer.write("<TR><TH>학번</TH><TH>이름</TH><TH>출석</TH><TH>중간</TH><TH>기말</TH><TH>과제</TH><TH>합계</TH><TH>평균</TH></TR> \n");
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
						System.out.println(files[i].getName() + "삭제 성공.");
					}
					else {
						System.out.println(files[i].getName() + "삭제 실패.");
					}
				}
			}
			
			if(txtfile.delete()) {
				System.out.println("파일삭제 성공.");
			} else {
				System.out.println("파일삭제 실패.");
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
		}
		
		try {
			writer = new FileWriter(txtfile, true);
			writer.write("\n\t성적집계표.txt");
			writer.write("\n========= ====== ==== ==== ==== ==== ==== ====");
			writer.write("\n학          번   이    름  출 석  중 간  기 말  과 제  합 계  평 균");
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

	// ActionEvent 발생 시 실행되는 메소드 -> 버튼클릭시, 입력칸에 입력후 엔터키를 쳤을때
	public void actionPerformed(ActionEvent w) {		
		Object o = w.getSource(); // 이벤트가 발생한 객체를 리턴
	
        if(o == add){
        	addDialog dialog = new addDialog(this, frame, "성적 데이터 입력");
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
	
	// 테이블의 행을 마우스로 클릭했을 때 실행되는 메소드
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
