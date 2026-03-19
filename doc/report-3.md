# Rapport – innlevering 1
**Team:** *Sprite Zero* – *Kim Harry Huynh, Guangwei Zhang, Fredrik Udengen, Njål Nordal, Leila Lu*


## Roller

Prosjektleder / Developer - Fredrik Udengen 
Kundekontakt / Developer - Njål Nordal 
Gitmaster / Developer - Leila Lu
Assetansvarlig / Developer - Guangwei Zhang
Assetansvarlig / Developer - Kim Harry Huynh 


## Konsept

* Inspirert av Binding of Isaac

* Spillfigur som kan styres: går i 8 retninger
* Todimensjonal verden: 
    * Vegg - blokk med fast størrelse som spilleren ikke kan gå gjennom 
    * Spilleren beveger seg rundt ved å gå fritt rundt i rommet 
    * Spilleren kan angripe fiender ved å slå
* Fiender som beveger seg og er skadelige ved angrep 
* Fiender skader spilleren ved berøring 
* Spilleren samler poeng ved å drepe fiender 
* Utfordringen i spillet er å bekjempe fiendene 

* Verden er bygget opp av blokker med fast størrelse 
* Verden er større enn skjermen og scroller horisontalt og vertikalt
* "Power-ups" som gir spilleren spesielle krefter


## Kjøring


Kompileres med mvn package.
Kjøres med java -jar target/skeleton-1.0-SNAPSHOT-fat.jar

Krever Java 25

## Teamprosess


Hovedsakelig velger vi å bruke Kanban.
Ukentlige møter hvor vi setter mål for uken, bruker trello for oversikt om veien videre, kommunikasjon skjer via Messenger 
Delegerer oppgaver ukentlig. deler teamet opp, og 1-2 utviklere jobber på hver sin branch hvor de implementerer/løser et bestemt problem. 
Når temaet er ferdig i den aktuelle branchen, sender de merge request til git masteren (Leila) som ordner opp i eventuelle git-konflikter. 
Google Docs brukes til referat. 

## Progresjon
Implementert rom, attack, interaksjon med enemy og gjøre/ta skade, grafikk (sprite). startmeny

## Mål
Implementere musikk


## Spesifikasjon

Vi skal lage et 2D-spill der spilleren styrer en karakter gjennom ulike rom,
unngår fiender og bekjemper de for å samle poeng. Målet er å overleve så lenge som mulig samtidig som man prøve å oppnå ny high score.

## Kravene til MVP er som følger:

Vise en statisk spilleverden
Vise spiller i spillverden
Flytte spiller i åtte retninger
Spiller interagerer med terrenget og monstre
Vise fiender/monstre; de skal interagere med terreng og spiller
Gi spiller poeng for å bekjempe fiender/monstre.
Spiller kan dø ved kontakt med fiender
Start-skjerm UI ved oppstart / game over


## Brukerhistorier

#### 1.
Som spiller trenger jeg å kunne skille mellom vegger, fiender og gulv.
Disse elementene må derfor ikke være veldig like hverandre, og man bør også ta hensyn til svaksynte og fargeblinde.

#### 2.
Som spiller vil jeg at kameraet skal følge karakteren min, slik at jeg alltid vet hvor jeg befinner meg.

#### 3.
Som spiller vil jeg kunne finne power-ups, slik at jeg kan variere angrepene mine.

#### 4. 
Som spiller vil jeg kunne se hvor mange liv jeg har, slik at jeg kan unngå å dø.

#### 5.
Som spiller vil jeg kunne finne helbredelsesgjenstander, slik at jeg kan øke livet mitt igjen.
Eksperimentering med koden

#### 6.
Som spiller vil jeg at spillet skal ha lydeffekter når jeg angriper eller tar skade, slik at jeg får bedre tilbakemelding på handlingene mine.

#### 7.
Som spiller vil jeg ha bakgrunnsmusikk som passer til stemningen, slik at spillopplevelsen blir mer engasjerende.

#### 8.
Som spiller vil jeg kunne pause spillet, slik at jeg kan ta pauser uten å miste progresjon.

#### 9.
Som spiller vil jeg at ulike rom skal ha forskjellige utfordringer og fiender, slik at spillet føles variert og ikke blir ensformig.

#### 10.
Som utvikler vil jeg at koden skal være modulær og lett å utvide, slik at vi enkelt kan legge til nye funksjoner senere.

#### 11.
Som spiller vil jeg ha en angrepsanimasjon, slik at jeg tydelig ser når angrepet mitt treffer.

#### 12.
Som spiller vil jeg at spillet skal bli gradvis vanskeligere, slik at det opprettholder utfordringen over tid.



