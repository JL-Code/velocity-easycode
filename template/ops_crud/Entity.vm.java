##引入宏定义
$!define

##使用宏定义设置回调（保存位置与文件后缀）
#save("/entity", ".java")

##使用宏定义设置包后缀
#setPackageSuffix("entity")

## 定义变量
#set($excludeFields = ["version", "deleted", "modifier", "creator", "creatorId",  "modifierId"])
#set($auditFields = ["modifier", "creator", "creatorId",  "modifierId","createdAt","moditiedAt"])

##使用全局变量实现默认包导入
$!autoImport
import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.jxqsq.qsq.infrastructure.entity.BaseEntity;

##使用宏定义实现类注释信息
#tableComment("实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("$tableInfo.obj.name")
public class $!{tableInfo.name} extends BaseEntity<$!{tableInfo.pkColumn[0].shortType}> {

#foreach($column in $tableInfo.fullColumn)
#if(${column.comment})
    /**
    * ${column.comment}
    */
#end
#if($column.type.equals("java.lang.Boolean"))
    @TableField("${column.obj.name}")
#end
#if(($column.type.equals("java.lang.Integer")||$column.type.equals("java.lang.Long")) && $column.name.equals("id"))
    @TableId(type=IdType.AUTO)
#end
#if($column.name.equals("creator") || $column.name.equals("creatorId")
      || $column.name.equals("createdAt"))
    @TableField(fill = FieldFill.INSERT)
#end
#if($column.name.equals("modifier")
       || $column.name.equals("modifierId") || $column.name.equals("modifiedAt"))
    @TableField(fill = FieldFill.UPDATE)
#end
    private $!{tool.getClsNameByFullName($column.type)} #convertBooleanNamingStyle($column.name);
#end
}