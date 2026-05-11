# Domain: ui
## Change: fix-dark-mode-colors

### ADDED
- **[REQ-ADD-001]** The system MUST include missing dark theme color definitions (`dark_surfaceVariant`, `dark_onSurfaceVariant`, `dark_onTertiary`) in `Color.kt` to maintain symmetry with the light theme structure.
  - **Scenario 1:** Given the application in dark mode, When UI components request `surfaceVariant` colors, Then they correctly fall back to `dark_onPrimary` ensuring clear input field contrast.

### MODIFIED
- **[REQ-MOD-001]** The dark mode background MUST be adjusted from magenta (`0xFF9A0963`) to standard dark grey (`0xFF121212`) in `Color.kt`.
  - **Scenario 1:** Given the user switches to dark mode, When viewing any screen, Then the background color is dark grey, avoiding eye strain and following standard design practices.
- **[REQ-MOD-002]** The dark mode surface MUST be adjusted from dark pink (`0xFF77084D`) to elevated dark grey (`0xFF1E1E1E`).
- **[REQ-MOD-003]** The dark mode primary color MUST be updated to a lighter brand purple (`0xFFB78AF7`) instead of white (`0xFFFFFFFF`).
- **[REQ-MOD-004]** The dark mode onPrimary color (used for standard input backgrounds) MUST be adjusted from magenta (`0xFFB90063`) to dark slate/grey (`0xFF2C2B2B`).
- **[REQ-MOD-005]** The dark mode scrim MUST be adjusted from amber (`0xFFFFC107`) to standard black (`0xFF000000`).
- **[REQ-MOD-006]** `Theme.kt` SHALL map dark mode color schemes dynamically to their proper dark variables (`dark_onTertiary`, `dark_surfaceVariant`, `dark_onSurfaceVariant`) instead of redirecting directly to primary/outline variables, achieving structural consistency with light mode.

### REMOVED
- **[REQ-REM-001]** The system MUST NOT directly assign `dark_primary` to `onTertiary` inside `Theme.kt`.
  - **Reason:** To adhere to clean code practices (`clean-code`) by maintaining responsibility within the color definition layer.
