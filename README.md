##代码生成

>1、配置 test/resources/generator.yaml

>2、运行 test/java/com.yonyou.cyx.generator.CodeGenerator.main

- 已完成
    + 接口出入参数禁止使用Map
    + 生成的文件名称可以去除多种前缀
    + 模块名称可以为空
    + swagger注解
    + 参数验证注解
    + 生成的java文件格式化
    + 生成的xml文件格式化
    + 生成整个数据库的表的代码
    
- 注意
    + 表中字段 is_deleted 要求是 int(1)。（`tinyint 会导致 isDeleted 是Boolean类型`）
    + 可在配置文件中配置多张表名，同时生成多张表代码
    + 生成代码的表需要有主键，否则会有错误，请自行修改


