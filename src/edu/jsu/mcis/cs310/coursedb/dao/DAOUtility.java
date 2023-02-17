package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;

public class DAOUtility {
    
    public static final int TERMID_SP23 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();

        try {
            
            if (rs != null) { 
                
                ResultSetMetaData metaData = rs.getMetaData();
                int numColumns = metaData.getColumnCount();

                while (rs.next()) {
                    
                    JsonObject record = new JsonObject();
                    
                    for (int i = 1; i <= numColumns; i++) {
                        
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        int columnType = metaData.getColumnType(i);
                        
                        if (columnType == Types.TIME) {
                            
                            Time timeValue = (Time) columnValue;
                            record.put(columnName.toLowerCase(), timeValue.toString());
                            
                        }
                        else {
                            
                            String columnStringValue = columnValue != null ? columnValue.toString() : "";
                            record.put(columnName.toLowerCase(), columnStringValue);
                            
                        }
                        
                    }
                    
                    records.add(record);
                    
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Jsoner.serialize(records);
        
    }
    
}
