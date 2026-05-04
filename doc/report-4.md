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
kommer samtidig. I tillegg kunne vi ha etablert retningslinjer for git-praksis tidligere, som for eksempel å ha konsistente commits-meldinger og holde til samme språk. 
De øvrige rollene har i praksis vært noe flytende, da alle også har jobbet som utviklere. Dette har 
fungert bra for fremdriften og vist verdien av tydelig kommunikasjon.



### Ytterlige forbedringer

- For å styrke samarbeidet enda mer kunne vi ha hatt en egen Kanban/Trello-ansvarlig. Denne rollen kunne hatt en konkret 
oversikt over hvem som gjorde hva til hvilke tider og hvordan utviklingen henger sammen. De kunne videreført denne 
informasjonen til gitmaster, slik at hele produksjons-pipelinen ble mer sammenhengende. 
- Vi hadde samme roller gjennom hele prosjektet. For å forbedre forståelsen av hva de ulike rollene innebærer, og for 
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

java -jar target/dungeoncrawler-1.26-SNAPSHOT-fat.jar

Krever Java 25 eller nyere

## 4. Teamprosess og samarbeid

Vi har brukt Kanban som arbeidsmetodikk, med Trello for å holde oversikt over oppgaver. Vi prøvde også parprogrammering.
Da Leila og Njål skulle parprogrammere på romlogikk, diskuterte de hvordan de kunne gå frem. Leila skrev en kjapp 
pseudokode utifra funksjonene Njål ville implementere. Deretter så Njål gjennom og bygget det videre ved å skrive det 
til kode. Slik itererte de prosessen og byttet på rollene. Gjennom dette fikk begge dannet en god forståelse av koden, 
lært å jobbe med andres kode, og foreslå forbedringer og implementasjoner til en annen person. Ulempen med denne 
prosessen var at det var tidskrevende, skapte mer rom for misforståelse, og mindre indiviudell kodefrihet.

Arbeidet ble organisert i ukentlige møter hvor vi oppdaterte hverandre på progresjonen, 
evaluerte hva vi hadde gjort og planla nye oppgaver og fordelte disse. Utenom disse møtene så har kommunikasjonen 
foregått via Messenger. Her kunne vi avklare raskt hvis man lurte på noe, eller komme med oppdateringer.
I tillegg har vi alle erfart at LLM-modeller har vært nyttige verktøy for å debugge og kvalitetssikre i henhold til SOLID. 
Likevel har vi opplevd at noen av forslagene ikke er fornuftige og/eller følger prinsippene for god kodestil. 


### Git og utviklingsflyt

Vi jobbet med:

- En egen dev branch utenfor main, her hadde vi:
  - egne branches per feature
  - egne branches for testfiler
- Merge requests som ble håndtert av gitmaster

Dette har gitt god struktur, men git-arbeidsflyten i en gruppe var relativt nytt for alle. Vi opplevde derfor enkelte 
merge-konflikter hvis noen glemte å pulle før de startet å jobbe. For eksempel, da to personer jobbet i samme branch på en større feature, og de endret på samme kodeseksjon så oppsto det en konflikt da de skulle commite. Da har git-masteren prøvd å løse dette ved å "resolve" konfliktene på gitlab, og kommunisere med teamet hvordan konflikten kan ha oppstått slik at det ikke gjentar seg, som for eksempel ved å oppfordre til å huske å oppdatere koden ved å "git pull" oftere eller si ifra hvilken kodeseksjon en jobber med.
Samtidig har vi rotert litt på hvem som er git master for at alle skal lære git-flyten. Gjennom denne arbeidsflyten har vi lært verdien av:
- hyppige commits
- god koordinering av oppgaver
- samarbeid og parprogrammering
- tydeligere kommunikasjon


### Generelt samarbeid

Samarbeidet i gruppen har fungert godt. Vi har vært åpne for å stille spørsmål og hjelpe hverandre. Parprogrammering og 
møter har blitt brukt ved mer komplekse oppgaver, noe som har:
- forbedret kodekvaliteten og kommunikasjonsevner 
- bidratt til økt felles forståelse av koden og oppgaven


## 5. MVP-krav
- Statisk spilleverden
- Spiller i spillverden
- Bevegelse i åtte retninger
- Interaksjon med terreng og fiender
- Fiender med oppførsel
- Progresjon
- Spilleren kan dø
- Start- og game over-skjerm

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

## 8. Brukerhistorier med akseptansekriterier

### Brukerhistorie
Som spiller vil jeg kunne bevege karakteren min i alle retninger, slik at jeg kan utforske rommene og unngå fiender.

#### Akseptansekriterier
- Spilleren beveger seg opp, ned, venstre og høyre med WASD eller piltaster
- Spilleren stopper når ingen taster er trykket
- Spilleren kan ikke gå gjennom vegger
- Animasjonen viser riktig retning spilleren beveger seg

#### Arbeidsoppgave
Implementere bevegelseskontroller med kollisjonssjekk og retningsbaserte animasjoner.


### Brukerhistorie
Som spiller vil jeg kunne angripe fiender med et sverdangrep, slik at jeg kan bekjempe dem og komme videre i spillet.

#### Akseptansekriterier
- Angrep utløses med piltastene
- Angrepet har en visuell animasjon med riktig retning
- Angrepet treffer fiender innenfor rekkevidde
- Det er en cooldown mellom angrep slik at man ikke kan spamme
- Lyd spilles av ved angrep

#### Arbeidsoppgave
Implementere en angrepsmekanisme med retningsbasert hitbox, cooldown og tilhørende lyd- og animasjonseffekter.


