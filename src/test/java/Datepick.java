import javax.swing.*; 
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.text.DateFormatter;

 

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

 

import java.awt.*; 
import java.awt.event.*; 
import java.text.DateFormat; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar; 
import java.util.Date;
import java.util.Properties;
    public class Datepick extends JFrame implements ActionListener
    {
    JLabel CheckDate; JButton check;
    
    public UtilDateModel model;
    public JDatePanelImpl datePanel;
    public JDatePickerImpl datePicker;
    
    public Datepick()
        {
          Properties p = new Properties();
          p.put("text.today", "Today");
          p.put("text.month", "Month");
          p.put("text.year", "Year");
        UtilDateModel model = new UtilDateModel();  
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);  
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        JPanel panel=new JPanel();
        CheckDate=new JLabel("Date:");
        check=new JButton("CHECK"); 
        check.addActionListener(this);
        panel.add(CheckDate);
        panel.add(datePicker);
        panel.add(check);
        add(panel);
        setBounds(200,150,400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true); 
        }
    public void actionPerformed(ActionEvent e) 
        {if(check==e.getSource())
        {
        Date selectedDate = (Date) datePicker.getModel().getValue();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String reportDate = df.format(selectedDate);
        JOptionPane.showMessageDialog(null,reportDate);
        }}
    
    public class DateLabelFormatter extends AbstractFormatter {

 

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

 

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

 

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

 

            return "";
        }

 

    }
    public static void main(String args[])
    {new Datepick();}
    }