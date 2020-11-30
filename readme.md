# Efnahagsspá
## Hópur 8
* Ari Sigþór Eiríksson
* Jón Guðjónsson
* Sigurjón Ólafsson
* Þorsteinn Sigurðsson

## Lýsing

Efnahagsspá er netviðmót þar sem notendur geta búið til eigin efnahagsspár. Notendur velja forsendur og gögn 
og viðmótið sækir síðan nauðsynleg gögn frá Hagstofu Íslands. Gögnin eru notuð til þess að gera efnahagsspá sem
er svo birt notanda bæði myndrænt og í töflum. Þær spár sem notendur gera eru vistaðar og geymdar inn á notendareikningi
þeirra, þannig að þeir geta nálgast þær aftur hvenær sem er og uppfært þær með nýjustu gögnum.

Verkefnið byggir á MVC aðferðarfræði og notar Java Spring umgjörðina (e. framework) og Thymleaf sniðmát. Auk þess
nýtir það einnig Java R sýndarvélina renjin til þess að keyra R forrit sem reikna efnahagsspár.

## Hvernig skal keyra Efnahagsspá

Forritið má finna það í möppunni Efnahagsspá. Það keyrir á Java 8 (eða hærra) og er byggt á maven grunni (e. maven project)

Í rót forritsins er maven pom skrá sem heldur utan um öll háð forritasöfn (e. dependancies) og aðra eiginleika forritsins. Til þess að þýða forrit má 
nota maven og skipunina "package". Síðan er hægt að keyra þýðinguna með Java. Einnig má nota IDE sem styður maven til þess að þýða forritið (forritið var 
til að mynda unnið í IntelliJ). 

Keyrsluklasi verkefnisins er skráin EfnahagsspaApplication.java sem finna má í ./src/main/java/is/hi/hbv501GEfnahagsspa.

Sú útgáfa af Efnhagsspá sem finna má hér keyrir með H2 gagnagrunn sem vistaður er í minni. Þessu má breyta með því að fara í ./src/main/resources/application.properties
og breyta línunni "spring.datasource.url=jdbc:h2:mem:testdb" í "spring.datasource.url=jdbc:h2:mem:file:~/database/databse".

## Skipulag forritunartexta

Forritið fylgir MVC aðferðarfræði og notast við Java Spring umgjörðina (e. framework).

Keyrsluklasinn EfnahagsspaApplication.java er í ./src/main/java/is/hi/hbv501GEfnahagsspa.

Stjórnendaklasa (e. controllers) má finna í ./src/main/java/is/hi/hbv501GEfnahagsspa/Controllers

Þjónustuklasar (e. services) eru í ./src/main/java/is/hi/hbv501GEfnahagsspa/Services

Tengingar við gagnagrunn má finna í ./src/main/java/is/hi/hbv501GEfnahagsspa/Repositories

Einingar (e. entities) eru í ./src/main/java/is/hi/hbv501GEfnahagsspa/Entities

Sniðmát (html og css skjöl) má finna í .src\main\resources

Eins og fram kom hér að ofan er pom skjal í rót forritisins sem heldur utan um háð forritasöfn en auk þess má einnig finna
skrá með Spring Boot stillingar í application.properties sem geymd er í ./src/main/resources/.