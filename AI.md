Where is was used: Across multiple comits starting from A-CheckStyle
Prompt: Here is a list of rules to follow for the code base. Please check and flag out if any.
    Naming
    Packages: all lowercase; no edu.nus.*. Use project/group name root.
    Classes/enums: PascalCase nouns.
    Methods: camelCase verbs.
    Variables: camelCase.
    Constants: SCREAMING_SNAKE_CASE.
    Boolean names start with is/has/was/can/should.
    Collections use plural names.
    Acronyms not fully uppercase (e.g., openDvdPlayer, not openDVDPlayer).
    English names only.
    Long scope → long names; small scope → short names (i, j, k allowed for loops).
    Associated constants share prefix.
    Test methods may use method_scenario_expectedBehavior.
    Layout
    4-space indentation (no tabs).
    Max line length 120 (prefer ≤110).
    Wrapped lines indented +8 spaces.
    Break lines after commas, before operators/dots.
    K&R braces style.
    Always use braces for loops and conditionals.
    One blank line between logical code blocks.
    Proper whitespace around operators, commas, keywords.
    Statements
    Every class must be in a package.
    Explicit imports only (no *).
    Consistent import ordering.
    Arrays declared as int[] a, not int a[].
    Variables declared in smallest scope and initialized where declared.
    Class variables not public (except constants or data classes).
    switch uses explicit // Fallthrough when intentional.
    Comments & Javadoc
    English only.
    Javadoc required for all public classes and public methods (except getters/setters, overrides, tests).
    Javadoc format:
    First sentence starts with Returns/Creates/Adds/etc.
    Include @param, @return, @throws when useful.
    Comments properly indented.
Output: Highlights some portion of the code where it violtated the style that both the Style checker and I missed out.
Rationale: Sometimes style checker and I may not be able to catch all the coding mistakes, good to have extra layer of checks 


Where it was used: A-JavaFX / FXML setup
Prompt: How do I wire up an FXML controller in JavaFX so that the controller class has access to UI components defined in the FXML file?
Output: (Explanation of @FXML annotation, FXMLLoader, and how the controller is linked to the FXML file via fx:controller)
Rationale: FXML and its controller binding mechanism were new to me, and reading the documentation alone was unclear. AI helped me understand the wiring quickly so I could implement the UI without spending too long on setup.


Where it was used: A-JUnit increment
Prompt: I have written a few test cases to test behaviour of my Task Manager. Give me some other ideas for unit test that I could write to help cover more scenarios. 
Output: (A very long extensive list of possible test cases)
Rationale: While human logic can catch most cases, I do not know what I do not know, and hence it is better the AI gives ideas of test cases I could implement so that I can cover more bases. 
  