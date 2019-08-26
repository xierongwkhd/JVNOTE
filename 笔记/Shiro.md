---
title: Shiro
date: 2019-06-03 18:11:31
tags: CSDN迁移
---
 [ ](http://creativecommons.org/licenses/by-sa/4.0/) 版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。  本文链接：[https://blog.csdn.net/MOKEXFDGH/article/details/90729949](https://blog.csdn.net/MOKEXFDGH/article/details/90729949)   
    
  ### 文章目录


      * [介绍](#_3)
        * [基本功能模块](#_4)
        * [基本Shiro架构](#Shiro_6)
        * [Subject](#Subject_8)
        * [Controller](#Controller_102)
      * [认证和授权](#_144)
        * [集成 Spring](#_Spring_145)
          * [Shiro.xml](#Shiroxml_176)
        * [Realm 编写](#Realm__259)
          * [认证](#_260)
          * [授权](#_381)
        * [权限注解](#_477)
      * [会话管理](#_509)
      * [缓存](#_636)
      * [Remeberme](#Remeberme_648)  


 
--------
 
### []()介绍

 
#### []()基本功能模块

 ![1](https://img-blog.csdnimg.cn/20190601142117670.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
#### []()基本Shiro架构

 ![2](https://img-blog.csdnimg.cn/20190601142731433.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)

 
#### []()Subject

 
```
		public class Quickstart {

		    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);
		
		
		    public static void main(String[] args) {
		    
		        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		        SecurityManager securityManager = factory.getInstance();
		
		        SecurityUtils.setSecurityManager(securityManager);
		
		        // 获取当前的 Subject. 调用 SecurityUtils.getSubject();
		        Subject currentUser = SecurityUtils.getSubject();
		        
		        // 测试使用 Session 
		        // 获取 Session: Subject#getSession()
		        Session session = currentUser.getSession();
		        session.setAttribute("someKey", "aValue");
		        String value = (String) session.getAttribute("someKey");
		        if (value.equals("aValue")) {
		            log.info("---> Retrieved the correct value! [" + value + "]");
		        }
		
		        // 测试当前的用户是否已经被认证. 即是否已经登录. 
		        // 调动 Subject 的 isAuthenticated() 
		        if (!currentUser.isAuthenticated()) {
		        	// 把用户名和密码封装为 UsernamePasswordToken 对象
		            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
		            // rememberme
		            token.setRememberMe(true);
		            try {
		            	// 执行登录. 
		                currentUser.login(token);
		            } 
		            // 若没有指定的账户, 则 shiro 将会抛出 UnknownAccountException 异常. 
		            catch (UnknownAccountException uae) {
		                log.info("----> There is no user with username of " + token.getPrincipal());
		                return; 
		            } 
		            // 若账户存在, 但密码不匹配, 则 shiro 会抛出 IncorrectCredentialsException 异常。 
		            catch (IncorrectCredentialsException ice) {
		                log.info("----> Password for account " + token.getPrincipal() + " was incorrect!");
		                return; 
		            } 
		            // 用户被锁定的异常 LockedAccountException
		            catch (LockedAccountException lae) {
		                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
		                        "Please contact your administrator to unlock it.");
		            }
		            // 所有认证时异常的父类. 
		            catch (AuthenticationException ae) {
		                //unexpected condition?  error?
		            }
		        }
		        
		        log.info("----> User [" + currentUser.getPrincipal() + "] logged in successfully.");
		
		        // 测试是否有某一个角色. 调用 Subject 的 hasRole 方法. 
		        if (currentUser.hasRole("schwartz")) {
		            log.info("----> May the Schwartz be with you!");
		        } else {
		            log.info("----> Hello, mere mortal.");
		            return; 
		        }
		
		        // 测试用户是否具备某一个行为. 调用 Subject 的 isPermitted() 方法。 
		        if (currentUser.isPermitted("lightsaber:weild")) {
		            log.info("----> You may use a lightsaber ring.  Use it wisely.");
		        } else {
		            log.info("Sorry, lightsaber rings are for schwartz masters only.");
		        }

		        // 测试用户是否具备某一个行为. 
		        if (currentUser.isPermitted("user:delete:zhangsan")) {
		            log.info("----> You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
		                    "Here are the keys - have fun!");
		        } else {
		            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
		        }
		
		        // 执行登出. 调用 Subject 的 Logout() 方法. 
		        System.out.println("---->" + currentUser.isAuthenticated());
		        
		        currentUser.logout();
		        
		        System.out.println("---->" + currentUser.isAuthenticated());
		
		        System.exit(0);
		    }
		}

```
 
#### []()Controller

 在 web 应用中的使用：

 
```
		@Controller
		@RequestMapping("/shiro")
		public class ShiroHandler {
		
			@RequestMapping("/login")
			public String login(@RequestParam("username") String username, 
								@RequestParam("password") String password){
				Subject currentUser = SecurityUtils.getSubject();
				if (!currentUser.isAuthenticated()) {
		            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		            token.setRememberMe(true);
		            try {
		            	System.out.println("1. " + token.hashCode());
		                currentUser.login(token);
		            } 
		            catch (AuthenticationException ae) {
		            	System.out.println("登录失败: " + ae.getMessage());
		            }
		        }
				return "redirect:/list.jsp";
			}
		}

```
 **认证的流程：**

  
  2. 获取当前的 Subject. 调用 SecurityUtils.getSubject(); 
  4. 测试当前的用户是否已经被认证. 即是否已经登录. 调用 Subject 的 isAuthenticated() 
  6. 若没有被认证, 则把用户名和密码封装为 UsernamePasswordToken 对象  
      1). 创建一个表单页面  
      2). 把请求提交到 SpringMVC 的 Handler  
      3). 获取用户名和密码. 
  8. 执行登录: 调用 Subject 的 login(AuthenticationToken) 方法.  
      （login内部通过securityManager使用Realm，所以我们需要自定义 Realm 类） 
  10. 自定义 Realm 的方法, 从数据库中获取对应的记录, 返回给 Shiro.  
      1). 实际上需要继承 org.apache.shiro.realm.AuthenticatingRealm 类  
      2). 实现 doGetAuthenticationInfo(AuthenticationToken) 方法. 
  12. 由 shiro 完成对密码的比对.  
--------
 
### []()认证和授权

 
#### []()集成 Spring

 我们先搭建一个简单的Demo，再对其中的细节进行分析。  
 这里只说明和Spring进行集成所需要的文件：

  
  2. 加入 Shiro 的 jar 包。 
  4. web.xml 中需要添加 **shiroFilter**。  
```
		<!-- 
		1. 配置  Shiro 的 shiroFilter.  
		2. DelegatingFilterProxy 实际上是 Filter 的一个代理对象. 默认情况下, Spring 会到 IOC 容器中查找和 
		<filter-name> 对应的 filter bean. 也可以通过 targetBeanName 的初始化参数来配置 filter bean 的 id. 
		-->
	    <filter>
	        <filter-name>shiroFilter</filter-name>
	        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	        <init-param>
	            <param-name>targetFilterLifecycle</param-name>
	            <param-value>true</param-value>
	            <!--
	            <param-name>targetBeanName </param-name>
	            <param-value>testABC</param-value>
	            -->
	        </init-param>     
	    </filter>
	    <filter-mapping>
	        <filter-name>shiroFilter</filter-name>
	        <url-pattern>/*</url-pattern>
	    </filter-mapping>

```
  
  2. 编写 Shiro 的配置文件，**Shiro.xml**。  
--------
 
##### []()Shiro.xml

 
```
		<?xml version="1.0" encoding="UTF-8"?>
		<beans xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
		
		    <!--  
		    1. 配置 SecurityManager!
		    -->     
		    <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
		        <property name="cacheManager" ref="cacheManager"/>
		        <property name="realm" ref="jdbcRealm"></property>
		    </bean>
		
		    <!--  
		    2. 配置 CacheManager. 
		    2.1 需要加入 Redis(ehcache)的 jar 包及配置文件. 
		    -->     
		    <bean id="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
		        <property name="cacheManagerConfigFile" value="classpath:ehcache.xml"/> 
		    </bean>
		
		    <!-- 
		    	3. 配置 Realm 
		    	3.1 直接配置实现了 org.apache.shiro.realm.Realm 接口的 bean，需要我们另外实现
		    -->     
		    <bean id="jdbcRealm" class="com.atguigu.shiro.realms.ShiroRealm">
		    	<property name="credentialsMatcher">
		    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
		    			<property name="hashAlgorithmName" value="MD5"></property>
		    			<property name="hashIterations" value="1024"></property>
		    		</bean>
		    	</property>
		    </bean>
    
	    <!--  
	    4. 配置 LifecycleBeanPostProcessor. 可以自定的来调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法. 
	    -->       
	    <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>
	
	    <!--  
	    5. 启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor 之后才可以使用. 
	    -->     
	    <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
	          depends-on="lifecycleBeanPostProcessor"/>
	    <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
	        <property name="securityManager" ref="securityManager"/>
	    </bean>
	
	    <!--  
	    6. 配置 ShiroFilter. 
	    6.1 id 必须和 web.xml 文件中配置的 DelegatingFilterProxy 的 <filter-name> 一致.
	        若不一致, 则会抛出: NoSuchBeanDefinitionException. 
	        因为 Shiro 会来 IOC 容器中查找和 <filter-name> 名字对应的 filter bean.
	    -->     
	    <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
	        <property name="securityManager" ref="securityManager"/>
	        <property name="loginUrl" value="/login.jsp"/>
	        <property name="successUrl" value="/list.jsp"/>
	        <property name="unauthorizedUrl" value="/unauthorized.jsp"/>
	        
	        <!--  
	        	配置哪些页面需要受保护. 
	        	以及访问这些页面需要的权限. 
	        	格式：url=拦截器，如以下Shiro提供的拦截器
	        	1). anon 可以被匿名访问
	        	2). authc 必须认证(即登录)后才可能访问的页面. 
	        	3). logout 登出.
	        -->
	        <property name="filterChainDefinitions">
	            <value>
	                /login.jsp = anon
	                /shiro/login = anon
	                /shiro/logout = logout
	                # everything else requires authentication:
	                /** = authc
	            </value>
	        </property>
	    </bean>
	</beans>

```
 
--------
 
#### []()Realm 编写

 
##### []()认证

 **作用：** 将login方法传递过来的token对象中的数据与数据库进行比对。  
 **加密：**

  
  2. 把一个字符串加密为 MD5 ：替换当前 Realm 的 credentialsMatcher 属性. 直接使用 HashedCredentialsMatcher 对象，如在 Shiro.xml 中配置：  
```
		<bean id="jdbcRealm" class="com.atguigu.shiro.realms.ShiroRealm">
		    <property name="credentialsMatcher">
		    	<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
		    		<property name="hashAlgorithmName" value="MD5"></property>
		    		<property name="hashIterations" value="1024"></property><!-- 加密次数 -->
		    	</bean>
		    </property>
		</bean>

```
  
  2. 使用 MD5 盐值加密:  
      1). 在 doGetAuthenticationInfo 方法返回值创建 SimpleAuthenticationInfo 对象的时候, 需要使用  
      SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName) 构造器。  
      2). 使用 ByteSource.Util.bytes() 来计算盐值。  
      3). 盐值需要唯一: 一般使用随机字符串或 user id。  
      4). 使用 new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations); 来计算盐值加密后的密码的值。  **Realm：**

 
```
		public class MyRealm extends AuthenticatingRealm {
			@Override
			protected AuthenticationInfo doGetAuthenticationInfo(
					AuthenticationToken token) throws AuthenticationException {
				
				//1. 把 AuthenticationToken 转换为 UsernamePasswordToken 
				UsernamePasswordToken upToken = (UsernamePasswordToken) token;
				
				//2. 从 UsernamePasswordToken 中来获取 username
				String username = upToken.getUsername();
				
				//3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
				System.out.println("从数据库中获取 username: " + username + " 所对应的用户信息.");
				
				//4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
				if("unknown".equals(username)){
					throw new UnknownAccountException("用户不存在!");
				}
				
				//5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常. 
				if("monster".equals(username)){
					throw new LockedAccountException("用户被锁定");
				}
				
				//6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
				//以下信息是从数据库中获取的.
				//1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象. 
				Object principal = username;
				//2). credentials: 密码. 
				Object credentials = null; //"fc1709d0a95a6be30bc5926fdb7f22f4";
				if("admin".equals(username)){
					credentials = "ce2f6417c7e1d32c1d81a797ee0b499f87c5de06";
				}else if("user".equals(username)){
					credentials = "073d4c3ae812935f23cb3f2a71943f49e082a718";
				}
				
				//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
				String realmName = getName();
				//4). 盐值. 
				ByteSource credentialsSalt = ByteSource.Util.bytes(username);
				
				SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
				info = new SimpleAuthenticationInfo(principal , credentials, credentialsSalt, realmName);
				return info;
			}
		
			public static void main(String[] args) {//用于我们测试时计算username+123456加密后的值
				String hashAlgorithmName = "SHA1";
				Object credentials = "123456";
				Object salt = ByteSource.Util.bytes("admin");//user
				int hashIterations = 1024;
				
				Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
				System.out.println(result);
			}
		}

```
 
--------
 **多Realm情况**  
 不用数据库使用不同加密方式，如：

 
```
		<bean id="secondRealm" class="com.atguigu.shiro.realms.SecondRealm">
	    	<property name="credentialsMatcher">
	    		<bean class="org.apache.shiro.authc.credential.HashedCredentialsMatcher">
	    			<property name="hashAlgorithmName" value="SHA1"></property>
	    			<property name="hashIterations" value="1024"></property>
	    		</bean>
	    	</property>
	    </bean>

```
 需要使用 **ModularRealmAuthenticator** 来管理，修改 Shiro.xml 配置文件：

  
  2. 添加 authenticator bean，配置 authenticationStrategy 属性，即认证策略，有三种：  
      FirstSuccessfulStrategy->只要有一个Realm认证成功即可，返回第一个认证成功的认证信息  
      AtLeastOneSuccessfulStrategy->只要有一个Realm认证成功即可，返回所有认证成功的认证信息  
      AllSuccessfulStrategy->所有Realm认证成功才算成功，返回所有认证成功的认证信息  
```
		<bean id="authenticator" class="org.apache.shiro.authc.pam.ModularRealmAuthenticator">
	    	<property name="authenticationStrategy">
	    		<!-- 默认使用AtLeastOneSuccessfulStrategy -->
	    		<bean class="org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy"/>
	    	</property>
	    </bean>

```
  
  2. securityManager bean 里添加：  
```
		<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
	        <property name="cacheManager" ref="cacheManager"/>
	        <property name="authenticator" ref="authenticator"></property>
	        
	        <property name="realms">
	        	<list>
	    			<ref bean="jdbcRealm"/>
	    			<ref bean="secondRealm"/>
	    		</list>
	        </property>
	    </bean>

```
 
--------
 
##### []()授权

 **设置角色和权限**，设置页面访问需要的权限（角色），修改shirofilter中的属性：

 
```
		<property name="filterChainDefinitions">
				<!-- 
				roles 角色过滤器 
				perms 角色对应权限
				-->
	            <value>
	                /login.jsp = anon
	                /shiro/login = anon
	                /shiro/logout = logout

					/user.jsp = roles[user]
                	/admin.jsp = roles[admin]
                	/user.jsp = perms[user:delete]
	                # everything else requires authentication:
	                /** = authc
	            </value>
	        </property>

```
 **给用户授权（分配角色，权限）：**

  
  2. 授权需要继承 AuthorizingRealm 类, 并实现其 doGetAuthorizationInfo 方法 
  4. AuthorizingRealm 类继承自 AuthenticatingRealm, 但没有实现 AuthenticatingRealm 中的  
      doGetAuthenticationInfo, 所以认证和授权只需要继承 AuthorizingRealm 就可以了. 同时实现他的两个抽象方法.  
      我们可以在之前的 MyRealm 中实现 doGetAuthorizationInfo 方法：  
```
		public class MyRealm extends AuthenticatingRealm {
			@Override
			protected AuthenticationInfo doGetAuthenticationInfo(
				//...用于认证
			}
		
			@Override//用于授权
			protected AuthorizationInfo doGetAuthorizationInfo(
						PrincipalCollection principals) {
				//1. 从 PrincipalCollection 中来获取登录用户的信息
				Object principal = principals.getPrimaryPrincipal();
				
				//2. 利用登录的用户的信息来用户当前用户的角色或权限(可能需要查询数据库)
				Set<String> roles = new HashSet<>();
				roles.add("user");
				if("admin".equals(principal)){
					roles.add("admin");
				}
				
				//3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
				/*
				info.setRoles("角色");//从数据库查询到登陆用户所拥有的角色
				info.addStringPermissions("");//根据角色查找角色所拥有的权限，并设置
				*/
				
				//4. 返回 SimpleAuthorizationInfo 对象. 
				return info;
			}
		}

```
 **角色权限设置**  
 我们之前是将设置写死在 Shiro.xml 中，我们也可以从数据库获取，步骤如下：  
 3. 写一个工厂类

 
```
		public class FilterChainDefinitionMapBuilder {
			public LinkedHashMap<String, String> buildFilterChainDefinitionMap(){
				LinkedHashMap<String, String> map = new LinkedHashMap<>();
				//通过filterChainDefinitions源码我们可以知道内部使用LinkedHashMap来存储信息
				//将得到的数据放入map
				map.put("/login.jsp", "anon");
				map.put("/shiro/login", "anon");
				map.put("/shiro/logout", "logout");
				map.put("/user.jsp", "authc,roles[user]");
				map.put("/admin.jsp", "authc,roles[admin]");
				map.put("/list.jsp", "user");
				map.put("/**", "authc");
				
				return map;
			}
		}

```
  
  2. 配置一个bean  
```
		<!-- 配置一个 bean, 该 bean 实际上是一个 Map. 通过实例工厂方法的方式 -->
		<!-- 配置实例方法 -->
	    <bean id="filterChainDefinitionMap" 
	    	factory-bean="filterChainDefinitionMapBuilder" factory-method="buildFilterChainDefinitionMap"></bean>
	    
	    <!-- 实例工厂类 -->
	    <bean id="filterChainDefinitionMapBuilder"
	    	class="com.atguigu.shiro.factory.FilterChainDefinitionMapBuilder"></bean>

```
  
  2. 注释掉配置文件中的 filterChainDefinitions 属性，添加：  
```
		<property name="filterChainDefinitionMap" ref="filterChainDefinitionMap"/>

```
 
--------
 
#### []()权限注解

  
  * **@RequiresAuthentication**：使用该注解标注的类，实例，方法在访问或调用时，当前Subject必须在当前session中已经过认证 
  * **@RequiresUser**：当前Subject必须是应用的用户，才能访问或调用被该注解标注的类，实例，方法。 
  * **@RequiresGuest**：使用该注解标注的类，实例，方法在访问或调用时，当前Subject可以是“gust”身份，不需要经过认证或者在原先的session中存在记录 
  * **@RequiresRoles(value={“admin”,“user”})**：当前Subject必须拥有所有指定的角色时，才能访问被该注解标注的方法。如果当天Subject不同时拥有所有指定角色，则方法不会执行还会抛出AuthorizationException异常。 
  * **@RequiresPermissions(value={“user:a”,“user:b”})**：当前Subject需要拥有某些特定的权限时，才能执行被该注解标注的方法。如果当前Subject不具有这样的权限，则方法不会被执行。  这些注解能用在 Service 层的方法上或者 Controller 层的方法上，如用在Service：  
 Service：

 
```
		public class ShiroService {
		    @RequiresRoles({"admin"})//必须拥有admin角色才能访问
		    public void shiroServiceMethod() {
		        System.out.println("Test ShiroServiceMethod, time: " + new Date());
		    }
		}

```
 Contoller：

 
```
		public class ShiroHandler {
		    @Autowired
		    private ShiroService shiroService;
		    
		    @RequestMapping("/shiroMethod")
		    public String shiroServiceMethod(){
		        shiroService.shiroServiceMethod();
		        return "redirect:/list.jsp";
		    }
		}

```
 
--------
 
### []()会话管理

 **特点：** 不依赖底层容器（tomcat等），可以在SE和EE中使用。在 Controller 层使用HttpSession，而在 Service 层可以使用 Shiro 提供的 session，就可以访问到 session 中的数据。

 **常用 API：**

  
  * Subject.getSession()：获取会话 
  * Subject.getId()：获取当前会话的唯一标识 
  * Subject.getHost()：获取当前Subject的主机地址 
  * session.getTimeout()、session.setTimeout()：获取、设置当前session的过期时间 
  * session.getStartTimestamp()、session.getLastAccesTime()：获取会话的启动时间和最后访问时间 
  * session.touch、session.stop：更新会话最后访问时间、销毁会话 
  * session.setAttribute(key,val)、session.getAttribute(key)、session.removeAttribute(key)：设置、获取、删除会话属性  **会话监听器**  
 SessionListener：

  
  * onStart(Session) 
  * onStop(Session) 
  * onException(Session)  **SessionDao：** 用于会话的持久化  
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190603164102252.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L01PS0VYRkRHSA==,size_16,color_FFFFFF,t_70)  
 首先修改 Shiro.xml 配置文件，添加：

 
```
		<!-- Session ID 生成器-->
		<bean id="sessionIdGenerator"
			class="org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator"/>
		
		<!-- Session DAO. 继承 EnterpriseCacheSessionDAO -->
		<bean id="sessionDAO"
			class="com.atguigu.shiro.realms.MySessionDao">
			<!-- 配置我们的缓存 -->
			<property name="activeSessionsCacheName" value="shiro-activeSessionCache"/>
			<!-- 配置Sessionid生成器 -->
			<property name="sessionIdGenerator" ref="sessionIdGenerator"/>
		</bean>
		
		<!-- 会话管理器-->
		<bean id="sessionManager" class="org.apache.shiro.session.mgt.DefaultSessionManager">
			<property name="globalSessionTimeout" value="1800000"/>
			<property name="deleteInvalidSessions" value="true"/>
			<property name="sessionValidationSchedulerEnabled" value="true"/>
			<property name="sessionDAO" ref="sessionDAO"/>
		</bean>
		<!-- 将sessionManager配给securityManager-->
		<bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
	        <property name="cacheManager" ref="cacheManager"/>
	        <property name="authenticator" ref="authenticator"></property>
	        
	        <property name="realms">
	        	<list>
	    			<ref bean="jdbcRealm"/>
	    			<ref bean="secondRealm"/>
	    		</list>
	        </property>
	        
	        <property name="sessionManager" ref="sessionManager"></property>
	    </bean>

```
 一般我们通过继承 EnterpriseCacheSessionDAO 基本实现类来实现我们的 SessionDao：

 
```
		public class MySessionDao extends EnterpriseCacheSessionDAO {

			@Autowired
			private JdbcTemplate jdbcTemplate = null;//我们先使用jdbctemplate操作
		
			@Override
			protected Serializable doCreate(Session session) {
				Serializable sessionId = generateSessionId(session);
				assignSessionId(session, sessionId);
				String sql = "insert into sessions(id, session) values(?,?)";
				jdbcTemplate.update(sql, sessionId,SerializableUtils.serialize(session));
				return session.getId();
			}
		
			@Override
			protected Session doReadSession(Serializable sessionId) {
				String sql = "select session from sessions where id=?";
				List<String> sessionStrList = jdbcTemplate.queryForList(sql,String.class, sessionId);
				if (sessionStrList.size() == 0)
					return null;
				return SerializableUtils.deserialize(sessionStrList.get(0));
			}
			
			@Override
			protected void doUpdate(Session session) {
				if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
					return; 
				}
				String sql = "update sessions set session=? where id=?";
				jdbcTemplate.update(sql, SerializableUtils.serialize(session),session.getId());
			}
		
			@Override
			protected void doDelete(Session session) {
				String sql = "delete from sessions where id=?";
				jdbcTemplate.update(sql, session.getId());
			}
		}

```
 SessionDao当中的 SerializableUtils 用于序列化 session：

 
```
		public class SerializableUtils {

			public static String serialize(Session session) {
				try {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(bos);
					oos.writeObject(session);
					return Base64.encodeToString(bos.toByteArray());
				} catch (Exception e) {
					throw new RuntimeException("serialize session error", e);
				}
			}
		
			public static Session deserialize(String sessionStr) {
				try {
					ByteArrayInputStream bis = new ByteArrayInputStream(
							Base64.decode(sessionStr));
					ObjectInputStream ois = new ObjectInputStream(bis);
					return (Session) ois.readObject();
				} catch (Exception e) {
					throw new RuntimeException("deserialize session error", e);
				}
			}
		}

```
 
--------
 
### []()缓存

 我们写的 Realm 默认是支持缓存的，缓存的配置如下：

 
```
		<bean id="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
			<property name="cacheManagerConfigFile" value="classpath:ehcache.xml"/> 
		</bean>

```
 如果用redis实现缓存需要重写cache、cacheManager、SessionDAO和初始化redis配置  
 详细过程可以参考：[地址](https://www.yonfon.com/c/shiro%E7%94%A8redis%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98)

 
--------
 
### []()Remeberme

 Remeberme 的 cookie 写在客户端，区别于认证：subject.isAuthenticated 表示用户进行了身份认证，而 subject.isRemebered 表示用户通过记住我登陆（两者的值是相对的）  
 。  
 **实现**

  
  2. 拦截器添加 user，表示用户拦截器，已经身份认证或记住我登陆都可：  
> /list.jsp = user
> 
>  
  
  2. Controller 中的 token 对象设置记住我：  
```
		token.setRememberMe(true);

```
  
  2. 此时就可以简单使用了，我们还可以设置记住我的时间，在 securityManager 中添加：  
```
		<property name="rememberMeManager.cookie.maxAge" value="10"/>

```
   
  