# CodeCatalysts Picasso Project

# Picasso

An application that allows the user to create expressions that
evaluate to colors and then eventually to images. This project implements the full picasso specification that can be found This application is mostly fully featured and ready for your use to generate cool images from expressions!

Javadocs for this project (Updated daily at 3:58 a.m) can be found [here](https://cs.wlu.edu/~sprenkles/cs209/projects/picasso/teams_docs/picasso-codecatalysts_docs/).

## Team Members

- Naka Assoumatine
- Jonathan Carranza Cortes
- Allison Hidalgo
- Gabe Hogan
- Sarah Lathrop

## Running Picasso

To run Picasso, run `picasso.Main`.

> [!NOTE]
> By default Picasso will run without the database experience. An error will appear, but can be safely ignored if the database is not being used. To run Picasso with the database, please add the database libary to your classpath. The database library can be found in the `lib` directory.

## Project Organization

`src` - the source code for the project

`conf` - the configuration files for the project

`images` - contains some sample images generated from Picasso. Some of the expressions for these images can be found in the `expressions` directory.

`demos` - similar to the `expressions` directory, but contains curated expressions by CodeCatalysts.

`lib` - contains the database library used in this project

## Extensions and Usage

**Allowing users to use up and down arrows to access history**

- Sometimes, user may have to press up and down arrow twice to change history. This is because of the cursor changing location in the text box.

**Generate Expressions Randomly**

- When the user clicks on the "random" button, a random expression is generated and set in the text box of Picasso. The user then presses the "evaluate" button to evaluate the random expression.
- The random expression is pseudorandom because every expression does not have an identical probability of being generated. Smaller expressions with less depth have a greater probability of being generated, but to the user the expression should appear random.
- This extension was implemented by generating a random expression depth, selecting a random operator or function, and then recursively selecting new operators or functions depending on the depth. Each time an operator, function, variable, or constant is generated, a String builder appends the element with its corresponding parenthesis to a string. Once the function is complete, this String is added to the text field for the user to see.

**Allow users to view multiple images at once in different windows**

- When users click on the "new panel" button, a new panel is opened on the screen.
- A second button was initially created to evaluate the expression in the text field in a new panel, but this button was later removed. The functionality was reused in the database extension so that users can click on a previously evaluated expression and open it in a new window.

**Database Extension**

- Implements a SQLite database to store expressions with their id, name, string, and evaluated time. The database used for the project's default runtime is `expression.db`. The databases used in testing are `test.db` and `badtest.db`, both are cleaned up _(deleted!)_ following the test suite and should never be used for application runtime.
- Implements a DatabaseViewer window that allows for the interaction with the database. The user can view all expressions in the database, edit existing expresssions (name & expression string), delete expressions, and open expressions in a new window (This utilizes the different windows extension).
- Expressions are added to the database on every seccuessful evalutation. They are not added if the expression is invalid (contains a ParseException or EvaluationException).
- Maintains backwards compatibility with the original Picasso application by allowing the user to run the application without the database. The database is only used/loaded if the project can detect that the database library is in the classpath. This also allows for the frame based history to still function without the database.

## The Picasso Language

### Allowed Expression Syntax

| **Expression**               | **Syntax**                                         | **Semantics**                                                                                                                                                                                                                           | **Examples**                              |
| ---------------------------- | -------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------- |
| **Constant**                 | `<any real number> [-]?[0-1]+(.[0-9]+)?`           | A real-valued number (note: to avoid potential ambiguity in parsing there should not be a space between the negative sign and the value).                                                                                               | `.87`, `1`, `-0.4`                        |
| **Color**                    | `[ constant, constant, constant ]`                 | An RGB color where each value can be any constant.                                                                                                                                                                                      | `[1, -1, 0.25]`, `[0.5, 1, 1]`            |
| **String**                   | `<any non-quote character between quotes>`         | An image of the given file name is read. This should return the nearest color from the image at the current (x, y) values. If the image cannot be read, an error is thrown.                                                             | `"foo.jpg"`, `"images/mickey.gif"`        |
| **Variable**                 | `<any alpha-numeric string> [a-zA-z]+[a-zA-Z0-9]*` | An expression represented by a word. The two variables `x` and `y` should always be defined to be the current coordinate in the image domain.                                                                                           | `a`, `bugs`, `q45`                        |
| **Assignment**               | `var = expr`                                       | Assigns an expression to a variable.                                                                                                                                                                                                    | `a = 1.0`, `bugs = "foo.png"`, `a = bugs` |
| **Unary Operator**           | `op expr`                                          | Prefixes an expression.                                                                                                                                                                                                                 | `!a`, `!(t + a * 0.1)`                    |
| **Binary Operator**          | `expr op expr`                                     | Combines two expressions into a single expression (in precedence order): `^` (exponentiate), `*` (times), `/` (divide), `%` (mod), `+` (plus), `-` (minus).                                                                             | `a + b`, `a / .2`, `a + 1.0 * c`          |
| **Unary Functions**          | `fun(expr)`                                        | A function that takes an expression as its single argument. Functions include: `floor`, `ceil`, `abs`, `clamp`, `wrap`, `sin`, `cos`, `tan`, `atan`, `exp`, `log`, `rgbToYCrCb`, `yCrCbToRGB`.                                          | `sin(a * b)`, `abs(x) - y / 0.4`          |
| **Multi-Argument Functions** | `fun(expr,...)`                                    | A function that takes two or more expressions as its arguments. Functions include: `perlinColor(expr, expr)`, `perlinBW(expr, expr)`, `imageWrap(string, x-coord-expr, y-coord-expr)`, `imageClip(string, x-coord-expr, y-coord-expr)`. | `perlinColor(x, y)`, `perlinBW(y, x + x)` |
| **No-Argument Function**     | `function()`                                       | A function that takes no arguments. For example, `random()` returns a random color (the same color, no matter what value of x and y are used to evaluate it, each time it is called).                                                   | `random()`                                |
| **Parentheses**              | `(expr)`                                           | Raises an expression's precedence.                                                                                                                                                                                                      | `(a + b) * 0.3`, `!(bugs - 0.1)`          |
| **Single-line comments**     | `// Comment`                                       | Everything after the `//` on the same line is ignored (like in C and Java).                                                                                                                                                             | `// Example comment`                      |

### Operators Precedence

Listed from highest to lowest:

1. `()` parentheses
2. `!` unary operators
3. `^` exponentiation
4. `*, /, %` multiplicative operators
5. `+, -` additive operators
6. `=` assignment

## Code Base History

This code base originated as a project in a course at Duke University. The professors realized that the code could be designed better and refactored. This code base has some code leftover from the original version. The project was further modified by Prof. Sarah Sprenkle for use in CSCI 209 at Washington and Lee University. The project was then further modified by the CodeCatalysts team to add the required features and extensions to their final project.
