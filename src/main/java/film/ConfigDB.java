package film;

import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.io.File;

public class ConfigDB {
    
    private String Password="";
    private String Username="root";
    private String Database="film";
    
    public Connection koneksi;
    
    public ConfigDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            koneksi=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.Database, this.Username, this.Password);
            System.out.println("Koneksi Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf Terjadi Kesalahan pada bagian : \n ["+e.toString() +"]");
        }
    }
    
    public Object[][]isiTabel(String SQL, int jumlah){
        
        Object[][] data=null;
        try {
            Statement st = ConfigDB.this.koneksi.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            rs.last();
            int baris=rs.getRow();
            rs.beforeFirst();
            int j=0;
            data = new Object[baris][jumlah];
            while (rs.next()){
                for (int i=0; i < jumlah; i++){
                    data[j][i]=rs.getString(i+1);
                }
                j++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf Terjadi Kesalahan pada bagian method isiTable : \n ["+e.toString()+"]");
        }
        return data;
    }
    
    public void tampilTabel(String Judul[],String SQL, JTable Tabel){
        try {
            String title[]=Judul;
            int jum = title.length;
            Tabel.setModel(new DefaultTableModel(isiTabel(SQL,jum),title));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf Terjadi Kesalahan pada bagian method tampilTabel : \n ["+e.toString() +"]");
        }
    }
    
    public void aturLebarKolom(JTable tabel, int baris[]){
        try {
            int getBaris[]=baris;
            int JumlahBaris=getBaris.length;
            tabel.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            for(int i = 0; i < JumlahBaris-1; i++){
                tabel.getColumnModel().getColumn(i).setPreferredWidth(getBaris[i]);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf Terjadi Kesalahan pada bagian method aturLebarKolom : \n ["+e.toString() +"]");
            
        }
    }
    
    public void simpanData(String SQL){
            try {
                Statement st = ConfigDB.this.koneksi.createStatement();
                st.execute(SQL);
                st.close();
                JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
         } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method simpan Data : \n ["+e.toString() +"]");
         }
    }
    
        public void ubahData(String SQL){
        try {
            Statement st = ConfigDB.this.koneksi.createStatement();
            st.execute(SQL);
            st.close();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method ubahData : \n ["+e.toString() +"]");
            
        }
    }
    
        public void hapusData(String SQL){
        try {
            Statement st = ConfigDB.this.koneksi.createStatement();
            st.execute(SQL);
            st.close();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (Exception e) {
        }
    }
     
        public boolean duplikasiData(String tabel, String Key, String nilai) {
        boolean ada = false;
        try {
            Statement st = ConfigDB.this.koneksi.createStatement();
            ResultSet rs = st.executeQuery("SELECT*FROM "+tabel+" WHERE "+Key+"=nilai");
            if(rs.next()){
                ada=true;
            }else{ada=false;}
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Maaf Terjadi Kesalahan pada bagian method duplikasi data : \n ["+e.toString() +"]");
        }
        return ada;
    }

    
}
