##引入宏定义
$!define

##使用宏定义设置回调（保存位置与文件后缀）
#save("/entity", ".java")

##使用宏定义设置包后缀
#setPackageSuffix("entity")

##使用全局变量实现默认包导入
$!autoImport
import lombok.Data;
import java.io.Serializable;

##使用宏定义实现类注释信息
#tableComment("实体类")
@Data
@TableName("$tableInfo.obj.name")
public class $!{tableInfo.name} implements Serializable {
    private static final long serialVersionUID = $!tool.serial();
#foreach($column in $tableInfo.fullColumn)
#if(${column.comment})
    /**
    * ${column.comment}
    */
#end
#if($column.type.equals("java.lang.Boolean"))
    @TableField("${column.obj.name}")
#end
    private $!{tool.getClsNameByFullName($column.type)} #convertBooleanNamingStyle($column.name);
#end
}