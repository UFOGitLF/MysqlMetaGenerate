package com.common;

import lombok.Getter;
import lombok.Setter;

import java.sql.Types;


/**
 * @author liufei
 */
@Getter
@Setter
public class ColumnDef {
    private String name;
    private int type;
    private String comment;

    public String getJavaTypeName() {
        switch (type) {
            case Types.TINYINT:
            case Types.INTEGER:
                return "Integer";
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                return "String";
            case Types.DECIMAL:
                return "BigDecimal";
            case Types.TIMESTAMP:
                return "Date";
            case Types.DATE:
                return "Date";
            case Types.BIGINT:
                return "Integer";
            case Types.DOUBLE:
                return "Double";
            default:
                break;
        }
        throw new RuntimeException("can not find java type Name for mysql type " + type);
    }
}
