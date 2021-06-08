##定义初始变量
#set($serviceName = $tool.append($tableInfo.name, "ServiceImpl"))
##设置回调
$!callback.setFileName($tool.append($serviceName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/service/impl"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($tableInfo.savePackageName)package $!{tableInfo.savePackageName}.#{end}service.impl;

import $!{tableInfo.savePackageName}.entity.$!{tableInfo.name};
import $!{tableInfo.savePackageName}.dao.$!{tableInfo.name}Dao;
import $!{tableInfo.savePackageName}.service.$!{tableInfo.name}Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>描述: [$tableInfo.name 服务实现层] </p>
 * <p>创建时间: $time.currTime("yyyy/MM/dd") </p>
 *
 * @author <a href="mailto:$!email" rel="nofollow">$author</a>
 * @version v1.0
 */
@Service
public class $!{serviceName} extends ServiceImpl<$!{tableInfo.name}Dao, $!{tableInfo.name}> implements $!{tableInfo.name}Service {
    
    private $!{tableInfo.name}Dao dao;
    
    public $!{serviceName}($!{tableInfo.name}Dao dao) {
        this.dao = dao;
    }
    
}