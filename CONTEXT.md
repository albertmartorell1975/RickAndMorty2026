# Context & Glossary

This document serves as the authoritative glossary and domain language reference for **RickAndMorty2026**.
The MVP would be to have an app where you can review a list of all characters and retrieve information about
the selected character
Have in mind:
- You can use any library out there but use them wisely, each third party library added is a dependency
  in your code.
- Deliver something. We want to review how did you structure the project, if you apply things like
  SOLID…. This app will let us the chance to talk about actual code.
- We are a very image oriented company, UX is important.
- You can use any of the endpoints in https://rickandmortyapi.com/, we will talk about performance
  probably…
- Shine at some aspect. If you think your strongs are about code, we will spend most of the time talking
  about code despite the app won’t run.
- If you already have another app develop we can check, don’t spend time on this and send it to us. We
  will review it using the same criteria.
  Extras:
- Be creative!
- Use Jetpack compose
- Cache images coming from network to improve performance
- Error handling
- Response caching
- Implement tests
- Possibility to filter or search…

## Domain Terms

- **Character**: A fictional entity (human, alien, robot, creature) in the Rick and Morty universe. Contains attributes such as name, status (Alive, Dead, Unknown), species, gender, image URL, origin, and current location.
- **Episode**: A specific broadcast episode of the series. Contains name, air date, episode code (e.g., S01E01), and a list of characters appearing in it.
- **Location**: A planet, dimension, space station, or place in the universe. Contains name, type, dimension, and resident characters.
- **Favorite**: A local bookmark marking a character as saved by the user for quick access.
- **Repository**: Data layer abstraction coordinating between remote API (Rick and Morty GraphQL/REST API) and local Room persistence.
