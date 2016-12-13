package com.softech.ls360.api.gateway.config.spring.annotation;

import org.springframework.stereotype.Controller;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Controller annotation serves two purposes. As an @Component, it is responsible for marking your controllers as Spring-managed 
 * beans eligible for instantiation and dependency injection. However, in the context of Spring MVC, it is also responsible for 
 * marking beans to be scanned for @RequestMapping. A bean marking with @RequestMapping cannot respond to requests if it is not 
 * annotated with @Controller. Thus, your REST controllers must, like your web controllers, be marked with @Controller.
 * 
 * Of course, this presents some challenges. You don’t want your REST controllers to be picked up by component scanning for your web 
 * DispatcherServlet, and you don’t want your web controllers to be picked up by component scanning for your REST DispatcherServlet,
 * either. The obvious solution is to place your web controllers in a separate package from your REST controllers and component scan
 * only the appropriate package in each application context. This is certainly an easy approach as well, and in most cases it can 
 * satisfy any use cases you might have. However, there is another way to tackle this problem: meta-annotations.
 * 
 * @Controller, @Service, and @Repository are all annotated with @Component. This means you can create your own stereotype 
 * annotations, too, by meta-annotating them with an existing @Component. It isn’t just @Component that is inherited, either. You can
 * meta-annotate a custom annotation with @Controller, for example, giving your annotation the same force as @Controller. This is 
 * especially important because it means that your custom annotations can mark controllers for request mapping, which solves the 
 * controller segregation problem quite nicely.
 * 
 * Both annotations (RestEndpoint and WebController) are practically identical, but their semantic meanings have important 
 * differences. Both indicate that the target bean is a controller eligible for request mapping. However, @WebController marks 
 * controllers for traditional web requests, whereas @RestEndpoint denotes RESTful web service endpoints. The lone value attribute 
 * serves the same purpose here as it does in other stereotype annotations: It provides a way to specify the bean name that overrides
 * the default bean name pattern.
 * 
 * @author basit.ahmed
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface RestEndpoint {
	String value() default "";
}
