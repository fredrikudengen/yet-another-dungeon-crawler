# Rapport – innlevering 4
**Team:** *Sprite Zero* – *Kim Harry Huynh, Guangwei Zhang, Fredrik Udengen, Njål Nordal, Leila Lu*...


## 1. Team og roller

Prosjektleder / Developer – Fredrik Udengen
Kundekontakt / Developer – Njål Nordal
Gitmaster / Developer – Leila Lu
Assetansvarlig / Developer – Guangwei Zhang
Assetansvarlig / Developer – Kim Harry Huynh

Refleksjon rundt roller

Rollene i teamet har i stor grad fungert etter hensikt. Gitmaster har hatt ansvar for merges og konflikthåndtering, noe som har bidratt til struktur i kodebasen. Samtidig har vi erfart at denne rollen til tider kan bli en flaskehals dersom flere pull requests kommer samtidig.

De øvrige rollene har i praksis vært noe flytende, da alle også har jobbet som utviklere. Dette har fungert bra for fremdriften, men gjør at ansvarsområdene ikke alltid er like tydelige.

Vi vurderer derfor å:

tydeliggjøre ansvar per rolle
eventuelt rotere roller for bedre kunnskapsdeling 
diskutere når en git-konflikt oppstår for å unngå flere slike tilfeller

## 2. Konsept

Spillet er inspirert av Binding of Isaac.

Spillfigur som kan bevege seg i 8 retninger
Todimensjonal verden med blokker:
Vegger (ikke gjennomtrengelige)
Fritt bevegelsesområde
Spilleren kan angripe fiender
Fiender beveger seg og skader spilleren ved kontakt
Spilleren får poeng ved å drepe fiender
Spillet handler om å overleve og bekjempe fiender

Verden:

Bygget opp av blokker med fast størrelse
Større enn skjermen (scrolling)
Inneholder power-ups (sverd og fart)

## 3. Kjøring

Kompileres med:

mvn package

Kjøres med:

java -jar target/skeleton-1.0-SNAPSHOT-fat.jar

Krever Java 25

## 4. Teamprosess og samarbeid

Vi har brukt Kanban som arbeidsmetodikk, med Trello for å holde oversikt over oppgaver. Arbeidet har blitt organisert i møter hvor vi har satt mål, fordelt oppgaver og evaluert progresjon.

Kommunikasjon har foregått via Messenger, noe som har fungert effektivt for raske avklaringer og oppdateringer.

Git og utviklingsflyt

Vi har jobbet med:

egne branches per oppgave
merge requests
Gitmaster som håndterer merges

Dette har gitt god struktur, men vi har opplevd enkelte merge-konflikter, noe som viser behov for:

- hyppigere commits
- bedre koordinering
- Samarbeid og parprogrammering

Samtidig har vi rotert litt med hvem som er git master for å fordele lærdom. 

Samarbeidet i gruppen har fungert godt. Vi har hatt lav terskel for å stille spørsmål og hjelpe hverandre.

Parprogrammering har blitt brukt ved mer komplekse oppgaver, noe som har:

forbedret kodekvalitet
økt felles forståelse

Dette kunne likevel vært brukt mer systematisk til å identifisere hvilke oppgaver som var mest komplekse, noe som ville gjort det lettere å planlegge møter.

## 5. Progresjon

Vi har implementert:

Rom (grunnleggende spillverden)
Spillerbevegelse
Angrep
Interaksjon med fiender
Skade og livssystem
Grafikk (sprites)
Startmeny
Lydeffekter og bakgrunnsmusikk
UI (hjerter/liv)
Power-ups (sverd og fart)
Tester for deler av funksjonaliteten

MVP-kravene er i stor grad oppfylt.

## 6. Spesifikasjon

Vi lager et 2D-spill der spilleren navigerer gjennom rom, unngår fiender og bekjemper dem for å samle poeng. Målet er å overleve så lenge som mulig og oppnå høyest mulig score.

## 7. MVP-krav
Statisk spilleverden
Spiller i spillverden
Bevegelse i åtte retninger
Interaksjon med terreng og fiender
Fiender med oppførsel
Poengsystem
Spilleren kan dø
Start-/game over-skjerm

## 8. Brukerhistorier med akseptansekriterier

Kamera

Som spiller vil jeg at kameraet skal følge karakteren min

Akseptansekriterier:

Kamera følger spilleren i alle retninger
Spilleren er alltid synlig
Verden scroller jevnt
Power-ups

Som spiller vil jeg kunne finne power-ups

Akseptansekriterier:

Power-ups finnes i verden
Spilleren kan plukke dem opp
De gir effekt (fart/skade)
Livssystem

Som spiller vil jeg kunne se hvor mange liv jeg har

Akseptansekriterier:

Spilleren har liv representert med hjerter
UI oppdateres ved skade
Game over ved 0 liv
Lyd

Som spiller vil jeg ha lydeffekter

Akseptansekriterier:

Lyd ved angrep
Lyd ved skade
Bakgrunnsmusikk spilles


## 9. Kjente bugs og begrensninger
Spilleren kan miste flere liv raskt ved kontinuerlig kontakt med fiender


## 10. Kode og tekniske valg

Koden er delt opp i komponenter for spiller, fiender, verden og rendering. Dette har gjort det mulig å utvikle funksjonalitet parallelt.

Vi har prioritert fungerende funksjonalitet fremfor perfekt arkitektur. Dette har ført til noe tett kobling mellom komponenter.

Vurdering:
Koden er lesbar og mulig å utvide
Noe behov for refaktorering
Testing:
Tester skrevet for deler av systemet
Mangler dekning for:
power-ups
lyd
UI

## 11. Prioritering videre

Neste steg:

Flere rom og dører
Flere fiendetyper
Bedre testing
Forbedret UI og brukeropplevelse


## 12. Retrospektiv
Hva fungerte bra:
Klar progresjon mot MVP
God arbeidsfordeling
Strukturert bruk av Git

Hva fungerte mindre bra:
Trello ikke alltid oppdatert
Noe overlapp i arbeid
Lite systematisk parprogrammering

Forbedringstiltak:
Oppdatere Trello jevnlig
Bedre koordinering før utvikling
Planlagte parprogrammeringsøkter


## 13. Møteoppsummering

Vi har hatt jevne møter hvor vi har:

satt mål
fordelt oppgaver
diskutert utfordringer
evaluert progresjon

Møtene har fungert bra, men oppfølging mellom møtene kan forbedres. Derimot har det vært veldig lett å oppdatere teamet på messenger, og dersom alle er gode venner fra før var det ikke vanskelig å møtes spontant. 

## 14. Refleksjon

Vi har i stor grad nådd målene for MVP og fått på plass sentral funksjonalitet som bevegelse, kamp, lyd og UI. Dette viser at planleggingen har vært realistisk og overkommelig.

Kanban og Trello har gitt god oversikt, men kunne vært brukt mer aktivt. Git har fungert bra, men krever bedre koordinering og rutiner for presise navn.

Samarbeidet i gruppen har vært godt, og parprogrammering har hatt positiv effekt der det er brukt. I tillegg har gruppechatten vært effektiv for å videreutvikle spillet mellom møtene.

Vi har etablert et solid grunnlag for videre utvikling, og tar med oss erfaringene videre for å forbedre både struktur og arbeidsflyt.