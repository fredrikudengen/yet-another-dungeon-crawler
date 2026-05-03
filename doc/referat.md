# Referat for møter

---

## Møte 1 - 05.02.26

**Tilstede:** Alle (Leila, Fredrik, Njål, Guang, Kim)

### Agenda
- Idémyldring og valg av spilltype
- Gjennomgang av krav
- Fordeling av roller og arbeidsoppgaver
- Planlegging av videre arbeid

### Beslutninger
- Gruppen valgte å utvikle et **dungeon crawler-spill**
- Begrunnelse: fleksibelt og enkelt å utvide med ny funksjonalitet
- Gikk gjennom kravene og etablerte en felles forståelse
- Diskuterte arbeidsfordeling og hva som regnes som jevn innsats

### Roller og ansvar
| Navn    | Ansvar                  |
|---------|-------------------------|
| Leila   | Gitmaster, referatansvarlig |
| Fredrik | Spillogikk              |
| Njål    | Spillogikk              |
| Guang   | Sprites og design       |
| Kim     | Layout (tittelskjerm, pausemeny)|
| Alle    | Skriver tester for egen kode |

### Verktøy
- Trello for oppgaveoversikt
- Messenger for kommunikasjon

### Arbeidsoppgaver til neste møte
- Alle: Sette seg inn i Git og libGDX
- Alle: Klargjøre utviklingsmiljø

---

## Møte 2 - 10.02.26

**Tilstede:** Alle

### Oppfølging fra forrige møte
- Git og libGDX: Vi brukte litt tid sammen for å snakke om Git og hvordan det fungerer (branches, commits)
- Utviklingsmiljø: Alle hadde lastet ned Java 25 og kunne kjøre Main

### Agenda
- Offisiell oppstart av prosjektet
- Sette opp delt Git-repository
- Teste branching og merge

### Beslutninger
- Bruke dev-branch som hovedgren
- Strukturere prosjektet etter MVC-arkitektur

### Arbeidsoppgaver til neste møte
- Alle: Opprette grunnstruktur i henhold til MVC
- Alle: Ta i bruk Trello
- Alle: Tenke på tema, atmosfære, navn til spillet
---

## Møte 3 - 12.02.26

**Tilstede:** Alle

### Oppfølging fra forrige møte
- Grunnstruktur: Lagt til arkitektur for menyer, controller og model
- Trello: Skrevet inn ideer til fremtidig funksjonalitet (fiendetyper, powerups)

### Agenda
- Status etter endringer i veiledningsplan
- Justere plan for presentasjon

### Situasjon
Endringer i veiledning førte til kortere frist på presentasjon. Planene måtte justeres raskt.

### Statusoppdatering
| Navn          | Status             |
|---------------|--------------------|
| Fredrik, Njål | Spillerbevegelse   |
| Guang         | Sprites            |
| Kim           | Romdesign og UI    |
| Leila         | Rapport og testing |

### Beslutninger
- Fokus på synlig funksjonalitet fremfor komplett implementasjon
- Prioritere det som kan vises i presentasjon

### Arbeidsoppgaver til neste møte
- Fredrik, Njål: Opprette Player klasse og koble sammen med input
- Guang: Lage flere Player sprites, tenke ut fiendesprites
- Kim: Lage tile og liv design
- Leila: Forberede rapport til neste presentasjon/møte med gruppeleder,
  skrive tester for Player når implementert

---

## Arbeidsøkt - uke 8-9 (ca. 16.-01.03.26)

**Deltakere:** Njål og Fredrik jobbet med Player og Controller, Guang og Kim sammarbeidet om grafikk/design,
Leila skrev rapport til presentasjon og testfiler

### Fokus
- Spillerbevegelse
- Rendering
- Prosjektstruktur

### Resultater
- Player angrep ble lagt til
- Enemy klasser ble lagt til
- Testfiler for attack, player og controller
- TitleScreen og PauseScreen lagt til
- Sprite for attack og FastEnemy lagt til

### Utfordringer og erfaringer
- Ujevn progresjon i gruppen
- Behov for bedre koordinering via Trello
  - Oppdater hyppigere, og ta i bruk aktivt

---

## Arbeidsmøte - slutten av februar

**Dato:** 26.02.26
**Deltakere:** Njål, Guang, Kim

