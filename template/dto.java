##引入宏定义
$!define

#set($dtoName = $tool.append($tableInfo.name, "DTO"))
##设置回调
$!callback.setFileName($tool.append($dtoName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/dto"))

##使用宏定义设置包后缀
#setPackageSuffix("dto")

##使用全局变量实现默认包导入
$!autoImport
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.*;

##使用宏定义实现类注释信息
#tableComment("数据传输对象")
@Data
@ApiModel(#tableComment("DTO"))
public class $!{tableInfo.name}DTO implements Serializable {
    private static final long serialVersionUID = $!tool.serial();
#foreach($column in $tableInfo.fullColumn)
    @ApiModelProperty(value = "$!{column.comment}" required=${column.obj.notNull})
    private $!{tool.getClsNameByFullName($column.type)} #convertBooleanNamingStyle($column.name);
#end
}