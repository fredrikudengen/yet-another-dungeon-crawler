# Rapport – innlevering 1
**Team:** *Sprite Zero* – *Kim Harry Huynh, Guangwei Zhang, Fredrik Udengen, Njål Nordal, Leila Lu*


Team/prosjekt: 

- Rollene i teamet fungerer veldig bra. Vi har kartlagt våre kompetanser, styrker og svakheter så vi vet hvordan å forholde seg til hverandre. 

- Fredrik er prosjektleder, han står for spillogikk og koden. Han og njål kom med ideer til spillet, så de vil naturligvis ha mer ansvar for utviklingen av spillet. Guang sitt hovedansvar er å designe karakterer, powerups, etc. ved å bruke sprite. Kim skal bistå og jobber med romutvikling for spillet. Leila er Git-master og har ansvar for å skrive rapportene, referatene og at kravene blir møtt. Njål skal jobbe med spillfunksjonaliteten og jobber tett med Guang og Kim for å få tegnet riktige trekk på karakterene (farge, størrelse, tema). Per nå er det lite delegering av arbeidsoppgaver og mer forståelse for prosjektet og kommunikasjon innad teamet for å passe på at vi alle er på samme side. 


- Ingen på gruppen har særlig erfaring med prosjektarbeid annet enn tidligere skoleoppgaver (inf100-102). Der var man gjerne 2 personer per gruppe, så å øke antallet til 5 er både spennende og ukjent. Derimot har vi alle erfaring fra 101 å utvikle spill som er grid basert. Foreløpig er vi fornøyde med de valgene vi har tatt. Vi får jobbe med våre egne styrker, men skal parprogrammere og lære fra hverandre om egenskaper vi ikke er komfortable med. Per nå har vi ingen tanker om hva som må forbedres, men kanskje det endrer seg etterhvert. 

- Gruppedynamikken fungerer veldig bra. Stemningen er uproblematisk og vi kommuniserer godt. Vi har en gruppe på messenger der alle aktivt deltar og svarer raskt. Vi har faste møter på torsdager 1230 med rom for fleksibilitet om nødvendig. 

- Frem til nå (omtrent 4-5 dager etter prosjektet kom ut) har vi klart å delegere oppgaver og ansvar, og har fått høre ideene til alle gruppemedlemmene. Guang har allerede startet å tenge karakterer, hørt med oss om tanker og ideer, og er svært mottakelig til tilbakemeldinger. Fredrik har jobbet med funksjonalitet, der karakteren kan flytte seg i 8 retninger med WASD. Utifra dette har Leila, Njål og Kim tenkt på hvordan å bygge spillet videre. Slik jobber vi med samarbeidet og kontinuerlig jobber med å forbedre prosjektstrukturen. 

- Vi har alle ansvar for å passe på at det blir en jevn fordeling med antall commits. 

- Forbedringspunkter: Vi er alle enige om at strukturering av prosjektet kan forbedres, men dette vil naturligvis komme av seg selv når vi får satt oss lenger inn i arbeidet. 


Krav og spesifikasjon:

- Hittil har vi prioriert design av karakterer, hvordan man skal tegne med ulike våpen og retninger av forflytning. Vi skal også prioritere å få på grid, slik at basen er klar. Skjelettet er på plass, så vi kan bygge videre på spillet. Videre skal vi prioritere at videre funksjonaliteter skal passe med MVC (model-view-controller)-strukturen: at ulike form av powerups skal ligge i model-mappen, inni en mappe som heter powerups.  

- Alle har ansvar for å skrive tester, gjerne tester for den funksjonaliteten de selv skriver og at vi har gode commit-meldinger. Generelt følge gode objekt-orienterte programmeringsprinsipper og SOLID. 

- Hittil har vi prioritert å få grunnlaget på plass slik at vi kan bygge funksjonaliteter oppover. 

Kode:
- Per nå er det minimalt med kode, da vi har jobbet mest med idémyldring og bygging av skjelettet. 
- Likevel har vi opprettet mapper der funksjonaliteter skal ligge. Som blant annet at bilder av karakterene ligger under resources, som igjen er delt opp i ulike mapper. 


Møtereferat hittil (12.02.26) – Prosjektgruppen Sprite Zero:

-Møte 1 – 05.02.26
    Tilstede: Alle
    Agenda:
    Idémyndring og valg av spilltype
    Gjennomgang av krav
    Fordeling av arbeidsoppgaver
    Planlegging videre
    Oppsummering:
    Valgte å utvikle et dungeon crawler-spill. Begrunnelse: enkelt å legge til funksjonalitet underveis.
    Gikk gjennom kravene for prosjektet.
    Diskuterte arbeidsfordeling og rettferdighet – må avklares med Anya/Martin (gruppeleder) hva som regnes som jevn fordeling.
    Arbeidsoppgaver:
    Leila: Git-master, referatansvarlig, notater til Git (forberedelse til eksamen)
    Fredrik: Ansvarlig for spillogikk (mest erfaring)
    Njål: Fleksibel rolle – koding foretrekkes fremfor tegning. Ansvarlig for å sette opp IntelliJ for effektiv klassediagram-produksjon.
    Guang: Ansvarlig for tegning/design/sprites
    Kim: Fleksibel rolle – tar ansvar for layout (tittelskjerm, pause)
    Felles: Alle skriver tester for egen kode.
    Verktøy og rutiner:
    Trello: Fredrik oppretter og administrerer. Skal brukes til å kartlegge arbeidsflyt.
    Java 25: Anbefales lastet ned av alle.
    IntelliJ: Anbefalt IDE – Njål tar ansvar for oppsett.
    Parprogrammering: Mulighet for å jobbe to og to ved behov.
    Til neste gang:
    Møtes etter behov – torsdager etter kl. 12:00 fungerer for alle.
    Alle setter seg inn i Git og libGDX.
    Avventer publisering av prosjektoppgaven.

- Møte 2 – 10.02.26
    Tilstede: Alle
    Oppsummering:
    Offisiell oppstart av prosjektet.
    Leila delte prosjektet med resten av gruppen.
    Teknisk:
    Testet Git merge og opprettelse av branches.
    Alle oppfordres til å gjøre seg kjent med Git-flow.
    Plan til neste møte (torsdag):
    Få på plass nødvendige mapper etter MVC-struktur.
    Pushe til dev-branch.
    Ta i bruk Trello aktivt.


- Møte 3 – 12.02.26
    Tilstede: Alle
    Endring i veiledning:
    Ny gruppeleder har overtatt vurderingen.
    Fikk motstridende informasjon: ny gruppeleder forventer presentasjon med frist fredag (én dags frist), til tross for at opprinnelig gruppeleder oppga at det ikke var noen frist.
    Konsekvens: Må justere planene raskt.
    Status:
    Fredrik: Har begynt på funksjonalitet – flytting av spiller.
    Guang: Har øvd på sprites.
    Kim: Ansvarlig for design av rom, i tillegg til layout for tittelskjerm/pause.
    Leila: Ansvarlig for doc/report-X.md (5 % av karakteren).
    Videre:
    Prioritere oppgavene ut fra ny frist.
    Fullføre presentasjonsmateriell innen fredag.