### Brukerhistorie
Som spiller vil jeg at fiender skal jage meg og angripe meg ved berøring, slik at spillet byr på utfordringer.

#### Akseptansekriterier
- Fiender begynner å bevege seg mot spilleren innenfor en viss radius
- Fiender navigerer rundt vegger
- Fienden skader spilleren ved kollisjon
- Fienden blir stunnet etter å ha skadet spilleren

#### Arbeidsoppgave
Implementere AI-styrt bevegelse med pathfinding, deteksjonsradius og kollisjonsbasert skade.


### Brukerhistorie
Som spiller vil jeg at karakteren min skal ta skade og kunne dø, slik at spillet gir en reell utfordring.

#### Akseptansekriterier
- Spilleren mister liv ved kontakt med fiender
- Spilleren er midlertidig usårbar etter å ha tatt skade (invincibility frames)
- Lyd spilles av når spilleren tar skade
- Spillet viser game over-skjerm når spilleren når 0 liv

#### Arbeidsoppgave
Implementere helsesystem med invincibility frames, lydeffekter og overgang til game over-skjerm ved død.


### Brukerhistorie
Som spiller vil jeg kunne plukke opp power-ups som midlertidig forbedrer fart, angrep eller helse, slik at jeg kan tilpasse spillestilen min.

#### Akseptansekriterier
- Power-ups er synlige i verden
- Power-up plukkes opp ved kollisjon med spilleren
- Fart- og angreps-powerups reverseres automatisk etter 10 sekunder
- Lyd spilles av ved pickup
- Power-ups forsvinner etter de er plukket opp

#### Arbeidsoppgave
Implementere ulike power-ups med tidsbasert effekt, pickup-logikk og lyd- og visuell feedback.


### Brukerhistorie
Som spiller vil jeg at dørene i rommet skal åpne seg når alle fiender er bekjempet, slik at jeg kan gå videre til neste rom.

#### Akseptansekriterier
- Dørene er lukket når rommet har fiender
- Dørene åpner automatisk når siste fiende dør
- Spilleren kan gå gjennom en åpen dør for å bytte rom
- Spillerens helse og stats beholdes mellom rom

#### Arbeidsoppgave
Implementere dørlogikk som åpner seg når rommet er ryddet, og rombytte som bevarer spillerens tilstand.



### Brukerhistorie
Som spiller vil jeg se helse og antall gjenværende fiender på skjermen, slik at jeg kan ta informerte beslutninger.

#### Akseptansekriterier
- Hjerter vises i øvre venstre hjørne og oppdateres ved skade
- Antall gjenværende fiender vises på skjermen
- HUD-elementer er alltid synlige uavhengig av kameraposisjon

#### Arbeidsoppgave
Implementere et HUD som viser spillerens helse og antall gjenværende fiender uavhengig av kameraet.



### Brukerhistorie
Som spiller vil jeg møte en forside når jeg starter spillet og ha tilgang til en hjelpeskjerm, slik at jeg vet hvordan jeg spiller.

#### Akseptansekriterier
- Forsiden viser spillets navn og en meny med Start, Help og Exit
- Hjelpeskjermen viser alle tastebindinger
- Man kan navigere menyen med piltaster og Enter
- ESC returnerer til forsiden fra hjelpeskjermen

#### Arbeidsoppgave
Implementere tittelskjerm og hjelpeskjerm med menynavigasjon og tastebindingsoversikt.

## 9. Kjente bugs og begrensninger
Spilleren kan miste flere liv ved kontakt med flere fiender i samme frame.

## 10. Kode og tekniske valg

Koden er delt opp i standard MVC komponenter, i tillegg til sound som en egen komponent. Vi har jobbet for å holde hver 
komponent godt adskilt fra andre ved å redusere avhengigheter på tvers av komponentene. Model inneholder alle entiteter 
og in-game logikk. View inneholder alt av grafikk og rendering. Controller har som eneste oppgave å lese input fra
brukeren og sende det videre. Dette har gjort det mulig å utvikle funksjonalitet parallelt og man kan dele inn i en klar
frontend og backend del.

## 12. Retrospektiv

Vi har i stor grad nådd målene for MVP og fått på plass sentral funksjonalitet som bevegelse, kamp, lyd og 
brukergrensesnitt. Dette tyder på at planleggingen har vært realistisk og gjennomførbar. Kanban og Trello har gitt god 
oversikt, men kunne vært brukt mer aktivt og systematisk. Git har fungert godt i samarbeid, men vi ser at det er behov 
for bedre koordinering og tydeligere rutiner, særlig når det gjelder commit-navn. Samarbeidet i gruppen har vært bra, 
og parprogrammering har hatt en positiv effekt der det har blitt brukt. Gruppechatten har også vært nyttig for å holde 
fremdriften oppe mellom møtene. Alt i alt har vi etablert et godt grunnlag for videre utvikling, og tar med oss 
erfaringene videre for å forbedre struktur og arbeidsflyt.

Hva fungerte mindre bra:
- Trello ikke alltid oppdatert
- Noe overlapp i arbeid

Forbedringstiltak:
- Oppdatere Trello jevnlig
- Bedre koordinering før utvikling
- Planlagte parprogrammeringsøkter

Prioritering videre:
- Utvide funksjonaliteter, for eksempel flere rom, flere power-ups, flere fiender, en boss. 
- Enda mindre avhengigheter på tvers av komponenter 
- Etablere retningslinjer
- Utdele en Kanban-rolle 
- Konkretisere alle rollenes funksjon og ansvar
