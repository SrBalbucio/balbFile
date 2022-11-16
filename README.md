# balbFile
Apenas um simples leitor de arquivos balbFile.<br>
Criado pela necessidade imediata de um leitor de arquivos extrememente simples e leve.

## Importando para o seu projeto
Maven:
```maven
  <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  
	<dependency>
	    <groupId>com.github.SrBalbucio</groupId>
	    <artifactId>balbFile</artifactId>
	    <version>1.0</version>
	</dependency>
```

Gradle:
```gradle

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
	dependencies {
	        implementation 'com.github.SrBalbucio:balbFile:1.0'
	}
  ```

Não achou o que precisa? Veja em: https://jitpack.io/#SrBalbucio/balbFile/1.0

## Como criar um arquivo
A criação de um arquivo usando o balbFile é muito simples:
```java
// Para criar um arquivo basta dizer a sua localização
// e se ele deve criar o arquivo se não existir
BalbFile file = new BalbFile(new File("arquivo.balb"), true);
// Após isso você já pode adicionar itens a ele
file.put("melanciaSementes", 500);
file.put("online", true);
file.put("name", "SrBalbucio, the best");
// Para gettar é a mesma ideia
boolean isOnline = (boolean) file.get("online");
int sementes = file.getInteger("melanciaSementes"):
```

Os saves e loads ocorrem automaticamente e não precisam ser gerenciados.<br>
Em caso de problemas me contatar pelo email: suporte@balbucio.xyz
