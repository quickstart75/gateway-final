<%-- One of the most powerful things you can do with tag files is establish a system of HTML templates for your application to use. 
    This templating system can take care of many of the repetitive tasks needed on pages across your application, cutting down on 
    duplicated code and making it easier to change the design of your site. 
    
    tag files work essentially like JSP files do. They contain the same syntax and must follow the same basic rules, and at run time
	they get translated and compiled into Java just like JSPs do. Tag files can use any normal template text (including HTML), any 
	other JSP tag, declarations, scriptlets, expressions, and expression language. It should not be surprising, however, that there
	are some minor differences between the two file formats, mainly concerning the directives available for tag files.
	
	Tag files can also use the include and taglib directives to include files and other tag libraries in the JSP, but there is no 
	page directive in tag files. The include directive can be used to include .jsp, .jspf, and other .tag files in a .tag file, or 
	.jspx and other .tagx files in a .tagx file. Using a taglib directive in a tag file is identical to using one in a JSP file.

	Instead of the page directive, tag files have a tag directive. This directive replaces the necessary functionality from JSP’s 
	page directive and also replaces many of the configuration elements from a <tag> element in a TLD file. The tag directive has the
	following attributes, none of which are required:

		* pageEncoding : This is equivalent to the page directive pageEncoding attribute and sets the character encoding of the tag’s
		                 output.
        * isELIgnored : Equivalent to its counterpart in the page directive, this instructs the container not to evaluate EL 
                        expressions in the tag file and defaults to false.

		* language : This specifies the scripting language used in the tag file (currently only Java is supported), just like the 
		             language attribute in the page directive.

		* deferredSyntaxAllowedAsLiteral : Just like the page directive attribute, this tells the container to ignore and not parse 
		                                   deferred EL syntax within the tag file.

		* trimDirectiveWhitespaces : This tells the container to trim white space around directives, equivalent to the same attribute
							 		on the page directive.

		* import : This attribute works just like the page directive’s import attribute. You can specify one or more comma-separated 
		           Java classes to import in this attribute, and you can use the attribute multiple times in the same tag directive 
		           or across multiple tag directives.

		* description : This is the equivalent of the <description> element in a TLD file, and specifying it can be helpful for 
		                developers to understand your tag better.

		* display-name : Equivalent to the <display-name> element in a TLD, there is usually no need to specify this.
		* small-icon and large-icon : These attributes essentially replace the <icon> element in a TLD, and you should never need to
									 specify them.

		* body-content : This is the replacement for <body-content> in a TLD, with one minor change: Its valid values are empty, 
						scriptless, and tagdependent. The JSP value available in a TLD is not valid for the body content of a tag 
						specified in a tag file. Due to the limitations of how tag files work, you cannot use scriptlets or 
						expressions within the nested body content when using a tag that was defined in a tag file. scriptless is the
						default value for this attribute.

		* dynamic-attributes : This string attribute is the counterpart of the <dynamic-attributes> element in a TLD and indicates 
								whether dynamic attributes are enabled. By default the value is blank, which means that dynamic 
								attributes are not supported. To enable dynamic attributes, set its value to the name of the EL 
								variable you want created to hold all of the dynamic attributes. The EL variable will have a type of 
								Map<String, String>. The map keys will be dynamic attribute names, and the values attribute values.
								
	Notice that equivalent directive attributes are missing for the <name>, <tag-class>, <tei-class>, <variable>, <attribute>, and 
	<tag-extension> elements. The tag name is inferred from and always equal to the tag filename (minus the .tag extension), and 
	<tag-class> is not needed because the tag file is the tag handler (or will be, after the container compiles it). There is no way
	to specify a TagExtraInfo class or a tag extension for tag files because there is simply no equivalent. This leaves <variable> 
	and <attribute>, which are replaced with the variable and attribute directives, respectively.

    The first directive in this file establishes that uses of the tag can contain body content, and that directive white space should
    be trimmed. Note the use of the trimDirectiveWhitespace attribute to accomplish this; the <jsp-config> in the deployment 
    descriptor does not affect tag files, so you need to do this manually in each tag file. 
--%>
<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="htmlTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ attribute name="bodyTitle" type="java.lang.String" rtexprvalue="true" required="true" %>
<%@ include file="/WEB-INF/jsp/base.jspf" %>

<%-- When you use a JSP tag, you typically specify all the attributes as normal XML attributes and then the contents within the tag 
    make up the entire tag body. However, when an attribute value is too long or contains JSP content, you can use the 
    <jsp:attribute> tag within the tag body to specify the attribute value. 

--%>
<template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <link rel="stylesheet" href="<c:url value="/resource/stylesheet/login.css" />" />
    </jsp:attribute>
    <jsp:attribute name="navigationContent" />
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main>
