package com.example.entity;

/**
 * ${comment!}
 */
public class ${className} {

<#if columns??>
<#list columns as col>
    /**
     * ${col.comment!}
     */
    private ${col.propertyType} ${col.propertyName};

</#list>
</#if>
}