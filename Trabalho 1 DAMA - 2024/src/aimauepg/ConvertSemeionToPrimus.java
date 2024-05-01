/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aimauepg;

/**
 *
 * @author zcr
 */

//import com.csvreader.CsvReader;
//import com.csvreader.CsvWriter;
import java.util.LinkedList;
import java.util.ListIterator;

public class ConvertSemeionToPrimus {
    
    public static void main(String[] args) {
       ConvertSemeionToPrimus cst = new ConvertSemeionToPrimus();
       String fn = "/home/zcr/Documents/uepg/ensino/grdUEPG/disciplinas/ia/estudos/semeion.csv";
       LinkedList<String[]> lData = cst.loadData(fn);
       String[] header = cst.loadHeader(fn);
       String[][] tData = cst.transform(lData,cst.getNCols(lData));
       cst.saveNewSemeion(header,tData);
    }
    
    private void saveNewSemeion(String[] header,String[][] tData) {
        try {
            CsvWriter csw = new CsvWriter("/home/zcr/Documents/uepg/ensino/grdUEPG/disciplinas/ia/estudos/sprimos.csv");
            csw.writeRecord(header);
            for (int a=0; a<tData.length;a++) {
               csw.writeRecord(tData[a]);
            }
            csw.flush();
            csw.close();
        } catch (Exception e) {
            
        }
    }
    
    private int getNCols(LinkedList<String[]> lData) {
        String[] s0 = lData.get(0);
        return s0.length;
    }
    
    private String[][] transform(LinkedList<String[]> lData, int nCol) {
        String newData[][] = new String[lData.size()][nCol-10+1];
        ListIterator<String[]> lit = lData.listIterator();
        int a = 0;
        while (lit.hasNext()) {
            String[] line = lit.next();
            String[] newLine = new String[line.length-10+1];
            java.lang.System.arraycopy(line, 0, newLine, 0, nCol-10);
            newData[a] = newLine;
            int position = nCol-10;
            newData[a][position] = calcClass(line);            
            a++;
        }
        return newData;
    }
    
    private String calcClass(String[] line) {
        int a = 0;
        int base = line.length-10;
        for (a=0; a<10; a++) {
            if (line[base+a].compareTo("1")==0)
                break;
        }
        a=a+1;
        if ((a==1) || (a==2) || (a==3) || (a==5) || (a==7) )
            return "1";
        else 
            return "0";
    }
    
    private LinkedList<String[]> loadData(String s) {
        LinkedList<String[]> lData = new LinkedList<String[]>();
        try {
          CsvReader rd = new CsvReader(s);
          rd.readHeaders();
          String[] dHeaders = rd.getHeaders();
          do {
               rd.readRecord();
               String[] rawData = rd.getValues();
               String[] rowData = new String[rawData.length-1];
               java.lang.System.arraycopy(rawData, 0, rowData, 0, rowData.length);
               lData.add(rowData);
//               System.out.println(rowData);
          } while (rd.skipRecord());
          System.out.print("dados lidos");
        } catch (Exception e) {
            System.exit(0);
        }
        return lData;
    }    

    private String[]loadHeader(String s) {
        try {
          CsvReader rd = new CsvReader(s);
          rd.readHeaders();
          String[] rawHeader = rd.getHeaders();
          String[] header = new String[rawHeader.length];
          java.lang.System.arraycopy(rawHeader, 0, header, 0, header.length);
          return header;
        } catch (Exception e) {
            System.exit(0);
        }
        return null;
    }    
    
}
