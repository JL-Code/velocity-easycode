package com.codegen.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TableInfo {
    private String tableName;
    private String className;
    private String comment;
    private List<ColumnInfo> columns;
    private ColumnInfo primaryKey;
}