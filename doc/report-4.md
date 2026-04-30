# Rapport – innlevering 4
**Team:** *Sprite Zero* – *Kim Harry Huynh, Guangwei Zhang, Fredrik Udengen, Njål Nordal, Leila Lu*

## 1. Team og roller

- Prosjektleder – Fredrik Udengen
- Kundekontakt – Njål Nordal
- Kundekontakt / Gitmaster – Leila Lu
- Assetansvarlig – Guangwei Zhang
- Assetansvarlig – Kim Harry Huynh

Fredrik var prosjektleder fordi han har mest erfaring med spill-utvikling. 
Han kunne derfor vurdere ulike forslag og hvordan vi burde implementere disse, og hvor mye jobb som må legges ned. 

Njål og Leila var kundekontakter: de videreførte meldinger til og fra gruppeleder, slik at gruppen fikk oversikt over hva
som var forventet.

Guangwei og Kim var assetansvarlige. De stod for grafikken (sprites, tiles) og lydene. De utforsket hvilket tema og 
atmosfære spillet skulle ha.

Leila var også gitmaster. Når man ville merge ulike branches så sendte man en merge request som Leila så gjennom før hun 
godkjente, og eventuelt løste konflikter som dukket opp.

### Refleksjon rundt roller

Rollene i gruppen har i stor grad fungert etter hensikt. Rollene har ført til en god flyt i arbeidet og en klar inndeling 
av ansvar. Samtidig har vi erfart at gitmaster-rollen til tider kunne bli en flaskehals dersom flere merge requests 
kommer samtidig. De øvrige rollene har i praksis vært noe flytende, da alle også har jobbet som utviklere. Dette har 
fungert bra for fremdriften og vist verdien av tydelig kommunikasjon.

### Ytterlige forbedringer

- For å styrke samarbeidet enda mer kunne vi ha hatt en egen Kanban/Trello-ansvarlig. Denne rollen kunne hatt en konkret 
oversikt over hvem som gjorde hva til hvilke tider og hvordan utviklingen henger sammen. De kunne videreført denne 
informasjonen til gitmaster, slik at hele produksjons-pipelinen ble mer sammenhengende. 
- Vi hadde sammen roller gjennom hele prosjektet. For å forbedre forståelsen av hva de ulike rollene innebærer, og for 
læringens skyld, kunne vi rotert på rollene innad i gruppen.

## 2. Konsept

Spillet er inspirert av Binding of Isaac.

- Spillfigur som kan bevege seg i 8 retninger
- Todimensjonal verden med tiles: Vegger, gulv og dører
- Fritt bevegelsesområde innenfor veggene
- Spilleren kan angripe fiender med et melee attack
- Fiender beveger seg mot og skader spilleren ved berøring
- Spilleren går videre å drepe fiender og gå til nye rom
- Spillet handler om å overleve, utforske rommene og bekjempe fiender

### Verden:

- Er bygget opp av tiles med fast størrelse
- Er større enn skjermen, vi bruker derfor scrolling
- Inneholder:
  - power-ups (angrep, fart og helse) som påvirker spilleren
  - dører som åpner når man har drept alle fiender i et rom, som fører til nye rom
  - fiender som jager spilleren

## 3. Kjøring

Kompileres med:

mvn package

Kjøres med:

java -jar target/skeleton-1.0-SNAPSHOT-fat.jar
====================== LAG JAR FIL FØR VI LEVERER =======================

Krever Java 25 eller nyere

## 4. Teamprosess og samarbeid

Vi har brukt Kanban som arbeidsmetodikk, med Trello for å holde oversikt over oppgaver. Vi prøvde også parprogrammering 
og test-driven (?) programmering. Arbeidet ble organisert i ukentlige møter hvor vi oppdaterte hverandre på progresjonen, 
evaluerte hva vi hadde gjort og planla nye oppgaver og fordelte disse. Utenom disse møtene så har kommunikasjonen 
foregått via Messenger. Her kunne vi avklare raskt hvis man lurte på noe, eller komme med oppdateringer.

### Git og utviklingsflyt

Vi jobbet med:

- En egen dev branch utenfor main, her hadde vi:
  - egne branches per feature
  - egne branches for testfiler
- Merge requests som ble håndtert av gitmaster

Dette har gitt god struktur, men git-arbeidsflyten i en gruppe var relativt nytt for alle. Vi opplevde derfor enkelte 
merge-konflikter hvis noen glemte å pulle før de startet å jobbe (UTDYP HVORDAN LØSTE KONFLIKT). Gjennom denne arbeidsflyten har vi lært verdien av:
- hyppige commits
- god koordinering av oppgaver
- samarbeid og parprogrammering

Samtidig har vi rotert litt på hvem som er git master for at alle skal lære git-flyten.

### Generelt samarbeid

Samarbeidet i gruppen har fungert godt. Vi har vært åpne for å stille spørsmål og hjelpe hverandre. Parprogrammering og 
møter har blitt brukt ved mer komplekse oppgaver, noe som har:
- forbedret kodekvaliteten
- bidratt til økt felles forståelse av koden og oppgaven


