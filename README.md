##代码生成

>1、配置 test/resources/generator.yaml

>2、运行 test/java/com.yonyou.cyx.generator.CodeGenerator.main

- 已完成
    + 接口出入参数禁止使用Map
    + 生成的文件名称可以去除多种前缀
    + 模块名称可以为空
- 注意
    + 表中字段 is_deleted 要求是 int(1)。（`tinyint 会导致 isDeleted 是Boolean类型`）
    
`如有建议请微信@幽林客!`

