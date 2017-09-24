# devcase-boot

## Introdução

O objetivo do projeto é estabelecer padrões e prover bibliotecas para facilitar o desenvolvimentos de sistemas de software em `Java` na
Devcase. Ele depende do `spring-boot` para seu funcionamento.

Alguns dos recursos do framework são:

  - Suporte a Undertow com JSP
  - Taglibs JSP
  - Componentes para a criação de cruds em aplicações web
  - Integração com SiteMesh
  - Ferramentas para a geração de scripts DDL
  - Repositórios JPA com mais recursos, com base em `spring-jpa-data`
  - Gerador automático de cruds (planejado)
  
## Getting Started

No arquivo `pom.xml`:

```xml
	<parent>
		<groupId>br.com.devcase.boot</groupId>
		<artifactId>devcase-boot-starter-parent</artifactId>
		<version>0.1-SNAPSHOT</version>
	</parent>
```

e também:

```xml
	<repositories>
		<repository>
			<id>devcase-repo</id>
			<name>Devcase Releases</name>
			<url>http://repo.devcase.com.br/release</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
		<repository>
			<id>devcase-repo-snapshots</id>
			<name>Devcase Snapshots</name>
			<url>http://repo.devcase.com.br/snapshot</url>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</repository>
	</repositories>
```

## AutoConfiguration x Opt-in

Componente                          |Feature                               | Habilitar
------------------------------------|--------------------------------------|------------------------------------------------------------------------
`devcase-boot-labels`               | Labels com textos úteis              | `@EnableDevcaseLabels`
`devcase-boot-jsp`                  | Undertow com JSP                     | `@EnableUndertowJsp` ou automático com `devcase-boot-starter-jsp`
`devcase-boot-sitemesh`             | SiteMesh                             | `@EnableSiteMesh`
`devcase-boot-crud`                 | Repositórios JPA                     | `@EnableJpaRepositories(repositoryBaseClass = CriteriaJpaRepository::class)`

