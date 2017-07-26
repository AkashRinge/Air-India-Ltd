/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleconnection;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author joker96
 */
public class OracleConnection
{
    public static String decrypt(String s)
    {
        char arr[] = s.toCharArray();
        for(int i=0; i<arr.length; i++)
            arr[i] += 13;
        return new String(arr);
    }
    public static Connection createConn()
    {
        Connection conn = null;
        try
        {
            String driverName = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);
            String serverName = "Akash-hpProbook";
            String serverPort = "1521";
            String sid = "XE";
            String url = "jdbc:oracle:thin:@" + serverName + ":" + serverPort + ":" + sid;
            String user = "root";
            String password = decrypt("`[#%Th&)+");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Succesful");
            return conn;
            
        } 
        catch(ClassNotFoundException e)
        {
            System.out.println("Database driver missing " + e.getMessage());
        }
        
        catch(SQLException e)
        {
            System.out.println("Could not connect to db " + e.getMessage());
        }
        return conn;
    }
    public static boolean uploadFile(InputStream in, String fileName)
    {
        
        Connection conn = createConn();
        int id = genId();
        try
        { 
            String sql = "insert into filetable values (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, fileName);
            ps.setBlob(3, in);
            ps.executeUpdate();
            conn.close();
            return true;
        }
        catch(Exception e)
        {
            System.out.println();
            e.printStackTrace();
            return false;
        }
    }
    public static String retrieveName(int id)
    {
        Connection conn = createConn();
        try
        {
            String sql = "select name from filetable where id = " + id;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst())
            {
                conn.close();
                return null;
            }
            rs.next();
            String name = rs.getString(1);
            System.out.println(name);
            conn.close();
            return name;
        }
        catch(Exception e)
        {e.printStackTrace();}
        return null;
    }
    public static Blob retrieveFile(String fileName)
    {
        Connection conn = createConn();
        try
        {
            System.out.println(fileName);
            String sql = "select filedata from filetable where name like '" + fileName + "'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst())
            {
                conn.close();
                return null;
            }
            rs.next();
            Blob blob = rs.getBlob(1);
            System.out.println(blob.getBinaryStream().toString());
            conn.close();
            return blob;
        }
        catch(Exception e)
        {e.printStackTrace();}
        return null;
    }
    public static int genId()
    {
        try
        {
            ArrayList<Integer> ids = idList();
            if(ids == null)
                return 1;
            return ids.get(ids.size()-1)+1;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    public static int recordcount()
    {
        Connection conn = createConn();
        try
        {
            String sql = "select count(*) from filetable";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int x = rs.getInt(1);
            conn.close();
            return x;    
        }
        catch(Exception e)
        {
            try{conn.close();} catch(Exception e2){}
            e.printStackTrace();
            return -1;
        }
    }
    public static ArrayList<Integer> idList()
    {
        Connection conn = createConn();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try
        {
            String sql = "select id from filetable";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst())
            {
                conn.close();
                return null;
            }
            while(rs.next())
                ids.add(rs.getInt(1));
            conn.close();
            return ids;
        }
        catch(Exception e)
        {e.printStackTrace();}
        return null;
    }
    
    public static InputStream retrieveFile2(int id)
    {
        Connection conn = createConn();
        try
        {
            String sql = "select filedata from filetable where id = " + id;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst())
            {
                conn.close();
                return null;
            }
            rs.next();
            byte arr[] = rs.getBytes("filedata");
            InputStream in = new ByteArrayInputStream(arr);
            //System.out.println(in.toString());
            conn.close();
            return in;
        }
        catch(Exception e)
        {e.printStackTrace();}
        return null;
    }
}
