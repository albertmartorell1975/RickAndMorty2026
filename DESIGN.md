---
name: Citadel Portal
colors:
  surface: '#0f131e'
  surface-dim: '#0f131e'
  surface-bright: '#353945'
  surface-container-lowest: '#0a0e19'
  surface-container-low: '#171b27'
  surface-container: '#1b1f2b'
  surface-container-high: '#262a36'
  surface-container-highest: '#313441'
  on-surface: '#dfe2f2'
  on-surface-variant: '#c3c9b3'
  inverse-surface: '#dfe2f2'
  inverse-on-surface: '#2c303c'
  outline: '#8d937f'
  outline-variant: '#434938'
  surface-tint: '#9fd754'
  primary: '#b2eb65'
  on-primary: '#203600'
  primary-container: '#97ce4c'
  on-primary-container: '#355500'
  inverse-primary: '#426900'
  secondary: '#54d7f0'
  on-secondary: '#00363f'
  secondary-container: '#00aac2'
  on-secondary-container: '#003942'
  tertiary: '#00f998'
  on-tertiary: '#00391f'
  tertiary-container: '#00d984'
  on-tertiary-container: '#005933'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#baf46d'
  primary-fixed-dim: '#9fd754'
  on-primary-fixed: '#112000'
  on-primary-fixed-variant: '#314f00'
  secondary-fixed: '#a4eeff'
  secondary-fixed-dim: '#54d7f0'
  on-secondary-fixed: '#001f25'
  on-secondary-fixed-variant: '#004e5a'
  tertiary-fixed: '#56ffa7'
  tertiary-fixed-dim: '#00e38a'
  on-tertiary-fixed: '#002110'
  on-tertiary-fixed-variant: '#00522f'
  background: '#0f131e'
  on-background: '#dfe2f2'
  surface-variant: '#313441'
  surface-base: '#1A1E29'
  surface-card: '#202428'
  surface-elevated: '#262C3A'
  surface-input: '#151821'
  text-primary: '#FFFFFF'
  text-secondary: '#9E9E9E'
  text-tertiary: '#6C727F'
  status-alive: '#55CC44'
  status-dead: '#D63D2E'
  status-unknown: '#9E9E9E'
  accent-orange: '#FF9800'
  border-subtle: rgba(255, 255, 255, 0.08)
  border-glow: rgba(151, 206, 76, 0.35)
typography:
  display-lg:
    fontFamily: Roboto Flex
    fontSize: 32px
    fontWeight: '800'
    lineHeight: 38px
    letterSpacing: -0.03em
  display-lg-mobile:
    fontFamily: Roboto Flex
    fontSize: 28px
    fontWeight: '800'
    lineHeight: 34px
    letterSpacing: -0.025em
  headline-lg:
    fontFamily: Roboto Flex
    fontSize: 24px
    fontWeight: '700'
    lineHeight: 30px
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Roboto Flex
    fontSize: 20px
    fontWeight: '700'
    lineHeight: 26px
    letterSpacing: -0.015em
  headline-sm:
    fontFamily: Roboto Flex
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
    letterSpacing: -0.01em
  body-lg:
    fontFamily: Roboto Flex
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Roboto Flex
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  body-sm:
    fontFamily: Roboto Flex
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
  label-lg:
    fontFamily: Roboto Flex
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 18px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Roboto Flex
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Roboto Flex
    fontSize: 11px
    fontWeight: '700'
    lineHeight: 14px
    letterSpacing: 0.04em
  code-sm:
    fontFamily: Roboto Flex
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-sm: 0.75rem
  gutter-lg: 1.25rem
  margin: 1rem
  margin-sm: 0.75rem
  margin-lg: 1.5rem
  space-2xs: 0.125rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
  space-2xl: 3rem
---

## Brand & Style

This design system translates the universe of multidimensional exploration into an ergonomic, modern mobile experience. Balancing utility-first API documentation discipline with vibrant sci-fi fandom, the visual identity pairs high-contrast dark space neutrals with radioactive green and interdimensional cyan accents.

