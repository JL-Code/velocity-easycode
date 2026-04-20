package com.codegen.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ColumnInfo {
    private String columnName;
    private String propertyName;
    private String columnType;
    private String propertyType;
    private String comment;
    private boolean isPrimaryKey;
    private boolean isAutoIncrement;
    private boolean isNullable;
}