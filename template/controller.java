##定义初始变量
#set($controllerName = $tool.append($tableInfo.name, "Controller"))
##设置回调
$!callback.setFileName($tool.append($controllerName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/controller"))
##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}controller;

import $!{tableInfo.savePackageName}.service.$!{tableInfo.name}Service;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

 /**
 * $!{tableInfo.comment}($!{tableInfo.name})表控制层
 * <p>创建时间: $time.currTime("yyyy/MM/dd") </p>
 *
 * @author <a href="mailto:$!email" rel="nofollow">$author</a>
 * @version v1.0
 */
@Api(tags = "$!{tableInfo.comment}")
@RestController
public class $!{controllerName} {

    private $!{tableInfo.name}Service service;
    public $!{controllerName}($!{tableInfo.name}Service service) {
        this.service = service;
    }

}