The design movement is **Modern Neon-Minimalist**: deep cosmic surface layers (`#0f131e` base, `#1b1f2a` / `#262C3A` containers) layered beneath crisp content cards, punctuated by razor-sharp radioactive accents and glowing visual indicators. The aesthetic prioritizes density and scan-efficiency for mobile catalog navigation while maintaining the kinetic edge of dimensional exploration. UI feedback feels responsive, technical, and electric without devolving into cluttered retro kitsch.

## Colors

The color architecture is anchored in pitch-dark cosmic depths, calibrated specifically to reduce OLED battery draw on mobile devices and emphasize high-chroma character artwork.

- **Primary (`#97CE4C`)**: The canonical portal fluid green. Used for high-priority interactive touchpoints, selected navigation states, active toggles, and decisive call-to-action buttons.
- **Secondary (`#11B0C8`)**: Dimension cyan. Serves as secondary action tinting, episode indicators, origin badges, and filter accents.
- **Tertiary (`#00FF9C`)**: Hyper-electric mint. Reserved strictly for subtle neon glow rings, active focus borders, and telemetry badges.
- **Neutral (`#0F131E` to `#262C3A`)**: A stepped hierarchy of rich, cool-tinted dark surfaces providing contrast against standard pure blacks.
- **Status Accents**: Explicitly encoded tri-state indicators for character survival:
  - `status-alive` (`#55CC44`): Radiant lifeform green.
  - `status-dead` (`#D63D2E`): Crimson alert red.
  - `status-unknown` (`#9E9E9E`): Indeterminate quantum grey.

## Typography

Typography is set in **Roboto Flex** across all roles, providing a mechanical, adaptable, and highly engineered aesthetic suited for interdimensional data catalogs. The variable parametric flexibility of Roboto Flex ensures crisp character rendering, precise tabular alignment, and immediate readability across dense lists and technical schema viewports.

- **Headlines**: Tightened negative tracking with heavy weights (700/800) in high-contrast white (`#FFFFFF`) to anchor section headers and title displays.
- **Metadata and Secondary Text**: Standard body weights rendered in muted cool grey (`#9E9E9E`) prevent visual fatigue across dense character attribute fields (Origin, Location, Episode counts).
- **Labels & Micro-copy**: Employs uppercase tracking (`label-sm`) for dimensional status badges, schema keys, and filter tallies.

## Layout & Spacing

This design system uses a 4-column fluid mobile grid anchored by an 8px rhythmic spatial scale, expandable to an 8-column layout on medium/tablet viewports and 12-column on desktop admin screens.

- **Margins & Gutters**: Outer mobile canvas margins are strictly `1rem` (16px), downscaling to `0.75rem` (12px) on ultra-compact devices (<360px). Gutters maintain a consistent `1rem` separation between cards.
- **Rhythm Rules**:
  - Horizontal padding inside cards is `1rem`, vertical padding is `0.875rem`.
  - Icon-to-text metadata pairings use `space-xs` (4px) to `space-sm` (8px).
  - Stacked vertical content groups maintain `space-md` (16px) separation.
  - Section blocks utilize `space-xl` (32px) margins to preserve breathing room between feed carousels and grid switches.

## Elevation & Depth

Visual hierarchy on dark galactic surfaces is established through tonal stacking, ultra-soft tinted borders, and selective radioactive luminescence rather than heavy black drop shadows.

- **Level 0 (Canvas Base)**: `#0F131E` / `#1A1E29` — Ground-level infinite canvas with zero shadow.
- **Level 1 (Card & Row Layer)**: `#202428` — Supported by a subtle low-contrast stroke: `1px solid rgba(255, 255, 255, 0.06)`. Diffuse shadow: `0 4px 16px rgba(0, 0, 0, 0.4)`.
- **Level 2 (Active/Floating Cards & Modals)**: `#262C3A` — Elevated layer for sheets and dialogs. Outlined with `1px solid rgba(255, 255, 255, 0.1)`. Shadow: `0 8px 24px rgba(0, 0, 0, 0.55)`.
- **Portal Glow Elevation**: Applied to primary CTA buttons, active chips, and favorited states:
  - Default: `0 0 12px rgba(151, 206, 76, 0.25)`
  - Active / Focus: `0 0 20px rgba(0, 255, 156, 0.45), inset 0 0 4px rgba(255, 255, 255, 0.2)`
