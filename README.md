# chat-cifo-java-2023

Chat application using WebSockets, REST, Spring Boot, Thymeleaf and React

See [Wiki](https://github.com/daniel-training/chat-cifo-java-2023/wiki) for more information.
<br />
<br />
<br />
<br />

## Development environment 
### Security configuration 

The Security is managed by JSON Web Tokens (JWT). A pair of RSA PEM-encoded PKCS#8 asymmetric keys are need to be configured in the environment.

To generate the development asymmetric keys, use your preferred manual method or by executing the script provided in:

```
src/main/resources/security/scripts/gen_asymmetric_keys.sh
```

Open a terminal in your IDE or project home and execute:

```
   $ cd src/main/resources/security/scripts/
   $ ./scripts/gen_asymmetric_keys.sh
```

