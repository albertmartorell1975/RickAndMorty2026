# Risks and Open Questions

## Risks
1. **API Rate Limiting / Downtime**: External Rick and Morty API could experience throttling or outages.
   - *Mitigation*: Implement offline-first caching via Room database.
2. **Image Loading Latency**: Large lists with heavy images can cause stuttering.
   - *Mitigation*: Use Coil with disk/memory caching and placeholder thumbnails.
3. **Pagination Scalability**: Loading large datasets of characters.
   - *Mitigation*: Implement Paging 3 or efficient chunked loading.

## Open Questions
- Should episodes and locations be included in the initial MVP release or deferred to Phase 2?
  - *Resolution*: Deferred to Phase 2; MVP focuses purely on Character List and Character Detail.