- **Glass App Bar / Floating Dock**: `rgba(26, 30, 41, 0.85)` with `16px` backdrop-filter blur and bottom stroke `1px solid rgba(255, 255, 255, 0.08)`.

## Shapes

The design system enforces a **roundedness index of 2**, establishing consistent, ergonomic geometry that softens technical data:

- **Base Radius (`rounded-md`, 8px / 0.5rem)**: Applied to input fields, buttons, toast containers, and episode pills.
- **Large Radius (`rounded-lg`, 16px / 1rem)**: Enforced on all character profile cards, image containers, modal bottom sheets, and hero banners.
- **Extra Large Radius (`rounded-xl`, 24px / 1.5rem)**: Segmented controls and filter search bars.
- **Pill / Circular (`rounded-full`)**: Character status indicator dots (8px circular), filter tag chips, avatar images, and floating action buttons.

## Components

### Character Cards
- **Geometry & Fill**: Bound by 16px (`rounded-lg`) corner radii, filled with `#202428`, framed in `1px solid rgba(255, 255, 255, 0.06)`.
- **Image Treatment**: Left-aligned (horizontal list card) or top-docked (vertical grid card) with seamless 16px corner-matched clipping.
- **Status Indicator**: Composed of an 8px circular dot with a soft radial glow (`box-shadow: 0 0 6px currentcolor`). Text labels immediately follow (e.g., `Alive - Alien`) using `label-md` in `#FFFFFF`.
- **Interaction**: Press scales the card down to `0.98` with an instantaneous border transition to `rgba(151, 206, 76, 0.5)`.

### Buttons
- **Primary CTA**: Background `#97CE4C`, text `#1A1E29` (bold 700), 8px border radius, accompanied by `0 0 16px rgba(151, 206, 76, 0.35)` glow. Active press shifts to `#00FF9C`.
- **Secondary Ghost**: Background `rgba(17, 176, 200, 0.1)`, text `#11B0C8`, border `1px solid rgba(17, 176, 200, 0.4)`.
- **Icon Action (Favorite Heart)**: Circular 36px touch target. Unfavorited: `#9E9E9E` icon on `rgba(255, 255, 255, 0.05)` backing. Favorited: `#00FF9C` active icon fill with pulse micro-animation.

### Chips & Filter Pills
- **Container**: Fully rounded (`rounded-full`), height 32px, horizontal padding `12px`.
- **Inactive**: Surface `#262C3A`, text `#9E9E9E`, border `1px solid rgba(255, 255, 255, 0.05)`.
- **Active**: Background `rgba(151, 206, 76, 0.15)`, text `#97CE4C`, border `1px solid #97CE4C`.

### Search Bar
- **Surface**: Height 48px, background `#151821`, shape `rounded-xl`, framed with `1px solid rgba(255, 255, 255, 0.1)`.
- **Content**: Left portal-green search icon, `#9E9E9E` placeholder text, right clear-action trigger. On focus: transitions border to `#00FF9C` with a `0 0 8px rgba(0, 255, 156, 0.2)` inner glow.

### Checkboxes & Radios
- **Checkbox**: 20px box, `rounded-xs` (4px), checked state `#97CE4C` fill with `#1A1E29` checkmark.
- **Radio**: 20px circle, checked state displays `#97CE4C` outer border with a 10px centered inner solid disc.

### Toast & Cache Feedback
- **Format**: Floating mobile snackbar dock, `rounded-md` (8px), background `#262C3A`, left decorative status stripe (3px width, `#11B0C8` for cache updates, `#55CC44` for offline sync, `#D63D2E` for network drop).
- **Typography**: Label `13px` medium text paired with a monospace response latency or page cache stamp.
  Android Resource Conversion Policy
  When implementing UI features that consume graphic assets:

## Android Resource Conversion Policy
When implementing UI features that consume graphic assets:

Target Folder: Only raw PNG/JPEG graphic assets located in docs/ui/<feature-id>/resources/ (or docs/ui/resources/) MUST be converted into .webp format and placed in app/src/main/res/drawable/ic_.webp.
Exclusion: Full screen mockups (docs/ui/<feature-id>/screens/screen.png or docs/ui/screens//screen.png) are visual reference layout guides ONLY and MUST NOT be converted into drawable resources.