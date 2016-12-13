# LS360-API-Gateway
LS360-API-Gateway Repo

This is a web project with spring security oAuth2 configured. This project is consist of several modules. The main module is LS360ApiGateway which is a web module. As normal convention is request comes to web modules. The controllers at web module. Controllers have services which perform business logics. Here LS360ApiGatewayService module comes into play. All the services are define in this module.

LS360ApiGatewayService module in turn includes other modules like LmsRepository, LmsApi etc.

Function of each module:

1) LS360ApiGatewayModules:
    This is a parent project that contain all the moduels. This is a simple maven multi module project. This module packaging type is pom.

2) LS360Util:
    This module contain some utility classes taht can use other modules like XmlUtil, JsonUtil, environment (dev, qa, prod) related classes. This module packaging type is jar.
    
3) LmsRepository:
    All the database related configuration classes are configure in this module. This module in turn use LS360Util module. This module use environment related classes in LS360Util module to configure database (dev, qa, prod). All the database related queries are perform in this module
    
4) LmsApi:
    This module make calls to LMS SOAP web services. All the configuration related to SOAP web service calls are configure in this module. This module in turn use LmsRepository module. Which means this module also has access to LS360Util module because LmsRepository module has a dependency on LS360Util module.
    
5) OAuth2Authorization:
    This module contain classes regarding OAuth2 token end point. This module use spring-security-jwt dependency. This module also use custom token enhancer and token converter to get more control over what include in payload while creating token. This module in turn use LmsRepository module. Which means this module also has access to LS360Util module because LmsRepository module has a dependency on LS360Util module. 
    
6) LS360ApiGatewayService:
    This module contain all the services (business logic) that LS360ApiGateway web module use. This module has a dependency on OAuth2Authorization module and LmsApi module. Which means this module has also access to LmsRepository module and LS360Util module.
    
7) LS360ApiGateway:
    This is main module. This is a web module. This module has a dependency on LS360ApiGatewayService. Which means this web module has access to all other modules because LS360ApiGatewayService module in tutn include other modules. This module contain all the configuration regarding spring web project. In this module spring security is configured. With spring security OAuth2 Authorization server and OAuth2 resource server is also configured. All the web requests are handled by this module. Like token generation end popint, Rest and SOAP web services end point. web controllers etc.
    
How to use:

    To use this project following are required
    
    1) JDK 8 (latest version)
    2) eclipse (prefer eclipse-jee-mars-2) Latest eclipse version
    3) Tomcat 8 (latest version)
    
    After installing above tools. Open the eclipse IDE. Configure java using following step
    
    * select Window --> Preferences --> Java --> Installed JREs --> Add --> Standard VM --> select directory of JDK and click finish
    
    Configure tomcat using following step
    
    * select Window --> Preferences --> Server --> Runtime Environment --> Add --> Add tomcat 8
    
    1) Now select Open perspective --> Git
    2) select clone a git repository.
    3) In the URI type https://github.com/360Training/LS360-API-Gateway.git
    4) Click on Next
    5) Brach selection dialog box open. Only master checkbox is selected. Click on next.
    6) In the directory type C:\Users\basit.ahmed\git\LS360ApiGatewayModules (change accoring to your system)
    7) click on Finish
    8) Now project is showing in you Git view.
    9) Right click on project and select Import projects
    10) Import existing eclipse project radio button is already checked. Click on Next
    11) Click finish
    
  Project is imported into your workspace. Now it will take sometime because maven needs to copy all the dependencies into your local .m2 directory.
  
Things to do After importing projects:

   1) if you have errors symbols on modules. Right click on LS360ApiGatewayModules --> Maven --> Update Project. Make sure all the modules are selected.
   
   2) You need to import sqlserver jar into your local maven repository. Since sqlserver.jar is propritery, so maven does not downlaod it. To import in the local reposiotry use the following steps:
   
   Note: You need maven in your system to run the command.
   1.	Visit the MSDN site for SQL Server and download the latest version of the JDBC driver for your operating system.
   2.	Unzip the package
   3.	Open a command prompt and switch into the expanded directory where the jar file is located.
   4.	Execute the following command. Be sure to modify the jar file name and version as necessary:
  	
  	    mvn install:install-file -Dfile=sqljdbc4.jar -Dpackaging=jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0
 
  If you don't have maven. Go to your local .m2 repository. On my system it is C:\Users\basit.ahmed\.m2\repository. Now create folder hierarch com --> microsoft --> sqlserver --> sqljdbc4 --> 4.0 and put jar in this folder.
  
  Now you are able to run the project. Right click on LS360ApiGateway --> Run As or Debug As --> Run On Server or Debug On Server.
  
  The project will start and land you on login page. Here you can login with your dev username, password.
  
  Thanks
  

    
    

