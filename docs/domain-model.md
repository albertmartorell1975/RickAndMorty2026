# Domain Model

## Core Entities

### Character
- `id`: Int (Primary Key)
- `name`: String
- `status`: String (Alive, Dead, Unknown)
- `species`: String
- `type`: String
- `gender`: String
- `image`: String (URL)
- `origin`: LocationRef
- `location`: LocationRef
- `episodeUrls`: List<String>
- `isFavorite`: Boolean

### Episode
- `id`: Int (Primary Key)
- `name`: String
- `airDate`: String
- `episode`: String (e.g., S01E01)
- `characterUrls`: List<String>

### Location
- `id`: Int (Primary Key)
- `name`: String
- `type`: String
- `dimension`: String
- `residentUrls`: List<String>

## Relationships
- A `Character` belongs to an `Origin` location and a current `Location`.
- A `Character` appears in multiple `Episode`s.
- An `Episode` features multiple `Character`s.
- A `Location` has multiple resident `Character`s.
