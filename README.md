# CC链payload-GUI生成工具
- 打包
- 在打包之前请删除MainController中的payloadGenerator.unSerializable();,不然生成payload将会执行一遍反序列化操作
![image](https://user-images.githubusercontent.com/108923559/221397075-e6351057-2cbc-4a69-82f3-7f932a4eef9e.png)
cd /CCexp
mvn package
- 启动
java -jar CCexp-1.0-SNAPSHOT.jar
- 支持base64结果及文件输出
![image](https://user-images.githubusercontent.com/108923559/221397138-4016c3f1-d452-4b2d-afa0-81a5e69f440f.png)
![image](https://user-images.githubusercontent.com/108923559/221397152-e968efae-bd3e-4edb-9f73-93939701605f.png)
- 命令参数按照行分割，作为数组传入
