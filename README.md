# CodeCatalysts
Authors: Naka Assoumatine, Jonathan Carranza Cortes, Allison Hidalgo, Gabe Hogan, and Sarah Lathrop


# Picasso

An application that allows the user to create expressions that
evaluate to colors and then eventually to images.

The given code base is a good start, but it is sparsely documented
(document each method/part after you understand what it's doing), and,
as your application grows, you may need to refactor.

See the specification for Picasso on the course web site.

## Running Picasso

To run Picasso, run `picasso.Main`

## Project Organization

`src` - the source code for the project

`conf` - the configuration files for the project

`images` - contains some sample images generated from Picasso. Some of the expressions for these images can be found in the `expressions` directory.

## Extensions and Usage
**Allowing users to use up and down arrows to access history**

- Sometimes, user may have to press up and down arrow twice to change history. This is because of the cursor changing location in the text box.

**Generate Expressions Randomly**

- When the user clicks on the "random" button, a random expression is generated and set in the text box of Picasso. The user then presses the "evaluate" button to evaluate the random expression.
- The random expression is pseudorandom because every expression does not have an identical probability of being generated. Smaller expressions with less depth have a greater probability of being generated, but to the user the expression should appear random.
- This extension was implemented by generating a random expression depth, selecting a random operator or function, and then recursively selecting new operators or functions depending on the depth. Each time an operator, function, variable, or constant is generated, a String builder appends the element with its corresponding parenthesis to a string. Once the function is complete, this String is added to the text field for the user to see.

**Other extensions here**

## Code Base History

This code base originated as a project in a course at Duke University.  The professors realized that the code could be designed better and refactored.  This code base has some code leftover from the original version.
