# Impact Faible
* Remove this unused import 'java.util.ArrayList'. [java:S1128]
* Remove this unused import 'java.util.List'. [java:S1128]
* Remove this unused method parameter "repository". [java:S1172]
* Explicitly specify the time zone by passing a ZoneId or a Clock to the .now() method. [java:S8688]

# Impact Moyen
* Move this file to a named package. [java:S1220]
*  Add a private constructor to hide the implicit public one. [java:S1118]
* This block of commented-out lines of code should be removed. [java:S125]

# Impact Important
* Replace this persistent entity with a simple POJO or DTO object. [java:S4684]
* Replace this instanceof check and cast with 'instanceof RapportConsole rapportconsole' [java:S6201]
* Add at least one assertion to this test case. [java:S2699]
* Replace this persistent entity with a simple POJO or DTO object. [java:S4684]
* Rename this generic name to match the regular expression '^[A-Z][0-9]?$'. [java:S119]
* Replace this persistent entity with a simple POJO or DTO object. [java:S4684]
* Replace this use of System.out by a logger. [java:S106]



