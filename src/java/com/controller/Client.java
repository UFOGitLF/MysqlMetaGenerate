package com.controller;

import com.common.ColumnDef;
import com.utils.ConnectJdbcUtils;
import com.utils.FileUtils;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author liufei
 */
public class Client {

    public static void main(String[] args) throws SQLException {
        DatabaseMetaData dbMetaData = null;

        String table = "http_api";


        dbMetaData = ConnectJdbcUtils.getDatabaseMetaData(dbMetaData);
        List<ColumnDef> columnDefs = getTableColumns(dbMetaData, null, table);

        String path = System.getProperty("user.dir") + "/files";
        System.out.println(path);
        String packageFullName = "";
        generateModel(columnDefs, table, packageFullName, path);

    }

    private static String getModelClassName(String tableName) {
        StringBuilder className = new StringBuilder();
        String[] split = tableName.split("_");
        for (String s : split) {
            className.append(tobit(s));
        }
        return className.toString();
    }


    private static String geneJavaFileName(String packageFullName, String sourceRootPath, String className) {
        StringBuilder fileName = new StringBuilder(sourceRootPath);
        String[] psplit = packageFullName.split("\\.");
        for (String s : psplit) {
            fileName.append("/").append(s);
        }
        fileName.append("/").append(className).append(".java");
        return fileName.toString();
    }

    private static String tobit(String s) {
        String[] split = s.split("_");
        StringBuilder ret = new StringBuilder();
        for (String t : split) {
            ret.append(t.toUpperCase().substring(0, 1)).append(t.substring(1));
        }
        return ret.toString();
    }

    private static String firstToSmall(String s) {
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }

    private static void generateModel(List<ColumnDef> columnDefList, String tableName, String packageFullName, String sourceRootPath) {
        String className = getModelClassName(tableName);

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageFullName).append(";\n")
                .append("import java.util.Date;\n")
                .append("import java.math.BigDecimal;\n")
                .append("import lombok.Data;\n")
                .append("import javax.persistence.*;\n")
                .append("@Data \n")
                .append("@Entity \n")
                .append("public class ").append(className).append(" {\n");
        for (int i= 0;i<columnDefList.size();i++){
            String properName = firstToSmall(tobit(columnDefList.get(i).getName()));
            sb.append("\n");
            sb.append("     /** \n");
            sb.append("      *").append(columnDefList.get(i).getComment()).append(".\n");
            sb.append("      */\n");
            if (i==0){
            }
            sb.append("     @Id \n");
            sb.append("     private ").append(columnDefList.get(i).getJavaTypeName()).append(" ").append(properName).append(";");
        }
        String fileName = geneJavaFileName(packageFullName, sourceRootPath, className);
        try {
            FileUtils.writeToFile(fileName, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得一个表中所有列的信息
     *
     * @param dbMetaData
     * @param schemaName
     * @param tableName
     */
    private static List<ColumnDef> getTableColumns(DatabaseMetaData dbMetaData, String schemaName, String tableName) {

        try {
            ResultSet rs = dbMetaData.getColumns(null, schemaName, tableName, "%");
            List<ColumnDef> columnDefs = new ArrayList<>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                int dataType = rs.getInt("DATA_TYPE");
                String remarks = rs.getString("REMARKS");

                ColumnDef columnDef1 = new ColumnDef();
                columnDef1.setName(columnName);
                columnDef1.setType(dataType);
                columnDef1.setComment(remarks);

                columnDefs.add(columnDef1);
            }
            return columnDefs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
