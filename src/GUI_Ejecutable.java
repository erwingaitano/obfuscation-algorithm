import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class GUI_Ejecutable extends JFrame {
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem salir = new JMenuItem();
    private JMenuItem abrir = new JMenuItem();
    private JLabel jLabel1 = new JLabel();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JMenu jMenu2 = new JMenu();
    private JMenuItem buscar = new JMenuItem();
    private JMenuItem reemplazar = new JMenuItem();
    private ObfuscationAlgorithm arch;
    private String opc;
    private JMenuItem menu_contarcaracteres = new JMenuItem();
    private JEditorPane jEditorPane1;
    private int i;

    public static void main(String[] args) {
        GUI_Ejecutable ejec = new GUI_Ejecutable();
        ejec.setTitle("Obfuscation Algorithm Data");
        ejec.setVisible(true);
        ejec.setSize(800, 600);
        ejec.setDefaultCloseOperation(3);

    }   
    public GUI_Ejecutable() {  
        try {            
           jbInit();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void jbInit() throws Exception {
        this.getContentPane().setLayout(null);
        this.setSize(new Dimension(384, 315));
        this.setJMenuBar(jMenuBar1);
        jMenu1.setText("File");
        opc = "";
        jEditorPane1 = new JEditorPane();
        jEditorPane1.setContentType("text/html");

        jEditorPane1.setFocusable(false);
        abrir.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        jMenuItem1_actionPerformed(e);
                    }
                });
        salir.setText("Exit");
        salir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
            
            });
        
        abrir.setText("Open");
        jLabel1.setText("File Content.");
        jLabel1.setBounds(new Rectangle(200, 5, 125, 30));
        jScrollPane1.setBounds(new Rectangle(15, 15, 170, 180));
        jScrollPane1.setAutoscrolls(true);
        jMenu1.addSeparator();
        jMenu1.add(abrir);
        jMenu1.add(abrir);
        jMenu1.addSeparator();
        jMenu1.add(salir);
        jMenu1.addSeparator();
        jMenuBar1.add(jMenu1);
   
        jScrollPane1.getViewport().add(jEditorPane1, null);
        this.getContentPane().add(jScrollPane1, BorderLayout.CENTER);
        this.getContentPane().add(jLabel1, BorderLayout.NORTH);

        
        
        //UBICACION DE TODO

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(3);
        this.add(jLabel1, BorderLayout.NORTH);
        this.add(jScrollPane1, BorderLayout.CENTER);

    }
   
   //OPCION ABRIR
    private void jMenuItem1_actionPerformed(ActionEvent e){
        
        try{
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);        
        String ruta = fc.getSelectedFile().getPath();            
        arch = new ObfuscationAlgorithm(ruta); 
        jEditorPane1.setText(arch.getDatos());
        
        jScrollPane1.setViewportView(jEditorPane1);
        
           
        }catch(Exception exc){
            exc.printStackTrace();
        }
        
    }

}//END260210
