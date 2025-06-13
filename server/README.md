# SM Share Server

### Bash Commands

Generate a self-singed certificate using Keytool
```bash
keytool -keystore keystore.jks \
  -alias smsshare \
  -genkeypair \
  -keyalg RSA \
  -keysize 4096 \
  -validity 3 \
  -dname 'CN=localhost, OU=ktor, O=ktor, L=Unspecified, ST=Unspecified, C=US'
```

Convert PEM into the PKCS12 format using openssl
```bash
openssl pkcs12 -export -in cert.pem -inkey key.pem -out keystore.p12 -name "smsshare"
```

Convert PKCS12 to the JKS format using keytool
```bash
keytool -importkeystore -srckeystore keystore.p12 -srcstoretype pkcs12 -destkeystore keystore.jks
```