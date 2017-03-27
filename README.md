# count
<br/>
用于统计数据库中每个表或者单个表的行数<br/>
使用方法<br/>

在所在目录运行mvn assembly:assembly 在target目录生成count-0.0.1-SNAPSHOT.jar<br/>
运行cd target<br> 
运行java -jar count-0.0.1-SNAPSHOT.jar 数据库名 url 用户名 密码 表（可选）<br/>
目前支持数据库（mysql pgsql）<br/>
例子：java -jar count-0.0.1-SNAPSHOT.jar mysql localhost:3306/test root 1
测试多人上传
