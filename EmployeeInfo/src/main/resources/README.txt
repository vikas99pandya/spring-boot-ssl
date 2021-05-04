-- generate key pair with keystore in JKS format
keytool -genkey -alias CustServer -keyalg RSA -validity 1825 -keystore "CustServer.jks" -storetype JKS -dname "CN=localhost,OU=My Company Name,O=My Organization,L=My Location,ST=My State,C=My Country Short Code" -keypass password -storepass password

---- export certificate from JKS
keytool -exportcert -alias CustServer -keystore CustServer.jks -file CustServer.cer
keytool -export -alias CustServer -file CustServer.crt -keystore CustServer.jks

-Add Server certificate to client truststore
keytool -importcert -alias CustServer -keystore CustClient.jks -file CustServer.cer