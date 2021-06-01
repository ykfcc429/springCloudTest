# SpringCloudTest
初探微服务,一个SpringCloud项目  
## 版本信息
```xml
        <java.version>11</java.version>
        <spring-boot.version>2.4.3</spring-boot.version>
        <spring-cloud.version>2020.0.2</spring-cloud.version>
```

## 技术栈
* springCloud 这有写上来的必要吗
* springBoot ?
* mysql 关系型数据库
* redis 非关系型数据库,消费者端用来缓存数据
* mybatis 数据持久化框架
* Eureka 服务注册与发现
* feign rpc调用
* gateway 网关
* swagger <http://localhost/swagger-ui/>

还加了一些小玩意,redis分布式锁以及布隆过滤器(手写的,~~不是很好用~~难用,但是能用)  

## 我实在是太懒了,这个文档从这里往下就从未更新过,而代码已经面目全非,有空再改
另一句话 没有懒的人,只有不想做的事

## 模块介绍
### provider-8079
生产者服务端,因为是在同一台电脑上部署,所以部署在不同的端口,8079是端口号
这里着重说Eureka的配置,其他mybatis,mysql的配置不用多说
#### 使用步骤
* 导入eureka依赖
```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>
```
* 启动类配置@EnableEurekaClient
```java
@SpringBootApplication
@EnableEurekaClient
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
```
* 在配置文件里编写Eureka的配置
```properties
server.port=8079
spring.application.name=provider-8079
#这里是eureka集群,所有要配置所有的节点信息
eureka.client.service-url.defaultZone=http://eureka8082.com:8082/eureka/,http://eureka8083.com:8083/eureka/,http://eureka8083.com:8083/eureka/
eureka.instance.instance-id=provider-8079
```
这样的话,当集群正常工作的时候,启动生产者就可以注册到eureka中心管理了

### consumer-80
消费者服务端,主要用来接收前端请求,返回数据,这里的数据都是通过redis缓存
消费者会与redis服务挂钩,也可以采用redis集群,一样的.
这里着重讲一下eureka配置和Ribbon和Redis的使用;
首先是导入依赖
```xml
        <dependency>
            <groupId>com.aFeng</groupId>
            <artifactId>provider-8079</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-ribbon -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-ribbon</artifactId>
            <version>1.4.7.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
        </dependency>
```
这里可以继承生产者引入的jar包,除了能拿到生产者的实体之外还能拿到其他依赖,譬如Eureka

配置文件编写,这里的url就是eureka里生产者注册的实例名称,通过下面注册的eureka集群可以访问到生产者服务
```properties
provider.url=http://PROVIDER-8079
eureka.client.register-with-eureka=false
eureka.client.service-url.defaultZone=http://eureka8082.com:8082/eureka/,http://eureka8083.com:8083/eureka/,http://eureka8083.com:8083/eureka/
```

然后是启动类的配置
```java
//这个exclude也很重要,因为引入了生产者的依赖会有mysql的依赖,springboot强制要求填写datasource的
//url连接,实际上这个服务根本不需要连接数据库,所以这里要忽略掉数据库的配置类
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@EnableEurekaClient
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }
}
```
配置Ribbon的Bean
```java
@Configuration
public class BeanConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

```
使用RestTemplate
```java
    RestTemplate restTemplate;
    
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
   
    @RequestMapping("/add")
    public boolean add(Goods goods){
        return restTemplate.postForObject(URL+"/goods/add",goods,Boolean.class);
    }
```

