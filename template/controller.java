##定义初始变量
#set($serviceName, = $tool.append($tableInfo.name, "Controller"))
##设置回调
$!callback.setFileName($tool.append($serviceName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/controller"))
##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}controller;

import $!{tableInfo.savePackageName}.entity.$!{tableInfo.name};
import $!{tableInfo.savePackageName}.service.$!{tableInfo.name}Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * $!{tableInfo.comment}($!{tableInfo.name})表控制层
 *
 * @author $!author
 * @since $!time.currTime()
 */
@Api(tags = "$!{tableInfo.comment}")
@RestController
public class $!{serviceName} {

    private $!{tableInfo.name}Service $!tool.firstLowerCase($tableInfo.name)Service;
    public $!{serviceName}($!{tableInfo.name}Service service) {
        this.service = service;
    }

}