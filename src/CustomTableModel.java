import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

//public class CustomTableModel extends DefaultTableModel implements Comparator{
public class CustomTableModel extends DefaultTableModel{
    private static final long serialVersionUID = 1L;

    int sortColumn;
 
    @Override
    public boolean isCellEditable(int row, int column)
    {
    	return false;
    }

// JTable 자체의 소팅 기능 사용으로 이 부분은 주석 처리함.
/*    
    public void sortColumn(int sortColumn) {
         this.sortColumn = sortColumn;
         Collections.sort(dataVector,this);
    }

    public int compare(Object o1, Object o2)  {

    	if (o1 == null) return -1;
        if (o2 == null) return 1;

         Vector vect1 = (Vector) o1;
         Vector vect2 = (Vector) o2;
         
         Comparable col1;
         Comparable col2;

         if(sortColumn<2) // 문자열 비교해서 정렬에 이용
         {
             col1 = (Comparable) vect1.get(sortColumn);
             col2 = (Comparable) vect2.get(sortColumn);
         }
         else // 스트링을 숫자로 변환한 다음 비교해서 정렬에 이용
         {
             col1 = (Comparable) Integer.valueOf((String)vect1.get(sortColumn));
             col2 = (Comparable) Integer.valueOf((String)vect2.get(sortColumn));
         }

         return col1.compareTo(col2);
    }
*/    
}


