/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlexporttoinsert;

import hiber.NewHibernateUtil;
import aiegui.message.Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Administrator
 */
public class SQLExportToInsert {

    public final static char CR = (char) 0x0D;
    public final static char LF = (char) 0x0A;

    public final static String CRLF = "" + CR + LF;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        String fileCSVFile = "C:\\Abdul\\Work\\IGA-OnsiteIssues\\IGA-103-PaxDataGUI\\IGA-103.csv";
        FileInputStream jafReader = new FileInputStream(new File(fileCSVFile));
       Reader r = new InputStreamReader(jafReader, "US-ASCII");
        String tableName = "message";
        int ch = 0;

        int i = 1;
        String[] columnNames = null;
        String tempLine = "";
        List<String> lines = new ArrayList<String>();
        while ((ch = r.read()) != -1) {

            char c = (char) ch;
            tempLine += c;
            if (c == '\r') {
                char char2 = (char) r.read();
                if (char2 == '\n') {
                    lines.add(tempLine);
                    tempLine = new String();
                } else {
                    tempLine += char2;
                }

            }

        }

        System.out.println("********* LIne : " + lines.get(1));

        int row = 0;
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (String line : lines) {
            row++;
            if (row == 1) {
                continue;
            }

            String[] data = line.split(",");
            System.out.println(" Populating message : " + line);
            Message message = populateMessage(data);
            System.out.println(" Saving record : " + row);
            session.save(message);
            System.out.println(" Saved ! : " + row);
            session.flush();
            session.clear();

            // session.close();
        }
        tx.commit();
        session.close();
        sessionFactory.close();
    }

    private static Message populateMessage(String[] data) throws NumberFormatException, ParseException {
        Message message = new Message();
        if (data[1] != null && !data[1].equals("NULL")) {
            message.setSourceSystemId(Integer.parseInt(data[1]));
        }
        if (data[2] != null && !data[2].equals("NULL")) {
            message.setSystemId(Integer.parseInt(data[2]));
        }
        if (data[3] != null && !data[3].equals("NULL")) {
            message.setDestinationSystemId(Integer.parseInt(data[3]));
        }

        if (data[4] != null && !data[4].equals("NULL")) {
            message.setInterfaceId(Integer.parseInt(data[4]));
        }

        if (data[5] != null && !data[5].equals("NULL")) {
            message.setAirport(data[5]);
        }

        if (data[6] != null && !data[6].equals("NULL")) {
            message.setMessageType(Integer.parseInt(data[6]));
        }

        if (data[7] != null && !data[7].equals("NULL")) {
            message.setMessageSubType(Integer.parseInt(data[7]));
        }

        if (data[8] != null && !data[8].equals("NULL")) {
            message.setMessageId(data[8]);
        }

        if (data[9] != null && !data[9].equals("NULL")) {
            message.setCorrelationId(data[9]);
        }

        if (data[10] != null && !data[10].equals("NULL")) {
            message.setUuid(data[10]);
        }

        if (data[11] != null && !data[11].equals("NULL")) {
            message.setMessage(data[11]);
        }

        if (data[12] != null && !data[12].equals("NULL")) {
            message.setRawMessage(data[12]);
        }

        if (data[13] != null && !data[13].equals("NULL")) {
            message.setRawMessageType(data[13]);
        }

        if (data[14] != null && !data[14].equals("NULL")) {
            message.setErrorCode(data[14]);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSSSSSS");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        if (data[15] != null && !data[15].equals("NULL")) {
            message.setStoDate(dateFormat.parse(data[15]));
        }

        if (data[16] != null && !data[16].equals("NULL")) {
            message.setStoTime(timeFormat.parse(data[16]));
        }

        if (data[17] != null && !data[17].equals("NULL")) {
            message.setFlightNumber(data[17]);
        }

        if (data[18] != null && !data[18].equals("NULL")) {
            message.setIcaoAirline(data[18]);
        }

        if (data[19] != null && !data[19].equals("NULL")) {
            message.setIataAirline(data[19]);
        }

        if (data[20] != null && !data[20].equals("NULL")) {
            message.setDatetime(dateTimeFormat.parse(data[20]));
        }

        if (data[21] != null && !data[21].equals("NULL")) {
            message.setAccepted(Boolean.parseBoolean(data[21]));
        }
        // hardcoding .. so that data can be imported
        if (data[22] != null && !data[22].equals("NULL")) {
            message.setCachePopulated(false);
        }
        // hardcoding .. so that data can be imported
        if (data[23] != null && !data[23].equals("NULL")) {
            message.setMessageCacheId(null);
        }

        return message;
    }

}
