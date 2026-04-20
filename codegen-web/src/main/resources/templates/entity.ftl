package com.example.entity;

/**
 * ${comment!}
 */
public class ${className} {

<#list columns as col>
    /**
     * ${col.comment!}
     */
    private ${col.propertyType} ${col.propertyName};

</#list>
}