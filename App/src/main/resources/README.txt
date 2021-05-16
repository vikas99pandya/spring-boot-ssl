
<--------- Functionalities ------------------------->
.. Rest Controller
.. Spring security, customized UserDetailsService & JWT token creation & validations
.. Filter(calculate time for each methods) & interceptors
.. Logging, console & appender,
.. Repository for one to many design
.. Aspect for calculate service timings
.. Caching
.. Https security for calling other microservices
.. Exception handling with controller advice
.. Sleuth for transaction handling
.. Circuit breaker/Spring retry implementation

.. Spring boot test, integration test, TDD test
.. Actuator for app monitoring ??
.. Open API & Swagger ??
.. Message resource handling ??
.. PDF and excel generation ??

.. Soap service ??


<---------- Generate keys ------------------------>
-- generate key pair with keystore in JKS format
keytool -genkey -alias CustServer -keyalg RSA -validity 1825 -keystore "CustServer.jks" -storetype JKS -dname "CN=localhost,OU=My Company Name,O=My Organization,L=My Location,ST=My State,C=My Country Short Code" -keypass password -storepass password

---- export certificate from JKS
keytool -exportcert -alias CustServer -keystore CustServer.jks -file CustServer.cer
keytool -export -alias CustServer -file CustServer.crt -keystore CustServer.jks

-Add Server certificate to client truststore
keytool -importcert -alias CustServer -keystore CustClient.jks -file CustServer.cer


<---------- Swagger & Open API --------------------->

http://localhost:8080/swagger-ui-custom.html
http://localhost:8080/api-docs


<----------