### Fokus
- Integrasjon av komponenter
- Sammenkobling av spiller og verden

### Resultater
- Spilleren kunne bevege seg i verden
- Arbeid med fiender startet

### Utfordringer
- Uklar struktur i koden på enkelte punkter
- Diskusjon rundt arkitektur - Lage klart skille mellom ulike komponenter (MVC) og redusere dependencies

---

## Møte - 05.03.26

**Tilstede:** Njål, Leila, Kim, Guang, Fredrik

### Oppfølging fra forrige møte
- Refaktorering av kode

### Agenda
- Status og videre plan

### Statusoppdatering
| Komponent        | Status          | Ansvarlig            |
|------------------|-----------------|----------------------|
| Spillerbevegelse | Fungerer        | Fredrik, Njål        |
| Fiender          | Under utvikling | Fredrik, Njål, Leila |
| UI               | Påbegynt        | Kim, Guang           |
| Lyd              | Påbegynt        | Kim                  |

### Beslutninger
- Fokus på spillbar prototype
- Fortsette arbeid i separate branches

### Arbeidsoppgaver til neste møte
- Klargjøre funksjonalitet for ét rom:
  - Spiller, fiender, grafikk, lyder
- Videreutvikle player-fiende sammenheng

---

## Arbeidsperiode - mars

**Periode:** Hele mars

### Fokus
- Ferdigstille kjernefunksjonalitet: angrep, skade, livssystem
- Integrasjon av lyd og grafikk

### Resultater
- Oppdatert pom.xml
- Refaktorert kode så mindre dependencies (private-public, getters-setters, interfaces)
- Lagt til lyder
- Lagt til tester for store deler av funksjonaliteten
- Lagt til Game over og HelpScreen

### Erfaringer
- Bedre samarbeid enn tidligere perioder
- Mer effektiv bruk av Git
- Noe merge-konflikter - Håndtert av Leila ved kommunikasjon med kode-ansvarlig

---

## Møte - 09.04.26

**Tilstede:** Kim, Fredrik, Leila, Guang, Njål

### Oppfølging fra forrige møte
- Mål om MVP i ét rom fullført
- Fokus på testing lykkes, god coverage

### Agenda
- Status mot nest siste frist
- Gjennomgang av testdekning
- Håndtering av merge-konflikt

### Statusoppdatering - testing
| Komponent     | Dekning | Ansvar     |
|---------------|------|------------|
| Model         | ca. 90% |            |
| Controller    | 100% |            |
| Dør/romlogikk | Mangler | Guang, Kim |

### Hendelser
- Merge-konflikt mellom test-branch og dev - løst i fellesskap

### Arbeidsoppgaver til neste møte
- README opphavsrett: Kim
- Tester for dørlogikk: Kim
- Tester for romlogikk: Guang
- Fokus på dekning av krav (README, rapport, god kodestil)

---

## Møte - 16.04.26

**Tilstede:** Kim, Fredrik, Leila, Guang, Njål

### Oppfølging fra forrige møte
- README opphavsrett: oppdatert
- Tester for dør/romlogikk: god dekning
- Refaktorering av store deler av koden, klart skille mellom komponentene (MVC) og mindre dependencies

### Bakgrunn
Møte etter samtale med gruppeleder/veileder.

### Agenda
- Forberede presentasjon
- Videreutvikle rapport

### Beslutninger
- Legge til mer refleksjon i rapporten
- Tydeliggjøre sammenheng mellom krav, implementasjon og prosess
- Leila hovedansvar for PowerPoint
-
### Arbeidsoppgaver frem mot innlevering
- Gå gjennom krav i henhold til prosjektet
- Finpusse tekniske detaljer (attack-range, variabel-navn)
- Lage PowerPoint

---

## Generell refleksjon

### Hva fungerte bra
- Konsistente møter ga struktur og fremdrift
- Selvstendig arbeid mellom møtene fungerte godt
- Kommunikasjon via Messenger var effektiv
- Git-bruken ble bedre underveis i prosjektet

### Forbedringspunkter
- Hyppigere oppdatering av Trello
- Bedre synkronisering i perioder med høy aktivitet
- Bedre rutiner for commit-meldinger: mer konsistente og presise, med enhetlig språk (norsk eller engelsk)