## 5. MVP-krav
Statisk spilleverden
Spiller i spillverden
Bevegelse i åtte retninger
Interaksjon med terreng og fiender
Fiender med oppførsel
Progresjon
Spilleren kan dø
Start- og game over-skjerm

## 6. Progresjon

Vi har implementert:

- Spillerbevegelse
- Interaksjon med fiender
- Angrep
- Skade og livssystem
- Power-ups (angrep, fart, helse)
- UI (liv, enemies left)
- Rom-progresjon
- Start- og andre skjermer
- Grafikk
- Lydeffekter og bakgrunnsmusikk
- Tester for mesteparten av funksjonaliteten

MVP-kravene er oppfylt.

## 7. Spesifikasjon (trenger vi dette?)

Vi lager et 2D-spill der spilleren navigerer gjennom rom, unngår fiender og bekjemper dem for å samle poeng. Målet er å 
overleve så lenge som mulig og oppnå høyest mulig score.

## 8. Brukerhistorier med akseptansekriterier

### Eksempel (FJERN ETTER HVERT)

Brukerhistorie:
Som spiller vil jeg at kameraet skal følge karakteren min

Akseptansekriterier:
- Spilleren er alltid synlig
- Kamera følger i alle retninger
- Bevegelsen er jevn

Arbeidsoppgaver:
- Implementere kamera klasse i koden
- Koble kameraposisjon til spillerens posisjon
- Oppdatere kamera i gameloop
- Osv..

Eksempel på en brukerhistorie som kan splittes opp:
Som spiller vil jeg ha lydeffekter, dette kan deles opp i:
- Som spiller vil jeg høre lyd når jeg angriper
- Som spiller vil jeg høre lyd når jeg tar skade
- Som spiller vil jeg ha bakgrunnsmusikk

### Som spiller vil jeg at kameraet skal følge karakteren min

#### Akseptansekriterier:
- Kamera følger spilleren i alle retninger
- Spilleren er alltid synlig
- Verden scroller jevnt
- Power-ups

#### Implementasjon:
- vi må bruke Camera fra libGDX og oppdatere dette etter controller-input (idk?)

### Som spiller vil jeg kunne finne power-ups

#### Akseptansekriterier:
- Power-ups finnes i verden
- Spilleren kan plukke dem opp
- De påvirker spilleren (fart/skade)

#### Implementasjon:
- vi må opprette en Powerup klasse og underklasser for de spesifikke powerupene
- powerupene må interagere med spilleren via collision detection

### Som spiller vil jeg kunne se hvor mange liv jeg har

#### Akseptansekriterier:
- Spilleren har liv representert med hjerter 
- UI oppdateres ved skade 
- Game over ved 0 liv 

#### Implementasjon
- vi må lage en UI klasse som tegner oppå spill-skjermen
- UI-en må lese hvor mange liv spilleren har og oppdatere seg

### Som spiller vil jeg ha lydeffekter

#### Akseptansekriterier:
- Lyd ved angrep
- Lyd ved skade
- Bakgrunnsmusikk spilles

#### Implementasjon 
- vi må lage Sound-arkitetur slik at .ogg filer spilles av når en hendelse skjer

## 9. Kjente bugs og begrensninger
Spilleren kan miste flere liv ved kontakt med flere fiender i samme frame.

## 10. Kode og tekniske valg

Koden er delt opp i standard MVC komponenter, i tillegg til sound som en egen komponent. Vi har jobbet for å holde hver 
komponent godt adskilt fra andre ved å redusere avhengigheter på tvers av komponentene. Model inneholder alle entiteter 
og in-game logikk. View inneholder alt av grafikk og rendering. Controller har som eneste oppgave å lese input fra
brukeren og sende det videre. Dette har gjort det mulig å utvikle funksjonalitet parallelt og man kan dele inn i en klar
frontend, og backend del.

========== SE PÅ DETTE UNDER ===================
## 12. Retrospektiv

Vi har i stor grad nådd målene for MVP og fått på plass sentral funksjonalitet som bevegelse, kamp, lyd og UI. Dette 
viser at planleggingen har vært realistisk og overkommelig. Kanban og Trello har gitt god oversikt, men kunne vært brukt 
mer aktivt. Git har fungert bra, men krever bedre koordinering og rutiner for presise navn. Samarbeidet i gruppen har 
vært godt, og parprogrammering har hatt positiv effekt der det er brukt. I tillegg har gruppechatten vært effektiv for å 
videreutvikle spillet mellom møtene. Vi har etablert et solid grunnlag for videre utvikling, og tar med oss erfaringene 
videre for å forbedre både struktur og arbeidsflyt.

Hva fungerte mindre bra:
Trello ikke alltid oppdatert
Noe overlapp i arbeid
Lite systematisk parprogrammering

Forbedringstiltak:
Oppdatere Trello jevnlig
Bedre koordinering før utvikling
Planlagte parprogrammeringsøkter
